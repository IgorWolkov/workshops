package workshop.first

import com.twitter.finagle.Http
import com.twitter.server.TwitterServer
import com.twitter.util.Await
import io.circe.generic.auto._
import io.finch.{Endpoint, _}
import io.finch.circe._
import workshop.User

/**
  * Created by Igor Wolkov 
  * Since 17.05.16
  */
object First extends TwitterServer {

  val postedUser: Endpoint[User] = body.as[User]

  val patchedUser: Endpoint[User => User] = body.as[User => User]

  val `update user data`: Endpoint[User] = post("api" :: "authenticate" :: string :: patchedUser) { (userId: String, pt: User => User) =>
    val access = Access.getAccess(userId)

    if(access.isAllowed) {
      val currentUser: User = UserRepository.get(userId)
      val submission: User = UserData.fromSubmission(pt(currentUser))
      val updatesUser: User = UserRepository.update(userId, submission)
      Ok(updatesUser)
    } else {
      Forbidden(new RuntimeException("Access denied"))
    }
  }

  // Internal code
  val postUsers: Endpoint[User] = post("api" :: string :: postedUser) { (userId: String, user: User) =>
    val createdUser: User = UserRepository.update(userId, user)
    Ok(createdUser)
  }

  // Internal code
  val getUsers: Endpoint[User] = get("api" :: string) { (userId: String) =>
    Ok(UserRepository.get(userId))
  }

  def main(): Unit = {
    log.info("Serving the Todo application")

    val server = Http.server
      .serve(s":8081", (postUsers :+: getUsers :+: `update user data`).toService)

    onExit { server.close() }

    Await.ready(adminHttpServer)
  }

}
