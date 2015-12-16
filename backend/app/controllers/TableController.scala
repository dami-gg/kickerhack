package controllers

import javax.inject.Inject

import model.KickerTable
import play.api.libs.json.{Json, JsObject}
import play.api.mvc.{Action, Controller}
import repository.{KickerTableComponentImpl, UserRepository}
import service.AuthService
import model.JsonConversions._

import scala.concurrent.Await


class TableController @Inject()(userRepo: UserRepository) extends Controller
 with KickerTableComponentImpl {

  def getTables = Action {
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
      Ok("cxxcxy")
    }
  }
  
  def registerPlayer(tableId: Long) = Action {
    request => {
      Ok("cxxcxy")
    }
  }
}

