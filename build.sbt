name := "markov-chain"

version := "1.0"

scalaVersion := "2.11.8"
val akkaVersion = "2.5.3"

libraryDependencies ++= {
  Seq(
    "org.apache.spark" %% "spark-sql" % "2.2.0"
  )
}