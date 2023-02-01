package com.actors

import akka.actor.typed._
import akka.actor.typed.scaladsl._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

object RootActor extends FailFastCirceSupport {
  sealed trait RootStoreCommand

  def apply(): Behavior[RootStoreCommand] = {
    /*
    Behaviors.setup(context => {
      Behaviors.receiveMessage {
       Behaviors.same
      }
    })
  }
  */
    Behaviors.same
  }
}