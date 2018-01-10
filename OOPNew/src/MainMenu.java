/**
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>
 * @version 2.0
 * <b>Description:</b> <br>
 * Main menu of the program, where user starts from. <br>
 * Receive CSV directory path from user to combine or Combined CSV file to filter and export to KML. <br>
 */

import java.util.Hashtable;

public class MainMenu {

	public static void main(String[] args){
		// TODO Auto-generated method stub
		
		//readAndWriteComb("DBwifiscans/","DBwifiscans/VeryUpgradedCSV.csv");
		//readAndWriteKML("CSV/VeryUpgradedCSV.csv", "date = 2017-10-27 16:27:03,2017-10-27 16:37:03", "CSV/VeryUpgradedKML.kml");
	
		//EstimateLoc.apEstimateLoc("CSVLoc\\testing\\_comb_all_BM2_.csv","CSVLoc\\testing\\MYAlgo1_BM2_4.csv", 4);
		//EstimateLoc.userEstimateLoc("CSVLoc\\testing\\_comb_all_BM2_.csv","CSVLoc\\testing\\_comb_no-GPS_TS2.csv" ,"CSVLoc\\MYAlgo2_BM2_4.csv", 4);

	}
	
	/**
	 * Function for Assigment 2.
	 * @param path - db location
	 * @param savePath - save location
	 */
	public static void readAndWriteComb(String path, String savePath) {
		Hashtable<String, Networks> strongPoints =  ImportCSV.validPath(path);
		ExportCSV.writeCsvFile(strongPoints,savePath,1);
	}
	
	/**
	 * Function for Assigment 2.
	 * @param path - db location
	 * @param filter - filter by
	 * @param savePath - save location
	 */
	public static void readAndWriteKML(String path, String filter, String savePath) {
		ExportKML.writeKMLFile(ImportCombinedCSV.filterCSV(path, filter), savePath, 1);
	}

}