package model

import org.joda.time.DateTime
import play.api.libs.json._
import model.Position._
import model.Side._

case class User(id: Option[Long], name: String)
case class Color(color: String)
case class KickerTable(id: Option[Long], name: Option[String], building: String, floor: String,
                       colorHome: Color, colorAway: Color, lastGoalScored: Option[DateTime], password: String)

case class Player(id: Option[Long], userId: Long, gameId: Long, position: Position, side: Side)
case class Game(id: Option[Long], tableId: Long, goalsHome: Int, goalsAway: Int,
                start: DateTime, end: Option[DateTime])

case class NfcData(uuid: String, tableId: Long, side: Side, position: Position)

object JsonConversions {
  implicit val userFormat = Json.format[User]
  implicit val colorWrites = new Writes[Color] { override def writes(o: Color): JsValue = JsString(o.color) }
  implicit val tableWrites = Json.writes[KickerTable]

  implicit val playerFormat = Json.format[Player]
  implicit val gameWrites = Json.writes[Game]
  implicit val nfcDataFormat = Json.format[NfcData]
}
