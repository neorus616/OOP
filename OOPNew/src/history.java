import java.util.Hashtable;

import org.apache.commons.csv.CSVRecord;

public class history {

	Hashtable<String, Networks> strongPointsCSV = new Hashtable<>();
	Hashtable<String, Networks> strongPoints = new Hashtable<>();
	Hashtable<String, Networks> filteredStrongPoints = new Hashtable<>();

	public history(Hashtable<String, Networks> strongPoints) {
		this.strongPoints = strongPoints;
	}

	public Hashtable<String, Networks> getPoints(){
		if(!this.filteredStrongPoints.isEmpty())
			return this.filteredStrongPoints;
		else 
			return this.strongPoints;
	}

	public void updateHistory(Hashtable<String, Networks> strongPoints) {
		this.strongPoints = strongPoints;
	}

	public void updateHistoryCSV(Hashtable<String, Networks> strongPoints) {
		this.strongPointsCSV = strongPoints;
		updateHistory(this.strongPointsCSV);
	}

	public void clear() {
		strongPointsCSV.clear();
		strongPoints.clear();
		filteredStrongPoints.clear();
	}

	public void filter(Filter filter) {
		filteredStrongPoints = new Hashtable<>();
		for (String key : this.strongPoints.keySet()) {
			if(filter.test(this.strongPoints.get(key)))
				this.filteredStrongPoints.put(key, this.strongPoints.get(key));
		}
		System.out.println("Filtered " + filteredStrongPoints.size() + " Points");
	}

	public int diffMAC() {
		Hashtable<String, Wifi> wifiCount = new Hashtable<>();
		if(this.filteredStrongPoints.isEmpty()) {
			for (String key : this.strongPoints.keySet()) {
				for (int i = 0; i < this.strongPoints.get(key).getPoints().size(); i++) {
					if(!wifiCount.contains(this.strongPoints.get(key).getPoints().get(i).getMAC()))
						wifiCount.put(this.strongPoints.get(key).getPoints().get(i).getMAC(), this.strongPoints.get(key).getPoints().get(i));
				}
			}
		} else {
			for (String key : this.filteredStrongPoints.keySet()) {
				for (int i = 0; i < this.filteredStrongPoints.get(key).getPoints().size(); i++) {
					if(!wifiCount.contains(this.filteredStrongPoints.get(key).getPoints().get(i).getMAC()))
						wifiCount.put(this.filteredStrongPoints.get(key).getPoints().get(i).getMAC(), this.filteredStrongPoints.get(key).getPoints().get(i));
				}
			}
		}
		return wifiCount.size();
	}

	public double[] findAPloc(String macSample) {
		Hashtable<String, APNetworks> samples = new Hashtable<>();
		boolean isSample = false;
		APNetworks network = new APNetworks();
		double [] loc = new double[3];
		for (String key : strongPoints.keySet()) {
			String time = strongPoints.get(key).getTime();
			double lat = strongPoints.get(key).getLat();
			double lon = strongPoints.get(key).getLon();
			double alt = strongPoints.get(key).getAlt();
			String id = strongPoints.get(key).getID();
			for (int i = 1; i <= strongPoints.get(key).getPoints().size(); i++) {
				String mac = strongPoints.get(key).getPoints().get(i).getMAC();
				String ssid = strongPoints.get(key).getPoints().get(i).getSSID();
				int channel = strongPoints.get(key).getPoints().get(i).getChannel();
				double signal = strongPoints.get(key).getPoints().get(i).getSignal();
				if(mac.equals(macSample))
					isSample = true;
				if(isSample) {
					network = new APNetworks(id, time);
					network.add(ssid, mac, (int)signal, channel, lat, lon, alt);
					samples.put(network.getPoints().get(0).getMAC(), network);
					isSample = false;
					break;
				}
			}
		}
		Hashtable<String, Networks> macLoc = EstimateLoc.apToNetworks(samples);
		loc[0] = macLoc.get(macSample).getLat();
		loc[1] = macLoc.get(macSample).getLon();
		loc[2] = macLoc.get(macSample).getAlt();
		return loc;
	}
}
