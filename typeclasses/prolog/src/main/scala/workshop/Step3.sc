/**
  * @see http://rnduja.github.io/2016/01/19/a_shapeless_primer/
  */

trait John
trait Carl
trait Tom

trait Child[T,U]

implicit val fallback: GrandChild[_,_] = new GrandChild[Nothing,Nothing]{}

implicit val john_carl = new Child[John,Carl]{}
implicit val carl_tom  = new Child[Carl,Tom ]{}

trait GrandChild[T,U]

implicit def grandChild[X,Y,Z](
                                implicit
                                xy: Child[X,Y],
                                yz: Child[Y,Z]
                              ) = new GrandChild[X,Z] {}


implicit def grandChildReordered[X,Y,Z](
                                         implicit
                                         yz: Child[Y,Z],
                                         xy: Child[X,Y]
                                       ) = new GrandChild[X,Z] {}
implicitly[ GrandChild[_, Tom] ]

implicitly[ GrandChild[John, _] ]

implicitly[ GrandChild[_, _] ]