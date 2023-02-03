package controllers

import akka.actor.typed.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.domain.Router
import com.services.UserService
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._


class AuthenticationController(store: ActorRef[Nothing])(val system: ActorSystem[_]) extends FailFastCirceSupport with Router {

  import com.models.UserModel._

  override val routes: Route = cors() {
    pathPrefix("authentication") {
      pathEndOrSingleSlash {
        get {
          complete(StatusCodes.OK, "GET")
        } ~
          post {
            complete(StatusCodes.OK, "Hi")
          }
      } ~ path("register") {
          post {
            entity(as[RegisterUser]) { user => {
              val result: Either[List[String], String] = UserService.register(user)

              result match {
                case Left(a) => complete(StatusCodes.BadRequest, a)
                case Right(b) => complete(StatusCodes.OK, b)
              }
            }
          }
        }
      }
    }
  }
}