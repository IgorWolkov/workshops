
def last[T](list: List[T]): T =
  list match {
    case Nil      => throw new Error("Nil.last")
    case x :: Nil => x
    case x :: xs  => last(xs)
  }

last(1 :: 2 :: 3 :: 4 :: Nil)


def init[T](list: List[T]): List[T] =
  list match {
    case Nil       => throw new Error("Nil.init")
    case x :: Nil  => Nil
    case x :: xs   => x :: init(xs)
  }

init(1 :: 2 :: 3 :: 4 :: 5 :: Nil)


def concat[T](xs: List[T], ys: List[T]): List[T] =
  xs match {
    case Nil      => ys
    case z :: zs  => z :: concat(zs, ys)
  }

concat(1 :: 2 :: 3 :: Nil, 4 :: 5 :: Nil)
concat(Nil, Nil)


def reverse[T](list: List[T]): List[T] =
  list match {
    case Nil      => Nil
    case x :: xs  => concat(reverse(xs), x :: Nil)
  }

reverse(1 :: 2 :: 3 :: 4 :: 5 :: Nil)


def quadro(list: List[Int]): List[Int] =
  list match {
    case Nil     => list
    case x :: xs => (x * x) :: quadro(xs)
  }


1 :: 2 :: 3 :: 4 :: 5 :: Nil map (x => x * x)

def pack(list: List[String]): List[List[String]] =
  list match {
    case Nil => Nil
    case x :: xs =>
      val (first, rest) =
        list.partition(_ == x)
      first :: pack(rest)
  }

pack("a" :: "a" :: "a" :: "b" :: "c" :: "c" :: "a" :: Nil)












