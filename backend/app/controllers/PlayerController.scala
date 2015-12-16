package controllers

import javax.inject.Inject

import model.Player
import play.api.libs.json.{Json, JsObject}
import play.api.mvc.BodyParsers.parse
import play.api.mvc.Controller
import repository.{PlayerRepository, UserRepository}
import java.util.UUID
import javax.inject.Inject

import model._
import play.api.Play.current
import org.joda.time.DateTime
import play.api.libs.json.{Writes, JsObject, Json}
import play.api.libs.json.Json
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.ws.{WSResponse, WSAuthScheme, WS}
import play.api.mvc._
import model.JsonConversions._
import service.ConfigServiceImpl
import repository.{PlayerRepository, UserRepository}
import model.Position._
import model.Side._

import scala.concurrent.Await

class PlayerController @Inject()(playerRepo: PlayerRepository) extends Controller  {

  //
  // Players
  //

  def getPlayers = Action {
    request => {
      val players:Seq[Player] = Await.result(playerRepo.list(), scala.concurrent.duration.Duration.Inf)

      Ok(JsObject(Map("users" -> Json.toJson(players))))
    }
  }

  def getPlayer(playerId: Long) = Action {
    val player:Player = Await.result(playerRepo.findById(playerId), scala.concurrent.duration.Duration.Inf)
    Ok(Json.toJson(player))
  }

  def addPlayer = Action(parse.json) { request =>
    val player = request.body.validate[Player]
    player.fold(
      errors => BadRequest,
      player => {
        playerRepo.insert(Player(null, player.user, player.game, player.position, player.side))
        Created
      }
    )
  }
}
