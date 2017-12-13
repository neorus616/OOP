/**
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>
 * @version 2.0
 * <b>Description:</b> <br>
 * Main menu of the program, where user starts from. <br>
 * Receive CSV directory path from user to combine or Combined CSV file to filter and export to KML. <br>
 */

public class MainMenu {

	public static void main(String[] args){
		// TODO Auto-generated method stub
		
		//ImportCSV.validPath("CSV\\");
		ImportCombinedCSV.filterCSV("C:\\Users\\Adminchuwi\\Documents\\OOP\\CSVLoc\\Algo2_test_BM1_4_3.csv", "date = 2017-10-27 16:27:03,2017-10-27 16:37:03");
	}

}