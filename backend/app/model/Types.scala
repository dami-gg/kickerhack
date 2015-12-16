package model

import java.util.UUID

import org.joda.time.DateTime
import play.api.libs.json._

case class User(id: Option[Long], name: String)
case class Color(color: String)
case class KickerTable(id: Option[Long], name: Option[String], building: String, floor: String,
                       colorHome: Color, colorAway: Color, lastGoalScored: Option[DateTime])

sealed trait Position
case object Defense extends Position
case object Attack extends Position

sealed trait Side
case object Home extends Side
case object Away extends Side

case class Player(userId: Long, position: Position, side: Side)
case class Game(id: Option[Long], tableId: Long, players: List[Player], goalsHome: Int, goalsAway: Int,
                start: DateTime, end: DateTime)

case class NfcData(uuid: UUID, tableId: Long, side: Side, position: Position)

object JsonConversions {
  implicit val userWrites = Json.writes[User]
  implicit val userFormat = Json.format[User]
  implicit val colorWrites = new Writes[Color] { override def writes(o: Color): JsValue = JsString(o.color) }
  implicit val tableWrites = Json.writes[KickerTable]

  implicit val PositionWrites = new Writes[Position] {
    override def writes(o: Position): JsValue = JsString(o match {
      case Defense => "defense"
      case Attack => "attack"
    })
  }

  implicit val sideWrites = new Writes[Side] {
    override def writes(o: Side): JsValue = JsString(o match {
      case Home => "home"
      case Away => "away"
    })
  }

  implicit val playerWrites = Json.writes[Player]
  implicit val gameWrites = Json.writes[Game]
  implicit val uuidWrites = new Writes[UUID] { override def writes(o: UUID): JsValue = JsString(o.toString) }
  implicit val nfcDataWrites = Json.writes[NfcData]
}
