package com.stacktrace.yo.markov

/**
  * Created by Stacktraceyo on 8/7/17.
  */
class WordSequence(words: String) {

  val splitTokens: List[String] = words.split(" ").toList
  val queue = scala.collection.immutable.Queue(splitTokens: _*)

  def getToken() = {
    queue.dequeue._1
  }

}

object WordSequence extends App {

  val x = new WordSequence("hello world")
  println(x.getToken())
  println(x.getToken())

}
