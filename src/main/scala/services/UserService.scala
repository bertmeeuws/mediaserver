package com.services

import com.domain.Service
import com.models.UserModel.RegisterUser
import errorHandling.authExceptions._

trait UserService {
  def register(userData: RegisterUser): Either[List[String], String]
}

object UserService extends Service with UserService {
  override def register(userData: RegisterUser): Either[List[String], String] = {
    type MyEither[A] = Either[String, A]

    registerUser[MyEither](userData)
  }
}