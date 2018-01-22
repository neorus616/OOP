package main.java.wifi;
/**
 * 
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>
 * @version 2.0
 */

public class AP extends Wifi {

	private double _lat = 0;
	private double _lon = 0;
	private double _alt = 0;

	/**
	 * Constructor
	 * @param ssid - AP Name.
	 * @param mac - MAC Address.
	 * @param signal - Signal strength.
	 * @param channel - Frequency.
	 * @param lat - Latitude.
	 * @param lon - Longitude.
	 * @param alt - Altitude.
	 */
	public AP(String ssid, String mac, int signal, int channel, double lat, double lon, double alt) {
		super(ssid, mac, signal, channel);
		_lat = lat;
		_lon = lon;
		_alt = alt;
		// TODO Auto-generated constructor stub
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
	
}
