package cmpe239.twitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;

import cmpe239.twitter.domain.CloudBean;
import cmpe239.twitter.domain.GoogleResponse;
import cmpe239.twitter.domain.Result;
import cmpe239.twitter.domain.Message;
import cmpe239.twitter.domain.TagFrequency;
import cmpe239.twitter.domain.Tags;
import cmpe239.twitter.domain.Tweets;
import cmpe239.twitter.repository.MongoStorage;


public class ReadTweetsText {

	private static final String URL = "http://maps.googleapis.com/maps/api/geocode/json";

//		public static void main(String[] args) throws IOException
//		{
//			MongoStorage st = new MongoStorage();
//			ReadTweetsText rt = new ReadTweetsText();
//			// store tweets collection
////			rt.store(st);		
//			rt.readTextAndStoreTagsForEachCluster(st);
////			rt.getAllDistinctLocations(st);
////			rt.getAllTagsCloudPerLocation(st, "USA");
//		}
	/*GoogleResponse res1 = rt.convertFromLatLong("27.89172963010205,130.9049239394132");
		if (res1.getStatus().equals("OK")) {
			int count = 0;
			for (Result result : res1.getResults()) {
				if (count == 0) {
					String address = result.getFormatted_address();
					System.out.println("Full address is :" + address);
					String[] splitAddress = address.split(",");
					int size = splitAddress.length;
					System.out.println("size of aplit address is :" + size);
					if(size > 1) {
						String addr = splitAddress[size-2]+","+splitAddress[size-1];
						System.out.println("needed address is :" + addr);
						count++;
					}
				} else {
				}
			}
		} else {
			System.out.println(res1.getStatus());
		}*/

	public Message readTextAndStoreTagsForEachCluster(MongoStorage store) throws IOException {
		// TODO Auto-generated method stub
		String NL = System.getProperty("line.separator");
		ArrayList<Tags> tagsStore = new ArrayList<Tags>();
		for(int i=0 ;i<25; i++)
		{
			HashMap<String, Integer> wordsList = new HashMap<String, Integer>();
			HashMap<String, Integer> sorted_wordsList = new HashMap<String, Integer>();
			ArrayList<TagFrequency> tagsFreq = new ArrayList<TagFrequency>();
			ArrayList<Tweets> tweet = store.getTweetPerCluster(i);
//			FileWriter outFile1 = new FileWriter("/Users/minu/Desktop/239Research/tagFrequencyList"+i+".csv");
//			PrintWriter out2 = new PrintWriter(outFile1);
			
			FileWriter outFile2 = new FileWriter("/Users/minu/Desktop/239Research/LDA_Final/tagList"+i+".csv");
			PrintWriter out3 = new PrintWriter(outFile2);

			// get the cluster and latitude and longitude

			Tags tagsInATweet = new Tags();
			boolean flag = false;
			for(Tweets t : tweet)
			{
				String[] tagList = t.getText().split("\\s+");

				if(!flag)	
				{
					tagsInATweet.setCluster(t.getClusterNumber());
					tagsInATweet.setLatCenter(t.getLatCenter());
					tagsInATweet.setLongCenter(t.getLongCenter());

					// Get the place for each lat and long
					String latlong = String.valueOf(t.getLatCenter())+","+String.valueOf(t.getLongCenter());
//					System.out.println("Latitude and Long : "+latlong);
					GoogleResponse res1 = new ReadTweetsText().convertFromLatLong(latlong);

					//Get the last two address details(location or country

					if (res1.getStatus().equals("OK")) {
						int count = 0;
						for (Result result : res1.getResults()) {
							if (count == 0) {
								String address = result.getFormatted_address();
//								System.out.println("Full address is :" + address);
								String[] splitAddress = address.split(",");
								int size = splitAddress.length;
//								System.out.println("size of split address is :" + size);
								if(size > 1) {
									String addr = splitAddress[size-1];
									//									String addr = splitAddress[size-1]+","+splitAddress[size-1];
//									System.out.println("needed address is :" + addr);
									tagsInATweet.setLocation(addr.trim());
									count++;
									break;
								}
							} else {
							}
						}
					} else {
						System.out.println("Not getting the Location ******* "+res1.getStatus());
					}
					// @Test

					flag = true;
				}
				for(String tag : tagList)
				{
					tag = tag.replaceAll("[\\s\\-()]", "").replaceAll("[-+.^:,]", "").replaceAll("\\{", "");
					if(tag.length()!=1 && !tag.equals("")  && !tag.isEmpty())
						out3.println(tag);
					if(!wordsList.containsKey(tag))
					{
						if(tag.length()!=1 && !tag.equals("")  && !tag.isEmpty())
						{
							wordsList.put(tag, 1);
						}
					}
					else
					{
						if(tag.length()!=1 && !tag.equals("") && !tag.isEmpty())
							wordsList.put(tag, (wordsList.get(tag) +1));
					}
				}
			}
			out3.close();
			sorted_wordsList = sortByValue(wordsList);

			for(String sorted_word : sorted_wordsList.keySet())
			{
				TagFrequency tagFrequency = new TagFrequency();
				tagFrequency.setTag(sorted_word);
				tagFrequency.setFrequency(sorted_wordsList.get(sorted_word));
				tagsFreq.add(tagFrequency);
			}

			tagsInATweet.setTagsWithFreq(tagsFreq);
			tagsStore.add(tagsInATweet);
//			out2.println(sorted_wordsList);
//			out2.close();
		}
		store.createTagsList(tagsStore);
		Message msg = new Message();
		msg.setMessage("success");
		return msg;
	}

	public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> map) {
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

			public int compare(Map.Entry<String, Integer> m1, Map.Entry<String, Integer> m2) {
				return (m2.getValue()).compareTo(m1.getValue());
			}
		});

		HashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	public Message store(MongoStorage store) {

		String csvFile = "./resources/TokenizedBigTagsNewTest.txt";
		String locationFile = "./resources/clusterResult.txt";
		BufferedReader br = null, br1 = null;
		String line = "", line1 = "";
		String cvsSplitBy = ",";

		try {
			br1 = new BufferedReader(new FileReader(locationFile));
			ArrayList<Tweets> tweetList = new ArrayList<Tweets>();
			while ((line1 = br1.readLine()) != null){
				String[] location = line1.split(cvsSplitBy);
				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					Tweets tweetData = new Tweets();
					String[] text = line.split(cvsSplitBy);	 
					if(location[3].equalsIgnoreCase(text[1]) && location[4].equalsIgnoreCase(text[2])){
						// write to mongo storage
						tweetData.setClusterNumber(Integer.valueOf(location[0]));
						tweetData.setLatCenter(Double.valueOf(location[1]));
						tweetData.setLongCenter(Double.valueOf(location[2]));
						tweetData.setLatitude(Double.valueOf(location[3]));
						tweetData.setLongitude(Double.valueOf(location[4]));
						tweetData.setText(text[5]);
						tweetList.add(tweetData);
						//						store.createTweets(tweetData);
						//						out1.println(location[0] + "," + location[1] + "," + location[2] + "," + text[5]);	 
					}

				}

			}
			//store the list to mongodb
			store.createTweetsList(tweetList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
//			System.out.println("");
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
		Message msg = new Message();
		msg.setMessage("success");
		return msg;
	}

	// Take the top 100 tweets per cluster
	public Tags getTagsCloudPerCluster(MongoStorage store, int cluster, int count)
	{

		Tags cloudTag = store.getAllTagsPerCluster(cluster);
		Tags cloud100 = new Tags();
		cloud100.setCluster(cloudTag.getCluster());
		cloud100.setLatCenter(cloudTag.getLatCenter());
		cloud100.setLongCenter(cloudTag.getLongCenter());
		ArrayList<TagFrequency> hundredTags = new ArrayList<TagFrequency>();
		int i =0;
		for(TagFrequency tf : cloudTag.getTagsWithFreq())
		{
			if(count == 100) {
				if(i<100)
				{
					hundredTags.add(tf);
					i++;
				}
			}
			else
			{
				if(i<3)
				{
					hundredTags.add(tf);
					i++;
				}
			}
		}
		cloud100.setTagsWithFreq(hundredTags);
		return cloud100;
	}

	// Take the top three tags per cluster
	public ArrayList<Tags> getAllTagsCloud(MongoStorage store, int count)
	{
		ArrayList<Tags> cloudTag = store.getAllTagsCluster();
		ArrayList<Tags> allCloudTag = new ArrayList<Tags>();
		for(Tags ct : cloudTag)
		{

			Tags cloud100 = new Tags();
			cloud100.setCluster(ct.getCluster());
			cloud100.setLatCenter(ct.getLatCenter());
			cloud100.setLongCenter(ct.getLongCenter());
			cloud100.setLocation(ct.getLocation());
			ArrayList<TagFrequency> hundredTags = new ArrayList<TagFrequency>();
			int i =0;
			ArrayList<TagFrequency> Tagsf = ct.getTagsWithFreq();
			//			System.out.println("the tags per cluster is : "+ct.getTagsWithFreq());

			for(TagFrequency tf : Tagsf)
			{
				if(i<3)
				{
					if(tf.getFrequency() < 10)
					{	
						hundredTags.add(tf);
						i++;
					}
				}
			}
			cloud100.setTagsWithFreq(hundredTags);
			allCloudTag.add(cloud100);
		}
		return allCloudTag;
	}

	public GoogleResponse convertFromLatLong(String latlongString) throws IOException {
		URL url = new URL(URL + "?latlng=" + URLEncoder.encode(latlongString, "UTF-8") + "&sensor=false");
		// Open the Connection 
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		ObjectMapper mapper = new ObjectMapper();
		GoogleResponse response = (GoogleResponse) mapper.readValue(in, GoogleResponse.class);
		in.close();
		return response;
	}

	public ArrayList<String> getAllDistinctLocations(MongoStorage store) {
		ArrayList<String> locs = new ArrayList<String>();
		ArrayList<String> locs1 = new ArrayList<String>();
		locs = store.getDistinctLocations();
		for(String loc : locs)
		{
			if(loc != null)
			{
				locs1.add(loc);
			}
		}
//		for(int i=0 ; i<locs.size() ;i++)
//			System.out.println("Location : "+locs.get(i));
		return locs1;
	}

	// Take the top 100 tags for each cluster per location
	public HashMap<Integer, String> getAllTagsCloudPerLocation(MongoStorage store, String Location) throws IOException
	{
		ArrayList<Tags> cloudTag = store.getTagsPerLocation(Location);
		int sizeCluster = cloudTag.size();
//		System.out.println("sizeCluster metod: "+sizeCluster);
		Gson gson = new Gson();
		HashMap<Integer, String> wordCloudInput = new HashMap<Integer, String>();
		int j=0;
		for(Tags ct : cloudTag)
		{
			ArrayList<TagFrequency> hundredTags = new ArrayList<TagFrequency>();
			int i =0;
			StringBuffer buf = new StringBuffer();
			ArrayList<TagFrequency> Tagsf = ct.getTagsWithFreq();
			File file = new File("./tagCloud"+j+".csv");
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter outFile2 = new FileWriter(file.getAbsoluteFile());
			PrintWriter out3 = new PrintWriter(outFile2);
			out3.println("text,size,topic");
			int k=0;
			for(TagFrequency tf : Tagsf)
			{
				if(i<100)
				{
					if(tf.getFrequency() < 10) {
						k++;
						CloudBean cb = new CloudBean();
						cb.setText(tf.getTag());
						cb.setSize(tf.getFrequency());
						cb.setTopic(ct.getCluster());
						String gsonString = gson.toJson(cb);
						out3.println(tf.getTag()+","+tf.getFrequency()+","+ct.getCluster());
						if(k==1)
						{
							buf.append("["+gsonString+",");
						}
						else if(i==99 || i == (Tagsf.size()-1))
							buf.append(gsonString+"]");
						else
							buf.append(gsonString+",");
						hundredTags.add(tf);
					}
					i++;
				}
				
			}
			j++;
			out3.close();
			String s = buf.toString();
			System.out.println("The JSON is : "+s);
			wordCloudInput.put(ct.getCluster(), s);
		}
		return wordCloudInput;
	}
	
	//find the frequency of tags for each location
	public HashMap<String, Integer> getAllTags(MongoStorage store)
	{
		ArrayList<Tags> cloudTag = store.getAllTagsCluster();
		ArrayList<Tags> allCloudTag = new ArrayList<Tags>();
		HashMap<String, Integer> countryTagFreq = new HashMap<String, Integer>();
		HashMap<String, Integer> sorted_countryTagFreq = new HashMap<String, Integer>();
		for(Tags ct : cloudTag)
		{
			String location = ct.getLocation();
			//if country already exists in the hashmap
			int prevCount;
			if(countryTagFreq.containsKey(location))
			{
				//take the value for key location
				prevCount = countryTagFreq.get(location);
			}
			else
				prevCount=0;
			ArrayList<TagFrequency> hundredTags = new ArrayList<TagFrequency>();
			int i =0;
			ArrayList<TagFrequency> Tagsf = ct.getTagsWithFreq();
			
			for(TagFrequency tf : Tagsf)
			{
				prevCount += tf.getFrequency();
			}
			if(location!=null)
				countryTagFreq.put(location, prevCount);
		}
		sorted_countryTagFreq = sortByValue(countryTagFreq);
		return sorted_countryTagFreq;
	}
}
