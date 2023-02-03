package com.utils


trait Validator[F] {
  def validate(item: F): Boolean
}

object ValidateTypeclasses {

  case class Email(value: String)
  case class DOB(value: String)

  private val emailRegex = """^[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$""".r

  implicit val emailValidator = new Validator[Email] {
    def validate(item: Email): Boolean = item match {
      case null => false
      case Email(e) if e.trim().isEmpty => false
      case Email(e) if emailRegex.findFirstMatchIn(e).isDefined => true
      case _ => false
    }
  }

  implicit val dobValidator = new Validator[DOB] {
    def validate(item: DOB): Boolean = item match {
      case DOB(s"""$d-$m-$y""") => true
      case _ => false
    }
  }

  def validate[F](k: F)(implicit validator: Validator[F]): Boolean = validator.validate(k)
}