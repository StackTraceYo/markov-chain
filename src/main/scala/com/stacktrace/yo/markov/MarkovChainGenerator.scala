package com.stacktrace.yo.markov

import java.nio.file.Paths

import akka.actor.ActorSystem
import akka.stream.scaladsl.{FileIO, Flow, Framing, Source}
import akka.stream.{ActorMaterializer, IOResult}
import akka.util.ByteString
import akka.{Done, NotUsed}
import com.stacktrace.yo.domain.TrumpTweet
import io.circe.Decoder

import scala.concurrent.Future

object MarkovChainGenerator extends App {


  implicit val as = ActorSystem()
  implicit val ec = as.dispatcher
  implicit val mat = ActorMaterializer()
  implicit val decoderBar: Decoder[List[TrumpTweet]] = Decoder[List[TrumpTweet]]


  val input = "src/main/resources/data/trumptweets.txt"
  var i = 0

  val inputSource: Source[String, Future[IOResult]] = FileIO.fromPath(Paths.get(input))
    .via(Framing.delimiter(ByteString("\n"), 1024, allowTruncation = true))
    .map(_.utf8String)

  val frequencyMap = scala.collection.mutable.HashMap[String, ProbabilityDistribution[String]]()


  val flow = Flow[String]
    .map(line => {

    })


}
