package ui;
/**
 * 
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>  
 * @version 2.0
 * <b>Description:</b> <br>
 * Object that running on LastModified of each file in loaded folder.
 */
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.ImportCSV;
import io.ImportCombinedCSV;
import io.ImportSQL;

public class watcher extends Thread {

	File _folder;
	long _lastModified = (long) 0.0;
	history _database;
	long [] _lastModifiedFiles;
	File[] listFiles;
	File _file; //combCSV
	long _fileLastModified = (long) 0.0;
	MainMenuGui _gui;
	String _ip;
	String _port;
	String _db;
	String _user;
	String _password;
	String _table;
	String _sqlModify = "";
	private static Connection _con = null;
	/**
	 * Constructor
	 * @param path - Directory path to CSV files
	 * @param database - Object that hold Database's
	 */
	public watcher(String path, history database) {
		_folder = new File(path);
		_lastModified = _folder.lastModified();
		_database = database;
		//_database.updateHistoryFolder(database.getPoints());
		listFiles = _folder.listFiles();
		_lastModifiedFiles = new long[listFiles.length];
		for (int i = 0; i < listFiles.length; i++) {
			_lastModifiedFiles[i] = listFiles[i].lastModified();
		}
	}

	public watcher(history database) {
		_database = database;
	}

	/**
	 * function that help get the watcher to notify the main gui to update it stats.
	 * @param gui - the main gui
	 */
	public void register(MainMenuGui gui){
		_gui = gui;
	}

	public void folderWatch(String path) {
		_folder = new File(path);
		_lastModified = _folder.lastModified();
		//_database.updateHistoryFolder(database.getPoints());
		listFiles = _folder.listFiles();
		_lastModifiedFiles = new long[listFiles.length];
		for (int i = 0; i < listFiles.length; i++) {
			_lastModifiedFiles[i] = listFiles[i].lastModified();
		}
		_database.updateHistoryFolder(ImportCSV.validPath(_folder.getAbsolutePath()+"\\"));
		_gui.update();
	}
	public void csvWatch(String CSVpath) {
		_file = new File(CSVpath);
		_fileLastModified = _file.lastModified();
	}

	public void sqlWatch(String ip, String port, String db, String user, String password, String table) {
		_ip = ip;
		_port = port;
		_db = db;
		String url = "jdbc:mysql://" + ip + ":"+ port +"/"+ db +"?useSSL=false";
		_user = user;
		_password = password;
		_table = table;
		_sqlModify = sqlModify(url, user, password, table);
		//System.out.println(_sqlModify);
	}

	public String sqlModify(String url, String user, String password, String table) {
		String modify = "";
		Statement st = null;
		ResultSet rs = null;
		try {     
			_con = DriverManager.getConnection(url, user, password);
			st = _con.createStatement();
			rs = st.executeQuery("SELECT VERSION()");
			while (rs.next()) {
				//Print one row          
				PreparedStatement pst = _con.prepareStatement("SELECT  table_name,  update_time FROM information_schema.tables where table_name='"+table+"'");
				rs = pst.executeQuery();
				if (rs.next())
					modify = rs.getString(2);
			}} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(watcher.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
			} finally {
				try {
					if (rs != null) {rs.close();}
					if (st != null) { st.close(); }
					if (_con != null) { _con.close();  }
				} catch (SQLException ex) {
					Logger lgr = Logger.getLogger(watcher.class.getName());
					lgr.log(Level.WARNING, ex.getMessage(), ex);
				}
			}
		return modify;
	}

	/**
	 * update current database and watch for changed in path directory.
	 */
	@Override
	public void run() {
		while(true) {
			Thread.yield();
			if((_lastModified != (long)0.0) && (_lastModified != _folder.lastModified())) {
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
				_database.updateHistoryFolder(ImportCSV.validPath(_folder.getAbsolutePath()+"\\"));
				_gui.update();
			} else if(!_sqlModify.equals("")){
				Statement st = null;
				ResultSet rs = null;
				try {
					_con = DriverManager.getConnection("jdbc:mysql://" + _ip + ":"+ _port +"/"+ _db +"?useSSL=false", _user, _password);
					st = _con.createStatement();
					rs = st.executeQuery("SELECT VERSION()");
					while (rs.next()) {   
						PreparedStatement pst = _con.prepareStatement("SELECT  table_name,  update_time FROM information_schema.tables where table_name='"+_table+"'");
						rs = pst.executeQuery();
						while (rs.next()){
							if(!rs.getString(2).equals(_sqlModify)) {
								System.out.println("SQL updated");
								_database.updateHistorySQL(ImportSQL.connectSQL(_ip, _password, _user, _port, _db, _table));
								_gui.update();
								_sqlModify = rs.getString(2);
							}
						}
					}
				} catch (SQLException ex) {
					Logger lgr = Logger.getLogger(watcher.class.getName());
					lgr.log(Level.SEVERE, ex.getMessage(), ex);
				} finally {
					try {
						if (rs != null) {rs.close();}
						if (st != null) { st.close(); }
						if (_con != null) { _con.close();  }
					} catch (SQLException ex) {
						Logger lgr = Logger.getLogger(watcher.class.getName());
						lgr.log(Level.WARNING, ex.getMessage(), ex);
					}
				}
			} else if(_file != null) {
				if(_fileLastModified != (long)0.0 && _fileLastModified != _file.lastModified()) {
					_database.updateHistoryCSV(ImportCombinedCSV.filterCSV(_file.getAbsolutePath(),""));
					_gui.update();
				}
			} else if(_lastModifiedFiles != null){
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
						_database.updateHistoryFolder(ImportCSV.validPath(_folder.getAbsolutePath()+"\\"));
						_gui.update();
					}
				}
			}
		}
	}
}
