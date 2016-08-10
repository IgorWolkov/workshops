
import reflect.runtime.universe._

trait Relation[A, B]

case class Wrap[T: Manifest](str: String)


def `evaluate relation`[T: TypeTag, R <: Relation[_, _]: TypeTag](wrap: Wrap[T]): String = {
  wrap match {
    case v: Wrap[T] if typeOf[T] <:< typeOf[R].typeArgs(0) => typeOf[R].typeArgs(0).toString
    case v: Wrap[T] if typeOf[T] <:< typeOf[R].typeArgs(1) => typeOf[R].typeArgs(1).toString
  }
}

`evaluate relation`[Int, Relation[Int, String]](Wrap[Int]("some value"))
`evaluate relation`[String, Relation[Int, String]](Wrap[String]("some value"))

// Doesn't compile
//`evaluate relation`[Int, Relation[Int, String]](Wrap[String]("some value"))

// Compiles and works without errors
`evaluate relation`[String, Relation[Boolean, String]](Wrap[String]("some value"))

// Compiles and works with runtime scala.MatchError
`evaluate relation`[Int, Relation[Boolean, String]](Wrap[Int]("some value"))
