
public class LocationFilter implements Filter {
	
	private double _maxAlt, _minAlt, _maxLon, _minLon, _maxLat, _minLat;
	public LocationFilter(String maxAlt, String minAlt, String maxLon, String minLon, String maxLat, String minLat) {
		_maxAlt = Double.parseDouble(maxAlt);
		_minAlt = Double.parseDouble(minAlt);
		_maxLon = Double.parseDouble(maxLon);
		_minLon = Double.parseDouble(minLon);
		_maxLat = Double.parseDouble(maxLat);
		_minLat = Double.parseDouble(minLat);
	}
	
	
	@Override
	public boolean test(Networks p) {
		// TODO Auto-generated method stub
		return this._maxAlt >= p.getAlt() && this._minAlt <= p.getAlt() &&
				this._maxLon >= p.getLon() && this._minLon <= p.getLon() &&
				this._maxLat >= p.getLat() && this._minLat <= p.getLat();
	}
	
	

}
