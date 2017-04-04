package student.storage;

import student.databaseconnection.DatabaseConnection;
import student.databaseconnection.DatabaseConnectionFactory;
import student.databaseconnection.DBConnectionException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import student.format.StdItem;
import student.format.Formatable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLStorage implements StudentStorage {

    private SQLStorage(){};

    private static DatabaseConnection connection;
    static {
        try {
            connection = DatabaseConnectionFactory.getDatabaseConnection();
            StorageFactory.register("SQL", new SQLStorage());
        } catch (DBConnectionException dbce){
            System.err.println(dbce.getMessage());
        }
    }

    public List<Formatable> getStudent(int id){

        List<Formatable> returningList = new ArrayList<>();
        String query;

        if (id == 0) {  query = "SELECT * FROM vwGetStudent"; }
        else {          query = "SELECT * FROM vwGetStudent WHERE fldStudentId = "+id; }
        try {
            ResultSet rsStudent = connection.runSelectQuery(query);
            while (rsStudent.next()){
                int currentStudentID = rsStudent.getInt("fldStudentId");
                List<Formatable> subItems = new ArrayList<>();

                ResultSet rsPhone = connection.runSelectQuery("SELECT * FROM tblStudentPhone WHERE fldStudentId = "+currentStudentID);
                Map<String, String> phonenumbers = new LinkedHashMap<>();
                while (rsPhone.next()){
                    phonenumbers.put(rsPhone.getString("fldType"), rsPhone.getString("fldNumber"));
                }
                subItems.add(new StdItem("phonenumbers", 1, phonenumbers));

                ResultSet rsCourse = connection.runSelectQuery("SELECT * FROM vwGetCoursesForStudent WHERE fldStudentId = "+currentStudentID);
                while (rsCourse.next()){
                    Map<String, String> course = new LinkedHashMap<>();
                    course.put("id", rsCourse.getString("fldCourseId"));
                    course.put("name", rsCourse.getString("name"));
                    course.put("status", rsCourse.getString("fldStatus"));
                    course.put("grade", rsCourse.getString("fldGrade"));
                    subItems.add(new StdItem("course", rsCourse.getInt("fldCourseId"), course));
                }

                Map<String, String> properties = new LinkedHashMap<>();
                properties.put("id", rsStudent.getString("fldStudentId"));
                properties.put("name", rsStudent.getString("fldName"));
                properties.put("surname", rsStudent.getString("fldSurName"));
                properties.put("age", rsStudent.getString("age"));
                properties.put("postAddress", rsStudent.getString("fldPostAddress"));
                properties.put("streetAdress", rsStudent.getString("fldStreetAdress"));

                returningList.add(new StdItem("student", currentStudentID, properties, subItems));
            }
        } catch (DBConnectionException dbe) {
  		    System.err.println("DATABASE CONNECTION ERROR: " + dbe.getMessage());
  	    } catch (SQLException sqle) {
  		    System.err.println("SQL ERROR: " + sqle.getMessage());
  	    }
        return returningList;
    }

    public List<Formatable> getStudentsByCourse(int id){

        List<Formatable> returningList = new ArrayList<>();
        String query;

        if (id == 0) {  query = "SELECT * FROM vwGetStudentsByCourse"; }
        else {          query = "SELECT * FROM vwGetStudentsByCourse WHERE fldCourseId = '"+id+"'"; }
        try {
            ResultSet rsStudent = connection.runSelectQuery(query);
            while (rsStudent.next()){
                int currentStudentID = rsStudent.getInt("fldStudentId");
                Map<String, String> properties = new LinkedHashMap<>();
                properties.put("id", rsStudent.getString("fldStudentId"));
                properties.put("name", rsStudent.getString("fldName"));
                properties.put("surname", rsStudent.getString("fldSurName"));
                returningList.add(new StdItem("student", currentStudentID, properties));
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

        if (id == 0) {  query = "SELECT * FROM vwGetCourse"; }
        else {          query = "SELECT * FROM vwGetCourse WHERE fldCourseId = "+id; }
        try {
            ResultSet rsCourse = connection.runSelectQuery(query);
            while (rsCourse.next()){
                int currentCourseID = rsCourse.getInt("fldCourseId");
                List<Formatable> subItems = new ArrayList<>();

                ResultSet rsStudent = connection.runSelectQuery("SELECT * FROM vwGetStudentForCourse WHERE fldCourseId = "+currentCourseID);
                while (rsStudent.next()){
                    Map<String, String> studentProperties = new LinkedHashMap<>();
                    studentProperties.put("id", rsStudent.getString("fldStudentId"));
                    studentProperties.put("name", rsStudent.getString("fldName"));
                    studentProperties.put("surname", rsStudent.getString("fldSurName"));
                    studentProperties.put("status", rsStudent.getString("fldStatus"));
                    studentProperties.put("grade", rsStudent.getString("fldGrade"));
                    subItems.add(new StdItem("student", rsStudent.getInt("fldStudentId"), studentProperties));
                }

                Map<String, String> properties = new LinkedHashMap<>();
                properties.put("id", rsCourse.getString("fldCourseId"));
                properties.put("name", rsCourse.getString("name"));
                properties.put("description", rsCourse.getString("fldDescription"));
                properties.put("startDate", rsCourse.getString("fldStartDate"));
                properties.put("endDate", rsCourse.getString("fldEndDate"));
                properties.put("points", rsCourse.getString("fldPoints"));

                returningList.add(new StdItem("course", currentCourseID, properties, subItems));
            }
        } catch (DBConnectionException dbe) {
  		    System.err.println("DATABASE CONNECTION ERROR: " + dbe.getMessage());
  	    } catch (SQLException sqle) {
  		    System.err.println("SQL ERROR: " + sqle.getMessage());
  	    }
        return returningList;
    }

    public List<Formatable> getCoursesByYear(int id){

        List<Formatable> returningList = new ArrayList<>();
        String query;

        if (id == 0) {  query = "SELECT * FROM vwGetCoursesByYear"; }
        else {          query = "SELECT * FROM vwGetCoursesByYear WHERE year = '"+id+"'"; }
        try {
            ResultSet rsCourse = connection.runSelectQuery(query);
            while (rsCourse.next()){
                int currentCourseID = rsCourse.getInt("fldCourseId");
                Map<String, String> properties = new LinkedHashMap<>();
                properties.put("id", rsCourse.getString("fldCourseId"));
                properties.put("name", rsCourse.getString("name"));
                returningList.add(new StdItem("course", currentCourseID, properties));
            }
        } catch (DBConnectionException dbe) {
  		    System.err.println("DATABASE CONNECTION ERROR: " + dbe.getMessage());
  	    } catch (SQLException sqle) {
  		    System.err.println("SQL ERROR: " + sqle.getMessage());
  	    }
        return returningList;
    }


}
