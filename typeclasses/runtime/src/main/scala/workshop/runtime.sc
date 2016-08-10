type ¬[A] = A => Nothing
type |∨|[T, U] = ¬[¬[T] with ¬[U]]
type ¬¬[A] = ¬[¬[A]]
type ∨[T, U] = {type lambda[X] = ¬¬[X] <:< (T |∨| U)}



def size[T: (Int ∨ String)#lambda](t: T) =
  t match {
    case i: Int    => i
    case s: String => s.length
  }

size(23)

size("foo")

size(1.0)


