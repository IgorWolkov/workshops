import shapeless._

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

implicit val hnilSized = Sized[HNil](_ => 0)

implicit def hconsSized[H, T <: HList](implicit hSized: Sized[H], sized: Sized[T]) =
  Sized[H :: T] { l =>
    hSized.size(l.head) + sized.size(l.tail)
  }

import Sized.size

//size(1)
//size("hello")

size(HNil)

size(1 :: "hello" :: HNil)

size(1 :: "" :: 1 :: HNil)

size(1 :: (1 :: "hello" :: HNil) :: HNil)