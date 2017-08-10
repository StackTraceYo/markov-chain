package com.stacktrace.yo;


import com.stacktrace.yo.markov.MarkovTextChainGenerator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

/**
 * Created by Ahmad on 8/9/2017.
 */
public class TrumpTweetGeneratorJava {

    public static void main(String[] args) throws FileNotFoundException {
        String input = "src/main/resources/data/trumptweets.txt";

        System.out.println(new MarkovTextChainGenerator(2)
                .generateDistributionFromStream(new FileInputStream(Paths.get(input).toFile()))
                .generate(20).mkString(" "));

    }

}
