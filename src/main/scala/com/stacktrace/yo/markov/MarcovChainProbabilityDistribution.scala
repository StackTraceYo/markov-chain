package com.stacktrace.yo.markov

import scala.collection.mutable
import scala.util.Random

/**
  * Created by Stacktraceyo on 8/7/17.
  */
class MarcovChainProbabilityDistribution[T](initRecord: T = null, initCount: Int = 1) {

  private lazy val distributionRecords: mutable.HashMap[T, Int] = mutable.HashMap[T, Int]()
  private lazy val distRandom = new Random(System.nanoTime())

  if (initRecord != null) {
    record(initRecord, initCount)
  }

  def getNext: T = {
    pickNextToken(distRandom.nextInt(getTotal))
  }

  private def pickNextToken(roll: Int): T = {
    var current = 0
    val x = for (disRecord <- distributionRecords if roll >= current) {
      if (roll < current + disRecord._2) return disRecord._1 else {
        current += disRecord._2
      }
    }
    throw new IllegalStateException("Probabilities Did Not Add Up Properly")
  }

  def record(in: T, count: Int = 1): mutable.HashMap[T, Int] = {
    distributionRecords.get(in) match {
      case None => distributionRecords.put(in, count)
      case Some(v) => distributionRecords(in) = distributionRecords(in) + count
    }
    distributionRecords
  }

  def record(tuple: (T, Int)): mutable.HashMap[T, Int] = {
    val in = tuple._1
    val count = tuple._2
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

  def merge(distribution: MarcovChainProbabilityDistribution[T]): MarcovChainProbabilityDistribution[T] = {
    distribution.getDistributions
      .foreach(tuple => {
        record(tuple)
      })
    this
  }
}
