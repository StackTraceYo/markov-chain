package com.stacktrace.yo.markov

import java.io.InputStream

import com.stacktrace.yo.markov.MarkovTextChainGenerator._

import scala.collection.immutable.Queue
import scala.collection.mutable
import scala.io.Source
import scala.util.matching.Regex

class MarkovTextChainGenerator(order: Integer = 1) {

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
    val queue: Queue[String] =
      Queue(
        input
          .split(" ")
          .toList
          .map(_.trim.toLowerCase)
//          .filter(twitterStuff)
          .map(normalize)
          .filter(whiteSpace): _*
      )

    val frequencyMap: FrequencyMap[String] = new FrequencyMap()
    Iterator.iterate(queue) { qi =>
      val qCurrent = new mutable.Queue[String]() ++ qi
      val orderString =
        for (a <- 1 until order) yield {
          if (qCurrent.nonEmpty) qCurrent.dequeue else ""
        }
      val tokenString = orderString
        .filter(!_.isEmpty)
        .mkString(" ")
      if (qCurrent.nonEmpty) {
        val follow = qCurrent.head
        frequencyMap.get(tokenString) match {
          case None => frequencyMap.put(tokenString, new MarcovChainProbabilityDistribution[String](follow))
          case Some(v) => v.record(follow)
        }
      }
      qi.dequeue._2
    }.takeWhile(q => q.nonEmpty).foreach(identity)
    frequencyMap
  }

  private def twitterStuff(in: String): Boolean = {
    val matches = dropList.map(_.findFirstIn(in).isDefined)

    !matches.contains(true)
  }

  private def whiteSpace(in: String): Boolean = {
    !in.trim.isEmpty
  }

  private def normalize(in: String): String = {
    in.trim.toLowerCase
      .replaceAll("""([\p{Punct}&&[^.$]]|\b\p{IsLetter}{1,2}\b)\s*""", "")
      .replaceAll("'|’|-|\"|`|~|“|\\.+|", "")
      .stripSuffix("-")
      .stripSuffix("'")
      .stripSuffix("...")
      .stripSuffix("..")
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

object MarkovTextChainGenerator {
  val word: Regex = "[^\\s\\.!?,:]+".r
  val whiteSpace: Regex = "\\s+".r
  val extLink: Regex = "[http|https]?://[^\\s]+".r
  val twitterMention: Regex = "\\.@[^\\s]+".r
  val twitterHash: Regex = "\\.#[^\\s]+".r
  val dateTime: Regex = "\\d+[\\.,:]+\\d+".r

  val dropList = List(extLink, twitterMention, dateTime)
}

