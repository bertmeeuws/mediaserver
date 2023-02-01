package controllers

import akka.actor.typed.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

import java.nio.file.FileSystems



class FileManagerController(store: ActorRef[Nothing])(val system: ActorSystem[_]) extends FailFastCirceSupport {
  var currentPath = ""

  val routes: Route = {
    path("filemanager") {
      pathEnd {
        val dir = FileSystems.getDefault.getPath("~")
        println(dir)
        complete(StatusCodes.OK)
      }
    }
  }
}
