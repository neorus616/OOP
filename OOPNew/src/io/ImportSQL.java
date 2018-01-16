package io;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import wifi.Networks;

import java.sql.Statement;

public class ImportSQL {

	private static Connection _con = null;

	public static void main(String[] args) {
		String ip = "5.29.193.52";
		String pw = "Lambda2();";
		String user = "oop2";
		String port = "3306";
		String db = "oop_course_ariel";
		String table = "ex4_db";
		test_101(ip,pw,user,port,db,table);
	}
	@SuppressWarnings("resource")
	public static Hashtable<String, Networks> test_101(String ip, String pw, String user, String port, String db, String table) {
		String url = "jdbc:mysql://"+ip+":"+port+"/"+db;
		Statement st = null;
		ResultSet rs = null;
		Hashtable<String, Networks> strongPoints = new Hashtable<>();
		try {
			_con = DriverManager.getConnection(url, user, pw);
			st = _con.createStatement();
			rs = st.executeQuery("SELECT VERSION()");
			if (rs.next()) {
				//rs.getString(1);
				System.out.println(rs.getString(1));
				PreparedStatement pst = _con.prepareStatement("SELECT * FROM "+table);
				rs = pst.executeQuery();
				Networks network = new Networks();
				while (rs.next()) {
					int size = rs.getInt(7);
					int len = 7+2*size;
					String time = rs.getString(2);
					double lat = rs.getDouble(4);
					double lon = rs.getDouble(5);
					double alt = rs.getDouble(6);
					String id = rs.getString(3);
					network = new Networks(id, time, lat, lon, alt);
					for(int i=8;i<=len;i++){
						String mac = rs.getString(i);
						String ssid = "";
						int channel = 0;
						double signal = rs.getDouble(i+1);
						network.add(ssid, mac, (int)signal, channel);
						i++;
					}
					strongPoints.put(time+id, network);
				}
			}
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(ImportSQL.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		} finally {
			try {
				if (rs != null) {rs.close();}
				if (st != null) { st.close(); }
				if (_con != null) { _con.close();  }
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(ImportSQL.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
		return strongPoints;
	}
}