object session {

  def sqrt(x: Double) = {

    def sqrtIter(guess: Double): Double =
      if(isGoodEnough(guess)) guess
      else sqrtIter(improve(guess))

    def isGoodEnough(guess: Double) =
      abs(guess * guess - x) < 0.001

    def improve(guess: Double) =
      (guess + x / guess) / 2

    def abs(x: Double): Double =
      if(x > 0) x else - x


    sqrtIter(1)
  }


  sqrt(2)
}