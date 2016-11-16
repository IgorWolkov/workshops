package week5

/**
  * Created by Igor Wolkov on 16.11.16.
  */
object list {

  trait List[T] {
    def isEmpty: Boolean
    def head: T
    def tail: List[T]
  }

  class Cons[T](val head: T, val tail: List[T]) extends List[T] {
    def isEmpty = false
  }

  class Nil[T] extends List[T] {
    val isEmpty = true
    def head: Nothing = throw new NoSuchElementException("Nil.head")
    def tail: Nothing = throw new NoSuchElementException("Nil.head")
  }

  def singleton[T](value: T) = new Cons[T](value, new Nil[T])

  def nth[T](n: Int, list: List[T]): T =
    if (list.isEmpty) throw new IndexOutOfBoundsException(s"Nil($n)")
    else if (n < 0) throw new IndexOutOfBoundsException(s"n should be non-negative")
    else if (n == 0) list.head
    else nth(n - 1, list.tail)
}















