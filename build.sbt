ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "JellyFin"
  )

lazy val akkaHttpVersion = "10.2.9"
lazy val akkaVersion = "2.6.19"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe" % "config" % "1.4.2" from "https://repo1.maven.org/maven2/com/typesafe/config/1.4.2/config-1.4.2.jar",
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
)

libraryDependencies += "ch.megard" %% "akka-http-cors" % "0.4.1"


val circeVersion = "0.14.1"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

// https://mvnrepository.com/artifact/de.heikoseeberger/akka-http-circe
libraryDependencies += "de.heikoseeberger" %% "akka-http-circe" % "1.39.2"
resolvers ++= Seq("central" at "https://repo1.maven.org/maven2/")

addCompilerPlugin("org.typelevel" % "kind-projector" % "0.13.2" cross CrossVersion.full)
