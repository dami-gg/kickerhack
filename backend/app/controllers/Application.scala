package controllers

import java.util.UUID

import model._
import play.api.Play.current
import org.joda.time.DateTime
import play.api.libs.json.{Writes, JsObject, Json}
import play.api.libs.json.Json
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.ws.{WSResponse, WSAuthScheme, WS}
import play.api.mvc._
import model.JsonConversions._
import service.ConfigServiceImpl

class Application extends Controller with ConfigServiceImpl {

  def auth(request: Request[AnyContent])(callback: User => Result) = {
    val authToken = request.headers.get("access_token").get
    WS.url(s"https://api.github.com/applications/$clientId/tokens/$authToken")
      .withAuth(clientId, clientSecret, WSAuthScheme.BASIC)
      .get()
      .map { response: WSResponse =>
        val json = response.json
        val userId = (json \ "user" \ "id").as[Int]
        val userName = (json \ "user" \ "login").as[String]
        callback(User(UserId(userId), userName))
      }
  }

  def testOAuth = Action.async { request =>
    auth(request) { user =>
      Ok(Json.toJson(List(user)))
    }
  }

  def health = Action {
    Ok("Alright then.")
  }

  //
  // Users
  //

  val mockUsers = Map(1l-> User(UserId(1), "Daniel"), 2l -> User(UserId(2), "Paul"), 3l -> User(UserId(3), "Salomé"), 4l -> User(UserId(4), "Roman"))
  def getUsers = getAll(mockUsers, "users")
  def getUser(userId: Long) = getById(userId, mockUsers)



  //
  // Tables
  //
  val mockTable = model.Table(id = TableId(567), name = "theTable", colorHome = Color("Blue"), building = "BMO", floor = "101", lastGoalScored = DateTime.now(), colorAway = Color("Red"))
  val mockTables = Map(567l -> mockTable)
  def getTables = getAll(mockTables, "tables")
  def getTable(tableId: Long) = getById(tableId, mockTables)

  //
  // Games
  //
  val mockPlayers = List(Player(UserId(1), Attack, Home), Player(UserId(2), Defense, Home),
    Player(UserId(3), Attack, Away), Player(UserId(4), Defense, Away))
  val mockGame = Game(GameId(999), TableId(567), mockPlayers, 5, 3, DateTime.now().minusMinutes(10), DateTime.now())
  val mockGames = Map(999l -> mockGame)
  def getGames = getAll(mockGames, "games")
  def getGame(gameId: Long) = getById(gameId, mockGames)


  //
  // NFC data
  //
  val mockNfcData = NfcData(UUID.fromString("de305d54-75b4-431b-adb2-eb6b9e546014"), TableId(567), Home, Attack)
  val mockNfcDatas = Map("de305d54-75b4-431b-adb2-eb6b9e546014" -> mockNfcData)
  def getNfcDatas = getAll(mockNfcDatas, "nfc-data")
  def getNfcData(uuid: String) = getById(uuid, mockNfcDatas)

  def getAll[T](all: Map[_,T], desc: String)(implicit writer: Writes[T]) = Action(Ok(JsObject(Map(desc -> Json.toJson(all.values.toList)))))

  def getById[I,T](id: I, map: Map[I,T])(implicit writer: Writes[T]) = Action {
    if (map contains id) {
      val t = map(id)
      Ok(Json.toJson(t))
    } else {
      NotFound("Not found: " + id)
    }
  }

  def getCurrentGame(tableId: Long) = getById(999l, mockGames)
}
