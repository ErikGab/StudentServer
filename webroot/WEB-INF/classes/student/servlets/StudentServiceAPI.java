package student.servlets;
import student.main.StudentService;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.io.IOException;
import java.sql.*;
import java.io.*;
import java.util.*;


public class StudentServiceAPI extends HttpServlet{

    private static StudentService ss;
    private static Map<String,APIRequest> apiMethods;


	@Override
	public void init(){
		try {
			Class.forName("student.main.StudentService");
            ss = StudentService.getInstance();
            APIRequest getFullStudentInfo =  (HttpServletRequest request, HttpServletResponse response) -> ss.getFullStudentInfo(request, response);
            APIRequest getStudentsByCourse = (HttpServletRequest request, HttpServletResponse response) -> ss.getStudentsByCourse(request, response);
            APIRequest getFullCourseInfo =   (HttpServletRequest request, HttpServletResponse response) -> ss.getFullCourseInfo(request, response);
            APIRequest getCoursesByYear =    (HttpServletRequest request, HttpServletResponse response) -> ss.getCoursesByYear(request, response);
            apiMethods = new HashMap<String, APIRequest>();
            apiMethods.put("student-null",   getFullStudentInfo);
            apiMethods.put("student-course", getStudentsByCourse);
            apiMethods.put("course-null",    getFullCourseInfo);
            apiMethods.put("course-year",    getCoursesByYear);
		} catch (ClassNotFoundException e){
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
		PrintWriter responseWriter = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), java.nio.charset.StandardCharsets.UTF_8), true);
        String apiKey = String.valueOf(request.getParameter("type"))+"-"+String.valueOf(request.getParameter("by"));
        System.out.println("apiKey = "+apiKey);

        if (apiMethods.keySet().contains(apiKey)){
            responseWriter.println(apiMethods.get(apiKey).respondToRequest(request, response));
        } else {
            responseWriter.println(getErrorMessage("400"));
        }
		responseWriter.close();
	}

    private String getErrorMessage(String code){
        String message;
        try {
            Properties errorXML = new Properties();
            errorXML.loadFromXML(new FileInputStream("webroot/WEB-INF/classes/student/main/responseMessages.xml"));
            message = errorXML.getProperty(code) + "\n";
        } catch (IOException ioe){
            System.err.println("Failed to load responseMessages");
            message = "error";
        }
        return message;
    }

    private void responsePacking(){

    }


}
