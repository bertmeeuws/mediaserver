package com.ffmpeg

import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.Behaviors


object CompressionActor {

  case class Request(query: String, replyTo: ActorRef[Response])
  case class Response(result: String)

  def apply(): Behaviors.Receive[Request] =
    Behaviors.receiveMessage[Request] {
      case Request(query, replyTo) =>
        println("inside")
        replyTo ! Response(s"Here are the cookies for [$query]!")
        Behaviors.same
    }
}