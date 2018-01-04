
public class LocationFilter implements Filter {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double _maxAlt, _minAlt, _maxLon, _minLon, _maxLat, _minLat;
	public LocationFilter(String maxAlt, String minAlt, String maxLon, String minLon, String maxLat, String minLat) {
		_maxAlt = Double.parseDouble(maxAlt);
		_minAlt = Double.parseDouble(minAlt);
		_maxLon = Double.parseDouble(maxLon);
		_minLon = Double.parseDouble(minLon);
		_maxLat = Double.parseDouble(maxLat);
		_minLat = Double.parseDouble(minLat);
	}
	
	public LocationFilter(String [] loc) {
		_minLat = Double.parseDouble(loc[0]);
		_maxLat = Double.parseDouble(loc[1]);
		_minLon = Double.parseDouble(loc[2]);
		_maxLon = Double.parseDouble(loc[3]);
		_minAlt = Double.parseDouble(loc[4]);
		_maxAlt = Double.parseDouble(loc[5]);
	}
	
	
	
	
	@Override
	public boolean test(Networks p) {
		// TODO Auto-generated method stub
		return this._maxAlt >= p.getAlt() && this._minAlt <= p.getAlt() &&
				this._maxLon >= p.getLon() && this._minLon <= p.getLon() &&
				this._maxLat >= p.getLat() && this._minLat <= p.getLat();
	}
	
	

}
