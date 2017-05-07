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

	private Connection conn = null;
  private String url;
  private Statement stmt = null;
  private ResultSet rs = null;
  private String databaseUserName = "studentservice";
  private String databasePassword = "ssapipass";
  static private final String CONNECTIONS_XML =
          "webroot/WEB-INF/classes/student/databaseconnection/availibleConnections.xml";
  private static final String DB_CONN_STR = "jdbc:postgresql:";
  private static final String DRIVER = "org.postgresql.Driver";

	static {
    try {
			Properties connectionProperties = new Properties();
			connectionProperties.loadFromXML(new FileInputStream(CONNECTIONS_XML));
			String urlFromFile = connectionProperties.getProperty("pgsql").split("::")[1];
			DatabaseConnectionFactory.registerConnection("pgsql", new PGSQLConnection(urlFromFile));
		} catch (FileNotFoundException e) {
			Debug.stderr("PGSQLConnection: availibleConnections.xml settings file missing." + e);
		} catch (IOException ie) {
			Debug.stderr(ie.getMessage());
		}
    try {
      Class.forName(DRIVER);
    } catch (ClassNotFoundException cnfe) {
      Debug.stderr("Could not load driver: " + cnfe.getMessage());
    }
	}

	public PGSQLConnection(String database, String host, String port) {
		url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
		getConnected(url);
	}

	public PGSQLConnection(String url) {
		getConnected(url);
	}

  private void getConnected(String db) {
		try {
      Debug.stdout("CONNECTING TO: " + DB_CONN_STR + db);
	    conn = DriverManager.getConnection(DB_CONN_STR + db, databaseUserName, databasePassword);
		} catch (Exception e) {
			Debug.stderr("ConnectionError " + e.getMessage());
		}
	}

  /** Executes a query that should return an answer, answer is returned as a ResultSet
  *
  * @param query a SELECT SQL query.
  */
	public ResultSet runSelectQuery(String query) throws DBConnectionException {
    rs = null;
    try {
	    stmt = conn.createStatement();
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
  public void runNonSelectQuery(String query)throws DBConnectionException {
	  try {
	    stmt = conn.createStatement();
	    stmt.executeUpdate(query);
	  } catch (Exception e) {
	    throw new DBConnectionException(e.getMessage());
	  }
	}


}
