package student.databaseconnection;

import java.sql.ResultSet;

public interface DatabaseConnection {

	public ResultSet runSelectQuery(String query) throws DBConnectionException;
	public void runNonSelectQuery(String query) throws DBConnectionException;

}
