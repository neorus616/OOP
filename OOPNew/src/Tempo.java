import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 * 
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>
 * @version 2.0  
 * <b>Description:</b> <br>
 * Temporary.
 */
public class Tempo {

	/**
	 * 
	 * @param path -  File path.
	 * @param wifiScan - User current APs scan.
	 */
	public static ArrayList<Networks> macFilterCSV(String path, Networks wifiScan) {
		ArrayList<Networks> APs = new ArrayList<Networks>();
		try {
			if(validCSV(path)) {
				FileReader in = new FileReader(path);
				BufferedReader bufferedReader = new BufferedReader(in);
				Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(bufferedReader);
				Networks network = new Networks();
				for (CSVRecord record : records) {
					String time = record.get("Time");
					double lat = Double.parseDouble(record.get("Lat"));
					double lon = Double.parseDouble(record.get("Lon"));
					double alt = Double.parseDouble(record.get("Alt"));
					String id = record.get("ID");
					boolean isSample = false;
					network = new Networks(id, time, lat, lon, alt);
					for (int i = 1; i <= Integer.parseInt(record.get("#WIFI Networks")); i++) {
						String mac = record.get("MAC" + i);
						String ssid = record.get("SSID" + i);
						int channel = Integer.parseInt(record.get("Frequncy" + i));
						double signal = Double.parseDouble(record.get("Signal" + i));
						network.add(ssid, mac, (int)signal, channel);
						for (int j = 0; j < wifiScan.getPoints().size(); j++) {
							if(mac.equals(wifiScan.getPoints().get(j).getMAC())) {
								isSample = true;
							}
						}
					}
					if(isSample) {
						//System.out.println(network);
						APs.add(network);
					}
					network = new Networks();
				}
				
			}
			else throw new IllegalArgumentException("Not a valid CSV file !");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			//e.printStackTrace();
			throw new IllegalArgumentException("Not a valid CSV file !");
		} catch (StringIndexOutOfBoundsException e) {
			throw new StringIndexOutOfBoundsException("Wrong filter statement ! \n try like this: \"id/date/location = Lenovo PB2-690Y/2017-10-27 16:13:51/32.16,34.80,46.34\" ");
		}
		return APs;
	}

	/**
	 * 
	 * Validate that the path is in the correct form of "/CSV/sample.csv".
	 * @param path -  File path.
	 * @return CSV file is valid or not.
	 */
	private static boolean validCSV(String path) {
		boolean validCSV = false;
		String extension = "";
		File f = new File(path);
		if(f.isFile())
			validCSV = true;
		int k = path.lastIndexOf('.');
		if (k > 0)
			extension = path.substring(k+1);
		if(extension.compareTo("csv") != 0)
			validCSV = false;
		return validCSV;
	}
}

