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
      users => Ok(Json.obj("users" -> Json.toJson(users)))
    }
  }

  def getUser(userId: Long) = Action.async {
    userRepo.findById(userId).map {
      case Some(user) => Ok(Json.toJson(user))
      case None => NotFound("No user with ID " + userId)
    }
  }

  def getMe = Action.async {
    request => authService.auth(request).map { user => Ok(Json.toJson(user)) }
  }
}
