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
            APIRequest getFullStudentInfo =  (HttpServletRequest r) -> ss.getFullStudentInfo(   parseId(r.getParameter("id"))  ,  r.getParameter("format")  );
            APIRequest getStudentsByCourse = (HttpServletRequest r) -> ss.getStudentsByCourse(  parseId(r.getParameter("id"))  ,  r.getParameter("format")  );
            APIRequest getFullCourseInfo = (HttpServletRequest r) -> ss.getFullCourseInfo(  parseId(r.getParameter("id"))  ,  r.getParameter("format")  );
            APIRequest getCoursesByYear =  (HttpServletRequest r) -> ss.getCoursesByYear(   parseId(r.getParameter("id"))  ,  r.getParameter("format")  );
            apiMethods = new HashMap<String, APIRequest>();
            apiMethods.put("student",       getFullStudentInfo);
            apiMethods.put("studentcourse", getStudentsByCourse);
            apiMethods.put("course",        getFullCourseInfo);
            apiMethods.put("courseyear",    getCoursesByYear);
		} catch (ClassNotFoundException e){
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        //response.setContentType("application/json");
        //response.setContentType("text/html");
        //response.setContentType("application/xml");
        //response.setContentType("text/xml");
		PrintWriter responseWriter = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), java.nio.charset.StandardCharsets.UTF_8), true);
		StringBuilder responseBuilder = new StringBuilder();
        String requestType = request.getParameter("type");      //students/courses/phonenumbers
        String requestBy = request.getParameter("by");

        if (requestType==null) { requestType = ""; }
        if (requestBy==null)   { requestBy = ""; }

        String apiKey = requestType+requestBy;
        System.out.println("apiKey = "+apiKey);

        if (apiMethods.keySet().contains(apiKey)){
            responseWriter.println(apiMethods.get(apiKey).respondToRequest(request));
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

    private int parseId(String stringId){
        int intId;
        if (stringId.equals("all")){
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

}
