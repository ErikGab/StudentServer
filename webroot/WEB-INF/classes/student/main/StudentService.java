package student.main;
import student.storage.StudentStorage;
import student.storage.StorageFactory;
import student.storage.StudentStorageException;
import student.format.FormatService;
import student.format.Formatable;
import student.format.FormatException;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.io.IOException;
import java.sql.*;

public class StudentService {

    private static StudentStorage storage;
    private static StudentService instance;

    static {
        instance = new StudentService();
        try {
            storage = StorageFactory.getStorage();
        } catch (StudentStorageException sse){
            System.err.println(sse.getMessage());
        }
    }

    public static StudentService getInstance(){
        return instance;
    }

    private StudentService(){};

    public String getFullStudentInfo(int id, String format){
        String response;
        List<Formatable> students;
        try {
            students = storage.getStudent(id);
            response = FormatService.formatList(students, format);
        } catch (StudentStorageException sse){
            System.err.println(sse.getMessage());
            response = getErrorMessage(666);
        } catch (FormatException fe){
            System.err.println(fe.getMessage());
            response = getErrorMessage(666);
        }
        return response;
    }

    public String getStudentsByCourse(int id, String format){
        String response;
        List<Formatable> students;
        try {
            students = storage.getStudentsByCourse(id);
            response = FormatService.formatList(students, format);
        } catch (StudentStorageException sse){
            System.err.println(sse.getMessage());
            response = getErrorMessage(666);
        } catch (FormatException fe){
            System.err.println(fe.getMessage());
            response = getErrorMessage(666);
        }
        return response;
    }

    public String getFullCourseInfo(int id, String format){
        String response;
        List<Formatable> courses;
        try {
            courses = storage.getCourse(id);
            response = FormatService.formatList(courses, format);
        } catch (StudentStorageException sse){
            System.err.println(sse.getMessage());
            response = getErrorMessage(666);
        } catch (FormatException fe){
            System.err.println(fe.getMessage());
            response = getErrorMessage(666);
        }
        return response;
    }

    public String getCoursesByYear(int id, String format){
        String response;
        List<Formatable> courses;
        try {
            courses = storage.getCoursesByYear(id);
            response = FormatService.formatList(courses, format);
        } catch (StudentStorageException sse){
            System.err.println(sse.getMessage());
            response = getErrorMessage(666);
        } catch (FormatException fe){
            System.err.println(fe.getMessage());
            response = getErrorMessage(666);
        }
        return response;
    }


    //obvious placeholder is obvious
    private String getErrorMessage(int code){
        return "ERROR 123: Något är knas";
    }

}
