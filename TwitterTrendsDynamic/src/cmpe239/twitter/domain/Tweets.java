package cmpe239.twitter.domain;

public class Tweets {
	
	private int clusterNumber;
	private double latCenter;
	private double longCenter;
	private double latitude;
	private double longitude;
	private String text;
	
	public int getClusterNumber() {
		return clusterNumber;
	}
	public void setClusterNumber(int clusterNumber) {
		this.clusterNumber = clusterNumber;
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
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
