import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.function.Predicate;  

public class networks {

	String _id = "";
	String _time = "";
	double _lat = 0;
	double _lon = 0;
	double _alt = 0;
	ArrayList<wifi> _points = new ArrayList<wifi>();
	
	public networks() {
		
	}
	
	public networks(String id, String time, double lat, double lon, double alt) {
		_id = id;
		_time = time;
		_lat = lat;
		_lon = lon;
		_alt = alt;
	}

	@Override
	public String toString() {
		return "ID=" + _id + ", Time=" + _time + ", Latitude=" + _lat + ", Longitude=" + _lon + ", Altitude=" + _alt
				+ ", #WiFi networks =" + _points.size()+ ": " + _points.toString();
	}
	
	public void add(String ssid, String mac, int signal, int channel){
		wifi p = new wifi(ssid, mac, signal, channel);
		int weak = checkSignal();
		if(_points.size()==10) {
			if(_points.get(weak).compareTo(p) == -1) {
				_points.remove(weak);
				_points.add(p);
			}
		}
		else _points.add(p);
	}
	
	public String getID() {
		return _id;
	}
	
	public String getTime() {
		return _time;
	}
	
	public double getLat() {
		return _lat;
	}
	
	public double getLon() {
		return _lon;
	}
	
	public double getAlt() {
		return _alt;
	}
	
	public ArrayList<wifi> getPoints() {
		return _points;
	}
	
	public int checkSignal() {
		int min = 0;
		for (int i = 1; i < _points.size()-1; i++) 
			if(_points.get(i).compareTo(_points.get(min)) == -1)
				min = i;
		return min;
	}
	public boolean isId(String id) {
	    return this.getID().equalsIgnoreCase(id);
	}
	
	public boolean isLocation(String location) {
		String splitBy = ",";
		String[] gps = location.split(splitBy);
	    return this.getLat() == Double.parseDouble(gps[0]) &&
	    		this.getLon() == Double.parseDouble(gps[1]) &&
	    		this.getAlt() == Double.parseDouble(gps[2]);
	}
	
	public boolean isTime(String time) {
		//System.out.println(time + " " + this.getTime());
	    return this.getTime().equals(time);
	}
	
	public boolean filter(String filterBy, String filter) {
		if(filterBy.equalsIgnoreCase("id"))
			return isId(filter);
		if(filterBy.equalsIgnoreCase("location"))
			return isLocation(filter);
		if(filterBy.equalsIgnoreCase("time"))
			return isTime(filter);
		return false;
	}

}
