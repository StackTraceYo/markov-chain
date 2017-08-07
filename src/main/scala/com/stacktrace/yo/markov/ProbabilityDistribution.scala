package com.stacktrace.yo.markov

import scala.collection.mutable

/**
  * Created by Stacktraceyo on 8/7/17.
  */
class ProbabilityDistribution[T] {

  val distributionRecords: mutable.HashMap[T, Int] = mutable.HashMap[T, Int]()

  def record(in: T, count: Int = 1): Unit = {
    distributionRecords.get(in) match {
      case None => distributionRecords.put(in, count)
      case Some(v) => distributionRecords(in) = distributionRecords(in) + count
    }
  }

  def getTotal: Int = {
    distributionRecords.size
  }

  def getFrequenceOfKey(key: T): Int = {
    distributionRecords.getOrElse(key, 0)
  }
}