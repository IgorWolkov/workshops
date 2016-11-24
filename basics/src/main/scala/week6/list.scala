package week6

/**
  * Created by Igor Wolkov on 16.11.16.
  */
object list {

  trait List[+T] {
    def isEmpty: Boolean
    def head: T
    def tail: List[T]

    def prepend[U >: T](elem: U): List[U] = new Cons[U](elem, this)
  }

  class Cons[T](val head: T, val tail: List[T]) extends List[T] {
    def isEmpty = false
  }

  object Nil extends List[Nothing] {
    val isEmpty = true
    def head: Nothing = throw new NoSuchElementException("Nil.head")
    def tail: Nothing = throw new NoSuchElementException("Nil.head")
  }

  object List {
    def apply[T](): List[T] = Nil

    def apply[T](x1: T): List[T] =
      new Cons[T](x1, List[T]())

    def apply[T](x1: T, x2: T): List[T] =
      new Cons[T](x1, List[T](x2))

    def apply[T](x1: T, x2: T, x3: T): List[T] =
      new Cons[T](x1, List[T](x2, x3))

    def apply[T](values: T*): List[T] =
      new Cons[T](values.head, List[T](values.tail: _*))
  }
}















