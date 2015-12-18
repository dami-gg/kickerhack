package model

import play.api.libs.json._

case object Side extends Enumeration {
  type Side = Value
  val HOME = Value("HOME")
  val AWAY = Value("AWAY")

  implicit val sideWrites = new Writes[Side] {
    override def writes(o: Side): JsValue = JsString(o match {
      case HOME => "HOME"
      case AWAY => "AWAY"
    })
  }
  implicit val sideReads: Reads[Side] = new Reads[Side] {
    override def reads(json: JsValue): JsResult[Side] = JsSuccess(Side.withName(json.as[String].toUpperCase))
  }
}

case object Position extends Enumeration {
  type Position = Value
  val DEFENSE = Value("DEFENSE")
  val ATTACK = Value("ATTACK")

  implicit val PositionWrites = new Writes[Position] {
    override def writes(o: Position): JsValue = JsString(o match {
      case DEFENSE => "DEFENSE"
      case ATTACK => "ATTACK"
    })
  }
  implicit val positionReads: Reads[Position] = new Reads[Position] {
    override def reads(json: JsValue): JsResult[Position] = JsSuccess(Position.withName(json.as[String].toUpperCase))
  }
}


