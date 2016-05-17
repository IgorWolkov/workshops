package workshop

import io.circe.Json
import io.finch._
import workshop.fourth.response.ApiResponse

/**
  * Created by Igor Wolkov 
  * Since 17.05.16
  */
package object fourth {

  object ApiResponse {
    def apply[T](response: => ApiResponse[T]): Output[Json] =
      response fold (
        apiErrors =>
          Ok(
            Json.obj(
              "status" -> Json.fromString("error"),
              "statusCode" -> Json.fromInt(apiErrors.statusCode),
              "errors" -> Json.arr(
                apiErrors.errors map { error =>
                  Json.obj(
                    "message" -> Json.fromString(error.message),
                    "friendlyMessage" -> Json.fromString(error.friendlyMessage)
                  )
                }: _*
              )
            )
          ),

        user =>
          Ok(
            Json.obj(
              "status" -> Json.fromString("ok"),
              "response" -> Json.obj(
                "name" -> Json.fromString(user.asInstanceOf[User].name),
                "email" -> Json.fromString(user.asInstanceOf[User].email)
              )
            )
          )
      )
  }
}
