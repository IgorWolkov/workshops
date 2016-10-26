
def factorial(n: Int): Int = {

  def `recursive factorial`(acc: Int, n: Int): Int =
    if(n == 0) acc
    else `recursive factorial`(acc * n, n - 1)

  `recursive factorial`(1, n)
}

factorial(5)