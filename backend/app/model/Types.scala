package model

import java.util.UUID

import org.joda.time.DateTime
import play.api.libs.json._

sealed abstract class ID(val value: Option[Long])
case class UserId(override val value: Option[Long]) extends ID(value)
case class TableId(override val value: Option[Long]) extends ID(value)
case class GameId(override val value: Option[Long]) extends ID(value)


case class User(id: UserId, name: String)
case class Users(users: List[User])
case class Color(color: String)
case class Table(id: TableId, name: String, building: String, floor: String, colorHome: Color, colorAway: Color, lastGoalScored: DateTime)
case class Tables(tables: List[Table])

sealed trait Position
case object Defense extends Position
case object Attack extends Position

sealed trait Side
case object Home extends Side
case object Away extends Side

case class Player(user: UserId, position: Position, side: Side)
case class Game(id: GameId, tableId: TableId, players: List[Player], goalsHome: Int, goalsAway: Int,
                start: DateTime, end: DateTime)
case class Games(games: List[Game])

case class NfcData(uuid: UUID, tableId: TableId, side: Side, position: Position)

object JsonConversions {
  implicit val idWrites = new Writes[ID] { override def writes(o: ID): JsValue = JsNumber(o.value) }
  implicit val userWrites = Json.writes[User]
  implicit val usersWrites = Json.writes[Users]
  implicit val colorWrites = new Writes[Color] { override def writes(o: Color): JsValue = JsString(o.color) }
  implicit val tableWrites = Json.writes[Table]
  implicit val tablesWrites = Json.writes[Tables]

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
  implicit val gamesWrites = Json.writes[Games]
  implicit val uuidWrites = new Writes[UUID] { override def writes(o: UUID): JsValue = JsString(o.toString) }
  implicit val nfcDataWrites = Json.writes[NfcData]
}
