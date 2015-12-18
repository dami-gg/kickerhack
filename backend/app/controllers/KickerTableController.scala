package controllers

import javax.inject.Inject

import controllers.actions.BasicAuth
import model.JsonConversions._
import model.{Game, KickerTable, Side}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import repository.{GamesRepository, KickerTableRepository}
import service.AuthServiceImpl

import scala.concurrent.duration.Duration
import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global


class KickerTableController @Inject()(authService: AuthServiceImpl,
                                      basicAuth: BasicAuth,
                                      gamesRepo: GamesRepository,
                                      gamesController: GamesController,
                                      kickerTableRepo: KickerTableRepository) extends Controller{

  def getTables = Action.async {
    kickerTableRepo.getAll().map(tables => Ok(Json.obj("tables" -> Json.toJson(tables))))
  }

  def getTable(tableId: Long) = Action.async {
    kickerTableRepo.findById(tableId).map {
      case Some(table) => Ok(Json.toJson(table))
      case None => NotFound("No table with ID " + tableId)
    }
  }

  def getCurrentGame(tableId: Long) = Action.async {
    for {
      currentGame <- gamesRepo.findCurrentGameForTable(tableId)
    gwp <- gamesController.withPlayers(currentGame.get)
    } yield {
      Ok(Json.toJson(gwp.get))
    }
  }

  def addGoal(tableId: Long, side: String) = Action.async { request =>
    kickerTableRepo.updateLastGoal(tableId)
    gamesRepo.findCurrentGameForTable(tableId).map {
      case None => Future.successful()
      case Some(game) =>
        val goalsAway = game.goalsAway + (if (side == Side.AWAY.toString) 1 else 0)
        val goalsHome = game.goalsHome + (if (side == Side.HOME.toString) 1 else 0)
        if (goalsAway > game.goalsAway) {
          gamesRepo.updateGoalAway(game.id.get, goalsAway)
        } else if (goalsHome > game.goalsHome) {
          gamesRepo.updateGoalHome(game.id.get, goalsHome)
        }
        if (goalsAway == 6 || goalsHome == 6) {
          gamesRepo.finishGame(game.id.get)
        }
    }.flatMap{ _ =>
      Future.successful(Ok("Goal was added."))
    }
  }
}

