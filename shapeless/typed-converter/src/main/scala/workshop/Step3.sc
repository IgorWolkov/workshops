/**
  * @see: http://stackoverflow.com/questions/38872242/map-single-type-hlist-to-hlist-of-target-types
  */

import shapeless._

case class TypedString[U](value: String)

trait Convert[In <: HList, Prescribed <: HList] {
  type Out <: HList
  def apply(i: In): Out
}

object Convert {
  type Aux[I <: HList, P <: HList, O <: HList] = Convert[I, P] { type Out = O }

  // Adapt the implicits accordingly.
  // The low priority one is left as an exercise to the reader.

  implicit val convertHNil: Convert.Aux[HNil, HNil, HNil] =
    new Convert[HNil, HNil] {
      type Out = HNil
      def apply(i: HNil): HNil = i
    }

  implicit def convertHConsTS[TS, TI <: HList, TP <: HList, TO <: HList](implicit
                                                                         c: Convert.Aux[TI, TP, TO]
                                                                        ): Convert.Aux[String :: TI, TS :: TP, TypedString[TS] :: TO] =
    new Convert[String :: TI, TS :: TP] {
      type Out = TypedString[TS] :: TO
      def apply(i: String :: TI): TypedString[TS] :: TO =
        TypedString[TS](i.head) :: c(i.tail)
    }
}

class PartiallyAppliedConvert[P <: HList] {
  def apply[I <: HList](i: I)(implicit c: Convert[I, P]): c.Out = c(i)
}

def convert[O <: HList]: PartiallyAppliedConvert[O] =
  new PartiallyAppliedConvert[O]

val list = "Hello" :: "world" :: HNil

val mapped = convert[Int :: String :: HNil](list)