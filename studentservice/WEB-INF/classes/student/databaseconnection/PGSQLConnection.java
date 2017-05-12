package student.databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.io.*;
import java.util.*;
import student.main.Debug;

public class PGSQLConnection implements DatabaseConnection {

	private static Connection conn = null;
  private static Statement stmt = null;
  private static ResultSet rs = null;
  private static String url = null;
  private static final String DB_USER_NAME = "studentservice";
  private static final String DB_PASSWORD = "ssapipass";
  private static final String CONNECTIONS_XML =
          "studentservice/WEB-INF/classes/student/databaseconnection/availibleConnections.xml";
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
	}

	public PGSQLConnection(String url) {
		this.url = url;
	}

  private void getConnected(String db_url) throws DBConnectionException {
		try {
      Debug.stdout("CONNECTING TO: " + DB_CONN_STR + db_url);
	    conn = DriverManager.getConnection(DB_CONN_STR + db_url, DB_USER_NAME, DB_PASSWORD);
		} catch (SQLException e) {
			Debug.stderr("ConnectionError " + e.getMessage());
      throw new DBConnectionException("Could not connect to database.");
		}
	}

  /** Executes a query that should return an answer, answer is returned as a ResultSet
  *
  * @param query a SELECT SQL query.
  */
	public ResultSet runSelectQuery(String query) throws DBConnectionException {
    connectIfneeded();
    rs = null;
    try {
	    stmt = conn.createStatement();
	    rs = stmt.executeQuery(query);
	  } catch (SQLException e) {
	    throw new DBConnectionException(e.getMessage());
	  }
	  return rs;
	}

  /** Executes a query that should NOT return an answer.
  *
  * @param query a SQL query that should not return a result ie UPDATE, INSERT, DELETE
  */
  public void runNonSelectQuery(String query) throws DBConnectionException {
    connectIfneeded();
	  try {
	    stmt = conn.createStatement();
	    stmt.executeUpdate(query);
	  } catch (SQLException e) {
	    throw new DBConnectionException(e.getMessage());
	  }
	}

  //Connection might get closed due to inactivity or other reasons...
  private void connectIfneeded() throws DBConnectionException {
    int retries = 0;
    while (true) {
      try {
        if (conn == null || !conn.isValid(2)) {
          getConnected(url);
        }
        break;
      } catch (SQLException sqle) {
        retries++;
        Debug.stderr("PGSQL Connection: Reconnecting...");
        if (retries > 2) {
          throw new DBConnectionException("Can't estabish a connection to DB.");
        }
      }
    }
  }


}
