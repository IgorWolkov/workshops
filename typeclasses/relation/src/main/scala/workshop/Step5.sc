
import reflect.runtime.universe._

trait Relation[A, B]

case class Wrap[T: Manifest](str: String)

// Doesn't work because we know nothing about A and B types
//trait AvailableRelation[T <: Relation[A, B]]

trait AvailableRelation[U, T <: Relation[_, _]]

// Dirty solution
implicit val `availableRelation#1` = new AvailableRelation[Int, Relation[Int, String]] {}
implicit val `availableRelation#2` = new AvailableRelation[String, Relation[Int, String]] {}


def `evaluate relation`[T: TypeTag, R <: Relation[_, _]: TypeTag](wrap: Wrap[T])(implicit availableRelation: AvailableRelation[T, R]): String = {
  wrap match {
    case v: Wrap[T] if typeOf[T] <:< typeOf[R].typeArgs(0) => typeOf[R].typeArgs(0).toString
    case v: Wrap[T] if typeOf[T] <:< typeOf[R].typeArgs(1) => typeOf[R].typeArgs(1).toString
  }
}
`evaluate relation`[Int, Relation[Int, String]](Wrap[Int]("some value"))
`evaluate relation`[String, Relation[Int, String]](Wrap[String]("some value"))

// Doesn't compile
//`evaluate relation`[Int, Relation[Int, String]](Wrap[String]("some value"))

// Doesn't compile
//`evaluate relation`[String, Relation[Boolean, String]](Wrap[String]("some value"))

// Doesn't compile
//`evaluate relation`[Int, Relation[Boolean, String]](Wrap[Int]("some value"))

// Doesn't compile
//`evaluate relation`[Boolean, Relation[Int, String]](Wrap[Boolean]("some value"))