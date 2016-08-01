trait Sized[-T] {
  def size(t: T): Int
}

object Sized {
  // Helper to get the size for a value
  def size[T](t: T)(implicit sized: Sized[T]): Int = sized.size(t)

  // Helper to create a type class instance from a function from T to Int
  def apply[T](f: T => Int) = new Sized[T] {
    override def size(t: T): Int = f(t)
  }
}

implicit val intSized: Sized[Int] = Sized(_ => 1)

implicit val stringSized: Sized[String] = Sized(s => s.length)



import Sized.size

size(1)

size("hello")