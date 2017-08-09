package com.stacktrace.yo

import java.io.FileInputStream
import java.nio.file.Paths

import com.stacktrace.yo.markov.MarkovTextChainGenerator


object TrumpTweetGenerator extends App {

  val input = "src/main/resources/data/trumptweets.txt"

  new MarkovTextChainGenerator(2)
    .generateDistributionFromStream(new FileInputStream(Paths.get(input).toFile))
    .generate(25)
    .foreach(println)


}
