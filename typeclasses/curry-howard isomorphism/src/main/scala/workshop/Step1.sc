type ¬[A] = A => Nothing

type ∨[T, U] = ¬[¬[T] with ¬[U]]

//implicitly[Int <:< (Int ∨ String)]