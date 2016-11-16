package week5

/**
  * Created by Igor Wolkov on 16.11.16.
  */
object nat {
  abstract class Nat {
    def isZero: Boolean
    def predecessor: Nat
    def successor: Nat = new Succ(this)
    def +(that: Nat): Nat
    def -(that: Nat): Nat
  }

  object Zero extends Nat {
    def isZero = true
    def predecessor: Nat = throw new NoSuchElementException("Zero.predecessor")
    def +(that: Nat): Nat = that
    def -(that: Nat): Nat =
      if(that.isZero)
        this
      else
        throw new NoSuchElementException("Zero - smth.")
  }

  class Succ(nat: Nat) extends Nat {
    def isZero = false

    def predecessor: Nat = nat

    def +(that: Nat): Nat = new Succ(nat + that)

    def -(that: Nat): Nat =
      if(that.isZero) this
      else nat - that.predecessor
  }
}
