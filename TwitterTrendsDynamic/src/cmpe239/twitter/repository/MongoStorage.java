package cmpe239.twitter.repository;

import java.awt.List;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import cmpe239.twitter.domain.TagFrequency;
import cmpe239.twitter.domain.Tags;
import cmpe239.twitter.domain.Tweets;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoStorage {
	private static MongoClient mongoClient;
	private static DB db;
	private static DBCollection tweetCollection = null;
	private static DBCollection tagCollection = null;

	public MongoStorage(){
		try {
			MongoClientURI uri = new MongoClientURI("mongodb://cmpe239:twitter@ds053140.mongolab.com:53140/twitter-trend-analysis");
			mongoClient = new MongoClient(uri);
			db = mongoClient.getDB( "twitter-trend-analysis" );
			tweetCollection = db.getCollection("tweets25");
			tagCollection = db.getCollection("tags25");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	//	@Autowired
	//	MongoOperations mongoOps;

	public void createTweets(Tweets tweetData)
	{
		BasicDBObject tweet = new BasicDBObject();
		tweet.append("cluster", tweetData.getClusterNumber());
		tweet.append("centerLat", tweetData.getLatCenter());
		tweet.append("centerLong", tweetData.getLongCenter());
		tweet.append("latitude", tweetData.getLatitude());
		tweet.append("longitude", tweetData.getLongitude());
		tweet.append("text", tweetData.getText());

		tweetCollection.insert(tweet);
		//		return null;

	}

	public void createTweetsList(ArrayList<Tweets> data)
	{
		ArrayList<DBObject> documents = new ArrayList<DBObject>();

		for(Tweets tweetData : data) {
			BasicDBObject tweet = new BasicDBObject();
			tweet.put("cluster", tweetData.getClusterNumber());
			tweet.put("centerLat", tweetData.getLatCenter());
			tweet.put("centerLong", tweetData.getLongCenter());
			tweet.put("latitude", tweetData.getLatitude());
			tweet.put("longitude", tweetData.getLongitude());
			tweet.put("text", tweetData.getText());
			documents.add(tweet);
		}

		tweetCollection.insert(documents);
	}

	public void createTagsList(ArrayList<Tags> data)
	{
		ArrayList<DBObject> documents = new ArrayList<DBObject>();

		for(Tags tag : data) {
			BasicDBObject tweet = new BasicDBObject();
			tweet.put("cluster", tag.getCluster());
			tweet.put("centerLatitude", tag.getLatCenter());
			tweet.put("centerLongitude", tag.getLongCenter());
			tweet.put("location", tag.getLocation());
			Iterator<TagFrequency> it = tag.getTagsWithFreq().iterator();
			ArrayList<BasicDBObject> tagList = new ArrayList<BasicDBObject>();
			while(it.hasNext())
			{
				TagFrequency tweettag = it.next();
				tagList.add(new BasicDBObject("tag", tweettag.getTag()).append("frequency", tweettag.getFrequency()));
			}
			tweet.put("tagsWithFreq", tagList);
			documents.add(tweet);
		}

		tagCollection.insert(documents);
		//		return null;

	}

	public ArrayList<Tweets> getTweetPerCluster(int cluster)
	{
		ArrayList<Tweets> tweets = new ArrayList<Tweets>();
		BasicDBObject query = new BasicDBObject("cluster", cluster);
		DBCursor cursor = tweetCollection.find(query);
		try {
			while(cursor.hasNext())
			{
				DBObject tweetObject = cursor.next();
				Tweets tweet = new Tweets();
				tweet.setClusterNumber((Integer) tweetObject.get("cluster"));
				tweet.setLatCenter((Double) tweetObject.get("centerLat"));
				tweet.setLongCenter((Double) tweetObject.get("centerLong"));
				tweet.setLatitude((Double) tweetObject.get("latitude"));
				tweet.setLongitude((Double) tweetObject.get("longitude"));
				tweet.setText((String) tweetObject.get("text"));
				// add the current poll to list of polls for display
				tweets.add(tweet);
			}
		} finally {
			cursor.close();
		}
		return tweets;
	}

	public Tags getAllTagsPerCluster(int cluster)
	{
		BasicDBObject query = new BasicDBObject("cluster", cluster);
		DBCursor cursor = tagCollection.find(query);
		ArrayList<TagFrequency> tagf = new ArrayList<TagFrequency>();
		Tags tag = new Tags();

		try {
			while(cursor.hasNext())
			{
				DBObject tagObject = cursor.next();
				tag.setCluster((Integer) tagObject.get("cluster"));
				tag.setLatCenter((Double) tagObject.get("centerLatitude"));
				tag.setLongCenter((Double) tagObject.get("centerLongitude"));
				tag.setLocation((String) tagObject.get("location"));
				//				ArrayList<TagFrequency> tagf = new ArrayList<TagFrequency>();
				BasicDBList choiceList = (BasicDBList) tagObject.get("tagsWithFreq");
				for(int i=0; i< choiceList.size(); i++)
				{
					BasicDBObject individualTag = (BasicDBObject) choiceList.get(i);
					TagFrequency tfreq = new TagFrequency();
					tfreq.setFrequency((Integer) individualTag.get("frequency"));
					tfreq.setTag((String) individualTag.get("tag"));
					tagf.add(tfreq);
				}
				tag.setTagsWithFreq(tagf);
			}
		} finally {
			cursor.close();
		}
		return tag;
	}

	public ArrayList<Tags> getAllTagsCluster()
	{
		DBCursor cursor = tagCollection.find();
		//		ArrayList<TagFrequency> tagf = new ArrayList<TagFrequency>();
		ArrayList<Tags> tagsList = new ArrayList<Tags>();

		try {
			while(cursor.hasNext())
			{
				Tags tag = new Tags();
				DBObject tagObject = cursor.next();
				tag.setCluster((Integer) tagObject.get("cluster"));
				tag.setLatCenter((Double) tagObject.get("centerLatitude"));
				tag.setLongCenter((Double) tagObject.get("centerLongitude"));
				tag.setLocation((String) tagObject.get("location"));
				ArrayList<TagFrequency> tagf = new ArrayList<TagFrequency>();
				BasicDBList choiceList = (BasicDBList) tagObject.get("tagsWithFreq");
				for(int i=0; i< choiceList.size(); i++)
				{
					BasicDBObject individualTag = (BasicDBObject) choiceList.get(i);
					TagFrequency tfreq = new TagFrequency();
					tfreq.setFrequency((Integer) individualTag.get("frequency"));
					tfreq.setTag((String) individualTag.get("tag"));
					tagf.add(tfreq);
				}
				tag.setTagsWithFreq(tagf);
				tagsList.add(tag);
			}
		} finally {
			cursor.close();
		}
		return tagsList;
	}

	public ArrayList<String> getDistinctLocations() {
		//		BasicDBObject dbObject=new BasicDBObject();
		java.util.List cursor = tagCollection.distinct("location");
		ArrayList<String> locsList = new ArrayList<String>();

		try {
			locsList = (ArrayList<String>) cursor;
		} finally {

		}
		return locsList;
	}

	public ArrayList<Tags> getTagsPerLocation(String loc)
	{
		System.out.println("Location Mongo : "+loc);
		BasicDBObject query = new BasicDBObject("location", loc);
		DBCursor cursor = tagCollection.find(query);
		ArrayList<Tags> tagsList = new ArrayList<Tags>();
		try {
			while(cursor.hasNext())
			{
				Tags tag = new Tags();
				DBObject tagObject = cursor.next();
				tag.setCluster((Integer) tagObject.get("cluster"));
				tag.setLatCenter((Double) tagObject.get("centerLatitude"));
				tag.setLongCenter((Double) tagObject.get("centerLongitude"));
				tag.setLocation((String) tagObject.get("location"));
				ArrayList<TagFrequency> tagf = new ArrayList<TagFrequency>();
				BasicDBList choiceList = (BasicDBList) tagObject.get("tagsWithFreq");
				for(int i=0; i< choiceList.size(); i++)
				{
					BasicDBObject individualTag = (BasicDBObject) choiceList.get(i);
					TagFrequency tfreq = new TagFrequency();
					tfreq.setFrequency((Integer) individualTag.get("frequency"));
					tfreq.setTag((String) individualTag.get("tag"));
					tagf.add(tfreq);
				}
				tag.setTagsWithFreq(tagf);
				tagsList.add(tag);
			}
		} finally {
			cursor.close();
		}
		System.out.println("Size of list fetched from mongo : "+tagsList.size());
		return tagsList;
	}

}
