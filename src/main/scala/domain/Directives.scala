package com.domain

import akka.http.scaladsl.server.Route


trait Router {
  val routes: Route
}