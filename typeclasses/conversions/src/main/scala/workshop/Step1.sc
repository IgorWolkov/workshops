import argonaut.Argonaut._
import argonaut.Parse._
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

JsonFormat[Person].write(Person("John", 19))

JsonFormat[Person].read(
  Parse.parseOption("""{ "person" : { "name" : "John", "age" : 19 } }""").get
)

