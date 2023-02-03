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

class AuthenticationController(store: ActorRef[Nothing])(val system: ActorSystem[_]) extends FailFastCirceSupport with Router {

  import com.models.UserModel._

  override val routes: Route = {
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
              println(user)
              val result = UserService.register(user)
              println(result)
              complete(StatusCodes.OK, "test")
            }
          }
        }
      }
    }
  }
}