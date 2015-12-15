package controllers

import java.util.UUID

import model._
import org.joda.time.DateTime
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import model.JsonConversions._

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  val mockUsers = Map(1l-> User(Some(UserId(1)), "Daniel"), 2l -> User(Some(UserId(2)), "Paul"), 3l -> User(Some(UserId(3)), "Salomé"), 4l -> User(Some(UserId(4)), "Roman"))
  val mockTable = model.Table(id = Some(TableId(567)), name = "theTable", colorHome = Color("Blue"), building = "BMO", floor = "101", lastGoalScored = DateTime.now(), colorAway = Color("Red"))
  val mockTables = Map(567l -> mockTable)

  //
  // Users
  //
  def getUsers = Action(Ok(Json.toJson(Users(mockUsers.values.toList))))

  def getUser(userId: Long) = Action {
    if (mockUsers contains userId) {
      Ok(Json.toJson(mockUsers(userId)))
    } else {
      NotFound
    }
  }

  //
  // Tables
  //
  def getTables = Action(Ok(Json.toJson(Tables(mockTables.values.toList))))

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
  def getGames = Action(Ok(Json.toJson(Games(mockGames))))
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
  val mockNfcData = NfcData()
  def getNfcData = Action
  def getNfcData(uuid: UUID) = Action {
    val nfcData = NfcData(uuid, TableId(567), Home, Attack)

    Ok(Json.toJson(nfcData))
  }
}
