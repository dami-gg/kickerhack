package controllers

import model.JsonConversions._
import model.{Position, Side, NfcData, KickerTable}
import play.api.libs.json.{JsObject, Json}
import play.api.mvc.{Action, Controller}

import scala.concurrent.Await


class NfcController extends Controller {

  def getNfcData(uuid: String) = Action {
    NfcData("Something", 1, Side.Home, Position.Defense)
    Ok(Json.toJson(NfcData("Something", 1, Side.Home, Position.Defense)))
  }

}

