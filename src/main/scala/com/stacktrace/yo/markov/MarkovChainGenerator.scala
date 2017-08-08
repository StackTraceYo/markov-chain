package com.stacktrace.yo.markov

import java.nio.file.Paths

import akka.actor.ActorSystem
import akka.stream.scaladsl.{FileIO, Flow, Framing, Source}
import akka.stream.{ActorMaterializer, IOResult}
import akka.util.ByteString

import scala.concurrent.Future

object MarkovChainGenerator extends App {


  implicit val as = ActorSystem()
  implicit val ec = as.dispatcher
  implicit val mat = ActorMaterializer()


  val input = "src/main/resources/data/trumptweets.txt"
  var i = 0

  val inputSource: Source[String, Future[IOResult]] = FileIO.fromPath(Paths.get(input))
    .via(Framing.delimiter(ByteString("\n"), 1024, allowTruncation = true))
    .map(_.utf8String)

  val generateChains = Flow[String]
    .map(line => {
      new MarkovTextChain(line).generate()
    })


  inputSource
    .via(generateChains)
    .via(Flow[MarkovTextChain#FrequencyMap]
      .map(freq =>
        freq.foreach(tuple => {
          println("\"" + tuple._1 + "\"" + " has distribution")
          tuple._2.getDistributions
            .foreach(tuple2 => {
              println(tuple2._1 + " : " + tuple._2.getProbabilityOfKey(tuple2._1))
            })
          println
        })
      )
    ).runForeach(x => x)


}
