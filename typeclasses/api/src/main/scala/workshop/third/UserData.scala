package workshop.third

import workshop.User

/**
  * Created by Igor Wolkov 
  * Since 17.05.16
  */

object UserData {
  class Invalid() extends RuntimeException()

  def attemptSubmission(user: User): Option[User] = {
    if(user.name != "" && user.email != "")
      Some(user)
    else
      None
  }
}
