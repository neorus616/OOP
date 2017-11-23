import java.io.*;
/**
 * 
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>  
 * <b>description:</b> <br>
 * validPath receives a path to directory that contains csv file\s
 * from the user. <br>
 * validates the path, and for each correct csv file,
 * calls the function readCSV() in fromCSV class.
 *
 */
public class validPath {

	/**
	 * 
	 * @param path - holds the user input for the csv directory.
	 * 
	 */
	public static void vp(String path) {
		int counter = 0;
		File f = new File(path);
		if(f.isDirectory()) {
			File[] a = f.listFiles();
			for(int i = 0; i < a.length; i++) {
				String fileName = a[i].getName();
				String extension = "";
				int k = fileName.lastIndexOf('.');
				if (k > 0)
					extension = fileName.substring(k+1);
				if(extension.compareTo("csv") == 0) {
					fromCSV.readCSV(path, fileName);
					counter++;
				}
			}
			if(counter == 0)
				System.out.println("there is no CSV files");
		}
		else 
			System.out.println(path + " is Not a Directory");
	}
}
