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
                            gamesRepo: GamesRepository
                           ) extends Controller {
  def health = Action {

    val table = KickerTable(None, Some("BlahTable"), "BMO", "1st", Color("Blue"), Color("Red"), None)
    val tableId = Await.result(kickerTableRepo.createKickerTable(table), Duration.Inf)
    val game = Game(None, tableId, 3, 4, DateTime.now(), None)
    val gameId: Int = Await.result(gamesRepo.insert(game), Duration.Inf)
    Ok("Alright then.")
  }
}
