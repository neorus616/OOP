import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class EstimateLoc {


	final static int NORM = 10000, POWER = 2, MIN_DIFF = 3, DIFF_NO_SIG = 100;
	final static double  SIG_DIFF = 0.4;

	public static void main(String[] args){

		//apEstimateLoc("CSVLoc\\testing\\_comb_all_BM2_.csv","CSVLoc\\testing\\MYAlgo1_BM2_4.csv", 4);
		userEstimateLoc("CSVLoc\\testing\\_comb_all_BM2_.csv","CSVLoc\\testing\\_comb_no-GPS_TS2.csv" ,"CSVLoc\\MYAlgo2_BM2_4.csv", 4);
		

		
	}

	public static void apEstimateLoc(String db, String savePath, int k) {
		try {
			Hashtable<String, APNetworks> strongPoints = new Hashtable<>();
			FileReader in = new FileReader(db);
			BufferedReader bufferedReader = new BufferedReader(in);
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(bufferedReader);
			APNetworks network = new APNetworks();
			network.setNumAP(k);
			for (CSVRecord record : records) {
				String time = record.get("Time");
				double lat = Double.parseDouble(record.get("Lat"));
				double lon = Double.parseDouble(record.get("Lon"));
				double alt = Double.parseDouble(record.get("Alt"));
				String id = record.get("ID");
				for (int i = 1; i <= Integer.parseInt(record.get("#WIFI Networks")); i++) {
					String mac = record.get("MAC" + i);
					String ssid = record.get("SSID" + i);
					int channel = Integer.parseInt(record.get("Frequncy" + i));
					double signal = Double.parseDouble(record.get("Signal" + i));
					network = new APNetworks(id, time);
					network.setNumAP(k);
					network.add(ssid, mac, (int)signal, channel, lat, lon, alt);
					if(!strongPoints.containsKey(network.getPoints().get(0).getMAC())) {
						strongPoints.put(network.getPoints().get(0).getMAC(), network);
					}
					else strongPoints.get(network.getPoints().get(0).getMAC()).add(ssid, mac, (int)signal, channel, lat, lon, alt);
				}	
			}
			Set<String> keys = strongPoints.keySet();
			Iterator<String> itr = keys.iterator();
			String str = "";
			double [] loc;
			Hashtable <String, Networks> locAP = new Hashtable<String, Networks>();
			while (itr.hasNext()) {
				str = itr.next();
				loc = EstimateAlgo.wcenter(strongPoints.get(str).getPoints());
				strongPoints.get(str).setLat(loc[0]);
				strongPoints.get(str).setLon(loc[1]);
				strongPoints.get(str).setAlt(loc[2]);
				Networks locAPs = new Networks(strongPoints.get(str).getID(),strongPoints.get(str).getTime(),loc[0],loc[1],loc[2]);
				locAPs.add(strongPoints.get(str).getPoints().get(0).getSSID(), str, strongPoints.get(str).getPoints().get(strongPoints.get(str).maxSignal()).getSignal(), strongPoints.get(str).getPoints().get(0).getChannel());
				locAP.put(str, locAPs);
			}
			ExportCSV.writeCsvFile(locAP, savePath, 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void userEstimateLoc(String db, String wifiscans, String filename, int k) {
		try {
			FileReader fr = new FileReader(wifiscans);
			BufferedReader bufferedReader = new BufferedReader(fr);
			Hashtable<String, Networks> userLoc =  new Hashtable<>();
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(bufferedReader);
			Networks network = new Networks();
			for (CSVRecord record : records) {
				String time = record.get("Time");
				String id = record.get("ID");
				network = new Networks(id, time, 0,0,0);
				for (int i = 1; i <= Integer.parseInt(record.get("#WIFI Networks")); i++) {
					String mac = record.get("MAC" + i);
					String ssid = record.get("SSID" + i);
					int channel = Integer.parseInt(record.get("Frequncy" + i));
					int signal = Integer.parseInt(record.get("Signal" + i));
					network.add(ssid, mac, signal, channel);
				}
				double [] loc = searchPi(db, network, k);
				network.setLat(loc[0]);
				network.setLon(loc[1]);
				network.setAlt(loc[2]);
				userLoc.put(time, network);
				network = new Networks(id, time, 0,0,0);
			}
			ExportCSV.writeCsvFile(userLoc, filename, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private static double[] searchPi(String filename, Networks userNetwork , int k) {
		Networks[] bestNetworks = new Networks[k];
		double[] bestPi = new double[k];
		double weight = 1;
		int c = 0;
		boolean isMatch = false;
		ArrayList<Networks> db = Tempo.macFilterCSV(filename, userNetwork);
		for (Networks networks : db) {
			for (int i = 0; i < userNetwork.getPoints().size(); i++) {
				for (int j = 0; j < networks.getPoints().size(); j++) {
					if(userNetwork.getPoints().get(i).getMAC().equals(networks.getPoints().get(j).getMAC())) {
						weight *= NORM /(
								Math.pow(difference(userNetwork.getPoints().get(i), networks.getPoints().get(j)),SIG_DIFF) *
								Math.pow(userNetwork.getPoints().get(i).getSignal(), POWER));
						isMatch = true;
						if(weight > 2) {
							System.out.println(userNetwork.getPoints().get(i).getMAC());
						}
					}
				}
				if(!isMatch) {
					weight *=NORM /(
							Math.pow(DIFF_NO_SIG,SIG_DIFF) *
							Math.pow(userNetwork.getPoints().get(i).getSignal(), POWER));
				}
				isMatch = false;

			}
			if(c < k) {
				bestPi[c] = weight;
				bestNetworks[c] = networks;
				c++;
			}
			else {
				int min = min(bestPi);
				if(bestPi[min] < weight) {
					bestPi[min] = weight;
					bestNetworks[min] = networks;
				}

			}
			weight = 1;
		}

		return EstimateAlgo.wcenter(bestNetworks, bestPi, c);

	}

	static double difference(Wifi a, Wifi b) {
		return Math.abs(b.getSignal() - a.getSignal()) + MIN_DIFF ;
	}

	static int min(double[] arr) {
		int min = 0;
		for (int i = 1; i < arr.length; i++) {
			if(arr[min]>arr[i])
				min = i;
		}
		return min;
	}
}