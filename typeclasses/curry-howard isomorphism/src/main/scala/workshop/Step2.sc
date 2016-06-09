type ¬[A] = A => Nothing

type ∨[T, U] = ¬[¬[T] with ¬[U]]

type ¬¬[A] = ¬[¬[A]]

implicitly[¬¬[Int] <:< (Int ∨ String)]

implicitly[¬¬[String] <:< (Int ∨ String)]

def size[T](t: T)(implicit ev: (¬¬[T] <:< (Int ∨ String))) =
  t match {
    case i: Int => i
    case s: String => s.length
  }

size(23)

size("foo")

// Compile error
//size(1.0)