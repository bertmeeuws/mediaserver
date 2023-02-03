package com.services

import com.domain.Service
import com.models.UserModel.RegisterUser
import errorHandling.authExceptions._

trait UserService {
  def register(userData: RegisterUser): String
}

object UserService extends Service with UserService {
  override def register(userData: RegisterUser): String = {
    type MyEither[A] = Either[String, A]

    val result = registerUser[MyEither](userData)
    println(result)
    "test"
  }
}