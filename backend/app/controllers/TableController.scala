package controllers

import javax.inject.Inject

import model.{Color, KickerTable}
import play.api.libs.json.{Json, JsObject}
import play.api.mvc.{Action, Controller}
import repository.{KickerTableRepository, UserRepository}
import service.AuthService
import model.JsonConversions._

import scala.concurrent.{Future, Await}


class TableController @Inject()(kickerTableRepository: KickerTableRepository) extends Controller  {

  def getTables = Action {
    val ins = kickerTableRepository.createKickerTable(KickerTable(Some(1l), None, "foo", "bar", Color("Blue"), Color("Red"), None))
    val res = Await.result(ins, scala.concurrent.duration.Duration.Inf)
    val result: Seq[KickerTable] = Await.result(kickerTableRepository.getAll(), scala.concurrent.duration.Duration.Inf)
    Ok(JsObject(Map("tables" -> Json.toJson(result))))

  }

  def getTable(tableId: Long) = Action {
    val result: Option[KickerTable] = Await.result(kickerTableRepository.findById(tableId),
      scala.concurrent.duration.Duration.Inf)

    result match {
      case Some(table) => Ok(Json.toJson(table))
      case None => NotFound
    }
  }

  def getCurrentGame(tableId: Long) = Action {
    request => {
      Ok("Should this not be implemented in the GameController?")
    }
  }

  def registerPlayer(tableId: Long) = Action {
    request => {
      Ok("Should this not be implemented in the PlayerController?")
    }
  }
}

