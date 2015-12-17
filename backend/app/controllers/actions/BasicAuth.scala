package controllers.actions

import org.apache.commons.codec.binary.Base64
import play.api.mvc.Results._
import play.api.mvc._

import scala.concurrent.Future

class RequestWithLogin[A](login: String, request: Request[A]) extends WrappedRequest[A](request)

class BasicAuth extends ActionBuilder[RequestWithLogin] {
  override def invokeBlock[A](request: Request[A], block: (RequestWithLogin[A]) => Future[Result]): Future[Result] =
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
              case login :: pass :: Nil if checkCredentials(login, pass) => Some(login)
              case _ => None
            }
          }
          .filter(_.isDefined)
          .head
          .map(login => block(new RequestWithLogin[A](login, request)))
      }.getOrElse {
      Future.successful(Unauthorized.withHeaders("WWW-Authenticate" -> """Basic realm="Secured""""))
    }

  def checkCredentials(username: String, password: String): Boolean = ???
}
