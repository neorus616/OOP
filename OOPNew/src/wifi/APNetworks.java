package wifi;
import java.util.ArrayList;

/**
 * 
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>  
 * @version 2.0
 */

public class APNetworks {

	String _id = "";
	String _time = "";
	double _lat = 0;
	double _lon = 0;
	double _alt = 0;
	ArrayList<AP> _points = new ArrayList<AP>();
	int _numAP = 10;
	
	/**
	 * Empty constructor
	 */
	public APNetworks() {}
	
	/**
	 * 
	 * @param id - Phone.
	 * @param time - Time "yyyy-MM-dd HH:mm:ss".
	 */
	public APNetworks(String id, String time) {
		_id = id;
		_time = time;
	}

	/**
	 * 
	 * @return Object attributes as a string.
	 */
	@Override
	public String toString() {
		return "ID=" + _id + ", Time=" + _time + ", #WiFi networks =" + _points.size()+ ": " + _points.toString();
	}
	

	/**
	 * 
	 * @param ssid - AP Name.
	 * @param mac - MAC Address.
	 * @param signal - Signal strength.
	 * @param channel - Frequency.
	 * @param lat - Latitude.
	 * @param lon - Longitude.
	 * @param alt - Altitude.
	 */
		public void add(String ssid, String mac, int signal, int channel, double lat, double lon, double alt){
		AP p = new AP(ssid, mac, signal, channel, lat, lon, alt);
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
	public ArrayList<AP> getPoints() {
		return _points;
	}
	
	/**
	 * search for min signal in arraylist of points
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
	 * search for max signal in arraylist of points
	 * @return - Index of strongest AP in Object.
	 */
	public int maxSignal() {
		int max = 0;
		for (int i = 1; i < _points.size(); i++) 
			if(_points.get(i).compareTo(_points.get(max)) == 1)
				max = i;
		return max;
	}
}