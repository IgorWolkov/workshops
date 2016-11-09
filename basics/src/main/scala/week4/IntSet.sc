
abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
  def union(that: IntSet): IntSet
}

object Empty extends IntSet {
  override def incl(x: Int): IntSet =
    new NonEmpty(x, Empty, Empty)

  override def contains(x: Int): Boolean = false


  override def union(that: IntSet): IntSet = that

  override def toString = "."
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {

  override def incl(x: Int): IntSet =
    if(x < elem) new NonEmpty(elem, left incl x, right)
    else if(x > elem) new NonEmpty(elem, left, right incl x)
    else this

  override def contains(x: Int): Boolean =
    if(x < elem) left contains x
    else if(x > elem) right contains x
    else true


  override def union(that: IntSet): IntSet =
    if(that == Empty)
      this
    else
    // Why this doesn't work?
    // (that incl elem).union(left).union(right)
      ((left union right) union that) incl elem

  override def toString = s"{$left $elem $right}"
}

val set1 = new NonEmpty(4, Empty, Empty)
val set2 = new NonEmpty(5, Empty, Empty)
val set3 = new NonEmpty(-1, Empty, Empty)

(set1 union set2) union set3

