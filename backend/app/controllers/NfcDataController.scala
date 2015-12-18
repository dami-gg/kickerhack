package controllers

import javax.inject.Inject

import model.{JsonConversions, NfcData}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import repository.NfcDataRepository
import service.AuthService
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import JsonConversions._

class NfcDataController @Inject()(nfcDataRepository: NfcDataRepository,
                                  authService: AuthService
                                 ) extends Controller {

  def getNfcData(tag: String) = Action.async {
    nfcDataRepository.getNfcData(tag).map {
      case Some(data) => Ok(Json.toJson(data))
      case None => NotFound(tag)
    }
  }

  def insertNfcData(data: NfcData) = Action.async {
    nfcDataRepository.insertNfcData(data).map(_ => Created)
  }

  def getAll = Action.async {
    nfcDataRepository.getAll.map(nfcData => Ok(Json.obj("nfc-data" -> Json.toJson(nfcData))))
  }

  def registerPlayer(uuid: String) = Action.async { request =>
    authService.auth(request).map { user => Ok(Json.toJson(user)) }
  }
}
