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

  // We have to use def instead of object because we need typed conversion
  implicit def listFormat[T](implicit f: JsonFormat[T]) =
    new JsonFormat[List[T]] {
      override def write(list: List[T]): Json =
        Json("persons" := Json.array(list map { f.write }: _*))

      override def read(json: Json): List[T] =
        (json.hcursor --\ "persons").as[List[Json]] map { list =>
          list map { f.read }
        } getOr (throw new RuntimeException)
    }
}

import JsonFormat._

def roundTrip[T](t: T)(implicit f: JsonFormat[T]) =
  f.read(f.write(t))

roundTrip(Person("John", 19))

roundTrip(List(Person("John", 19), Person("Max", 21)))

roundTrip(List(
  List(Person("John", 19), Person("Max", 21)),
  List(Person("Merry", 16), Person("Ann", 17))
))