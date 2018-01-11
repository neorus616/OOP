package ui;
/**
 * 
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>  
 * @version 2.0
 * <b>Description:</b> <br>
 * Object that running on LastModified of each file in loaded folder.
 */
import java.io.File;
import io.ImportCSV;

public class watcher extends Thread {

	File _folder;
	long _lastModified;
	history _database;
	long [] _lastModifiedFiles;
	File[] listFiles;
	MainMenuGui _gui;
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
		listFiles = _folder.listFiles();
		_lastModifiedFiles = new long[listFiles.length];
		for (int i = 0; i < listFiles.length; i++) {
			_lastModifiedFiles[i] = listFiles[i].lastModified();
		}
	}

	public void register(MainMenuGui gui){
		_gui = gui;
	}

	/**
	 * update current database and watch for changed in path directory.
	 */
	@Override
	public void run() {
		_database.updateHistory(ImportCSV.mergeHash(_database.getPoints(), ImportCSV.validPath(_folder.getAbsolutePath()+"\\")));
		_gui.update();
		while(true) {
			if(_lastModified != _folder.lastModified()) {
				System.out.println("changed");
				listFiles = _folder.listFiles();
				_lastModifiedFiles = new long[listFiles.length];
				for (int i = 0; i < listFiles.length; i++) {
					_lastModifiedFiles[i] = listFiles[i].lastModified();
				}
				_lastModified = _folder.lastModified();
				try {
					sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				_database.updateHistory(ImportCSV.validPath(_folder.getAbsolutePath()+"\\"));
				_gui.update();
			} else {
				for (int i = 0; i < _lastModifiedFiles.length; i++) {
					if(_lastModifiedFiles[i] != listFiles[i].lastModified()) {
						System.out.println("changed");
						_lastModifiedFiles[i] = listFiles[i].lastModified();
						try {
							sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						_database.updateHistory(ImportCSV.validPath(_folder.getAbsolutePath()+"\\"));
						_gui.update();
					}
				}
			}
		}
	}
}
