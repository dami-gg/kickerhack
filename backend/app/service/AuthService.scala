package service

import model.User
import play.api.mvc.{Result, AnyContent, Request}
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.ws.{WSResponse, WSAuthScheme, WS}
import play.api.Play.current

trait AuthService{
  def auth(request: Request[AnyContent])(callback: User => Result)
}

trait AuthServiceImpl extends AuthService with ConfigServiceImpl {
    def auth(request: Request[AnyContent])(callback: User => Result) = {
      val authToken = request.headers.get("access_token").get
      WS.url(s"https://api.github.com/applications/$clientId/tokens/$authToken")
        .withAuth(clientId, clientSecret, WSAuthScheme.BASIC)
        .get()
        .map { response: WSResponse =>
          val json = response.json
          val userId = (json \ "user" \ "id").as[Long]
          val userName = (json \ "user" \ "login").as[String]
          callback(User(Some(userId), userName))
        }
    }
}



