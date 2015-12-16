package controllers

import javax.inject.Inject

import play.api.mvc.{Action, Controller}
import repository.UserRepository
import service.AuthService


class TableController  @Inject()(userRepo: UserRepository) extends Controller{

  def getTables = Action {
    request => {
      Ok("cxxcxy")
    }
  }

  def getTable(tableId: Long) = Action {
    request => {
      Ok("cxxcxy")
    }
  }

  def getCurrentGame(tableId: Long) = Action {
    request => {
      Ok("cxxcxy")
    }
  }
  def registerPlayer(tableId: Long) = Action {
    request => {
      Ok("cxxcxy")
    }
  }
}

