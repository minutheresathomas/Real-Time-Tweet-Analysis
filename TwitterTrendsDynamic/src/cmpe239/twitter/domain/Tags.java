package cmpe239.twitter.domain;

import java.util.ArrayList;
import java.util.List;


public class Tags {
	private int cluster;
	private double latCenter;
	private double longCenter;
	private String location;
	private ArrayList<TagFrequency> tagsWithFreq;
	public int getCluster() {
		return cluster;
	}
	public void setCluster(int cluster) {
		this.cluster = cluster;
	}
	public double getLatCenter() {
		return latCenter;
	}
	public void setLatCenter(double latCenter) {
		this.latCenter = latCenter;
	}
	public double getLongCenter() {
		return longCenter;
	}
	public void setLongCenter(double longCenter) {
		this.longCenter = longCenter;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public ArrayList<TagFrequency> getTagsWithFreq() {
		return tagsWithFreq;
	}
	public void setTagsWithFreq(ArrayList<TagFrequency> tagsWithFreq) {
		this.tagsWithFreq = tagsWithFreq;
	}
	
}
