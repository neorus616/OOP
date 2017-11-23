import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * 
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>  
 * <b>description:</b> <br>
 *writeCsvFile receives an object from the function readCSV to write the object 
 *content to a CSV file in our desirable format. <br>
 *
 */

class toCSV {
	//Delimiter used in CSV file
	private static final String NEW_LINE_SEPARATOR = "\n";
	//CSV file header
	static final Object [] FILE_HEADER = {
			"Time","ID","Lat","Lon", "Alt", "#WIFI Networks",
			"SSID1", "MAC1", "Frequncy1", "Signal1",
			"SSID2", "MAC2", "Frequncy2", "Signal2",
			"SSID3", "MAC3", "Frequncy3", "Signal3",
			"SSID4", "MAC4", "Frequncy4", "Signal4",
			"SSID5", "MAC5", "Frequncy5", "Signal5",
			"SSID6", "MAC6", "Frequncy6", "Signal6",
			"SSID7", "MAC7", "Frequncy7", "Signal7",
			"SSID8", "MAC8", "Frequncy8", "Signal8",
			"SSID9", "MAC9", "Frequncy9", "Signal9",
			"SSID10", "MAC10", "Frequncy10", "Signal10"
	};

	/**
	 * 
	 * @param network - object that contains: id, time, location and #WiFi networks 
	 * (up to 10 points)
	 * @param fileName - holds the path and file name where it saves the new CSV file.
	 */
	static void writeCsvFile(Hashtable<String, networks> strongPoints, String fileName) {
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		//Create the CSVFormat object with "\n" as a record delimiter
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
		try {
			//initialize FileWriter object	
			fileWriter = new FileWriter(fileName);
			//initialize CSVPrinter object 
			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
			//Create CSV file header
			csvFilePrinter.printRecord(FILE_HEADER);
			for (String network : strongPoints.keySet()){
				List networkDataRecord = new ArrayList();
				networkDataRecord.add(strongPoints.get(network).getTime());
				networkDataRecord.add(strongPoints.get(network).getID());
				networkDataRecord.add(strongPoints.get(network).getLat());
				networkDataRecord.add(strongPoints.get(network).getLon());
				networkDataRecord.add(strongPoints.get(network).getAlt());
				networkDataRecord.add(strongPoints.get(network).getPoints().size());
				for (wifi point : (strongPoints.get(network).getPoints())) {
					networkDataRecord.add(point.getSSID());
					networkDataRecord.add(point.getMAC());
					networkDataRecord.add(point.getChannel());
					networkDataRecord.add(point.getSignal());
				}
				//Write a new network object list to the CSV file
				csvFilePrinter.printRecord(networkDataRecord);
			}

			//System.out.println("CSV file was created successfully !!!");
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !");
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter/csvPrinter !");
			}
		}
	}
}