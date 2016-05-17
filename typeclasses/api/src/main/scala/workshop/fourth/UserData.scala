package workshop.fourth

import workshop.User
import workshop.fourth.response._

/**
  * Created by Igor Wolkov 
  * Since 17.05.16
  */

object UserData {
  class Invalid extends RuntimeException()

  def fromSubmission(user: User): ApiResponse[User] = {
    if(user.name != "" && user.email != "")
      Right(user)
    else
      Left(ApiErrors(List(ApiError(
        "Invalid user data",
        "Provided data is unacceptable",
        500
      ))))
  }
}
