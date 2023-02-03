package errorHandling

import cats.{ApplicativeError, MonadError}
import cats.implicits._
import cats.instances.list
import com.models.UserModel.RegisterUser


object authExceptions {
  def attemptDivideApplicativeErrorAbove2[F[_]](x: Int, y: Int)(implicit ae: ApplicativeError[F, String]): F[Int] =
    if (y == 0) ae.raiseError("Bad Math")
    else if (y == 1) ae.raiseError("Waste of Time")
    else ae.pure(x / y)

  def handler[F[_]](f: F[Int])(implicit ae: ApplicativeError[F, String]): F[Int] = {
    ae.handleError(f) {
      case "Bad Math" => -1
      case "Waste of Time" => -2
      case _ => -3
    }
  }

  def getTemperatureByCity[F[_]](city: String)(implicit ae: ApplicativeError[F, String]): F[Int] = {
    ae.pure(78)
  }

  def getCityClosestToCoordinate[F[_]](x: (Int, Int))(implicit ae: ApplicativeError[F, String]): F[String] = {
    ae.pure("Minneapolis, MN")
  }

  def validateCountry[F[_]](country: String)(implicit ae: ApplicativeError[F, String]): F[String] = country.toLowerCase() match {
    case "belgium" => ae.pure("Belgium")
    case _ => ae.raiseError("Only Belgium is allowed")
  }

  def validateAge[F[_]](age: Int)(implicit ae: ApplicativeError[F, String]): F[String] = age match {
    case (a) if a > 18 => ae.pure("Age is allowed")
    case _ => ae.raiseError("Must be above 18 years old")
  }

  def registerUser[F[_] : MonadError[*[_], String]](x: RegisterUser): F[String] = {
    println("-"*25)

    val age = validateAge[F](x.age)
    val country = validateCountry[F](x.country)

    val validated = List(age, country).traverseEither()

    println(validated)

    val result = for {
      c <- validateAge[F](x.age)
      t <- validateCountry[F](x.country)
    } yield t


    result
  }
}