package workshop.fourth

import scala.language.postfixOps

/**
  * Created by Igor Wolkov 
  * Since 17.05.16
  */
object response {
  type ApiResponse[T] = Either[ApiErrors, T]

  case class ApiError(message: String,
                      friendlyMessage: String,
                      statusCode: Int,
                      context: Option[String] = None)

  case class ApiErrors(errors: List[ApiError]) {
    def statusCode = errors map { _.statusCode } max
  }
}
