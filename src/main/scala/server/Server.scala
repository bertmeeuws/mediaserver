package com.server

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route

import scala.io.StdIn
import scala.util.{Failure, Success}

object Server {
  def Bootstrap(routes: Route)(implicit system: ActorSystem[_]): Unit = {
    // Akka HTTP still needs a classic ActorSystem to start

    import system.executionContext

    val futureBinding = Http().newServerAt("0.0.0.0", 3002).bind(routes)
    StdIn.readLine() // let it run until user presses return
    futureBinding
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}