

val x = new Rational(1, 2)
val y = new Rational(2, 3)
val z = new Rational(3, 5)
x + y
x - y - z
x < z
x max y
-z

class Rational(x: Int, y: Int) {
  require(y > 0, "Denominator should be non-negative")

  def this(x: Int) =
    this(x, 1)

  def this(s: String) = this(s.toInt)

  private def gcd(a: Int, b: Int): Int =
    if(b == 0) a else gcd(b, a % b)

  private val g = gcd(Math.abs(x), y)

  val numer = x / g
  val denom = y / g

  def <(that: Rational): Boolean =
    this.numer * that.denom < that.numer * this.denom

  def max(that: Rational) =
    if(this < that) that else this

  def +(that: Rational): Rational =
    new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom
    )

  def unary_- : Rational = new Rational(-numer, denom)

  def -(that: Rational): Rational = this + -that

  override def toString = numer + "/" + denom

}
