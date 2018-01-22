package main.java.io;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import main.java.filters.*;
import main.java.wifi.Networks;

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
	 * @return Hashtable of filtered networks.
	 */
	public static Hashtable<String, Networks> filterCSV(String path, String filter) {
		Hashtable<String, Networks> strongPoints = new Hashtable<>();
		try {
			Filter filterBy = filter(filter, false);
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
						double signal = Double.parseDouble(record.get("Signal" + i));
						network = new Networks(id, time, lat, lon, alt);
						network.add(ssid, mac, (int)signal, channel);
						if(filterBy != null && filterBy.test(network)) {
							if(!strongPoints.containsKey(network.getPoints().get(0).getMAC())) {
								strongPoints.put(network.getPoints().get(0).getMAC(), network);
							}
							else if(network.getPoints().get(0).compareTo(strongPoints.get(network.getPoints().get(0).getMAC()).getPoints().get(0)) == 1) {
								strongPoints.remove(network.getPoints().get(0).getMAC());
								strongPoints.put(network.getPoints().get(0).getMAC(), network);
							}	
						}
						else if(filter.equals("")){
							for (int k = i+1; k <= Integer.parseInt(record.get("#WIFI Networks")); k++) {
								mac = record.get("MAC" + k);
								ssid = record.get("SSID" + k);
								channel = Integer.parseInt(record.get("Frequncy" + k));
								signal = Double.parseDouble(record.get("Signal" + k));
								network.add(ssid, mac, (int)signal, channel);
							}
							if(!strongPoints.containsKey(time+id)) {
								strongPoints.put((time+id), network);
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

	/**
	 * 
	 * @param filter - String that explain the filter
	 * @param not - states if NOT operator is used
	 * @return Filter object
	 */
	public static Filter filter(String filter, boolean not) {
		int k = filter.lastIndexOf('=');
		if(filter.contains("ID") || filter.contains("id") || filter.contains("Id")) //ID = lenovo
			return new IDFilter(filter.substring(k+1).trim(), !not);
		if(filter.contains("Location") || filter.contains("location")) { 
			if(filter.substring(k+1).trim().split(",").length == 6)  //Location = 100,200,150,200,30,60
				return new LocationFilter(filter.substring(k+1).trim().split(","), !not); //Location = 32.16876665,34.81320794,100
			else return new LocationFilter(filter.substring(k+1).trim().split(",")[0],filter.substring(k+1).trim().split(",")[1],filter.substring(k+1).trim().split(",")[2]);
		}
		if(filter.contains("Date") || filter.contains("date")) //Date = 2017-10-27 16:27:03,2017-10-27 16:37:03
			return new TimeFilter(filter.substring(k+1).trim().split(","), !not);
		return null;
	}

	/**
	 * 
	 * @param filter1 - First filter
	 * @param filter2 - Second filter
	 * @param operator - states if AND or OR is used
	 * @return Filter object
	 */
	public static Filter filter(Filter filter1, Filter filter2, String operator) {
		if(filter1 == null)
			return filter2;
		else if (filter2 == null)
			return filter1;
		else if(operator.equals("AND"))
			return new AndFilter(filter1, filter2);
		else if(operator.equals("OR"))
			return new OrFilter(filter1,filter2);
		return null;

	}
}