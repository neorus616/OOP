package filters;
import wifi.Networks;

/**
 * 
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>
 * @version 2.0 
 * Description:
 * Location filter.
 */

public class LocationFilter implements Filter {

	private static final long serialVersionUID = 456L;
	private double _maxAlt, _minAlt, _maxLon, _minLon, _maxLat, _minLat;
	private double _Lat,  _Lon,  _radius;
	private boolean _not = true;

	/**
	 * Constructor
	 * @param startLat - Start Latitude
	 * @param startLong - Start Longitude
	 * @param endLat - End Latitude
	 * @param endLong - End Longitude
	 */
	public LocationFilter(String Lat, String Lon,String radius) {
		_Lat = Double.parseDouble(Lat);
		_Lon = Double.parseDouble(Lon);
		_radius = Double.parseDouble(radius);
	}

	/**
	 * Constructor
	 * @param minLat - Minimum Latitude
	 * @param maxLat - Maximum Latitude
	 * @param minLon - Minimum Longitude
	 * @param maxLon - Maximum Longitude
	 * @param minAlt - Minimum Altitude
	 * @param maxAlt - Maximum Altitude
	 */
	public LocationFilter(String minLat, String maxLat, String minLon, String maxLon, String minAlt, String maxAlt) {
		_maxAlt = Double.parseDouble(maxAlt);
		_minAlt = Double.parseDouble(minAlt);
		_maxLon = Double.parseDouble(maxLon);
		_minLon = Double.parseDouble(minLon);
		_maxLat = Double.parseDouble(maxLat);
		_minLat = Double.parseDouble(minLat);
	}

	/**
	 * Constructor
	 * @param loc - Array of location
	 */
	public LocationFilter(String [] loc) {
		_minLat = Double.parseDouble(loc[0]);
		_maxLat = Double.parseDouble(loc[1]);
		_minLon = Double.parseDouble(loc[2]);
		_maxLon = Double.parseDouble(loc[3]);
		_minAlt = Double.parseDouble(loc[4]);
		_maxAlt = Double.parseDouble(loc[5]);
	}

	/**
	 * Constructor for not operator
	 * @param loc - Array of location
	 * @param not - not operator
	 */
	public LocationFilter(String [] loc, boolean not) {
		_minLat = Double.parseDouble(loc[0]);
		_maxLat = Double.parseDouble(loc[1]);
		_minLon = Double.parseDouble(loc[2]);
		_maxLon = Double.parseDouble(loc[3]);
		_minAlt = Double.parseDouble(loc[4]);
		_maxAlt = Double.parseDouble(loc[5]);
		_not = not;
	}

	@Override
	public boolean test(Networks p) {
		// TODO Auto-generated method stub
		if(_radius == 0) {
		if(_not)
			return _maxAlt >= p.getAlt() && _minAlt <= p.getAlt() &&
			_maxLon >= p.getLon() && _minLon <= p.getLon() &&
			_maxLat >= p.getLat() && _minLat <= p.getLat();
			else return !(_maxAlt >= p.getAlt() && _minAlt <= p.getAlt() &&
					_maxLon >= p.getLon() && _minLon <= p.getLon() &&
					_maxLat >= p.getLat() && _minLat <= p.getLat());
		} else {
			return (distance(p.getLat(), p.getLon(), _Lat, _Lon) <= _radius);
		}
	}

	@Override
	public String toString() {
		if(_radius == 0) {
		if(_not)
			return "LocationFilter [_maxAlt=" + _maxAlt + ", _minAlt=" + _minAlt + ", _maxLon=" + _maxLon + ", _minLon="
			+ _minLon + ", _maxLat=" + _maxLat + ", _minLat=" + _minLat + "]";
		else return "Not(LocationFilter [_maxAlt=" + _maxAlt + ", _minAlt=" + _minAlt + ", _maxLon=" + _maxLon + ", _minLon="
		+ _minLon + ", _maxLat=" + _maxLat + ", _minLat=" + _minLat + "])";
		} else {
			return "LocationFilter [_Lat=" + _Lat + ", _Lon=" + _Lon + ", _radius=" + _radius + "]";
		}
	}



	/**
	 * @author Jason Winn <br>  
	 *  * @version 1.0
	 * <b>Description:</b> <br>
	 * Calculate distance between two locations on earth
	 * using the Haversine formula.
	 * Source: https://github.com/jasonwinn/haversine/blob/master/Haversine.java
	 * @param startLat - Start Latitude
	 * @param startLong - Start Longitude
	 * @param endLat - End Latitude
	 * @param endLong - End Longitude
	 * @return distance between 2 points.
	 */
	public static double distance(double startLat, double startLong,
			double endLat, double endLong) {

		double dLat  = Math.toRadians((endLat - startLat));
		double dLong = Math.toRadians((endLong - startLong));

		startLat = Math.toRadians(startLat);
		endLat   = Math.toRadians(endLat);

		double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(startLat) * Math.cos(endLat) * Math.pow(Math.sin(dLong / 2), 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		int EARTH_RADIUS = 6371; // Approximate Earth radius in KM
		return EARTH_RADIUS * c*1000; // <-- distance
	}

}
