
public class wifi implements Comparable<wifi>{

	private String _ssid = "";
	private String _mac = "";
	private int _signal = 0;
	private int _channel = 0;
	
	public wifi(String ssid, String mac, int signal, int channel) {
		_ssid = ssid;
		_mac = mac;
		_signal = signal;
		_channel = channel;
	}

	@Override
	public String toString() {
		return "SSID=" + _ssid + ", MAC=" + _mac + ", Signal=" + _signal + ", Channel=" + _channel;
	}
	
	public String getSSID() {
		return _ssid;
	}
	
	public String getMAC() {
		return _mac;
	}
	
	public int getSignal() {
		return _signal;
	}
	
	public int getChannel() {
		return _channel;
	}

	@Override
	public int compareTo(wifi w) {
		// TODO Auto-generated method stub
		if(this._signal < w._signal)
			return -1;
		if (this._signal > w._signal)
			return 1;
		return 0;
	}

	
	
}
