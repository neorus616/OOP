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
			for (int i = 0; i < strongPoints.get(key).getPoints().size(); i++) {
				String mac = this.strongPoints.get(key).getPoints().get(i).getMAC();
				String ssid = strongPoints.get(key).getPoints().get(i).getSSID();
				int channel = strongPoints.get(key).getPoints().get(i).getChannel();
				double signal = strongPoints.get(key).getPoints().get(i).getSignal();
				if(mac.equals(macSample))
					isSample = true;
				if(isSample) {
					network = new APNetworks(id, time);
					network.add(ssid, mac, (int)signal, channel, lat, lon, alt);
					if(!samples.containsKey(network.getPoints().get(0).getMAC())) {
						samples.put(network.getPoints().get(0).getMAC(), network);
					}
					else 
						samples.get(network.getPoints().get(0).getMAC()).add(ssid, mac, (int)signal, channel, lat, lon, alt);
					isSample = false;
					break;
				}
			}
		}
		Hashtable<String, Networks> macLoc = EstimateLoc.apToNetworks(samples);
		if(!macLoc.isEmpty()) {
			loc[0] = macLoc.get(macSample).getLat();
			loc[1] = macLoc.get(macSample).getLon();
			loc[2] = macLoc.get(macSample).getAlt();
		}
		return loc;
	}

	public double[] findUserloc(String [] macSample) {
		String time = macSample[0];
		String id = macSample[1];
		Hashtable<String, Networks> goodSamples = new Hashtable<>();
		Networks wifiScan = new Networks(time,id,0,0,0);
		for (int i = 6; i < macSample.length; i=i+4) {
			String ssid = macSample[i];
			String mac = macSample[i+1];
			int channel = Integer.parseInt(macSample[i+2]);
			double signal = Double.parseDouble(macSample[i+3]);
			wifiScan.add(ssid, mac, (int)signal, channel);
		}
		for (String key : strongPoints.keySet()) {
			boolean isSample = false;
			for (int i = 0; i < macSample.length; i++) {
				for (int j = 0; j < wifiScan.getPoints().size(); j++) {
					if(strongPoints.get(key).getPoints().get(i).getMAC().equals(wifiScan.getPoints().get(j).getMAC())) {
						isSample = true;
					}
				}
			}
			if(isSample) {
				//System.out.println(network);
				goodSamples.put(time+id, strongPoints.get(key));
			}
		}
		double[] loc = EstimateLoc.searchPi2(wifiScan, 10, goodSamples);
		return loc;
	}

}
