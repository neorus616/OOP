package main.java.io;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import main.java.wifi.Networks;

/**
 * 
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>
 * @version 2.0 
 * Description:
 * Use JAK library to write KML file from the CSV file (which filterCSV sent).
 */
public class ExportKML {

	/** 
	 * 
	 * @param strongPoints - a filtered hashmap.
	 * @param fileName -path and file name where it saves the new KML file.
	 * @param state - states which kind of kml we want to write
	 */

	public static void writeKMLFile(Hashtable<String, Networks> strongPoints, String fileName, int state) {

		final Kml kml = new Kml();
		Document document = kml.createAndSetDocument();

		Folder folder = document.createAndAddFolder();
		folder.withName("WIFI newtworks").withOpen(true);
		if(state == 1) {
			for (String point : strongPoints.keySet()) {
				Placemark placemark = folder.createAndAddPlacemark();
				placemark.withName(strongPoints.get(point).getPoints().get(0).getSSID()).withDescription("<b>SSID:</b> " + strongPoints.get(point).getPoints().get(0).getSSID() +"<br/><b>MAC:</b> " + 
						strongPoints.get(point).getPoints().get(0).getMAC() +"<br/><b>Frequency:</b> " + 
						strongPoints.get(point).getPoints().get(0).getChannel() +"<br/><b>Signal:</b> " + 
						strongPoints.get(point).getPoints().get(0).getSignal() + "<br/><b>Date:</b>" +
						strongPoints.get(point).getTime() + "<br/><b>ID:</b> " + strongPoints.get(point).getID()).createAndSetLookAt().withLongitude(strongPoints.get(point).getLon()).withLatitude(strongPoints.get(point).getLat()).withAltitude(strongPoints.get(point).getAlt()).withRange(12000000);
				placemark.createAndSetPoint().addToCoordinates(strongPoints.get(point).getLon(), strongPoints.get(point).getLat(), strongPoints.get(point).getAlt()); // set coordinates
				placemark.createAndSetTimeStamp().setWhen(createtimesrap(strongPoints.get(point).getTime()) + "Z");
			}
		}
		else {
			for (String point : strongPoints.keySet()) {
				for (int i = 0; i < strongPoints.get(point).getPoints().size(); i++) {
					Placemark placemark = folder.createAndAddPlacemark();
					placemark.withName(strongPoints.get(point).getPoints().get(i).getSSID()).withDescription("<b>SSID:</b> " + strongPoints.get(point).getPoints().get(i).getSSID() +"<br/><b>MAC:</b> " + 
							strongPoints.get(point).getPoints().get(i).getMAC() +"<br/><b>Frequency:</b> " + 
							strongPoints.get(point).getPoints().get(i).getChannel() +"<br/><b>Signal:</b> " + 
							strongPoints.get(point).getPoints().get(i).getSignal() + "<br/><b>Date:</b>" +
							strongPoints.get(point).getTime() + "<br/><b>ID:</b> " + strongPoints.get(point).getID()).createAndSetLookAt().withLongitude(strongPoints.get(point).getLon()).withLatitude(strongPoints.get(point).getLat()).withAltitude(strongPoints.get(point).getAlt()).withRange(12000000);
					placemark.createAndSetPoint().addToCoordinates(strongPoints.get(point).getLon(), strongPoints.get(point).getLat(), strongPoints.get(point).getAlt()); // set coordinates
					placemark.createAndSetTimeStamp().setWhen(createtimesrap(strongPoints.get(point).getTime()) + "Z");
				}
			}
		}
		try {
			kml.marshal(new File(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found !");
		}
	}

	/**
	 * 
	 * @param time - time
	 * @return - time in kml format
	 */
	private static String createtimesrap(String time){
		String []g=time.split(" ");
		String a=g[0]+"T";
		return a+g[1];
	}
}
