package controllers

import javax.inject.Inject

import model.{Player, Game}
import play.api.libs.json
import play.api.libs.json.{JsValue, Writes, Json}
import play.api.mvc.{Action, Controller}
import repository.{PlayerRepository, GamesRepository}
import model.JsonConversions.gameFormat
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class GameWithPlayers(game: Game, players: Seq[Player])
object GameWithPlayers {
  implicit val gameWithPlayersWrites = new Writes[GameWithPlayers] {
    override def writes(o: GameWithPlayers): JsValue = {
      val game = o.game
      val players = o.players.map(p => Json.obj(
        "userId" -> Json.toJson(p.userId),
        "position" -> Json.toJson(p.position),
        "side" -> Json.toJson(p.side))
      )

      Json.obj(
        "id" -> Json.toJson(game.id),
        "tableId" -> Json.toJson(game.tableId),
        "goalsHome" -> Json.toJson(game.goalsHome),
        "goalsAway" -> Json.toJson(game.goalsAway),
        "start" -> Json.toJson(game.start),
        "end" -> Json.toJson(game.end),
        "players" -> Json.toJson(players)
      )
    }
  }
}

class GamesController @Inject()(gamesRepo: GamesRepository,
                                playerRepo: PlayerRepository) extends Controller {



  def getGames = Action.async {
    for {
      games <- gamesRepo.list()
      gamesWithPlayers <- Future.sequence(games.map(withPlayers))
    } yield {
      Ok(Json.toJson(gamesWithPlayers.flatten))
    }
  }

  def getGame(gameId: Long) = Action.async {
    for {
      game0 <- gamesRepo.findById(gameId)
      gwp <- withPlayers(game0.get)
    } yield {
      Ok(Json.toJson(gwp.get))
    }
  }

  def withPlayers(game: Game): Future[Option[GameWithPlayers]] = {
    game.id match {
      case Some(id) => playerRepo.findbyGame(id).map(ps => Some(GameWithPlayers(game, ps)))
      case None => Future.successful(None)
    }
  }



}
