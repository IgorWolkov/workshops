type ¬[A] = Nothing

type ∨[T, U] = ¬[¬[T] with ¬[U]]

//type ¬¬[A] = ¬[¬[A]]

implicitly[Int <:< (Int ∨ String)]