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
 * <b>description:</b> <br>
 * validPath receives a path to directory that contains csv file\s
 * from the user. <br>
 * validates the path, and for each correct csv file,
 * calls the function readCSV() in fromCSV class.
 * 
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
	 * @param path - holds the user input for the csv directory.
	 * 
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
					System.out.println(fileName);
					fromCSV.readCSV(path, fileName);
					counter++;
				}
			}
			if(counter == 0)
				System.out.println("there is no CSV files");
		}
		else 
			System.out.println(path + " is Not a Directory");
	}

	/**
	 * 
	 * @param path - holds the directory.
	 * @param fileName - holds the file name.
	 * @exception if the file corrupted/wrong format.
	 * 
	 */
	static void readCSV(String path, String fileName) {
		try {
			FileReader in = new FileReader(path + fileName);
			BufferedReader bufferedReader = new BufferedReader(in);
			String csvSplitBy = ",";
			String[] catchModel = bufferedReader.readLine().split(csvSplitBy);	
			Hashtable<String, networks> strongPoints =  new Hashtable<>();
			if(catchModel.length > 2 && catchModel[2].contains("model")) {
				String model = catchModel[2].substring(6, catchModel[2].length());
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
					if(strongPoints.containsKey(time))
						strongPoints.get(time).add(ssid, mac, signal, channel);
					else {
						network = new networks(model, time, lat, lon, alt);
						network.add(ssid, mac, signal, channel);
						strongPoints.put(time, network);
					}
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
