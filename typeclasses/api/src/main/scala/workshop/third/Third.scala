package workshop.third

import com.twitter.finagle.Http
import com.twitter.server.TwitterServer
import com.twitter.util.Await
import io.circe.generic.auto._
import io.finch.circe._
import io.finch.{Endpoint, _}
import workshop.User

/**
  * Created by Igor Wolkov 
  * Since 17.05.16
  */
object Third extends TwitterServer {

  val postedUser: Endpoint[User] = body.as[User]

  val patchedUser: Endpoint[User => User] = body.as[User => User]

  val authenticate: Endpoint[User] = post("api" :: "authenticate" :: string :: patchedUser) { (userId: String, pt: User => User) =>
    val access = Access.getAccess(userId)

    if(access.isAllowed) {
      try {
        val currentUser: User = UserRepository.get(userId)
        val submission: Option[User] = UserData.attemptSubmission(pt(currentUser))
        if(submission.isDefined) {
          val updatesUser: User = UserRepository.update(userId, submission.get)
          Ok(updatesUser)
        } else {
          BadRequest(new RuntimeException("Invalid user data"))
        }
      } catch {
        case e: UserRepository.DatabaseError => InternalServerError(new RuntimeException("Cannot access db"))
      }
    } else {
      Forbidden(new RuntimeException("Access denied"))
    }
  }

  val postUsers: Endpoint[User] = post("api" :: string :: postedUser) { (userId: String, user: User) =>
    val createdUser: User = UserRepository.update(userId, user)
    Ok(createdUser)
  }

  val getUsers: Endpoint[User] = get("api" :: string) { (userId: String) =>
    Ok(UserRepository.get(userId))
  }

  def main(): Unit = {
    log.info("Serving the Todo application")

    val server = Http.server
      .serve(s":8081", (postUsers :+: getUsers :+: authenticate).toService)

    onExit { server.close() }

    Await.ready(adminHttpServer)
  }

}