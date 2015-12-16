package model

import java.util.UUID

import org.joda.time.DateTime
import play.api.libs.json._
import slick.lifted.MappedTo
import model.Position._
import model.Side._

sealed abstract class ID(val value: Long) extends MappedTo[Long]
case class UserId(override val value: Long) extends ID(value)
case class TableId(override val value: Long) extends ID(value)
case class GameId(override val value: Long) extends ID(value)


case class User(id: Option[Long], name: String)
case class Color(color: String)
case class Table(id: Option[TableId], name: String, building: String, floor: String, colorHome: Color, colorAway: Color, lastGoalScored: DateTime)

case class Player(id: Option[Long], user: Long, game: Long, position: Position, side: Side)
case class Game(id: Option[GameId], tableId: TableId, players: List[Player], goalsHome: Int, goalsAway: Int,
                start: DateTime, end: DateTime)

case class NfcData(uuid: UUID, tableId: TableId, side: Side, position: Position)

object JsonConversions {
  implicit val idWrites = new Writes[ID] { override def writes(o: ID): JsValue = JsNumber(o.value) }
  implicit val userWrites = Json.writes[User]
  implicit val userFormat = Json.format[User]
  implicit val colorWrites = new Writes[Color] { override def writes(o: Color): JsValue = JsString(o.color) }
  implicit val tableWrites = Json.writes[Table]

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
