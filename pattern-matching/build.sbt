organization := "workshop"

name := "pattern-matching"

version := "1.0"

scalaVersion := "2.11.6"

resolvers ++= Seq(
  "Maven Central Server"          at "http://repo1.maven.org/maven2",
  "TypeSafe Repository Releases"  at "http://repo.typesafe.com/typesafe/releases/",
  "TypeSafe Repository Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
  "Sonatype"                      at "https://oss.sonatype.org/content/groups/public",
  "Twitter"                       at "http://maven.twttr.com"
)

resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value
)

