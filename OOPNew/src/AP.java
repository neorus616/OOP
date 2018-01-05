
public class AP extends Wifi {
	
	
	private double _lat = 0;
	private double _lon = 0;
	private double _alt = 0;

	public AP(String ssid, String mac, int signal, int channel, double lat, double lon, double alt) {
		super(ssid, mac, signal, channel);
		_lat = lat;
		_lon = lon;
		_alt = alt;
		// TODO Auto-generated constructor stub
	}
	
	public double getLat() {
		return _lat;
	}
	
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
	
	public void setAlt(double alt) {
		this._alt = alt;
	}
	
}
