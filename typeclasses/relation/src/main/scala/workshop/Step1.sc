
import reflect.runtime.universe._

case class Wrap[T: Manifest](str: String)


def `evaluate relation`[T: TypeTag, A: TypeTag, B: TypeTag](wrap: Wrap[T]): String =
  wrap match {
    case v: Wrap[T] if typeOf[T] <:< typeOf[A] => typeOf[A].toString
    case v: Wrap[T] if typeOf[T] <:< typeOf[B] => typeOf[B].toString
}

`evaluate relation`[Int, Int, String](Wrap[Int]("some value"))
`evaluate relation`[String, Int, String](Wrap[String]("some value"))

// Does't compile
//`evaluate relation`[Int, Int, String](Wrap[String]("some value"))

// Compiles and works with runtime scala.MatchError
`evaluate relation`[Boolean, Int, String](Wrap[Boolean]("some value"))