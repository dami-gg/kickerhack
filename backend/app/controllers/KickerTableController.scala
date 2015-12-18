package controllers

import javax.inject.Inject

import controllers.actions.BasicAuth
import model.JsonConversions._
import model.{KickerTable, Side}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import repository.{GamesRepository, KickerTableRepository}
import service.AuthServiceImpl

import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global


class KickerTableController @Inject()(authService: AuthServiceImpl,
                                      basicAuth: BasicAuth,
                                      gamesRepo: GamesRepository,
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
    gamesRepo.findCurrentGameForTable(tableId).map {
      case Some(game) => Ok(Json.toJson(game))
      case None => NotFound("No game in progress on table " + tableId)
    }
  }

  def addGoal(tableId: Long, side: String) = Action.async { request =>
    kickerTableRepo.updateLastGoal(tableId)
    gamesRepo.findCurrentGameForTable(tableId).map {
      case None => Future.successful()
      case Some(game) =>
        if(game.goalsAway>4 || game.goalsHome>4 ){
          //gamesRepo.closeGame(game.id.get)
        }
        if (side == Side.AWAY.toString) {
          gamesRepo.updateGoalAway(game.id.get, game.goalsAway + 1)
        } else {
          gamesRepo.updateGoalHome(game.id.get, game.goalsHome + 1)
        }
    }.flatMap{ _ =>
      Future.successful(Ok("Goal was added."))
    }
  }
}

