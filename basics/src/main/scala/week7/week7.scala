package week7

/**
  * Created by st on 29.11.16.
  */
object week7 extends App {
  object expr {
    def show(e: Expr): String =
      e match {
        case Number(n)        => n.toString
        case Sum(left, right) =>
          s"( ${show(left)} + ${show(right)} )"
      }
  }

  println(expr.show(Sum(Number(1), Number(44))))

  val list = 2 :: 3 :: 5 :: 7 :: 11 :: Nil

  val five = 5
  val Seven = 7

  list match {
    case x :: y :: `five` :: Seven :: zs if x * x == 4 => println("pattern matching is working")
  }
}
