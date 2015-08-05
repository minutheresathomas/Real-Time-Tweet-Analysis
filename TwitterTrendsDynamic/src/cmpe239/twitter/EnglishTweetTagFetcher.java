package cmpe239.twitter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import cmpe239.twitter.domain.Message;


public class EnglishTweetTagFetcher {
//	private String fFileName;
	
//	public static void main(String[] args) throws IOException
//	{
//		EnglishTweetTagFetcher test = new EnglishTweetTagFetcher();
//		test.read();
//	}
	
	public EnglishTweetTagFetcher(){}
	
	private static void log(String aMessage){
		System.out.println(aMessage);
	}

	public static Message read() throws IOException {
		log("Reading from file.");

		String fFileName = "./resources/filteredTweets.txt";
		String NL = System.getProperty("line.separator");
		Scanner scanner = new Scanner(new FileInputStream(fFileName), "UTF-8");
//		File file = new File("/Users/minu/Desktop/239Research/TokenizedTweets.txt");
		File file = new File("./resources/TokenizedBigTagsNewTest.txt");
//		File file = new File("/Users/minu/Desktop/239Research/TokenizedTweetTagsMallet.txt"); // takes only the texts
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);

		try {
			
			
			while (scanner.hasNextLine()){
				StringBuilder tweetData = new StringBuilder();
				String finalTextString = null;
				String line = scanner.nextLine();
				StringBuilder textData = new StringBuilder();

				if(line.startsWith("["))
				{
					String[] columns = line.split(",");
						for(int i=6 ; i<columns.length ; i++)
						{
//							if(columns[i].startsWith("#"))
								textData.append(columns[i]);
						}
						String textTweet = textData.toString();
						String resultString = textTweet.replaceAll("[^\\x00-\\x7F]", " "); // working with tweets
						finalTextString = resultString.replaceAll(",", " ").replaceAll("\"", " ").replace("'", "");
						
						if(line.endsWith("]"))
						{
							finalTextString = finalTextString.replace("]", ""); 
						}
						else
						{
							String nextLine;
							while(!(nextLine = scanner.nextLine()).endsWith("]")) 
							{
								String nextLineText = nextLine.replaceAll("[^\\x00-\\x7F]", " ").replace("]", ""); 
								finalTextString = finalTextString + nextLineText;
							}
							String nextLineText = nextLine.replaceAll("[^\\x00-\\x7F]", " ").replace("]", ""); 
							finalTextString = finalTextString + nextLineText;
						}
						tweetData.append(columns[0].replace("[", ""));
						tweetData.append(",");
						tweetData.append(columns[2]);
						tweetData.append(",");
						tweetData.append(columns[3]);
						tweetData.append(",");
						tweetData.append(columns[4]);
						tweetData.append(",");
						tweetData.append(columns[5]);
						tweetData.append(",");
				}
				String fileRow = null;
				if(finalTextString!=null) 
				{
					finalTextString = finalTextString.replaceAll("\n", " ").replaceAll("\r", " ")
							.replaceAll(NL, " ").replaceAll(",", " ").replaceAll("\"", " ").replaceAll("'", "").replaceAll("@", " ");
					finalTextString = finalTextString.replaceAll("\\r\\n|\\r|\\n", " ");
					
					//@Test
					String[] finalWords = finalTextString.split(" ");
					StringBuilder hashTagList = new StringBuilder();
					for(String word : finalWords)
					{
						if(word.startsWith("#") || word.startsWith("@") || word.contains("@") || word.contains("#"))
						{
							String word1 = word.replaceAll("#", "");
							hashTagList.append(word1+" ");
						}
					}
					String hashTagTextString = hashTagList.toString(); 
					//@Test
					
					if(!hashTagTextString.isEmpty())
					{
						tweetData.append(hashTagTextString+NL);
						fileRow = tweetData.toString();
						log(fileRow);
						bw.write(fileRow);
					}
				}
			}
			bw.close();
		}
		finally{
			scanner.close();
		}Message msg = new Message();
		msg.setMessage("success");
		return msg;
	}
}
