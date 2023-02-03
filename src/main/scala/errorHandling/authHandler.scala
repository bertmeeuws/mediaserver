package errorHandling

import cats.{ApplicativeError, MonadError}
import cats.implicits._
import cats.instances.list
import com.models.UserModel.RegisterUser

object authExceptions {
  def validateCountry[F[_]](country: String)(implicit ae: ApplicativeError[F, String]): F[String] = country.toLowerCase() match {
    case "belgium" => ae.pure("Belgium")
    case _ => ae.raiseError("Only Belgium is allowed")
  }

  def validateAge[F[_]](age: Int)(implicit ae: ApplicativeError[F, String]): F[String] = age match {
    case (a) if a > 18 => ae.pure("Age is allowed")
    case _ => ae.raiseError("Must be above 18 years old")
  }

  def registerUser[F[_] : MonadError[*[_], String]](x: RegisterUser): Either[List[String], String] = {

    //Random validation
    val age = validateAge[F](x.age)
    val country = validateCountry[F](x.country)

    val validated = List(age, country)

    //Mapping all errors into a list
    val allErrors = validated.foldLeft[List[String]](List.empty)((acc, item) => {
      val result: List[String] = item match {
        case Left(a) => a.toString() :: acc
        case Right(_) => acc
      }

      result
    })

    if(allErrors.length == 0) Right("JWT") else Left(allErrors)
  }
}