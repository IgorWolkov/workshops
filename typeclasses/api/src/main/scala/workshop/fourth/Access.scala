package workshop.fourth

import workshop.fourth.response.{ApiError, ApiErrors, ApiResponse}


/**
  * Created by Igor Wolkov 
  * Since 17.05.16
  */
object Access {
  trait Status
  object Allowed extends Status
  object Denied extends Status

  def getAccess(id: String): ApiResponse[Access] = {
    if(id == "admin")
      Right(Access(Allowed))
    else
      Left(ApiErrors(List(ApiError(
        "Access denied",
        "We were unable to check your credentials, please try again shortly",
        500
      ))))
  }
}

import workshop.fourth.Access._

case class Access(status: Status) {
  def isAllowed: Boolean = status == Allowed
}
