package com.models

import com.models.FileManagerModels.FileItem
import io.circe._
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}


object UserModel {
  case class RegisterUser(name: String, surname: String, email: String, age: Int, country: String)

  implicit val registerUserDecoder: Decoder[RegisterUser] = deriveDecoder
  implicit val registerUserEncoder: Encoder[RegisterUser] = deriveEncoder
}