package algorithms;
import java.util.ArrayList;

import wifi.AP;
import wifi.Networks;

/**
 * 
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>
 * @version 2.0 <b>Description:</b> <br>
 *          Calculate location of AP or user based on Algorithm 1 or 2.
 */
public class EstimateAlgo {

	/**
	 * 
	 * @param points - ArrayList of Networks object.
	 * @return Array of weighted Latitude, Longitude and Altitude.
	 */
	public static double[] wcenter(ArrayList<AP> points) {
		double wlat = 0, wlon = 0, walt = 0, weight = 0;
		for (AP point : points) {
			wlat += (point.getLat()) / (Math.pow(point.getSignal(), 2));
			wlon += (point.getLon()) / (Math.pow(point.getSignal(), 2));
			walt += (point.getAlt()) / (Math.pow(point.getSignal(), 2));
			weight += 1 / Math.pow(point.getSignal(), 2);
		}
		double[] wcenter = new double[3];
		wcenter[0] = wlat / weight;
		wcenter[1] = wlon / weight;
		wcenter[2] = walt / weight;
		return wcenter;
	}

	/**
	 *
	 * @param network - best networks
	 * @param pi - best PI
	 * @param k - how many "best" networks
	 * @return Array of weighted Latitude, Longitude and Altitude.
	 */
	public static double[] wcenter(Networks[] network, double[] pi, int k) {
		double wlat = 0, wlon = 0, walt = 0, weight = 0;
		for (int i = 0; i < k; i++) {
			wlat += (network[i].getLat()) * pi[i];
			wlon += (network[i].getLon()) * pi[i];
			walt += (network[i].getAlt()) * pi[i];
			weight += pi[i];
		}
		double[] wcenter = new double[3];
		wcenter[0] = wlat / weight;
		wcenter[1] = wlon / weight;
		wcenter[2] = walt / weight;
		return wcenter;
	}
}
