package com.stacktrace.yo.markov

import scala.collection.mutable.ListBuffer
import scala.util.Random

/**
  * Created by Stacktraceyo on 8/8/17.
  */
class MarkovChain[T](distribution: FrequencyMap[T]) {

  val random: Random = new Random(System.nanoTime())

  def generate(maxCount: Int, countChars: Boolean = false): List[T] = {
    var tokenList = ListBuffer[T]()
    var (token, currentDist) = getRandom
    tokenList += token
    Iterator.iterate(tokenList) { tokenList =>
      distribution.get(tokenList.last) match {
        case None =>
        case Some(v) =>
          tokenList += v.getNext
      }
      tokenList
    }.takeWhile(_.size < maxCount).foreach(identity)
    tokenList.toList
  }

  private def getRandom: (T, MarcovChainProbabilityDistribution[T]) = {
    val startToken =
      distribution
        .keySet
        .toList(random.nextInt(distribution.size))
    (startToken, distribution(startToken))
  }

  def printDistribution(): Unit = {
    if (distribution == null) {
      throw new IllegalStateException("Distribution needs To Be Generated Before Printed")
    }
    distribution.foreach(tuple => {
      println("\"" + tuple._1 + "\"" + " has distribution")
      tuple._2.getDistributions
        .foreach(tuple2 => {
          println(tuple2._1 + " : " + tuple._2.getProbabilityOfKey(tuple2._1))
        })
      println
    })
  }
}