package com.stacktrace.yo.markov

import scala.collection.mutable

/**
  * Created by Stacktraceyo on 8/7/17.
  */
class ProbabilityDistribution[T](initRecord: T = null, initCount: Int = 1) {

  private lazy val distributionRecords: mutable.HashMap[T, Int] = mutable.HashMap[T, Int]()
  if (initRecord != null) {
    record(initRecord, initCount)
  }

  def record(in: T, count: Int = 1): mutable.HashMap[T, Int] = {
    distributionRecords.get(in) match {
      case None => distributionRecords.put(in, count)
      case Some(v) => distributionRecords(in) = distributionRecords(in) + count
    }
    distributionRecords
  }

  def getTotal: Int = {
    distributionRecords.size
  }

  def getFrequenceOfKey(key: T): Int = {
    distributionRecords.getOrElse(key, 0)
  }

  def getProbabilityOfKey(key: T): Double = {
    distributionRecords.getOrElse(key, 0) / getTotal.toDouble
  }

  def getDistributions: mutable.HashMap[T, Int] = {
    distributionRecords
  }
}
