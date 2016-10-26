
def incr(a: Int) = a + 1

val functionalValue: Int => Int = incr
functionalValue(7)

val deanonymizedFunctionalValue = (a: Int) => a * 2
deanonymizedFunctionalValue(8)