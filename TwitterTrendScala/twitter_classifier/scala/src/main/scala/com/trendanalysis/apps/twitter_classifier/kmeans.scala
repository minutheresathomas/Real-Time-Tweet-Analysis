package com.trendanalysis.apps.twitter_classifier

import com.google.gson.{GsonBuilder, JsonParser}
import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import scala.io.Source
import java.io._

object kmeans {
  
  def main(args: Array[String]) {
    // Process program arguments and set properties
    if (args.length < 3) {
      System.err.println("Usage: " + this.getClass.getSimpleName +
        " <tweetInput> <outputModelDir> <numClusters> <numIterations>")
      System.exit(1)
    }
    val Array(tweetInput, outputModelDir, Utils.IntParam(numClusters), Utils.IntParam(numIterations)) = args

    val conf = new SparkConf().setAppName(this.getClass.getSimpleName)
    val sc = new SparkContext(conf)
    
    // Pretty print some of the tweets.
    val tweets = sc.textFile(tweetInput)
    
    println("--- Training the model and persist it")
    val parsedData = tweets.map {
      line => Vectors.dense(line.split(',').slice(1,3).map(_.toDouble))
    }
    val model = KMeans.train(parsedData, numClusters, numIterations)
    val clusterCenters = model.clusterCenters.map(_.toArray)
    val tweetsByGroup = tweets.map {_.split(',').slice(1, 3).map(_.toDouble)}
       .groupBy{rdd => model.predict(Vectors.dense(rdd))}
       .collect()

   val pw = new PrintWriter(new File("/Users/minu/Desktop/239Research/clusterResult.txt" ))
   val pw1 = new PrintWriter(new File("/Users/minu/Desktop/239Research/clusterResult1.txt"))
   
   for((group, tweets1) <- tweetsByGroup){
	println(s"\nCLUSTER:"+ group)
	pw1.write(group + "," + clusterCenters(group)(0) + "," + clusterCenters(group)(1))
        println("Latitude centre:" + clusterCenters(group)(0) + ", Longitude center:" +  clusterCenters(group)(1))
	for(coordinate <- tweets1){
	    pw.write(group + "," + clusterCenters(group)(0) + "," + clusterCenters(group)(1) + "," + coordinate(0)+","+ coordinate(1) + "\n")
	    println("Latitude:" + coordinate(0) + ", longitude:" + coordinate(1))

	}
  //Cache the vectors RDD since it will be used for all the KMeans iterations.
    //val vectors = texts.map(Utils.featurize).cache()
    //vectors.count()  // Calls an action on the RDD to populate the vectors cache.

    }

   pw.close
   pw1.close
   
   sc.stop()
   
  }
}
