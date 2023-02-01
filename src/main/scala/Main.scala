

import akka.actor.TypedActor.context
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.server.Route
import com.controllers._
import com.server.Server
import com.actors._
import controllers.FileManagerController


object Main {
  def main(args: Array[String]): Unit = {
    println("Hello world!")

    val rootBehavior = Behaviors.setup[Nothing] { context =>
      //val store = context.spawn(RootActor(), "Store")
      implicit val store = ActorSystem(Behaviors.empty, "SingleRequest")

      val api = new VideoController(store)(context.system)
      val fileManagerRoutes = new FileManagerController(store)(context.system)


      //TODO concat routes
      val routes: Route = pathEnd {
        api.routes, fileManagerRoutes.routes
      }





      Server.Bootstrap(routes)(context.system)
      Behaviors.same
    }

  ActorSystem[Nothing](rootBehavior, "API")
}
}