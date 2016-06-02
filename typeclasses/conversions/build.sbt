organization := "workshop"

name := "type-classes-conversions"

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

libraryDependencies ++= Seq(
  "com.typesafe"                  %  "config"                         % "1.2.1",
  "joda-time"                     %  "joda-time"                      % "2.9.2",
  "com.twitter"                   %% "twitter-server"                 % "1.20.0",
  "com.github.finagle"            %% "finch-core"                     % "0.10.0",
  "com.github.finagle"            %% "finch-argonaut"                 % "0.10.0",
  "com.github.finagle"            %% "finch-circe"                    % "0.10.0",
  "com.amazonaws"                 % "aws-java-sdk-dynamodb"           % "1.10.77",
  "com.github.mpilquist"          %% "simulacrum"                     % "0.7.0",
  "io.argonaut"                   %% "argonaut"                       % "6.1"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)



mainClass in (Compile, packageBin) := Some("forex.Main")

