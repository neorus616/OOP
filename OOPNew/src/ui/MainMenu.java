package ui;
/**
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>
 * @version 2.0
 * <b>Description:</b> <br>
 * Main menu of the program, where user starts from. <br>
 * Receive CSV directory path from user to combine or Combined CSV file to filter and export to KML. <br>
 */

import java.util.Hashtable;

import io.ExportCSV;
import io.ExportKML;
import io.ImportCSV;
import io.ImportCombinedCSV;
import wifi.Networks;

public class MainMenu {

//	public static void main(String[] args){
//		// TODO Auto-generated method stub
//		
//		//readAndWriteComb("TestCSVFilesForAssigment1/","TestCSVFilesForAssigment1/VeryUpgradedCSV.csv");
//		//readAndWriteKML("TestCSVFilesForAssigment1/VeryUpgradedCSV.csv", "id = SHIELD Tablet", "TestCSVFilesForAssigment1/VeryUpgradedKML.kml");
//	
//		//EstimateLoc.apEstimateLoc("TestCSVFilesForAssigment2/_comb_all_BM2_.csv","TestCSVFilesForAssigment2/MYAlgo1_BM2_4.csv", 4);
//		//EstimateLoc.userEstimateLoc("TestCSVFilesForAssigment2/_comb_all_BM2_.csv","TestCSVFilesForAssigment2/_comb_no-GPS_TS2.csv" ,"TestCSVFilesForAssigment2//MYAlgo2_BM2_4.csv", 4);
//
//	}
	
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