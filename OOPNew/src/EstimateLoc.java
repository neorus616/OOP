import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class EstimateLoc {


	final static int NORM = 10000, POWER = 2, MIN_DIFF = 3, DIFF_NO_SIG = 100;
	final static double  SIG_DIFF = 0.4;

	public static void main(String[] args){
			
		Networks userNetwork = new Networks("ID", "Time", 0, 0, 0);
		userNetwork.add("temp1", "c0:ac:54:f5:b4:c9", -50, 10);
		userNetwork.add("temp2", "b4:ee:b4:86:c0:f1", -70, 10);
		userNetwork.add("temp3", "80:1f:02:f5:d8:e8", -90, 10);
		
		String fileName = "C:\\Users\\Lowhacker\\Desktop\\book1.csv";
		searchPi(fileName, userNetwork, 3);
		
	}
	
	public static void apEstimateLoc(String db, String wifiscans, String filename, int k) {
		
	}

	public static void userEstimateLoc(String db, String wifiscans, String filename, int k) {
		try {
		      FileReader fr = new FileReader(wifiscans);
		      BufferedReader csv_br = new BufferedReader(fr);
		      String line = "";
		      while ((line = csv_br.readLine()) != null) {
		        try {
		        	
		        }
		        catch (Exception e)
		        {
		          System.err.println("ERR reading line " + ") Line: " + line);
		        }
		      }
		    }
		    catch (Exception e) {
		      e.printStackTrace();
		    }
	}
	
	
	

	private static double[] searchPi(String filename, Networks userNetwork , int k) {
		Networks[] bestNetworks = new Networks[k];
		double[] bestPi = new double[k];
		double weight = 1;
		int c = 0;
		boolean isMatch = false;
		ArrayList<Networks> db = Tempo.macFilterCSV(filename, userNetwork);
		for (Networks networks : db) {
			//System.out.println(networks.toString());
			for (int i = 0; i < userNetwork.getPoints().size(); i++) {
				for (int j = 0; j < networks.getPoints().size(); j++) {
					if(userNetwork.getPoints().get(i).getMAC().equals(networks.getPoints().get(j).getMAC())) {
						//System.out.println(difference(userNetwork.getPoints().get(i), networks.getPoints().get(j)));
						weight *= NORM /(
								Math.pow(difference(userNetwork.getPoints().get(i), networks.getPoints().get(j)),SIG_DIFF) *
								Math.pow(userNetwork.getPoints().get(i).getSignal(), POWER));
						
						isMatch = true;
					}
				}
				if(!isMatch) {
					weight *=NORM /(
							Math.pow(DIFF_NO_SIG,SIG_DIFF) *
							Math.pow(userNetwork.getPoints().get(i).getSignal(), POWER));
				}
				isMatch = false;
			}
			if(c < k) {
				System.out.println(weight);
				bestPi[c] = weight;
				bestNetworks[c] = networks;
				c++;
			}
			else {
				int min = min(bestPi);
				if(bestPi[min] < weight) {
					bestPi[min] = weight;
					bestNetworks[min] = networks;
				}

			}
			weight = 1;
		}
		
		return EstimateAlgo.wcenter(bestNetworks, bestPi);

	}

	static double difference(Wifi a, Wifi b) {
		return Math.abs(b.getSignal() - a.getSignal()) ;
	}

	static int min(double[] arr) {
		int min = 0;
		for (int i = 1; i < arr.length; i++) {
			if(arr[min]>arr[i])
				min = i;
		}
		return min;
	}
}
