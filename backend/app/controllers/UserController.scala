package controllers

import javax.inject.Inject

import model.JsonConversions._
import play.api.libs.json.Json
import play.api.mvc.{Controller, _}
import repository.UserRepository
import service.AuthServiceImpl

import scala.concurrent.ExecutionContext.Implicits.global

class UserController @Inject()(userRepo: UserRepository, authService: AuthServiceImpl) extends Controller {

  def getUsers = Action.async { result =>
    userRepo.list().map {
      players => Ok(Json.toJson(players))
    }
  }

  def getUser(playerId: Long) = Action.async {
    userRepo.findById(playerId).map {
      player => Ok(Json.toJson(player))
    }
  }

  def getMe = Action.async {
    request => authService.auth(request).map { user => Ok(Json.toJson(user)) }
  }
}
