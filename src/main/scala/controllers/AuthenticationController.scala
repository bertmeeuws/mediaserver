package controllers

import akka.actor.typed.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

class AuthenticationController(store: ActorRef[Nothing])(val system: ActorSystem[_]) extends FailFastCirceSupport {

  val routes: Route = {
    path("authentication") {
      pathEnd {
        get {
          complete(StatusCodes.OK, "GET")
        } ~
        post {
          complete(StatusCodes.OK, "Hi")
        }
      }
    }
  }
}