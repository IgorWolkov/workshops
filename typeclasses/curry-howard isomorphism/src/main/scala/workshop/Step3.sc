/**
  * @see http://milessabin.com/blog/2011/06/09/scala-union-types-curry-howard/
  */

type ¬[A] = A => Nothing

type ∨[T, U] = ¬[¬[T] with ¬[U]]

type ¬¬[A] = ¬[¬[A]]

type |∨|[T, U] = { type λ[X] = ¬¬[X] <:< (T ∨ U) }

def size[T: (Int |∨| String)#λ](t: T) =
  t match {
    case i: Int => i
    case s: String => s.length
  }

size(23)

size("foo")

// Compile error
//size(1.0)