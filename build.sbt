name := "markov-chain"

version := "1.0"

scalaVersion := "2.11.8"
val akkaVersion = "2.5.3"

libraryDependencies ++= {
  Seq(
    "com.typesafe.akka" % "akka-actor_2.11" % akkaVersion,
    "com.typesafe.akka" % "akka-stream_2.11" % akkaVersion,
    "de.knutwalker" %% "akka-stream-json" % "3.4.0",
    "de.knutwalker" %% "akka-stream-circe" % "3.4.0",
    "de.knutwalker" %% "akka-http-circe" % "3.4.0"
  )
}