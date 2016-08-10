type N[A] = A => Nothing
type D[T, U] = N[N[T] with N[U]]
//type M[A] = N[N[A]]

type W[T, U] = N[T] with N[U]


trait T1 {
  val int: Int
}

trait T2 {
  val str: String
}

type S[A] = A => String

type SS[A, B] = S[A] with S[B]

//implicitly[S[Int] <:< (SS[Int, String])]


trait A
trait B

implicitly[A <:< (A with B)]
