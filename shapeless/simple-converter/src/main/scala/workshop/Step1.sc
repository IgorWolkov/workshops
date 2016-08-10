/**
  * @see: http://stackoverflow.com/questions/36515901/shapeless-map-hlist-depending-on-target-types
  */

import java.net.URL
import shapeless._



val name = "Stackoverflow"
val url = "https://stackoverflow.com/q"
val list = name :: url :: HNil

val mapped: String :: URL :: HNil = ???