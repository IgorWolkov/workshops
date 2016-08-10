organization := "workshop"

name := "type-classes-conversions"

version := "1.0"

scalaVersion := "2.11.6"

resolvers ++= Seq(
  "Maven Central Server"          at "http://repo1.maven.org/maven2",
  "TypeSafe Repository Releases"  at "http://repo.typesafe.com/typesafe/releases/",
  "TypeSafe Repository Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"
)

libraryDependencies ++= Seq(
  "io.argonaut"                   %% "argonaut"                       % "6.1"
)


