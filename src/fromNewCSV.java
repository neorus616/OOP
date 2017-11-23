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
 * <b>description:</b> <br>
 * filterCSV receives file and a filter which will be used for filtering the content
 * of the file into a hashmap.
 * validate 
 */

public class fromNewCSV {
	
	/**
	 * 
	 * @param path - holds the file path.
	 * @param filter - holds the attribute to filter with.
	 */

	public static void filterCSV(String path, String filter) {
		try {
			Map<String, networks> strongPoints = new HashMap<>();
			String newPath = newPath(path);
			int k = filter.lastIndexOf('=');
			String filterBy = filter.substring(0,k).trim(); //id or location or time
			filter = filter.substring(k+1).trim(); //which id or location or time
			if(validCSV(path, filter)) {
			FileReader in = new FileReader(path);
			BufferedReader bufferedReader = new BufferedReader(in);
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(bufferedReader);
			networks network = new networks();
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
					network = new networks(id, time, lat, lon, alt);
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
			toKML.writeKMLFile(strongPoints, newPath + "newUpgradedKML.kml");
			}
			else System.out.println("not a valid CSV file !");
			
		} catch (IOException | IllegalArgumentException e) {
			// TODO Auto-generated catch block
			System.out.println("not a valid CSV file !");
		}
	}
	private static boolean validCSV(String path, String filter) {
		boolean validFilter = true;
		boolean validCSV = false;
		String extension = "";
		File f = new File(path);
		if(f.isFile())
			validCSV = true;
		if(validCSV && (filter.contains("id") || filter.contains("time") || filter.contains("location")))
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
	
	private static String newPath(String path) {
		int k = path.lastIndexOf("/");
		int j = path.lastIndexOf("\\");
		String newPath = k<j ? path.substring(0,j+1) : path.substring(0,k+1);
		return newPath;
	}
}
