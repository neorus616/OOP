import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 * 
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>  
 * <b>description:</b> <br>
 * readCSV uses Commons Apache library, receives a path and a file name which the
 * function vp(validPath) send to it (which checked if it contain/s CSV file/s) <br>
 * and validate if the CSV file is in the right format for our request. <br>
 * It creates an object "networks" which stores the records of the content.
 * after it done, it sends it to the function writeCsvFile() in toCSV class.
 *
 */

public class fromCSV {


	/**
	 * 
	 * @param path - holds the directory.
	 * @param fileName - holds the file name.
	 * @exception if the file corrupted/wrong format.
	 * 
	 */
	static void readCSV(String path, String fileName) {
		try {
			boolean validCSV = true;
			FileReader in = new FileReader(path + fileName);
			BufferedReader bufferedReader = new BufferedReader(in);
			String csvSplitBy = ",";
			String[] wifi = bufferedReader.readLine().split(csvSplitBy);
			String model = "";
			if(wifi.length > 2 && wifi[2].contains("model"))
				model = model + wifi[2].substring(6, wifi[2].length());
			else validCSV = false;
			Hashtable<String, networks> strongPoints =  new Hashtable<>(); ////K. V
			if(validCSV) {
				Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(bufferedReader);
				networks network = new networks();
				for (CSVRecord record : records) {
					String mac = record.get("MAC");
					String ssid = record.get("SSID");
					String time = record.get("FirstSeen");
					int channel = Integer.parseInt(record.get("Channel"));
					int signal = Integer.parseInt(record.get("RSSI"));
					double lat = Double.parseDouble(record.get("CurrentLatitude"));
					double lon = Double.parseDouble(record.get("CurrentLongitude"));
					double alt = Double.parseDouble(record.get("AltitudeMeters"));
					// System.out.println(mac + ssid + time + channel + signal + lat + lon + alt);
					
					if(strongPoints.containsKey(time))
						strongPoints.get(time).add(ssid, mac, signal, channel);
					else {
						network = new networks(model, time, lat, lon, alt);
						network.add(ssid, mac, signal, channel);
						strongPoints.put(time, network);
					}
					
//					if(network.getTime().equals(time))
//						network.add(ssid, mac, signal, channel);
//					else {
//						if(!network.getTime().equals(""))
//							toCSV.writeCsvFile(network, path + "newUpgradedCSV.csv");
//						network = new networks(model, time, lat, lon, alt);
//						network.add(ssid, mac, signal, channel);
//					}
				}
				toCSV.writeCsvFile(strongPoints, path + "newUpgradedCSV.csv");
				
			}
			else System.out.println(fileName + " is not a valid CSV format !");

		} catch (IOException | IllegalArgumentException e) {
			// TODO Auto-generated catch block
			System.out.println(fileName + " is not a valid CSV file !");
		}
		

	}

}
