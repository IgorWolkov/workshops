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
trait SpaceCalculator[T] {
  def `calculate space`(t: T): Double
}
object SpaceCalculator {
  def apply[T](implicit spaceCalculator: SpaceCalculator[T]) = spaceCalculator
  implicit object BeerSpaceCalculator extends SpaceCalculator[Beer] {
    def `calculate space`(beer: Beer): Double = beer.capacity
  }
  implicit object NuggetsSpaceCalculator extends SpaceCalculator[Nuggets] {
    def `calculate space`(nuggets: Nuggets): Double = nuggets.length * nuggets.height * nuggets.width
  }
  implicit object ChipsSpaceCalculator extends SpaceCalculator[Chips] {
    def `calculate space`(chips: Chips): Double = chips.length * chips.height * chips.width
  }
}
import SpaceCalculator._
SpaceCalculator[Beer].`calculate space`(Beer(0.5))
SpaceCalculator[Nuggets].`calculate space`(Nuggets(0.2, 0.1, 0.05))
SpaceCalculator[Chips].`calculate space`(Chips(0.2, 0.1, 0.05))
trait MealHolder[T] {
  private [workshop] var `free space`: Double = 0
  private [workshop] var meal: mutable.Stack[T] = new mutable.Stack[T]()

  def put(t: T)(implicit spaceCalculator: SpaceCalculator[T]): Unit = {
    val space = spaceCalculator.`calculate space`(t)

    if(`free space` < space)
      throw new IllegalArgumentException("Not enough free space")

    `free space` -= space

    meal.push(t)
  }
  def get: T = meal.pop()
}
object MealHolder {
  def apply[T: SpaceCalculator](implicit mealHolder: MealHolder[T]) = mealHolder

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


MealHolder[Beer].put(Beer(0.5))
MealHolder[Beer].get
MealHolder[Chips]
MealHolder[Chips].put(Chips(0.5, 0.5, 0.5))
