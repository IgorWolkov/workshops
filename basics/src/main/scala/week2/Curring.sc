
def sum(f: Int => Int)(a: Int, b: Int): Int =
  if(a > b) 0
  else a + sum(f)(a + 1, b)

val value = sum(x => x)(1, 4)

val functionalValue: (Int, Int) => Int = sum(x => x * x)
functionalValue(1, 4)
