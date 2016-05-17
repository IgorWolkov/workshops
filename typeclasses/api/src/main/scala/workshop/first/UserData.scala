package workshop.first

import workshop.User

/**
  * Created by Igor Wolkov 
  * Since 17.05.16
  */

object UserData {
  class Invalid extends RuntimeException()

  def fromSubmission(user: User): User =
    if(user.name != "" && user.email != "") user
    else                                    throw new Invalid

}
