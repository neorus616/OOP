import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 * 
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>
 * @version 2.0  
 * <b>Description:</b> <br>
 * Receive file path and filter type, filtering the content accordingly into a Hashmap.
 */
public class ImportCombinedCSV {

	/**
	 * 
	 * @param path -  File path.
	 * @param filter - Attribute to filter with.
	 */
	public static Map<String, Networks> filterCSV(String path, String filter) {
		Map<String, Networks> strongPoints = new HashMap<>();
		try {
//			String newPath = newPath(path);
			Filter filter1 = filter(filter);
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
					for (int i = 1; i <= Integer.parseInt(record.get("#WIFI Networks")); i++) {
						String mac = record.get("MAC" + i);
						String ssid = record.get("SSID" + i);
						int channel = Integer.parseInt(record.get("Frequncy" + i));
						int signal = Integer.parseInt(record.get("Signal" + i));
						network = new Networks(id, time, lat, lon, alt);
						network.add(ssid, mac, signal, channel);
						if(filter1.test(network)) {
							if(!strongPoints.containsKey(network.getPoints().get(0).getMAC())) {
								strongPoints.put(network.getPoints().get(0).getMAC(), network);
							}
							else if(network.getPoints().get(0).compareTo(strongPoints.get(network.getPoints().get(0).getMAC()).getPoints().get(0)) == 1) {
								strongPoints.remove(network.getPoints().get(0).getMAC());
								strongPoints.put(network.getPoints().get(0).getMAC(), network);
							}	
						}
					}
				}
			}
			else throw new IllegalArgumentException("Not a valid CSV file !");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Not a valid CSV file !");
		} catch (StringIndexOutOfBoundsException e) {
			throw new StringIndexOutOfBoundsException("Wrong filter statement ! \n try like this: \"id/date/location = Lenovo PB2-690Y/2017-10-27 16:13:51/32.16,34.80,46.34\" ");
		}
		return strongPoints;
	}

	/**
	 * 
	 * Validate that the path and the filter in the correct form of "/CSV/sample.csv" and "id\date\location = xyz".
	 * @param path -  File path.
	 * @param filter - Attribute to filter with.
	 * @return CSV file or filter type is valid or not.
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
	
	private static Filter filter(String filter) {
		int k = filter.lastIndexOf('=');
		if(filter.contains("id")) //id = lenovo
			return new IDFilter(filter.substring(k+1).trim());
		if(filter.contains("location")) //location = 100,200,150,200,30,60
			return new LocationFilter(filter.substring(k+1).trim().split(","));
		if(filter.contains("date")) //date = 2017-10-27 16:27:03,2017-10-27 16:37:03
			return new TimeFilter(filter.substring(k+1).trim().split(","));
		return null;
	}

//	/**
//	 * 
//	 * Gets the directory where the combined CSV is located(to save later the KML file).
//	 * @param path - hold file path.
//	 * @return newPath - directory path. 
//	 */
//	private static String newPath(String path) {
//		int k = path.lastIndexOf("/");
//		int j = path.lastIndexOf("\\");
//		String newPath = k<j ? path.substring(0,j+1) : path.substring(0,k+1);
//		return newPath;
//	}
}