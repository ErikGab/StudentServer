package student.databaseconnection;

import java.io.*;
import java.util.*;

public class DatabaseConnectionFactory {

	static private final String CONNECTIONS_XML = "webroot/WEB-INF/classes/student/databaseconnection/availibleConnections.xml";
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
					//System.out.println("DBCFactory: Trying to load :" + className);
					System.out.println(connection + " connection found, loading class: " + className);
		 			try{
		 				Class.forName(className);
		 			}catch(ClassNotFoundException cnfe){
						System.err.println(cnfe.getMessage());
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("DBCFactory: availibleConnections.xml settings file missing." + e);
		} catch (IOException ie) {
			System.err.println(ie);
		}
		driver = System.getProperty("driver");
	}

	public static void registerDriver(String drivername, DatabaseConnection connection){
		//System.out.println("Factory: Registering : " + drivername);
		System.out.println("New connection registerd: " + drivername);
		connections.put(drivername, connection);
	}

	private DatabaseConnectionFactory(){};

	public static DatabaseConnection getDatabaseConnection() throws DBConnectionException{
		if (connections.containsKey(driver)){
			System.out.println("Returning requested driver: " + driver);
      return connections.get(driver);
		} else if (connections.containsKey(DEFAULTDRIVER)){
			System.err.println(driver + " driver not found.\nReturning default driver: " + DEFAULTDRIVER);
      return connections.get(DEFAULTDRIVER);
    } else {
			System.err.println("No suitable driver found: " + driver);
			throw new DBConnectionException("No " + driver + " support availible.");
		}
	}
}
