package controllers

import java.util.UUID
import javax.inject.Inject

import model._
import org.joda.time.DateTime
import play.api.libs.json.{JsObject, Json}
import play.api.mvc._
import model.JsonConversions._
import repository.UserRepository

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global

class Application @Inject()(userRepo: UserRepository) extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  val mockTable = model.Table(id = Some(TableId(567)), name = "theTable", colorHome = Color("Blue"), building = "BMO", floor = "101", lastGoalScored = DateTime.now(), colorAway = Color("Red"))
  val mockTables = Map(567l -> mockTable)

  //
  // Users
  //
  def getUsers = Action {
    request => {
      val users:Seq[User] = Await.result(userRepo.list(), scala.concurrent.duration.Duration.Inf)

      Ok(JsObject(Map("users" -> Json.toJson(users))))
    }
  }

  def getUser(userId: Long) = Action {
    userRepo.findById(userId).onSuccess {
      case s => Ok(Json.toJson(s))
    }
    
    NotFound
  }

  def addUser = Action(parse.json) { request =>
    val user = request.body.validate[User]
    user.fold(
      errors => BadRequest,
      user => {
        userRepo.insert(User(null, user.name))
        Created
      }
    )
  }

  //
  // Tables
  //
  def getTables = Action(Ok(JsObject(Map("tables" -> Json.toJson(mockTables.values.toList)))))

  def getTable(tableId: Long) = Action {
    if (mockTables contains tableId) {
      val table = mockTables(tableId)
      Ok(Json.toJson(table))
    } else {
      NotFound("Not found: " + tableId)
    }
  }

  //
  // Games
  //
  val mockPlayers = List(Player(UserId(1), Attack, Home), Player(UserId(2), Defense, Home),
    Player(UserId(3), Attack, Away), Player(UserId(4), Defense, Away))
  val mockGame = Game(Some(GameId(999)), TableId(567), mockPlayers, 5, 3, DateTime.now().minusMinutes(10), DateTime.now())
  val mockGames = List(mockGame)
  def getGames = Action(Ok(JsObject(Map("games" -> Json.toJson(mockGames)))))
  def getGame(gameId: Long) = Action {
    if (mockTables contains gameId) {
      val table = mockTables(gameId)
      Ok(Json.toJson(table))
    } else {
      NotFound("Not found: " + gameId)
    }
  }

  //
  // NFC data
  //
  val mockNfcData = NfcData(UUID.fromString("de305d54-75b4-431b-adb2-eb6b9e546014"), TableId(567), Home, Attack)
  val mockNfcDatas = Map("de305d54-75b4-431b-adb2-eb6b9e546014" -> mockNfcData)
  def getNfcDatas = TODO
  def getNfcData(uuid: UUID) = TODO
}
