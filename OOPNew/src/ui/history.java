package ui;
/**
 * 
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>
 * @version 2.0
 */

import java.util.Hashtable;

import Algorithms.EstimateLoc;
import filters.Filter;
import wifi.APNetworks;
import wifi.Networks;
import wifi.Wifi;

public class history {

	Hashtable<String, Networks> strongPointsCSV = new Hashtable<>();
	Hashtable<String, Networks> strongPoints = new Hashtable<>();
	Hashtable<String, Networks> filteredStrongPoints = new Hashtable<>();

	/**
	 * Constructor
	 * @param strongPoints - Database
	 */
	public history(Hashtable<String, Networks> strongPoints) {
		this.strongPoints = strongPoints;
	}
	
	/**
	 * 
	 * @return current database(if filtered is empty then all database, otherwise the filtered database).
	 */
	public Hashtable<String, Networks> getPoints(){
		if(!this.filteredStrongPoints.isEmpty())
			return this.filteredStrongPoints;
		else 
			return this.strongPoints;
	}

	/**
	 * replace current database with new database.
	 * @param strongPoints - database
	 */
	public void updateHistory(Hashtable<String, Networks> strongPoints) {
		this.strongPoints = strongPoints;
	}

	/**
	 * replace current CSVdatabase with new database and send to updateHistory.
	 * @param strongPoints - database
	 */
	public void updateHistoryCSV(Hashtable<String, Networks> strongPoints) {
		this.strongPointsCSV = strongPoints;
		updateHistory(this.strongPointsCSV);
	}

	/**
	 * clear all the database's.
	 */
	public void clear() {
		strongPointsCSV.clear();
		strongPoints.clear();
		filteredStrongPoints.clear();
	}

	/**
	 * filter the database, and put the filtered database into FilteredStrongPoints.
	 * @param filter - filter
	 */
	public void filter(Filter filter) {
		filteredStrongPoints = new Hashtable<>();
		for (String key : this.strongPoints.keySet()) {
			if(filter.test(this.strongPoints.get(key)))
				this.filteredStrongPoints.put(key, this.strongPoints.get(key));
		}
		System.out.println("Filtered " + filteredStrongPoints.size() + " Points");
	}

	/**
	 * Calculate how many different MAC address in current database(filtered if not empty).
	 * @return MAC count
	 */
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

	/**
	 * Search in our Database the macSample, and calculate it's location
	 * @param macSample - MAC Address that we need to find
	 * @return Location of the MAC Address as [Lat,Lon,Alt]
	 */
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
	
	/**
	 * Search in our Database a scan that similar to scanSample, and calculate it's location
	 * @param scanSample - Scan sample in a format of CSVCombined
	 * @return Location of user as [Lat,Lon,Alt]
	 */
	public double[] findUserloc(String [] scanSample) {
		String time = scanSample[0];
		String id = scanSample[1];
		Hashtable<String, Networks> goodSamples = new Hashtable<>();
		Networks wifiScan = new Networks(time,id,0,0,0);
		for (int i = 6; i < scanSample.length; i=i+4) {
			String ssid = scanSample[i];
			String mac = scanSample[i+1];
			int channel = Integer.parseInt(scanSample[i+2]);
			double signal = Double.parseDouble(scanSample[i+3]);
			wifiScan.add(ssid, mac, (int)signal, channel);
		}

		for (String key : strongPoints.keySet()) {
			boolean isSample = false;
			for (int i = 0; i < strongPoints.get(key).getPoints().size(); i++) {
				for (int j = 0; j < wifiScan.getPoints().size(); j++) {
					if(strongPoints.get(key).getPoints().get(i).getMAC().equals(wifiScan.getPoints().get(j).getMAC()))
						isSample = true;
				}
			}
			if(isSample)
				goodSamples.put(key, strongPoints.get(key));
		}
		double[] loc = EstimateLoc.searchPi(wifiScan, 4, goodSamples);
		return loc;
	}

}
