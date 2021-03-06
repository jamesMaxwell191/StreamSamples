name := "StreamSamples"

version := "1.0"

scalaVersion := "2.12.7"

lazy val akkaVersion = "2.5.18"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test")
    