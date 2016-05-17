package workshop.first

/**
  * Created by Igor Wolkov 
  * Since 17.05.16
  */
object Access {
  trait Status
  object Allowed extends Status
  object Denied extends Status

  def getAccess(id: String): Access = {
    if(id == "admin")
      Access(Allowed)
    else
      Access(Denied)
  }
}

import workshop.first.Access._

case class Access(status: Status) {
  def isAllowed: Boolean = status == Allowed
}
