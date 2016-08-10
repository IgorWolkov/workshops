
import reflect.runtime.universe._

type ¬[A] = A => Nothing
type |∨|[T, U] = ¬[¬[T] with ¬[U]]
type ¬¬[A] = ¬[¬[A]]
type ∨[T, U] = { type λ[X] = ¬¬[X] <:< (T |∨| U) }

case class Wrap[T: Manifest](str: String)


def `evaluate relation`[T: (A ∨ B)#λ, A: TypeTag, B: TypeTag](wrap: Wrap[T])(implicit tt: TypeTag[T]): String =
  wrap match {
    case v: Wrap[T] if typeOf[T] <:< typeOf[A] => typeOf[A].toString
    case v: Wrap[T] if typeOf[T] <:< typeOf[B] => typeOf[B].toString
  }

`evaluate relation`[Int, Int, String](Wrap[Int]("some value"))
`evaluate relation`[String, Int, String](Wrap[String]("some value"))
// Does't compile
//`evaluate relation`[Int, Int, String](Wrap[String]("some value"))

// Does't compile
//`evaluate relation`[Boolean, Int, String](Wrap[Boolean]("some value"))