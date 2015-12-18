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


class TableController @Inject()(authService: AuthServiceImpl, gamesRepository: GamesRepository) extends Controller with KickerTableRepository{

  def getTables = Action {
    val result: Seq[KickerTable] = Await.result(repository.getAll(), scala.concurrent.duration.Duration.Inf)
    Ok(Json.obj("tables" -> Json.toJson(result)))
  }

  def getTable(tableId: Long) = Action.async {
    repository.findById(tableId).map {
      kickerTable => Ok(Json.toJson(kickerTable))
    }
  }

  def getCurrentGame(tableId: Long) = Action.async {
    gamesRepository.findCurrentGameForTable(tableId).map {
      game => Ok(Json.toJson(game))
    }
  }

  def addGoal(side: String) = BasicAuth.async { request =>
    repository.updateLastGoal(request.table.id.get)
    gamesRepository.findCurrentGameForTable(request.table.id.get).map {
      case None => Future.successful()
      case Some(game) =>
        if (side == Side.AWAY.toString) {
          gamesRepository.updateGoalAway(game.id.get, game.goalsAway + 1)
        } else {
          gamesRepository.updateGoalHome(game.id.get, game.goalsHome + 1)
        }
    }.flatMap{ _ =>
      Future.successful(Ok("sdfd"))
    }
  }
}

