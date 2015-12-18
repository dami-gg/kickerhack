package controllers.actions

import javax.inject.Inject

import model.KickerTable
import org.apache.commons.codec.binary.Base64
import play.api.mvc.Results._
import play.api.mvc._
import repository.KickerTableRepository
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

case class RequestWithTable[A](table: KickerTable, request: Request[A]) extends WrappedRequest[A](request)

class BasicAuth @Inject()(kickerTableRepo: KickerTableRepository) extends ActionBuilder[RequestWithTable] {

  def unauthorized = Future.successful(Unauthorized.withHeaders("WWW-Authenticate" -> """Basic realm="Secured""""))

  def invokeBlock[A](request: Request[A], block: (RequestWithTable[A]) => Future[Result]): Future[Result] =
    getMaybeCredentials(request) match {
      case None => unauthorized
      case Some((login, password)) => kickerTableRepo
        .findByIdAndPassword(login, password)
        .flatMap {
          case None => unauthorized
          case Some(table) => block(new RequestWithTable[A](table, request))
        }
    }

  private def getMaybeCredentials[A](request: Request[A]): Option[(Long, String)] =
    for {
      header <- getAuthorizationHeader(request)
      encodedCredentials <- getEncodedCredentials(header)
      credentials <- decodeCredentials(encodedCredentials)
    } yield credentials

  private def getAuthorizationHeader[A](request: Request[A]): Option[String] =
    request
      .headers
      .get("Authorization")

  private def getEncodedCredentials(header: String): Option[String] =
    header.
      split(" ")
      .drop(1)
      .headOption

  private def decodeCredentials(encoded: String): Option[(Long, String)] =
    new String(Base64.decodeBase64(encoded.getBytes)).split(":").toList match {
      case login :: pass :: Nil if login forall Character.isDigit => Some(login.toLong, pass)
      case _ => None
    }
}
