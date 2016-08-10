import argonaut.Argonaut._
import argonaut._

case class Person(name: String, age: Int)

trait JsonFormat[T] {
  def write(t: T): Json
  def read(json: Json): T
}

object JsonFormat {

  def apply[T](implicit f: JsonFormat[T]) = f

  implicit object PersonJsonForma extends JsonFormat[Person] {
    def write(person: Person): Json =
      Json("person" := Json("name" := person.name, "age" := person.age))

    override def read(json: Json): Person = {
      (for {
        name <- (json.hcursor --\ "person" --\ "name").as[String]
        age <- (json.hcursor --\ "person" --\ "age").as[Int]
      } yield Person(name, age)).getOr(throw new RuntimeException)
    }
  }
}

import JsonFormat._

def roundTripOld[T](t: T)(implicit f: JsonFormat[T]) =
  f.read(f.write(t))

def roundTripModern[T: JsonFormat](t: T) = {
  val f = implicitly[JsonFormat[T]]
  f.read(f.write(t))
}

roundTripOld(Person("John", 19))
roundTripModern(Person("John", 19))