package student.databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.*;
import java.util.*;
import student.main.Debug;

import javax.swing.JOptionPane;

public class SQLiteConnection implements DatabaseConnection {

	private Connection conn = null;
  private Statement stmt = null;
  private ResultSet rs = null;
  private static final String CONNECTIONS_XML =
          "webroot/WEB-INF/classes/student/databaseconnection/availibleConnections.xml";
  private static final String DB_CONN_STR = "jdbc:sqlite:";
  private static final String DRIVER = "org.sqlite.JDBC";

  static {
		try {
			Properties connectionProperties = new Properties();
			connectionProperties.loadFromXML(new FileInputStream(CONNECTIONS_XML));
			String urlFromFile = connectionProperties.getProperty("sqlite").split("::")[1];
			DatabaseConnectionFactory.registerConnection("sqlite", new SQLiteConnection(urlFromFile));
		} catch (FileNotFoundException e) {
			Debug.stderr("SQLiteConnection: availibleConnections.xml settings file missing." + e);
		} catch (IOException ie) {
			Debug.stderr(ie.getMessage());
		}
    try {
      Class.forName(DRIVER);
    } catch (ClassNotFoundException cnfe) {
      Debug.stderr("Could not load driver: " + cnfe.getMessage());
    }
	}

	public SQLiteConnection(String db) {
		getConnected(db);
	}

	private void getConnected(String db) {
		try {
	    conn = DriverManager.getConnection(DB_CONN_STR + db);
		} catch (Exception e) {
			Debug.stderr(e.getMessage());
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
