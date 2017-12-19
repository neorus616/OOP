import java.io.BufferedReader;
import java.io.File;
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
 * @version 2.0
 * <b>Description:</b> <br>
 * Receive path to CSV file\s directory from user and validate the path. <br>
 * Use Commons Apache library to validate and import the CSV file in the desirable format. <br>
 * Create object "networks" which stores the records of the content.
 * then, send it to ExportCSV class for further processing.
 */
public class ImportCSV {

	/**
	 * @param path - User input for the CSV directory.
	 * @exception IllegalArgumentException - No CSV files or not a directory.
	 */
	public static void validPath(String path) {
		int counter = 0;
		File f = new File(path);
		if(f.isDirectory()) {
			File[] a = f.listFiles();
			for(int i = 0; i < a.length; i++) {
				String fileName = a[i].getName();
				String extension = "";
				int k = fileName.lastIndexOf('.');
				if (k > 0)
					extension = fileName.substring(k+1);
				if(extension.compareTo("csv") == 0) {
					ImportCSV.readCSV(path, fileName);
					counter++;
				}
			}
			if(counter == 0) {
				throw new IllegalArgumentException("there is no CSV files");
			}
		}
		else {
			throw new IllegalArgumentException(path + " is Not a Directory");
		}

	}

	/**
	 * @param path - Directory path.
	 * @param fileName - File name.
	 * @exception IllegalArgumentException - The file corrupted or in wrong format.
	 */
	static void readCSV(String path, String fileName) {
		try {
			FileReader in = new FileReader(path + fileName);
			BufferedReader bufferedReader = new BufferedReader(in);
			String csvSplitBy = ",";
			String[] catchModel = bufferedReader.readLine().split(csvSplitBy);	
			Hashtable<String, Networks> strongPoints =  new Hashtable<>();
			if(catchModel.length > 2 && catchModel[2].contains("model")) {
				String model = catchModel[2].substring(6, catchModel[2].length());
				Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(bufferedReader);
				Networks network = new Networks();
				for (CSVRecord record : records) {
					String mac = record.get("MAC");
					String ssid = record.get("SSID");
					String time = record.get("FirstSeen");
					int channel = Integer.parseInt(record.get("Channel"));
					int signal = Integer.parseInt(record.get("RSSI"));
					double lat = Double.parseDouble(record.get("CurrentLatitude"));
					double lon = Double.parseDouble(record.get("CurrentLongitude"));
					double alt = Double.parseDouble(record.get("AltitudeMeters"));
					if(strongPoints.containsKey(time))
						strongPoints.get(time).add(ssid, mac, signal, channel);
					else {
						network = new Networks(model, time, lat, lon, alt);
						network.add(ssid, mac, signal, channel);
						strongPoints.put(time, network);
					}
				}
				ExportCSV.writeCsvFile(strongPoints, path + "newUpgradedCSV.csv", 1);
			}
			else System.out.println(fileName + " is not a valid CSV format !");

		} catch (IOException | IllegalArgumentException e) {
			throw new IllegalArgumentException(fileName + " is not a valid CSV file !");

		}
	}
}
