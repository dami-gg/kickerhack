package controllers

import javax.inject.Inject

import model.JsonConversions._
import model.{User, JsonConversions, NfcData}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import repository.{GamesRepository, KickerTableRepository, NfcDataRepository}
import service.AuthServiceImpl

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Success, Failure, Try}

class NfcDataController @Inject()(authService: AuthServiceImpl, nfcDataRepository: NfcDataRepository, gamesRepository: GamesRepository) extends Controller {

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

  def registerPlayer(uuid: String) = Action { request =>
      val currentUser = Await.ready(authService.auth(request), Duration.Inf).value.get match {
        case Success(t) => t
        case Failure(e) => throw new IllegalArgumentException();
      }
      val nfcData = Await.ready(nfcDataRepository.getNfcData(uuid), Duration.Inf).value.get match {
        case Success(t) => t.getOrElse(throw new IllegalArgumentException())
        case Failure(e) => throw new IllegalArgumentException();
      }
      val game = Await.ready(gamesRepository.findCurrentGameForTable(nfcData.tableId), Duration.Inf).value.get match {
        case Success(t) => t
        case Failure(e) => throw new IllegalArgumentException();
      }
      if(game.nonEmpty){
        //try to register player
      }else{
        //create game
      }

      Ok("sdf")
  }
}
