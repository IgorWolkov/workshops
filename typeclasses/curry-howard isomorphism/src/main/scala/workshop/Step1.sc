/**
  * @see http://milessabin.com/blog/2011/06/09/scala-union-types-curry-howard/
  */

type ¬[A] = Nothing

type ∨[T, U] = ¬[¬[T] with ¬[U]]

//type ¬¬[A] = ¬[¬[A]]

implicitly[Int <:< (Int ∨ String)]