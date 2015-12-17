package controllers

import javax.inject.Inject

import model.JsonConversions._
import model.Side.Side
import model.{Game, Color, KickerTable, Side}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import repository.{GamesRepository, KickerTableRepository}
import service.AuthServiceImpl

import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global


class TableController @Inject()(authService: AuthServiceImpl, kickerTables: KickerTableRepository, gamesRepository: GamesRepository) extends Controller {

  def getTables = Action.async {
    kickerTables.list().map {
      tables => Ok(Json.toJson(tables))
    }
  }

  def getTable(tableId: Long) = Action.async {
    kickerTables.findById(tableId).map {
      kickerTable => Ok(Json.toJson(kickerTable))
    }
  }

  def getCurrentGame(tableId: Long) = Action.async {
    gamesRepository.findCurrentGameForTable(tableId).map {
      game => Ok(Json.toJson(game))
    }
  }

  def addGoal(tableId: Long, side: String) = Action.async{
    kickerTables.updateLastGoal(tableId)
    gamesRepository.findCurrentGameForTable(tableId).map {
      case None => Future.successful()
      case Some(game) =>
        if (side.equals(Side.Away.toString)) {
          gamesRepository.updateGoalAway(game.id.get, game.goalsAway + 1)
        } else {
          gamesRepository.updateGoalAway(game.id.get, game.goalsHome + 1)
        }
    }.flatMap{ _ =>
      Future.successful(Ok("dfs"))
    }
  }
}

