package controllers

import javax.inject.Inject

import repository._
import model._
import play.api.mvc._

import scala.concurrent.Await
import scala.concurrent.duration.Duration


class Application @Inject()(gamesRepo: GamesRepository,
                            nfcDataRepo: NfcDataRepository
                           ) extends Controller with KickerTableRepository {
  def health = Action {

    val table = KickerTable(None, Some("BlahTable"), "BMO", "1st", Color("Blue"), Color("Red"), None, "pass")
    val tableId = Await.result(repository.createKickerTable(table), Duration.Inf)
    val game = Game(None, tableId, 3, 4, System.currentTimeMillis(), None)
    val gameId: Int = Await.result(gamesRepo.insert(game), Duration.Inf)
    val nfcData = NfcData("osrainoiant", tableId, Side.HOME, Position.Attack)
    Await.result(nfcDataRepo.insertNfcData(nfcData), Duration.Inf)
    Ok("Alright then.")
  }
}
