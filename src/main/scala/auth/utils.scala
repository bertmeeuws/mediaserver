package com.auth

import akka.http.scaladsl.model.headers.BasicHttpCredentials
import akka.http.scaladsl.server.directives._


trait AuthUtils {
  def authenticator(credentials: Credentials): Option[String]
}

object Utils extends AuthUtils{
  val validCredentials = BasicHttpCredentials("John", "p4ssw0rd")

  def authenticator(credentials: Credentials): Option[String] = credentials match {
    case p @ Credentials.Provided(id) if p.verify("p4ssw0rd") => Some(id)
    case _ => {
      None
    }
  }

}