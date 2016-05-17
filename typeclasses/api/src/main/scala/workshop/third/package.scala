package workshop

import io.circe.{Encoder, Json}

/**
  * Created by Igor Wolkov 
  * Since 17.05.16
  */
package object third {
  implicit val encodeException: Encoder[Exception] = Encoder.instance(e =>
    Json.obj(
      "type" -> Json.fromString(e.getClass.getSimpleName),
      "message" -> Json.fromString(e.getMessage)
    )
  )
}
