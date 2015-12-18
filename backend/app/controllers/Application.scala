package controllers

import javax.inject.Inject

import org.joda.time.DateTime
import repository._
import model._
import play.api.mvc._

import scala.concurrent.Await
import scala.concurrent.duration.Duration


class Application @Inject()(kickerTableRepo: KickerTableRepository,
                            gamesRepo: GamesRepository,
                            nfcDataRepo: NfcDataRepository
                           ) extends Controller {
  def health = Action {

    val table = KickerTable(None, Some("BlahTable"), "BMO", "1st", Color("Blue"), Color("Red"), None, password = "pass")
    val tableId = Await.result(kickerTableRepo.createKickerTable(table), Duration.Inf)
    val game = Await.result(gamesRepo.insert(Game(None, tableId, 3, 4, System.currentTimeMillis(), None)), Duration.Inf)
    val nfcData = NfcData("nfc-" + DateTime.now().toString, tableId, Side.HOME, Position.ATTACK)
    Await.result(nfcDataRepo.insertNfcData(nfcData), Duration.Inf)
    Ok("Alright then.")
  }
}
