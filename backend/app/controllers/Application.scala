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
    Ok("Alright then.")
  }
}
