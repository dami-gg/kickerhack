package model

import java.util.UUID

import org.joda.time.DateTime
import play.api.data.validation.Constraints
import play.api.libs.functional.syntax
import play.api.libs.functional.syntax.toInvariantFunctorOps
import play.api.libs.json._
import model.Position._
import model.Side._

import scala.util.matching.Regex


case class User(id: Option[Long], name: String)
case class Color(color: String)
case class KickerTable(id: Option[Long], name: Option[String], building: String, floor: String,
                       colorHome: Color, colorAway: Color, lastGoalScored: Option[DateTime])

case class Player(id: Option[Long], user: Long, game: Long, position: Position, side: Side)
case class Game(id: Option[Long], tableId: Long, players: List[Player], goalsHome: Int, goalsAway: Int,
                start: DateTime, end: DateTime)

case class NfcData(uuid: String, tableId: Long, side: Side, position: Position)

object JsonConversions {
  implicit val userWrites = Json.writes[User]
  implicit val userFormat = Json.format[User]
  implicit val colorWrites = new Writes[Color] { override def writes(o: Color): JsValue = JsString(o.color) }
  implicit val tableWrites = Json.writes[KickerTable]

  implicit val playerFormat = Json.format[Player]
  implicit val gameWrites = Json.writes[Game]
  implicit val uuidWrites = new Writes[UUID] { override def writes(o: UUID): JsValue = JsString(o.toString) }
  implicit val nfcDataWrites = Json.writes[NfcData]
}
