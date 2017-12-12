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
	public static void filterCSV(String path, String filter) {
		try {
			Map<String, Networks> strongPoints = new HashMap<>();
			String newPath = newPath(path);
			int k = filter.lastIndexOf('=');
			String filterBy = filter.substring(0,k).trim(); //id or location or time
			filter = filter.substring(k+1).trim(); //which id or location or time
			if(validCSV(path, filterBy)) {
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
						if(network.filter(filterBy, filter)) {
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
				ExportKML.writeKMLFile(strongPoints, newPath + "newUpgradedKML.kml");
			}
			else throw new IllegalArgumentException("Not a valid CSV file !");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Not a valid CSV file !");
		} catch (StringIndexOutOfBoundsException e) {
			throw new StringIndexOutOfBoundsException("Wrong filter statement ! \n try like this: \"id/date/location = Lenovo PB2-690Y/2017-10-27 16:13:51/32.16,34.80,46.34\" ");
		}
	}

	/**
	 * 
	 * Validate that the path and the filter in the correct form of "/CSV/sample.csv" and "id\date\location = xyz".
	 * @param path -  File path.
	 * @param filter - Attribute to filter with.
	 * @return CSV file or filter type is valid or not.
	 */
	private static boolean validCSV(String path, String filter) {
		boolean validFilter = true;
		boolean validCSV = false;
		String extension = "";
		File f = new File(path);
		if(f.isFile())
			validCSV = true;
		if(validCSV && (filter.contains("id") || filter.contains("date") || filter.contains("location")))
			validCSV = true;
		else if(validCSV)
			validFilter = false;
		int k = path.lastIndexOf('.');
		if (k > 0)
			extension = path.substring(k+1);
		if(extension.compareTo("csv") != 0)
			validCSV = false;
		return validCSV&&validFilter;
	}

	/**
	 * 
	 * Gets the directory where the combined CSV is located(to save later the KML file).
	 * @param path - hold file path.
	 * @return newPath - directory path. 
	 */
	private static String newPath(String path) {
		int k = path.lastIndexOf("/");
		int j = path.lastIndexOf("\\");
		String newPath = k<j ? path.substring(0,j+1) : path.substring(0,k+1);
		return newPath;
	}
}