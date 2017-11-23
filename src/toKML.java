import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;

/**
 * 
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>  
 * <b>description:</b> <br>
 * a class that uses JAK library to write KML file, that will contain our data from 
 * the CSV file(which filterCSV sent)
 */

public class toKML {

	/**
	 * 
	 * @param strongPoints - a filtered hashmap.
	 * @param fileName -path and file name where it saves the new KML file. 
	 */
	static void writeKMLFile(Map<String, networks> strongPoints, String fileName) {
		final Kml kml = new Kml();
		Document document = kml.createAndSetDocument();
		for (String point : strongPoints.keySet()){
			document.createAndAddPlacemark().withName(strongPoints.get(point).getPoints().get(0).getSSID()).withDescription("<b>SSID:</b> " + strongPoints.get(point).getPoints().get(0).getSSID() +"<br/><b>MAC:</b> " + 
					strongPoints.get(point).getPoints().get(0).getMAC() +"<br/><b>Frequency:</b> " + 
					strongPoints.get(point).getPoints().get(0).getChannel() +"<br/><b>Signal:</b> " + 
					strongPoints.get(point).getPoints().get(0).getSignal() + "<br/><b>Date:</b>" +
					strongPoints.get(point).getTime() + "<br/><b>ID:</b> " + strongPoints.get(point).getID()).createAndSetPoint().
			addToCoordinates(strongPoints.get(point).getLon(), strongPoints.get(point).getLat(), strongPoints.get(point).getAlt());
			//to do: time stamp
		}
		try {
			kml.marshal(new File(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File Not Found !");
		}
	}
}
