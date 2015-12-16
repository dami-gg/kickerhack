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

  val mockUsers = List(User(UserId(1), "Daniel"), User(UserId(2), "Paul"), User(UserId(3), "Salomé"), User(UserId(4), "Roman"))
  val mockTable = model.Table(id = TableId(567), name = "theTable", colorHome = Color("Blue"), building = "BMO", floor = "101", lastGoalScored = DateTime.now(), colorAway = Color("Red"))
  val mockTables = Map("theTable" -> mockTable)

  //
  // Users
  //
  def getUsers = Action {
    request => Ok(Json.toJson(mockUsers))
  }

  def getUser(playerId: String) = Action(NotFound)

  //
  // Tables
  //
  def getTables() = play.mvc.Results.TODO
  def getTable(tableId: String) = Action {
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
  def getGames() = play.mvc.Results.TODO
  def getGame(gameId: String) = play.mvc.Results.TODO

  //
  // NFC data
  //
  def getNfcData(uuid: UUID) = Action {
    val nfcData = NfcData(uuid, TableId(567), Home, Attack)

    Ok(Json.toJson(nfcData))
  }
}
