package com.stacktrace.yo.markov

/**
  * Created by Stacktraceyo on 8/8/17.
  */
class MarkovChain[T](distribution: FrequencyMap[T]) {

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