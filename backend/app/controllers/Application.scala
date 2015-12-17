package controllers

import java.util.UUID
import javax.inject.Inject

import model._
import org.joda.time.DateTime
import play.api.libs.json.{Writes, JsObject, Json}
import play.api.libs.json.Json
import play.api.mvc._
import model.JsonConversions._

import repository._
import service.ConfigServiceImpl
import model.Position._
import model.Side._
import JsonConversions._

import scala.concurrent.duration.Duration

class Application @Inject()(userRepo: UserRepository,
                            nfcDataRepo: NfcDataRepository,
                            kickerTableRepo: KickerTableRepository)
  extends Controller with ConfigServiceImpl {
import scala.concurrent.Await


  def health = Action {
    val kickerTable = KickerTable(None, Some("OurTable"), "BMO", "1st",
      Color("Blue"), Color("Red"), None)


    val id = Await.result(kickerTableRepo.createKickerTable(kickerTable), Duration.Inf)
    val nfcData = NfcData("nom-nom-nom", id, Home, Attack)
    Await.result(nfcDataRepo.insertNfcData(nfcData), Duration.Inf)
    Ok("Alright then.")
  }

  //
  // Users
  //

  def getUsers = Action {
    request => {
      val users:Seq[User] = Await.result(userRepo.list(), scala.concurrent.duration.Duration.Inf)

      Ok(JsObject(Map("users" -> Json.toJson(users))))
    }
  }

  def getUser(userId: Long) = Action {
    val user:User = Await.result(userRepo.findById(userId), scala.concurrent.duration.Duration.Inf)
    Ok(Json.toJson(user))
  }

  def addUser = Action(parse.json) { request =>
    val user = request.body.validate[User]
    user.fold(
      errors => BadRequest,
      user => {
        userRepo.insert(User(null, user.name))
        Created
      }
    )
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

  def getAll[T](all: Map[_,T], desc: String)(implicit writer: Writes[T]) = Action(Ok(JsObject(Map(desc -> Json.toJson(all.values.toList)))))

  def getById[I,T](id: I, map: Map[I,T])(implicit writer: Writes[T]) = Action {
    if (map contains id) {
      val t = map(id)
      Ok(Json.toJson(t))
    } else {
      NotFound("Not found: " + id)
    }
  }

  def getCurrentGame(tableId: Long) = getById(999l, mockGames)
}
