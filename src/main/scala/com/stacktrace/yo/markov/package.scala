package com.stacktrace.yo

/**
  * Created by Stacktraceyo on 8/8/17.
  */
package object markov {

  type FrequencyMap[T] = scala.collection.mutable.HashMap[T, MarcovChainProbabilityDistribution[T]]

  def createMarkovChain[T](distribution: FrequencyMap[T]): MarkovChain[T] = new MarkovChain[T](distribution)


}
