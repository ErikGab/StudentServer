package student.databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;


public class SQLiteConnection implements DatabaseConnection {

	Connection conn = null;
  	Statement stmt = null;
  	ResultSet rs = null;
  	static private final String CONNECTIONS_XML = "webroot/WEB-INF/classes/student/databaseconnection/availibleConnections.xml";
	static {
		try {
			Properties connectionProperties = new Properties();
			connectionProperties.loadFromXML(new FileInputStream(CONNECTIONS_XML));
			String urlFromFile = connectionProperties.getProperty("sqlite").split("::")[1];
			DatabaseConnectionFactory.registerDriver("sqlite", new SQLiteConnection(urlFromFile));
		} catch (FileNotFoundException e) {
			System.err.println("SQLiteConnection: availibleConnections.xml settings file missing." + e);
		} catch (IOException ie) {
			System.err.println(ie);
		}
	}

	private final static String DB_CONN_STR="jdbc:sqlite:";
  	static{
    	try{
      		Class.forName("org.sqlite.JDBC");
    	} catch (ClassNotFoundException cnfe){
      		System.err.println("Could not load driver: "+cnfe.getMessage());
    	}
  	}

	public SQLiteConnection(String db){
		getConnected(db);
	}

	private void getConnected(String db){
		try{
	        conn = DriverManager.getConnection(DB_CONN_STR+db);
		} catch (Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}


	public ResultSet runSelectQuery(String query)throws DBConnectionException {
	    try {
	        stmt = conn.createStatement();
	        rs = null;
	        rs = stmt.executeQuery(query);
	    } catch (Exception e) {
	        throw new DBConnectionException(e.getMessage());
	    }
	    return rs;
	}

public void runNonSelectQuery(String query)throws DBConnectionException {
	    try {
	        stmt = conn.createStatement();
	        stmt.executeUpdate(query);
	    } catch (Exception e) {
	        throw new DBConnectionException(e.getMessage());
	    }
	}

}
