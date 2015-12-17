package controllers

import javax.inject.Inject

import model.{Color, KickerTable}
import play.api.libs.json.{Json, JsObject}
import play.api.mvc.{Action, Controller}
import repository.{KickerTableRepository, UserRepository}
import service.AuthService
import model.JsonConversions._
import model.{Color, KickerTable}
import org.joda.time.DateTime
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import repository.KickerTableRepository
import service.AuthServiceImpl

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global


class TableController @Inject()(authService: AuthServiceImpl, kickerTableRepository: KickerTableRepository) extends Controller  {

  def getTables = Action {
    kickerTableRepository.createKickerTable(KickerTable(None, Option("blabla"), "asdlk", "1", Color("red"), Color("black"), None))
    val result: Seq[KickerTable] = Await.result(kickerTableRepository.getAll(), scala.concurrent.duration.Duration.Inf)
    Ok(Json.obj("tables" -> Json.toJson(result)))
  }

  def getTable(tableId: Long) = Action.async {
    kickerTableRepository.findById(tableId).map{
      kickerTable => Ok(Json.toJson(kickerTable))
    }
  }

  def getCurrentGame(tableId: Long) = Action {
    request => {
      Ok("Should this not be implemented in the GameController?")
    }
  }

  def registerPlayer(tableId: Long) = Action.async { request =>
    authService.auth(request).map { user => Ok(Json.toJson(user)) }
  }
}

