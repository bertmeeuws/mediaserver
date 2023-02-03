

import akka.actor.TypedActor.context
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import com.controllers._
import com.server.Server
import com.actors._
import controllers._
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.unmarshalling.Unmarshal



object Main {
  def main(args: Array[String]): Unit = {
    println("Hello world!")

    val rootBehavior = Behaviors.setup[Nothing] { context =>
      //val store = context.spawn(RootActor(), "Store")
      implicit val store = ActorSystem(Behaviors.empty, "SingleRequest")

      val videoController = new VideoController(store)(context.system)
      val fileManager = new FileManagerController(store)(context.system)
      val authenticationController = new AuthenticationController(store)(context.system)

      val routes: Route = videoController.routes ~ fileManager.routes ~ authenticationController.routes

      Server.Bootstrap(routes)(context.system)
      Behaviors.same
    }

  ActorSystem[Nothing](rootBehavior, "API")
}
}