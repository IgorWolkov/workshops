organization := "workshop"

name := "type-classes-api"

version := "1.0"

scalaVersion := "2.11.6"

resolvers ++= Seq(
  "Sbt plugins"                   at "https://dl.bintray.com/sbt/sbt-plugin-releases",
  "Sonatype Releases"             at "http://oss.sonatype.org/content/repositories/releases",
  "Maven Central Server"          at "http://repo1.maven.org/maven2",
  "TypeSafe Repository Releases"  at "http://repo.typesafe.com/typesafe/releases/",
  "TypeSafe Repository Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
  "Sonatype"                      at "https://oss.sonatype.org/content/groups/public",
  "Twitter"                       at "http://maven.twttr.com",
  "Edofic snapshots"              at "http://edofic.github.com/repository/snapshots",
  "Scalaz Bintray Repo"           at "http://dl.bintray.com/scalaz/releases",
  "Couchbase"                     at "http://files.couchbase.com/maven2/"
)

resolvers += Resolver.mavenLocal

val circeVersion = "0.4.1"
val finagleVersion = "0.10.0"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies ++= Seq(
  "com.github.finagle" %% "finch-core" ,
  "com.github.finagle" %% "finch-argonaut",
  "com.github.finagle" %% "finch-circe"
).map(_ % finagleVersion)

libraryDependencies ++= Seq(
  "com.typesafe"                  %  "config"                         % "1.2.1",
  "joda-time"                     %  "joda-time"                      % "2.9.2",
  "com.twitter"                   %% "twitter-server"                 % "1.20.0"
)

