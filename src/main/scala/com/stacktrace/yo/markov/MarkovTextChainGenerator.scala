package com.stacktrace.yo.markov

import java.io.InputStream

import scala.io.Source

class MarkovTextChainGenerator {

  def generateDistributionFromStream(inputStream: InputStream): MarkovChain[String] = {
    markovChainFrom(
      Source
        .fromInputStream(inputStream)
        .getLines()
        .map(generateFrequency)
        .reduceLeft(mergeDistributions)
    )
  }

  private val markovChainFrom: (FrequencyMap[String]) => MarkovChain[String] = (map: FrequencyMap[String]) => {
    createMarkovChain[String](map)
  }

  private def generateFrequency(input: String): FrequencyMap[String] = {
    val queue = scala.collection.immutable.Queue(input.split(" ").toList: _*)
    val frequencyMap: FrequencyMap[String] = new FrequencyMap()
    Iterator.iterate(queue) { qi =>
      val (prefix, nextTokens) = qi.dequeue
      if (nextTokens.nonEmpty) {
        val follow = nextTokens.head
        frequencyMap.get(prefix) match {
          case None => frequencyMap.put(prefix, new MarcovChainProbabilityDistribution[String](follow))
          case Some(v) => v.record(follow)
        }
      }
      nextTokens
    }.takeWhile(q => q.nonEmpty).foreach(identity)
    frequencyMap
  }

  private def normalize(string: String): String = {
    string.toLowerCase.trim
  }

  private def mergeDistributions(left: FrequencyMap[String], right: FrequencyMap[String]) = {
    right.foreach(tuple => {
      val rightKey = tuple._1
      val rightValue = tuple._2
      left.get(rightKey) match {
        case None => left.put(rightKey, rightValue)
        case Some(leftMap) => leftMap.merge(rightValue)
      }
    })
    left
  }
}

