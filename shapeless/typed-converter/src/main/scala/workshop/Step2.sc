/**
  * @see: http://stackoverflow.com/questions/38872242/map-single-type-hlist-to-hlist-of-target-types
  */

import shapeless._

case class TypedString[U](value: String)

trait Convert[I <: HList, O <: HList] { def apply(i: I): O }

object Convert extends LowPriorityConvertInstances {
  implicit val convertHNil: Convert[HNil, HNil] = new Convert[HNil, HNil] {
    def apply(i: HNil): HNil = i
  }

  implicit def convertHConsTS[TS, T <: HList, TO <: HList](implicit
                                                           c: Convert[T, TO]
                                                          ): Convert[String :: T, TypedString[TS] :: TO] =
    new Convert[String :: T, TypedString[TS] :: TO] {
      def apply(i: String :: T): TypedString[TS] :: TO = TypedString[TS](i.head) :: c(i.tail)
    }
}

sealed class LowPriorityConvertInstances {
  implicit def convertHCons[H, T <: HList, TO <: HList](implicit
                                                        c: Convert[T, TO]
                                                       ): Convert[H :: T, H :: TO] = new Convert[H :: T, H :: TO] {
    def apply(i: H :: T): H :: TO = i.head :: c(i.tail)
  }
}


class PartiallyAppliedConvert[O <: HList] {
  def apply[I <: HList](i: I)(implicit c: Convert[I, O]): O = c(i)
}

def convert[O <: HList]: PartiallyAppliedConvert[O] =
  new PartiallyAppliedConvert[O]

val list = "Hello" :: "world" :: HNil

val mapped: TypedString[Int] :: TypedString[String] :: HNil =
  convert[TypedString[Int] :: TypedString[String] :: HNil](list)