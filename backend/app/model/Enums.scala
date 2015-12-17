package model

import play.api.libs.json._

case object Side extends Enumeration {
  type Side = Value
  val Home = Value("Home")
  val Away = Value("Away")

  implicit val sideWrites = new Writes[Side] {
    override def writes(o: Side): JsValue = JsString(o match {
      case Home => "Home"
      case Away => "Away"
    })
  }
  implicit val sideReads: Reads[Side] = new Reads[Side] {
    override def reads(json: JsValue): JsResult[Side] = JsSuccess(Side.withName(json.as[String].capitalize))
  }
}

case object Position extends Enumeration {
  type Position = Value
  val Defense = Value("Defense")
  val Attack = Value("Attack")

  implicit val PositionWrites = new Writes[Position] {
    override def writes(o: Position): JsValue = JsString(o match {
      case Defense => "Defense"
      case Attack => "Attack"
    })
  }
  implicit val positionReads: Reads[Position] = new Reads[Position] {
    override def reads(json: JsValue): JsResult[Position] = JsSuccess(Position.withName(json.as[String].capitalize))
  }
}


