/*
* Steps:
* 1. Create MealHolder without checking of meal's volume
* 2. Check meal volume with Wrapper on Beer, Nuggets and Chips
* 3. Add types for cool, freeze and common meal, replace implicit Wrapper with SpaceCalculator
* */
import scala.collection.mutable
case class Beer(capacity: Double)
case class Nuggets(length: Double, height: Double, width: Double)
case class Chips(length: Double, height: Double, width: Double)
trait MealHolder[T] {
  private [workshop] var `free space`: Double = 0
  private [workshop] var meal: mutable.Stack[T] = new mutable.Stack[T]()

  def put(t: T)(implicit `space calculator`: T => Double): Unit = {
    val space = `space calculator`(t)

    if(`free space` < space)
      throw new IllegalArgumentException("Not enough free space")

    `free space` -= space

    meal.push(t)
  }

  def get: T = meal.pop()
}
object MealHolder {
  def apply[T](implicit mealHolder: MealHolder[T]) = mealHolder
  implicit object Cooler extends MealHolder[Beer] {
    `free space` = 10
  }

  implicit object Freezer extends MealHolder[Nuggets] {
    `free space` = 10
  }

  implicit object Cupboard extends MealHolder[Chips] {
    `free space` = 10
  }
}
import MealHolder._
implicit val `beer space calculator`: Beer => Double =
  (beer: Beer) => beer.capacity
implicit val `nugget space calculator`: Nuggets => Double =
  (nuggets: Nuggets) => nuggets.length * nuggets.width * nuggets.height

//implicit val `chips space calculator`: Chips => Double =
//  (chips: Chips) => chips.length * chips.width * chips.height
MealHolder[Beer].put(Beer(0.5))
MealHolder[Beer].put(Beer(0.33))
MealHolder[Beer].put(Beer(8.0))
MealHolder[Beer].get
MealHolder[Beer].get
MealHolder[Beer].get
//MealHolder[Nuggets].put(Nuggets(1, 2, 5))
//MealHolder[Nuggets].put(Nuggets(1, 2, 5))
//MealHolder[Nuggets].get
MealHolder[Chips]
