trait Monad[M[_]] {
  def point[A](a: A): M[A]
  def bind[A, B](m: M[A])(f: A => M[B]): M[B]
}

class EitherMonad[A] extends Monad[({type λ[α] = Either[A, α]})#λ] {
  def point[B](b: B): Either[A, B] = ???
  def bind[B, C](m: Either[A, B])(f: B => Either[A, C]): Either[A, C] = ???
}