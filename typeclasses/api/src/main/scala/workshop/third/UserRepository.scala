package workshop.third

import workshop.User

import scala.collection._

/**
  * Created by Igor Wolkov 
  * Since 17.05.16
  */
object UserRepository {

  class DatabaseError extends RuntimeException

  private[this] val db: mutable.Map[String, User] = mutable.Map.empty[String, User]

  def get(userId: String): User = synchronized { db(userId) }

  def update(userId: String, user: User): User =
    synchronized {
      // Emulate db errors
      if(Math.random() < 0.3)
        throw new DatabaseError

      db += (userId -> user)
      user
    }
}
