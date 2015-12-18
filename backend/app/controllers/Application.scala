package controllers

import javax.inject.Inject

import repository._
import model._
import org.joda.time.DateTime
import play.api.libs.json.{JsObject, Json, Writes}
import play.api.mvc._

import scala.concurrent.Await
import scala.concurrent.duration.Duration


class Application @Inject()(kickerTableRepo: KickerTableRepository,
                            gamesRepo: GamesRepository,
                            nfcDataRepo: NfcDataRepository
                           ) extends Controller {
  def health = Action {

    val table = KickerTable(None, Some("BlahTable"), "BMO", "1st", Color("Blue"), Color("Red"), None)
    val tableId = Await.result(kickerTableRepo.createKickerTable(table), Duration.Inf)
    val game = Await.result(gamesRepo.insert(Game(None, tableId, 3, 4, DateTime.now(), None)), Duration.Inf)
    val nfcData = NfcData("nfc-" + DateTime.now().toString, tableId, Side.Home, Position.Attack)
    Await.result(nfcDataRepo.insertNfcData(nfcData), Duration.Inf)
    Ok("Alright then.")
  }
}
