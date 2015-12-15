package model

import _root_.org.joda.time.DateTime
import org.joda.time.DateTime
import play.api.libs.json._

case class User(id: Option[Long], name: String)
case class Color(color: String)
case class Table(id: Long, building: String, floor: String, colorHome: Color, colorAway: Color, lastGoalScored: DateTime)

sealed trait Position
case object Defense extends Position
case object Attack extends Position

sealed trait Side
case object Home extends Side
case object Away extends Side

case class Player(user: User, position: Position, side: Side)
case class Game(id: Long, table: Table, players: List[Player], goalsHome: Int, goalsAway: Int,
                start: DateTime, end: DateTime)

object JsonConversions {
  implicit val userWrites = Json.writes[User]
  implicit val colorWrites = new Writes[Color] {
    override def writes(o: Color): JsValue = JsString(o.color)
  }
  implicit val tableWrites = Json.writes[Table]
}
