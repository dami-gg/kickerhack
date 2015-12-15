package controllers

import model.{Table, Color, User, Player}
import org.joda.time.DateTime
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import model.JsonConversions._

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  val mockUsers = List(User(Some(1), "Daniel"), User(Some(2), "Paul"), User(Some(3), "Salomé"), User(Some(4), "Roman"))
  val mockTable = model.Table(567, "theTable", colorHome = Color("Blue"), floor = "101", lastGoalScored = DateTime.now(), colorAway = Color("Red"))
  val mockTables = Map("theTable" -> mockTable)

  def getPlayers = Action {
    request => Ok("A list of players: " + Json.toJson(mockUsers))
  }
  def getPlayer(playerId: String) = Action(NotFound)

  def getTables() = play.mvc.Results.TODO
  def getTable(tableId: String) = Action {
    if (mockTables contains tableId) {
      val table = mockTables(tableId)
      Ok(Json.toJson(table))
    } else {
      NotFound("Not found: " + tableId)
    }
  }


  def getGames() = play.mvc.Results.TODO
  def getGame(gameId: String) = play.mvc.Results.TODO
}
