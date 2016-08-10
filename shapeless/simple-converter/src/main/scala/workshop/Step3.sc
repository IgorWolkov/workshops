/**
  * @see: http://stackoverflow.com/questions/36515901/shapeless-map-hlist-depending-on-target-types
  */

import java.net.URL
import shapeless._

val name = "Stackoverflow"
val url = "https://stackoverflow.com/q"
val list = name :: url :: HNil

//val mapped: String :: URL :: HNil = ???

trait Convert[I <: HList, O <: HList] { def apply(i: I): O }

sealed class LowPriorityConvertInstances {
  implicit def convertHCons[H, T <: HList, TO <: HList](implicit
                                                        c: Convert[T, TO]
                                                       ): Convert[H :: T, H :: TO] = new Convert[H :: T, H :: TO] {
    def apply(i: H :: T): H :: TO = i.head :: c(i.tail)
  }
}