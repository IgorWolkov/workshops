package workshop.fourth

import com.twitter.finagle.Http
import com.twitter.server.TwitterServer
import com.twitter.util.Await
import io.circe.Json
import io.circe.generic.auto._
import io.finch.circe._
import io.finch.{Endpoint, _}
import workshop.User

/**
  * Created by Igor Wolkov
  * Since 17.05.16
  */
object Fourth extends TwitterServer {

  val postedUser: Endpoint[User] = body.as[User]

  val patchedUser: Endpoint[User => User] = body.as[User => User]

  val authenticate: Endpoint[Json] = post("api" :: "authenticate" :: string :: patchedUser) { (userId: String, pt: User => User) =>
    ApiResponse (
      for {
        access <- Access.getAccess(userId).right
        currentUser <- UserRepository.get(userId).right
        submission <- UserData.fromSubmission(pt(currentUser)).right
        updatesUser <- UserRepository.update(userId, submission).right
      } yield updatesUser
    )
  }

  val postUsers: Endpoint[User] = post("api" :: string :: postedUser) { (userId: String, user: User) =>
    val createdUser: User = UserRepository.rawupdate(userId, user)
    Ok(createdUser)
  }

  val getUsers: Endpoint[User] = get("api" :: string) { (userId: String) =>
    Ok(UserRepository.rawget(userId))
  }

  def main(): Unit = {
    log.info("Serving the Todo application")

    val server = Http.server
      .serve(s":8081", (postUsers :+: getUsers :+: authenticate).toService)

    onExit { server.close() }

    Await.ready(adminHttpServer)
  }

}
