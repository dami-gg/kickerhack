package controllers.actions

import com.google.inject.Inject
import model.KickerTable
import org.apache.commons.codec.binary.Base64
import play.api.mvc.Results._
import play.api.mvc._
import repository.KickerTableRepository

import scala.concurrent.Future

class RequestWithTable[A](table: KickerTable, request: Request[A]) extends WrappedRequest[A](request)

class BasicAuth @Inject()(tableRepository: KickerTableRepository) extends ActionBuilder[RequestWithTable] {

  def unauthorized = Future.successful(Unauthorized.withHeaders("WWW-Authenticate" -> """Basic realm="Secured""""))

  def invokeBlock[A](request: Request[A], block: (RequestWithTable[A]) => Future[Result]): Future[Result] =
    request
      .headers
      .get("Authorization")
      .flatMap { authorization =>
        authorization
          .split(" ")
          .drop(1)
          .headOption
          .map { encoded =>
            new String(Base64.decodeBase64(encoded.getBytes)).split(":").toList match {
              case login :: pass :: Nil => Some(login, pass)
              case _ => None
            }
          }
          .filter(_.isDefined)
          .head
          .map(loginPassTuple => tableRepository.findByIdAndPassword(loginPassTuple._1.toLong, loginPassTuple._2))
          .map(tableFuture => tableFuture.map { tableOption =>
            tableOption.map { table =>
              block(new RequestWithTable[A](table, request))
            }
          }
      }.getOrElse {
      Future.successful(Unauthorized.withHeaders("WWW-Authenticate" -> """Basic realm="Secured""""))
    }

  def invokeBlock[A](request: Request[A], block: (RequestWithTable[A]) => Future[Result]): Future[Result] =
    for {
      header <- getHeader(request)
      encoded <- getEncoded(header)
      (login, password) <- decode(encoded)
    } match
  {
     tableRepository.findByIdAndPassword(login, password).flatMap {
    case Some(table) => block(new RequestWithTable[A](table, request))
    case None => unauthorized
  }
  }

  private def getHeader[A](request: Request[A]): Option[String] =
    request
      .headers
      .get("Authorization")

  private def getEncoded(header: String): Option[String] =
    header.
      split(" ")
      .drop(1)
      .headOption

  private def decode(encoded: String): Option[(Long, String)] =
    new String(Base64.decodeBase64(encoded.getBytes)).split(":").toList match {
      case login :: pass :: Nil if login forall Character.isDigit => Some(login.toLong, pass)
      case _ => None
    }
}
