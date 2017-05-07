package student.databaseconnection;

import java.io.*;
import java.util.*;
import student.main.Debug;

public class DatabaseConnectionFactory {

	static private final String CONNECTIONS_XML =
          "webroot/WEB-INF/classes/student/databaseconnection/availibleConnections.xml";
	static private final String DEFAULTCONNECTION = "sqlite";
	static private Map<String, DatabaseConnection> connections = new HashMap<>();

  // Reads connections-xml and loads "active" connections into JVM
	static {
		try {
			Properties connectionProperties = new Properties();
	    connectionProperties.loadFromXML(new FileInputStream(CONNECTIONS_XML));
			for (String connection : connectionProperties.stringPropertyNames()) {
	 			String className = connectionProperties.getProperty(connection).split("::")[0];
				if (connectionProperties.getProperty(connection).split("::")[2].equals("true")) {
					Debug.stdout(connection + " connection found, loading class: " + className);
		 			try {
		 				Class.forName(className);
		 			} catch (ClassNotFoundException cnfe) {
						Debug.stderr(cnfe.getMessage());
					}
				}
			}
		} catch (FileNotFoundException e) {
			Debug.stderr("DBCFactory: availibleConnections.xml settings file missing." + e);
		} catch (IOException ie) {
			Debug.stderr(ie.getMessage());
		}
	}

  /** Loaded drivers use this method to register their existence to this Factory
  *
  * @param connectionName name of DatabaseConnection to register
  * @param connection a DatabaseConnection instance to register
  */
	public static void registerConnection(String connectionName, DatabaseConnection connection) {
		Debug.stdout("New DB connection registerd: " + connectionName);
		connections.put(connectionName, connection);
	}

	private DatabaseConnectionFactory() {};

  /** Returns requested (-D at startup) connection if found. If requested connection is not found
  * a defaultconnection is returned.
  */
	public static DatabaseConnection getDatabaseConnection() throws DBConnectionException {
    String connection = System.getProperty("connection");
		if (connections.containsKey(connection)) {
			Debug.stdout("Returning requested connection: " + connection);
      return connections.get(connection);
		} else if (connections.containsKey(DEFAULTCONNECTION)) {
			Debug.stderr(connection + " connection not found. Returning default connection: " +
              DEFAULTCONNECTION);
      return connections.get(DEFAULTCONNECTION);
    } else {
			Debug.stderr("No suitable connection found: " + connection);
			throw new DBConnectionException("No " + connection + " support availible.");
		}
	}

}
