package student.storage;

import student.databaseconnection.DatabaseConnection;
import student.databaseconnection.DatabaseConnectionFactory;
import student.databaseconnection.DBConnectionException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import student.main.Student;
import student.format.Formatable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLStorage implements StudentStorage {

  private static DatabaseConnection connection;
  static {
    try {
      connection = DatabaseConnectionFactory.getDatabaseConnection();
    } catch (DBConnectionException dbce){
      System.err.println(dbce.getMessage());
    }
  }

  public List<Formatable> getStudent(int id){

    List<Formatable> returningList = new ArrayList<>();
    String query;

    if (id == 0) {
      query = "SELECT * FROM vwAllStudents";
    } else {
      query = "SELECT * FROM vwAllStudents WHERE fldStudentId = "+id;
    }
    try {
      ResultSet rsStudent = connection.runSelectQuery(query);
      while (rsStudent.next()){
          ResultSet rsPhone = connection.runSelectQuery("SELECT * FROM tblStudentPhone WHERE fldStudentId = "+id);
          Map<String, String> phonenumbers = new HashMap<>();
          while (rsPhone.next()){
            phonenumbers.put(rsPhone.getString("fldType"), rsPhone.getString("fldNumber"));
          }

          returningList.add(new Student(rsStudent.getInt("fldStudentId"),
                                        rsStudent.getString("fldName"),
                                        rsStudent.getString("fldSurName"),
                                        rsStudent.getString("age"),
                                        rsStudent.getString("fldPostAddress"),
                                        rsStudent.getString("fldStreetAdress"),
                                        phonenumbers));
      }
    } catch (DBConnectionException dbe) {
  		System.err.println("DATABASE CONNECTION ERROR: " + dbe.getMessage());
  	} catch (SQLException sqle) {
  		System.err.println("SQL ERROR: " + sqle.getMessage());
  	}
    return returningList;
  }
  public void removeStudent(int id){

  }
  public int addStudent(String name, String surName, String dateOfBirth){
    return 1;
  }

}
