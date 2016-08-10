/**
  * @see: http://stackoverflow.com/questions/38872242/map-single-type-hlist-to-hlist-of-target-types
  */

import shapeless._

case class TypedString[U](value: String)

val list = "Hello" :: "world" :: HNil

// We need something like this
//val mapped: TypedString[Int] :: TypedString[Boolean] :: HNil =
//  convert[TypedString[Int] :: TypedString[Boolean] :: HNil](list)