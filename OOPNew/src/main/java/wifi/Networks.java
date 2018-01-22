package main.java.wifi;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import main.java.filters.Filter;

/**
 * 
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>  
 * @version 2.0
 */

public class Networks {

	private String _id = "";
	private String _time = "";
	private double _lat = 0;
	private double _lon = 0;
	private double _alt = 0;
	private ArrayList<Wifi> _points = new ArrayList<Wifi>();
	private int _numAP = 10;

	/**
	 * Empty constructor
	 */
	public Networks() {}

	/**
	 * Constructor
	 * @param id - Phone.
	 * @param time - Time "yyyy-MM-dd HH:mm:ss".
	 * @param lat - Latitude.
	 * @param lon - Longitude.
	 * @param alt - Altitude.
	 */
	public Networks(String id, String time, double lat, double lon, double alt) {
		_id = id;
		_time = time;
		_lat = lat;
		_lon = lon;
		_alt = alt;
	}

	/**
	 * 
	 * @return Object attributes as a string.
	 */
	@Override
	public String toString() {
		return "ID=" + _id + ", Time=" + _time + ", Latitude=" + _lat + ", Longitude=" + _lon + ", Altitude=" + _alt
				+ ", #WiFi networks =" + _points.size()+ ": " + _points.toString();
	}

	/**
	 * 
	 * @param ssid - AP Name.
	 * @param mac - MAC Address.
	 * @param signal - Signal strength.
	 * @param channel - Frequency.
	 */
	public void add(String ssid, String mac, int signal, int channel){
		Wifi p = new Wifi(ssid, mac, signal, channel);
		if(_points.size()==_numAP) {
			int weak = minSignal();
			if(_points.get(weak).compareTo(p) == -1) {
				_points.remove(weak);
				_points.add(p);
			}
		}
		else _points.add(p);
	}

	/**
	 * set size of arraylist
	 * @param numAP - size of arraylist
	 */
	public void setNumAP(int numAP) {
		this._numAP = numAP;
	}

	/**
	 * 
	 * @return Object's ID.
	 */
	public String getID() {
		return _id;
	}

	/**
	 * 
	 * @return Object's Time.
	 */
	public String getTime() {
		return _time;
	}

	/**
	 * 
	 * @return Object's Latitude.
	 */
	public double getLat() {
		return _lat;
	}

	/**
	 * set Object's Latitude.
	 * @param lat - Latitude.
	 */
	public void setLat(double lat) {
		this._lat = lat;
	}
	/**
	 * 
	 * @return Object's Longitude.
	 */
	public double getLon() {
		return _lon;
	}

	/**
	 * set Object's Longitude.
	 * @param lon - Longitude.
	 */
	public void setLon(double lon) {
		this._lon = lon;
	}

	/**
	 * 
	 * @return Object's Altitude.
	 */
	public double getAlt() {
		return _alt;
	}

	/**
	 * set Object's Altitude.
	 * @param alt - Altitude.
	 */
	public void setAlt(double alt) {
		this._alt = alt;
	}

	/**
	 * 
	 * @return Object's Access points.
	 */
	public ArrayList<Wifi> getPoints() {
		return _points;
	}

	/**
	 * 
	 * @return Index of weakest AP in Object.
	 */
	public int minSignal() {
		int min = 0;
		for (int i = 1; i < _points.size(); i++) 
			if(_points.get(i).compareTo(_points.get(min)) == -1)
				min = i;
		return min;
	}

	/**
	 * 
	 * @param id - Phone filter.
	 * @return True if object's ID equals filter's ID, false otherwise.
	 */
	public boolean isId(String id) {
		return this.getID().equalsIgnoreCase(id);
	}

	/**
	 * 
	 * @param time - Time filter.
	 * @return True if object's time equals filter's time, false otherwise.
	 */
	public boolean isTime(String time) {
		String splitBy = ",";
		String[] date = time.split(splitBy);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		LocalDateTime dateTime1= LocalDateTime.parse(date[0], formatter);
		LocalDateTime dateTime2= LocalDateTime.parse(date[1], formatter);
		LocalDateTime dateTime3= LocalDateTime.parse(this.getTime(), formatter);

		long diffInMilli = java.time.Duration.between(dateTime1, dateTime2).getSeconds();
		long diffInMilli1 = java.time.Duration.between(dateTime3, dateTime2).getSeconds();

		return (diffInMilli > diffInMilli1 && diffInMilli1 > 0);
	}

	/**
	 * 
	 * @param filterBy - Filter type.
	 * @param filter - Filter condition.
	 * @return True if object meet the required conditions, otherwise false.
	 */
	public boolean filter(String filterBy, String filter) {
		if(filterBy.equalsIgnoreCase("id"))
			return isId(filter);
		if(filterBy.equalsIgnoreCase("date"))
			return isTime(filter);
		return false;
	}

	/**
	 * test filter using interface
	 * @param f - filter to test by
	 * @return true if pass the test, false otherwise
	 */
	public boolean filter(Filter f){
		return f.test(this);
	}

}