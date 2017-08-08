package com.stacktrace.yo.markov

/**
  * Created by Ahmad on 8/7/2017.
  */
class MarkovTextChain(input: String) {

  type FrequencyMap = scala.collection.mutable.HashMap[String, ProbabilityDistribution[String]]
  val queue = scala.collection.immutable.Queue(input.split(" ").toList: _*)

  def generate(): FrequencyMap = {
    val frequencyMap: FrequencyMap = new FrequencyMap()
    Iterator.iterate(queue) { qi =>
      val (prefix, nextTokens) = qi.dequeue
      if (nextTokens.nonEmpty) {
        val follow = nextTokens.head
        frequencyMap.get(prefix) match {
          case None => frequencyMap.put(prefix, new ProbabilityDistribution[String](follow))
          case Some(v) => v.record(follow)
        }
      }
      nextTokens
    }.takeWhile(q => q.nonEmpty).foreach(identity)
    frequencyMap
  }

  def normalize(string: String): String = {
    string.toLowerCase.trim
  }
}

object MarkovTextChain extends App {


  val chain = new MarkovTextChain("According to Wikipedia, a Markov Chain is a random process where the next state is dependent on the previous state. This is a little difficult to understand, so I'll try to explain it better")
  chain.generate()
    .foreach(tuple => {
      println("\"" + tuple._1 + "\"" + " has distribution")
      tuple._2.getDistributions
        .foreach(tuple2 => {
          println(tuple2._1 + " : " + tuple._2.getProbabilityOfKey(tuple2._1))
        })
      println
    })
}


