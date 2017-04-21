organization := "workshop"

name := "finch"

version := "1.0"

scalaVersion := "2.12.2"

resolvers ++= Seq(
  "Maven Central Server"          at "http://repo1.maven.org/maven2",
  "TypeSafe Repository Releases"  at "http://repo.typesafe.com/typesafe/releases/",
  "TypeSafe Repository Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
  "Sonatype"                      at "https://oss.sonatype.org/content/groups/public",
  "Twitter"                       at "http://maven.twttr.com"
)

resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
  "com.github.finagle" %% "finch-core"    % "0.14.0",
  "com.github.finagle" %% "finch-circe"   % "0.14.0",
  "io.circe"           %% "circe-generic" % "0.7.0"
)

