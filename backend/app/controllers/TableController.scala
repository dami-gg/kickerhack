package controllers

import javax.inject.Inject

import model.JsonConversions._
import model.{Color, KickerTable}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import repository.KickerTableRepository
import service.AuthServiceImpl

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global


class TableController @Inject()(authService: AuthServiceImpl, kickerTables: KickerTableRepository) extends Controller {

  def getTables = Action {
    kickerTables.insert(KickerTable(None, Option("blabla"), "asdlk", "1", Color("red"), Color("black"), None))
    val result: Seq[KickerTable] = Await.result(kickerTables.list(), scala.concurrent.duration.Duration.Inf)
    Ok(Json.toJson(result))
  }

  def getTable(tableId: Long) = Action.async {
    kickerTables.findById(tableId).map {
      kickerTable => Ok(Json.toJson(kickerTable))
    }
  }

  def getCurrentGame(tableId: Long) = Action {
    request => {
      Ok("Should this not be implemented in the GameController?")
    }
  }

  def addGoal()
}

