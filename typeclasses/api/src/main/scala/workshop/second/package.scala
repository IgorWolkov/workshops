package workshop

import io.circe.{Encoder, Json}

/**
  * Created by Igor Wolkov 
  * Since 17.05.16
  */

/**
  * This is internal part of code
  */
package object second {
  implicit val encodeException: Encoder[Exception] = Encoder.instance(e =>
    Json.obj(
      "type" -> Json.fromString(e.getClass.getSimpleName),
      "message" -> Json.fromString(e.getMessage)
    )
  )
}
