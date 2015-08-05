# Real-Time-Tweet-Analysis
Real-Time-Tweet-Analysis

READ ME

Scala and Java Code to collect and filter the code.
1.	Install Spark from Github : https://github.com/apache/spark
2.	Start Spark-Master : go to Spark home folder/sbin . Run ./start-master
3.	Start Spark Workers :  go to Spark home folder/bin. Run ./bin/spark-class org.apache.spark.deploy.worker.Worker <<spark-master>>
4.  	Build project with sbt from TwitterTrend_Scala project from the home folder 	(twitter_classifier/scala).
sbt  assembly
5.   Run Collect.scala from Spark home folder.
./bin/spark-submit --class "com.trendanalysis.apps.twitter_classifier.Collect" --	master <spark-master> <jar-file-of-project> <output-folder> <number-of-	tweets> <number-of-seconds> <number-of-partitions> --consumerKey 	<consumer-key> --consumerSecret <consumer-secret> --accessToken 	61407213- <access-token> --accessTokenSecret <access-token-secret>
6.	Run ExamineAndTrain.scala from Spark home folder.
./bin/spark-submit --class 	"com.trendanalysis.apps.twitter_classifier.ExamineAndTrain" --	master <spark-master> <jar-file-of-project>  '<input-folder-for-tweets' 	'<output-folder>’ <number-of-clusters> <number-of-iterations>
Copy the files to TwitterTrendsDynamic project’s resource folder to cleanse 	the tweet texts more.
7.	Run the kmeans.scala from Spark home folder.
./bin/spark-submit --class "com. trendanalysis.apps.twitter_classifier.kmeans" --	master <spark-master> <jar-file-of-project>  '<input-folder>' ‘<output-folder>' 	<number-cluster> <number-iterations>
8.	Run Stanford NLP tool on each cluster result.
a.	Download jar toolkit from http://nlp.stanford.edu/software/tmt/tmt-0.4/tmt-0.4.0.jar
b.	Run jar using java –jar tmt-0.4.0.jar. Then run the code with 				LDAModeling.scala with the csv files that got generated from k-means 			clustering. 
c.	Convert the results to csv and keep it inside the resources folder of 			Java Project.
9.	Run the java code from main of ReadTweetText.java to store the k-means 	cluster results to MongoDB.
10.	Deploy the project to a Tomcat Server and see the results. 
