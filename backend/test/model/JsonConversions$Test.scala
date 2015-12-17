package model

import model.Position.Position
import org.specs2.mutable.Specification
import play.api.libs.json._
import JsonConversions._

class JsonConversions$Test extends Specification {

  "An instance of Player" should {
    "be converted to JSON" in {
      val player = Player(Some(1l), 2l, 3l, Position.Defense, Side.AWAY)
      val json: JsValue = Json.toJson(player)

      val parsedVal: JsValue = Json.parse(
        """
          {
           "id" : 1,
           "user" : 2,
           "game" : 3,
           "position" : "Defense",
           "side" : "AWAY"
          }
        """)

      json must equalTo(parsedVal)
    }
    "be created from JSON when the ID is present" in {
      val json: JsValue = Json.parse(
        """
          {
           "id" : 1,
           "user" : 2,
           "game" : 3,
           "position" : "Defense",
           "side" : "AWAY"
          }
        """)

      val player: JsResult[Player] = Json.fromJson[Player](json)

      println(player)
      player.get must be equalTo Player(Some(1l), 2l, 3l, Position.Defense, Side.AWAY)
    }
    "be created from JSON when no ID is present" in {
      val json: JsValue = Json.parse(
        """
          {
           "user" : 2,
           "game" : 3,
           "position" : "Defense",
           "side" : "AWAY"
          }
        """)

      val player: JsResult[Player] = Json.fromJson[Player](json)

      println(player)
      player.get must be equalTo Player(None, 2l, 3l, Position.Defense, Side.AWAY)
    }


  }

}
