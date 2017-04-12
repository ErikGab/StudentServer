package student.databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

public class MSSQLConnection implements DatabaseConnection {

	Connection conn = null;
    String url;
    Statement stmt = null;
    ResultSet rs = null;
    String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    String databaseUserName = "JavaLogin";
    String databasePassword = "Losen123";

	static {
		try {
			Properties connectionProperties = new Properties();
			connectionProperties.loadFromXML(new FileInputStream("src/main/java/app/dbconn/availibleConnections.xml"));
			String urlFromFile = connectionProperties.getProperty("mssql").split("::")[1];
			DatabaseConnectionFactory.registerDriver("mssql", new MSSQLConnection(urlFromFile));
		} catch (FileNotFoundException e) {
			System.err.println("availibleConnections.xml settings file missing.");
		} catch (IOException ie) {
			System.err.println(ie);
		}
	}

	public MSSQLConnection(String database, String host, String instance, String port){
		url = "jdbc:sqlserver://"+host+"\\"+instance+":"+port+";databaseName="+database+"";
		getConnected();
	}
	public MSSQLConnection(String url){
		this.url = "jdbc:sqlserver:"+url;
		getConnected();
	}

	private void getConnected(){
		try{
			Class.forName(driver).newInstance();
	        conn = DriverManager.getConnection(url, databaseUserName, databasePassword);
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
