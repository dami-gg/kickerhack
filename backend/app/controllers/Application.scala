package controllers

import java.util.UUID
import javax.inject.Inject

import model.JsonConversions._
import model.Position._
import model.Side._
import model._
import org.joda.time.DateTime
import play.api.libs.json.{JsObject, Json, Writes}
import play.api.mvc._
import repository.UserRepository
import service.{AuthServiceImpl, ConfigServiceImpl}

import scala.concurrent.ExecutionContext.Implicits.global

class Application @Inject()(userRepo: UserRepository, authService: AuthServiceImpl) extends Controller with ConfigServiceImpl {

  import scala.concurrent.Await

  def test = Action.async { request =>
    authService.auth(request).map { user => Ok(Json.toJson(user)) }
  }

  def health = Action {
    Ok("Alright then.")
  }

  //
  // Games
  //
  val mockPlayers = List(Player(Some(1), 1, 1, Attack, Home), Player(Some(2), 2, 1, Defense, Home),
    Player(Some(3), 3, 1, Attack, Away), Player(Some(4), 4, 1, Defense, Away))
  val mockGame = Game(Some(999), 567, mockPlayers, 5, 3, DateTime.now().minusMinutes(10), DateTime.now())
  val mockGames = Map(999l -> mockGame)

  def getGames = getAll(mockGames, "games")

  def getGame(gameId: Long) = getById(gameId, mockGames)


  //
  // NFC data
  //
  val mockNfcData = NfcData("de305d54-75b4-431b-adb2-eb6b9e546014", 567, Home, Attack)
  val mockNfcDatas = Map("de305d54-75b4-431b-adb2-eb6b9e546014" -> mockNfcData)

  def getNfcDatas = getAll(mockNfcDatas, "nfc-data")

  def getNfcData(uuid: String) = getById(uuid, mockNfcDatas)

  def getAll[T](all: Map[_, T], desc: String)(implicit writer: Writes[T]) = Action(Ok(JsObject(Map(desc -> Json.toJson(all.values.toList)))))

  def getById[I, T](id: I, map: Map[I, T])(implicit writer: Writes[T]) = Action {
    if (map contains id) {
      val t = map(id)
      Ok(Json.toJson(t))
    } else {
      NotFound("Not found: " + id)
    }
  }

  def getCurrentGame(tableId: Long) = getById(999l, mockGames)
}
