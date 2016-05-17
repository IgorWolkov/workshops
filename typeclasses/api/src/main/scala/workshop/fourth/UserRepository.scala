package workshop.fourth

import workshop.User
import workshop.fourth.response._

import scala.collection._

/**
  * Created by Igor Wolkov 
  * Since 17.05.16
  */
object UserRepository {

  class DatabaseError extends RuntimeException

  private[this] val db: mutable.Map[String, User] = mutable.Map.empty[String, User]

  def get(userId: String): ApiResponse[User] = synchronized { Right(db(userId)) }

  def update(userId: String, user: User): ApiResponse[User] =
    synchronized {
      // Emulate db errors
      if(Math.random() < 0.3)
        Left(ApiErrors(List(ApiError(
          "Database connection failure",
          "We were unable to check your credentials, please try again shortly",
          500
        ))))
      db += (userId -> user)

      Right(user)
    }

  // Internal code
  def rawget(userId: String): User = synchronized { db(userId) }

  // Internal code
  def rawupdate(userId: String, user: User): User =
    synchronized {
      // Emulate db errors
      if(Math.random() < 0.3)
        throw new DatabaseError

      db += (userId -> user)
      user
    }
}
