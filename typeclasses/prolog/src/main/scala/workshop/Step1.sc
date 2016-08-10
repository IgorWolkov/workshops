/**
  * @see http://rnduja.github.io/2016/01/19/a_shapeless_primer/
  */

trait John
trait Carl
trait Tom

trait Child[T,U]

implicit val john_carl = new Child[John,Carl]{}
implicit val carl_tom  = new Child[Carl,Tom ]{}

trait GrandChild[T,U]

implicit def grandChild[X,Y,Z](
                                implicit
                                xy: Child[X,Y],
                                yz: Child[Y,Z]
                              ) = new GrandChild[X,Z] {}

implicitly[ GrandChild[John, Tom] ]

// No facts
//implicitly[ GrandChild[John, Carl] ]


implicitly[ GrandChild[John, _] ]

// See next step
//implicitly[ GrandChild[_, Tom] ]