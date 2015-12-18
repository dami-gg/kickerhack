package controllers

import javax.inject.Inject

import model.JsonConversions._
import model._
import org.joda.time.DateTime
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import repository._
import service.AuthServiceImpl

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

class NfcDataController @Inject()(authService: AuthServiceImpl, nfcDataRepository: NfcDataRepository, gamesRepository: GamesRepository, playerRepository: PlayerRepository) extends Controller {

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
    var returnCode = Ok("Game created")
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
    if (game.isEmpty) {
      val newGame = Game(None, nfcData.tableId, 0, 0, DateTime.now(), None)
      val newGameId = Await.ready(gamesRepository.insert(newGame), Duration.Inf).value.get match {
        case Success(t) => t
        case Failure(e) => throw new IllegalArgumentException();
      }
      playerRepository.insert(Player(None, currentUser.id.get, newGameId.id.get, nfcData.position, nfcData.side))
    } else {
      val players = Await.ready(playerRepository.findbyGame(game.get.id.get), Duration.Inf).value.get match {
        case Success(t) => t
        case Failure(e) => throw new IllegalArgumentException();
      }
      val player = players.find(p => p.position == nfcData.position && p.side == nfcData.side)
      if (player.isEmpty) {
        playerRepository.insert(Player(None, currentUser.id.get, game.get.id.get, nfcData.position, nfcData.side))
      } else {
        returnCode = BadRequest("Position is already used")
      }
    }

    returnCode
  }
}
