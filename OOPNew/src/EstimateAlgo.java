import java.util.ArrayList;

/**
 * 
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>
 * @version 2.0
 * <b>Description:</b> <br>
 * 
 */
public class EstimateAlgo {
	
	/**
	 * 
	 * @param network - ArrayList of Networks object.
	 * @return Array of weighted Latitude, Longitude and Altitude.
	 */
	public static double[] wcenter(ArrayList<Networks> network){
		double wlat = 0, wlon = 0 ,walt = 0, weight = 0;

		for (Networks networks : network) {
			wlat += (networks.getLat())/(Math.pow(networks.getPoints().get(0).getSignal(), 2));
			wlon += (networks.getLon())/(Math.pow(networks.getPoints().get(0).getSignal(), 2));
			walt += (networks.getAlt())/(Math.pow(networks.getPoints().get(0).getSignal(), 2));
			weight += 1/Math.pow(networks.getPoints().get(0).getSignal(), 2);
			}
		double [] wcenter = new double[3];
		wcenter[0] = wlat/weight;
		wcenter[1] = wlon/weight;
		wcenter[2] = walt/weight;
		
		return wcenter;
	}
	
	public static double[] wcenter(Networks[] network, double[] pi){
		double wlat = 0, wlon = 0 ,walt = 0, weight = 0;
		for (int i = 0; i < network.length; i++) {
			wlat += (network[i].getLat())* pi[i];
			wlon += (network[i].getLon())* pi[i];
			walt += (network[i].getAlt())* pi[i];
			weight += pi[i];
			}
		double [] wcenter = new double[3];
		wcenter[0] = wlat/weight;
		wcenter[1] = wlon/weight;
		wcenter[2] = walt/weight;
		
		return wcenter;
	}	
}
