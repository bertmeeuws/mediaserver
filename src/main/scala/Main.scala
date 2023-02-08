

import akka.actor.TypedActor.context
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.AskPattern.Askable
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import com.controllers._
import com.server.Server
import com.actors._
import controllers._
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.util.Timeout
import com.compression.VideoCompression
import com.ffmpeg.CompressionActor.{Request, Response}

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.util._




object Main {
  def main(args: Array[String]): Unit = {
    println("Hello world!")
    VideoCompression.startCompression()
    println("Compression started")

    val rootBehavior = Behaviors.setup[Request] { context =>
      //val store = context.spawn(RootActor(), "Store")
      implicit val store = ActorSystem(Behaviors.empty, "RootStore")


      implicit val scheduler = store.scheduler
      implicit val timeout = Timeout(5.seconds)
      implicit val ec = store.executionContext


      val videoController = new VideoController(store)(context.system)
      val fileManager = new FileManagerController(store)(context.system)
      val authenticationController = new AuthenticationController(store)(context.system)

      val routes: Route = videoController.routes ~ fileManager.routes ~ authenticationController.routes

      Server.Bootstrap(routes)(context.system)
      Behaviors.same
    }

  ActorSystem[Request](rootBehavior, "API")
}
}