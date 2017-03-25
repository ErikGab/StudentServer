package student.storage;

import student.databaseconnection.DatabaseConnection;
import student.databaseconnection.DatabaseConnectionFactory;
import student.databaseconnection.DBConnectionException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import student.main.Student;
import student.main.Course;
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
      query = "SELECT * FROM vwGetAllStudents";
    } else {
      query = "SELECT * FROM vwGetAllStudents WHERE fldStudentId = "+id;
    }
    try {
      ResultSet rsStudent = connection.runSelectQuery(query);
      while (rsStudent.next()){
          int currentStudentID = rsStudent.getInt("fldStudentId");
          System.out.println("SQLStorage: ping!"+currentStudentID);
          ResultSet rsPhone = connection.runSelectQuery("SELECT * FROM tblStudentPhone WHERE fldStudentId = "+currentStudentID);
          Map<String, String> phonenumbers = new HashMap<>();
          while (rsPhone.next()){
              System.out.println("SQLStorage: ping!");
            phonenumbers.put(rsPhone.getString("fldType"), rsPhone.getString("fldNumber"));
          }

          returningList.add(new Student(currentStudentID,
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


  public List<Formatable> getCourse(int id){

    List<Formatable> returningList = new ArrayList<>();
    String query;

    if (id == 0) {
      query = "SELECT * FROM vwGetAllCourses";
    } else {
      query = "SELECT * FROM vwGetAllCourses WHERE fldCourseId = "+id;
    }
    try {
      ResultSet rsCourse = connection.runSelectQuery(query);
      while (rsCourse.next()){
          int currentCourseID = rsCourse.getInt("fldCourseId");
          System.out.println("SQLStorage: ping!"+currentCourseID);
          returningList.add(new Course(currentCourseID,
                                        rsCourse.getString("fldStartDate"),
                                        rsCourse.getString("fldEndDate"),
                                        rsCourse.getString("nick"),
                                        rsCourse.getString("fldPoints"),
                                        rsCourse.getString("fldDescription"),
                                        new HashMap<>()));
      }
    } catch (DBConnectionException dbe) {
  		System.err.println("DATABASE CONNECTION ERROR: " + dbe.getMessage());
  	} catch (SQLException sqle) {
  		System.err.println("SQL ERROR: " + sqle.getMessage());
  	}
    return returningList;
  }


}
