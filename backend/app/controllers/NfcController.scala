package controllers

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import model.JsonConversions._
import model.{NfcData, Position, Side}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import service.AuthServiceImpl


class NfcController @Inject()(authService: AuthServiceImpl) extends Controller {

  def getNfcData(uuid: String) = Action {
    NfcData("Something", 1, Side.Home, Position.Defense)
    Ok(Json.toJson(NfcData("Something", 1, Side.Home, Position.Defense)))
  }

  def registerPlayer(uuid: String) = Action.async { request =>
    authService.auth(request).map { user => Ok(Json.toJson(user)) }
  }

}

