package controllers

import javax.inject.Inject

import model.{JsonConversions, NfcData}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import repository.NfcDataRepository

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import JsonConversions._

class NfcDataController @Inject()(nfcDataRepository: NfcDataRepository) extends Controller {

  def getNfcData(tag: String) = Action {
    val nfcData: Option[NfcData] = Await.result(nfcDataRepository.getNfcData(tag), Duration.Inf)
    nfcData match {
      case Some(data) => Ok(Json.toJson(data))
      case None => NotFound(tag)
    }
  }

  def insertNfcData(data: NfcData) = Action {
    Await.result(nfcDataRepository.insertNfcData(data), Duration.Inf)
    Created
  }

  def getAll = Action {
    val result: Seq[NfcData] = Await.result(nfcDataRepository.getAll, Duration.Inf)
    Ok(Json.obj("nfc-data" -> Json.toJson(result)))
  }
}
