package service

import javax.inject.Inject

import com.google.inject.ImplementedBy
import model.User
import play.api.mvc.{Result, AnyContent, Request}
import repository.UserRepository
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.ws.{WSResponse, WSAuthScheme, WS}
import play.api.Play.current

import scala.concurrent.{Await, Future}

@ImplementedBy(classOf[AuthServiceImpl])
trait AuthService{
  def auth(request: Request[AnyContent]):Future[User]
}

class AuthServiceImpl @Inject()(userRepo: UserRepository) extends AuthService with ConfigServiceImpl {
    def auth(request: Request[AnyContent]):Future[User] = {
      val authToken = request.headers.get("access_token").get
      WS.url(s"https://api.github.com/applications/$clientId/tokens/$authToken")
        .withAuth(clientId, clientSecret, WSAuthScheme.BASIC)
        .get().flatMap { response: WSResponse =>
          val json = response.json
          val userId = (json \ "user" \ "id").as[Long]
          val userName = (json \ "user" \ "login").as[String]
          val githubUser = User(Some(userId), userName)
          userRepo.findById(userId).map(user => user).recover{
            case e: RuntimeException =>
              userRepo.insert(githubUser)
              githubUser
          }
        }
    }
}



