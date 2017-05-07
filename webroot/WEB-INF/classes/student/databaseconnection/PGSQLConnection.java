package student.databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.*;
import java.util.*;
import student.main.Debug;

import javax.swing.JOptionPane;

public class PGSQLConnection implements DatabaseConnection {

	//
	//	THIS IS A COPY OF MSSQLConnection.
	//
	//	2DO MAKE POSTGRES CONNECTION OF THIS TEMPLATE
	//

	private Connection conn = null;
  private String url;
  private Statement stmt = null;
  private ResultSet rs = null;
  private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
  private String databaseUserName = "JavaLogin";
  private String databasePassword = "Losen123";

	static {
		try {
			Properties connectionProperties = new Properties();
			connectionProperties.loadFromXML(
              new FileInputStream("src/main/java/app/dbconn/availibleConnections.xml"));
			String urlFromFile = connectionProperties.getProperty("mssql").split("::")[1];
			DatabaseConnectionFactory.registerConnection("pgsql", new PGSQLConnection(urlFromFile));
		} catch (FileNotFoundException e) {
			Debug.stderr("availibleConnections.xml settings file missing.");
		} catch (IOException ie) {
			Debug.stderr(ie.getMessage());
		}
	}

	public PGSQLConnection(String database, String host, String instance, String port) {
		url = "jdbc:sqlserver://" + host + "\\" + instance + ":" + port + ";databaseName=" + database;
		getConnected();
	}

	public PGSQLConnection(String url) {
		this.url = "jdbc:sqlserver:" + url;
		getConnected();
	}

	private void getConnected() {
		try{
			Class.forName(DRIVER).newInstance();
	    conn = DriverManager.getConnection(url, databaseUserName, databasePassword);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

  /** Executes a query that should return an answer, answer is returned as a ResultSet
  *
  * @param query a SELECT SQL query.
  */
	public ResultSet runSelectQuery(String query) throws DBConnectionException {
	    try {
	        stmt = conn.createStatement();
	        rs = null; //Behövs denna??
	        rs = stmt.executeQuery(query);
	    } catch (Exception e) {
	        throw new DBConnectionException(e.getMessage());
	    }
	    return rs;
	}

  /** Executes a query that should NOT return an answer.
  *
  * @param query a SQL query that should not return a result ie UPDATE, INSERT, DELETE
  */
  public void runNonSelectQuery(String query) throws DBConnectionException {
	    try {
	        stmt = conn.createStatement();
	        stmt.executeUpdate(query);
	    } catch (Exception e) {
	        throw new DBConnectionException(e.getMessage());
	    }
	}

}
