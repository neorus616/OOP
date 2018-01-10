/**
 * 
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>
 * @version 2.0
 */

import java.io.File;

public class watcher extends Thread {

	File _folder;
	long _lastModified;
	history _database;

	/**
	 * Constructor
	 * @param path - Directory path to CSV files
	 * @param database - Object that hold Database's
	 */
	public watcher(String path, history database) {
		_folder = new File(path);
		_lastModified = _folder.lastModified();
		_database = database;
		_database.updateHistory(database.getPoints());
	}

	/**
	 * update current database and watch for changed in path directory.
	 */
	@Override
	public void run() {
		_database.updateHistory(ImportCSV.mergeHash(_database.getPoints(), ImportCSV.validPath(_folder.getAbsolutePath()+"\\")));
		System.out.println(_database.getPoints().size());
		while(true) {
			if(_lastModified != _folder.lastModified()) {
				System.out.println("changed");
				_lastModified = _folder.lastModified();
				try {
					sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				_database.updateHistory(ImportCSV.validPath(_folder.getAbsolutePath()+"\\"));
			}
		}
	}
}
