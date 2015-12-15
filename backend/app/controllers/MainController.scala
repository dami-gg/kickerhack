package controllers

import javax.inject.Inject

import model.User
import play.api.libs.json.Json
import repository.UserRepository
import play.api.mvc._
import model.JsonConversions._
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

import scala.util.{Failure, Success}

class MainController @Inject()(userRepo: UserRepository) extends Controller {
  
  val mockUsers = List(User(None, "Daniel"), User(None, "Paul"), User(None, "Salomé"), User(None, "Roman"))

  def getPlayers = Action {
    request => {
      mockUsers.foreach((u:User) => userRepo.insert(u))
      val users:Seq[User] = Await.result(userRepo.list(), scala.concurrent.duration.Duration.Inf)

      Ok(Json.toJson(users))
    }
  }
  def getPlayer(playerId: String) = Action(NotFound)

  def getTables() = play.mvc.Results.TODO
  def getTable(tableId: String) = play.mvc.Results.TODO


  def getGames() = play.mvc.Results.TODO
  def getGame(gameId: String) = play.mvc.Results.TODO
}