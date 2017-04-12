package student.main;
import student.storage.StudentStorage;
import student.storage.StorageFactory;
import student.storage.StudentStorageException;
import student.format.FormatService;
import student.format.Formatable;
import student.format.FormatException;
import javax.servlet.*;
import javax.servlet.http.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.sql.*;
import java.io.*;
import java.util.*;

public class StudentService {

    private static StudentStorage storage;
    private static StudentService instance;

    static {
        instance = new StudentService();
        try {
            //2DO remove hardcoding of SQL. make -D input set default storagetype
            storage = StorageFactory.getStorage("SQL");
        } catch (StudentStorageException sse){
            System.err.println(sse.getMessage());
        }
    }

    public static StudentService getInstance(){
        return instance;
    }

    private StudentService(){};

    public String getFullStudentInfo(HttpServletRequest request, HttpServletResponse response){
        try {
            List<Formatable> students;
            students = storage.getStudent(parseId(request.getParameter("id")));
            return formatResponse(students, request.getParameter("format"), response);
        } catch (StudentStorageException sse){
            System.err.println(sse.getMessage());
            return getErrorMessage(666);
        }
    }

    public String getStudentsByCourse(HttpServletRequest request, HttpServletResponse response){
        try {
            List<Formatable> students;
            students = storage.getStudentsByCourse(parseId(request.getParameter("id")));
            return formatResponse(students, request.getParameter("format"), response);
        } catch (StudentStorageException sse){
            System.err.println(sse.getMessage());
            return getErrorMessage(666);
        }
    }

    public String getFullCourseInfo(HttpServletRequest request, HttpServletResponse response){
        try {
            List<Formatable> courses;
            courses = storage.getCourse(parseId(request.getParameter("id")));
            return formatResponse(courses, request.getParameter("format"), response);
        } catch (StudentStorageException sse){
            System.err.println(sse.getMessage());
            return getErrorMessage(666);
        }
    }

    public String getCoursesByYear(HttpServletRequest request, HttpServletResponse response){
        try {
            List<Formatable> courses;
            courses = storage.getCoursesByYear(parseId(request.getParameter("id")));
            return formatResponse(courses, request.getParameter("format"), response);
        } catch (StudentStorageException sse){
            System.err.println(sse.getMessage());
            return getErrorMessage(666);
        }
    }

    //
    //   2DO Fixa det här med ERROR meddelanden och Headers
    //
    //
    //
    //obvious placeholder is obvious
    public String getErrorMessage(int code){
        return getErrorMessage(String.valueOf(code));
    }
    public String getErrorMessage(String code){
        String message;
        try {
            Properties errorXML = new Properties();
            errorXML.loadFromXML(new FileInputStream("webroot/WEB-INF/classes/student/main/responseMessages.xml"));
            message = errorXML.getProperty(code) + "\n";
        } catch (IOException ioe){
            System.err.println("Failed to load responseMessages");
            message = "error: "+code;
        }
        return message;
    }

    private String formatResponse(List<Formatable> list, String format, HttpServletResponse response){
        String responseData;
        try {
            responseData = FormatService.formatList(list, format);
            response.setContentType(FormatService.getContentType(format));
            response.setStatus(response.SC_OK);
            response.setCharacterEncoding("UTF-8");
        } catch (FormatException fe){
            System.err.println(fe.getMessage());
            responseData = getErrorMessage(666);
        }
        return responseData;
    }

    private int parseId(String stringId){
        int intId;
        if (stringId == null || stringId.equals("all")){
            intId = 0;
        } else {
            try {
                intId = Integer.parseInt(stringId);
            } catch (NumberFormatException nfe){
                intId = 0;
            }
        }
        return intId;
    }

    //2DO this is never called. Should an APImethod be added to reach this?
    private void changeStorage(String type){
        try {
            storage = StorageFactory.getStorage(type);
        } catch (StudentStorageException sse){
            System.err.println(sse.getMessage());
        }
    }


}
