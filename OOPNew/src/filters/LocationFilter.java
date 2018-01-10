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
	private boolean _not = true;
	
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
		if(_not)
		return _maxAlt >= p.getAlt() && _minAlt <= p.getAlt() &&
				_maxLon >= p.getLon() && _minLon <= p.getLon() &&
				_maxLat >= p.getLat() && _minLat <= p.getLat();
				else return !(_maxAlt >= p.getAlt() && _minAlt <= p.getAlt() &&
				_maxLon >= p.getLon() && _minLon <= p.getLon() &&
				_maxLat >= p.getLat() && _minLat <= p.getLat());
	}

	@Override
	public String toString() {
		if(_not)
			return "LocationFilter [_maxAlt=" + _maxAlt + ", _minAlt=" + _minAlt + ", _maxLon=" + _maxLon + ", _minLon="
			+ _minLon + ", _maxLat=" + _maxLat + ", _minLat=" + _minLat + "]";
		else return "Not(LocationFilter [_maxAlt=" + _maxAlt + ", _minAlt=" + _minAlt + ", _maxLon=" + _maxLon + ", _minLon="
		+ _minLon + ", _maxLat=" + _maxLat + ", _minLat=" + _minLat + "])";
	}
	
	

}
