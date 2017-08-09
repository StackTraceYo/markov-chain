package com.stacktrace.yo

import java.io.FileInputStream
import java.nio.file.Paths

import com.stacktrace.yo.markov.MarkovTextChainGenerator


object TrumpTweetGenerator {


  def main(args: Array[String]): Unit = {
    val input = "src/main/resources/data/trumptweets.txt"

    println(new MarkovTextChainGenerator(2)
      .generateDistributionFromStream(new FileInputStream(Paths.get(input).toFile))
      .generate()
      .mkString(" ")
    )
  }
}
