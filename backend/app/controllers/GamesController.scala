package controllers

import javax.inject.Inject

import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import repository.GamesRepository
import model.JsonConversions.gameFormat
import scala.concurrent.ExecutionContext.Implicits.global

class GamesController @Inject()(gamesRepo: GamesRepository) extends Controller {

  def getGames = Action.async {
    gamesRepo.list().map(
      games => Ok(Json.obj("games" -> Json.toJson(games)))
    )
  }

  def getGame(gameId: Long) = Action.async {
    gamesRepo.findById(gameId).map {
      case Some(game) => Ok(Json.toJson(game))
      case None => NotFound("No game with ID " + gameId)
    }
  }

}
