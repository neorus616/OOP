package wifi;
/**
 * 
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>
 * @version 2.0
 */
public class Wifi implements Comparable<Wifi>{

	private String _ssid = "";
	private String _mac = "";
	private int _signal = 0;
	private int _channel = 0;
	
	/**
	 * Constructor
	 * @param ssid - AP Name.
	 * @param mac - MAC Address.
	 * @param signal - Signal strength.
	 * @param channel - Frequency.
	 */
	public Wifi(String ssid, String mac, int signal, int channel) {
		_ssid = ssid;
		_mac = mac;
		_signal = signal;
		_channel = channel;
	}

	/**
	 * 
	 * @return object attributes as a string.
	 */
	@Override
	public String toString() {
		return "SSID=" + _ssid + ", MAC=" + _mac + ", Signal=" + _signal + ", Channel=" + _channel;
	}
	
	/**
	 * 
	 * @return Object's AP Name.
	 */
	public String getSSID() {
		return _ssid;
	}
	
	/**
	 * 
	 * @return Object's MAC Address.
	 */
	public String getMAC() {
		return _mac;
	}
	
	/**
	 * 
	 * @return Object's Signal strength.
	 */
	public int getSignal() {
		return _signal;
	}
	
	/**
	 * 
	 * @return Object's Frequency.
	 */
	public int getChannel() {
		return _channel;
	}

	/**
	 * @return 0 if the object's signal is equal to this object's signal;
	 * 1 if object's signal is weaker than the object's signal;
	 * -1 if this object's signal is stronger than object's signal.
	 */
	@Override
	public int compareTo(Wifi w) {
		// TODO Auto-generated method stub
		if(this._signal < w._signal)
			return -1;
		if (this._signal > w._signal)
			return 1;
		return 0;
	}
	
}