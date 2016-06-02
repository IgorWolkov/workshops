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
  private [workshop] var meal: mutable.Stack[T] = new mutable.Stack[T]()

  def put(t: T): Unit = meal.push(t)
  def get: T = meal.pop()
}

object MealHolder {
  def apply[T](implicit mealHolder: MealHolder[T]) = mealHolder

  implicit object Cooler extends MealHolder[Beer]
  implicit object Freezer extends MealHolder[Nuggets]
  implicit object Cupboard extends MealHolder[Chips]
}

import MealHolder._

MealHolder[Beer].put(Beer(0.5))
MealHolder[Beer].put(Beer(0.33))
MealHolder[Beer].put(Beer(8.0))
MealHolder[Beer].get
MealHolder[Beer].get
MealHolder[Beer].get
MealHolder[Nuggets].put(Nuggets(1, 2, 5))
MealHolder[Nuggets].get
