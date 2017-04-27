package student.databaseconnection;

import java.io.*;
import java.util.*;
import student.main.Debug;

public class DatabaseConnectionFactory {

	static private final String CONNECTIONS_XML =
          "webroot/WEB-INF/classes/student/databaseconnection/availibleConnections.xml";
	static private final String DEFAULTDRIVER = "sqlite";
	static private String driver;
	static private Map<String, DatabaseConnection> connections = new HashMap<>();
	static {
		try {
			Properties connectionProperties = new Properties();
	    connectionProperties.loadFromXML(new FileInputStream(CONNECTIONS_XML));
			for(String connection : connectionProperties.stringPropertyNames()){
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
		driver = System.getProperty("driver");
	}

	public static void registerDriver(String drivername, DatabaseConnection connection) {
		Debug.stdout("New DB connection registerd: " + drivername);
		connections.put(drivername, connection);
	}

	private DatabaseConnectionFactory() {};

	public static DatabaseConnection getDatabaseConnection() throws DBConnectionException {
		if (connections.containsKey(driver)) {
			Debug.stdout("Returning requested driver: " + driver);
      return connections.get(driver);
		} else if (connections.containsKey(DEFAULTDRIVER)) {
			Debug.stderr(driver + " driver not found.\nReturning default driver: " + DEFAULTDRIVER);
      return connections.get(DEFAULTDRIVER);
    } else {
			Debug.stderr("No suitable driver found: " + driver);
			throw new DBConnectionException("No " + driver + " support availible.");
		}
	}

}
