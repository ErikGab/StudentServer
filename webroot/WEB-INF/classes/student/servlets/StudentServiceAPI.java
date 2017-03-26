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

    private StudentService ss;

	@Override
	public void init(){
		try {
			Class.forName("student.main.StudentService");
            ss = StudentService.getInstance();
		} catch (ClassNotFoundException e){
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter responseWriter = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), java.nio.charset.StandardCharsets.UTF_8), true);
		StringBuilder responseBuilder = new StringBuilder();
        String requestFormat = request.getParameter("format");  //xml/json/csv/html/etc
        String requestType = request.getParameter("type");      //students/courses/phonenumbers
        String requestBy = request.getParameter("by");
        String requestId = request.getParameter("id");          //studentId/courseId

        if (requestType == null) {
            responseBuilder.append(getErrorMessage("400"))
                            .append("'type'");
        } else {
            switch (requestType) {
                case "student":
                    responseBuilder.append(studentRequest(requestBy, requestId, requestFormat));
                    break;
                case "course":
                    responseBuilder.append(courseRequest(requestBy, requestId, requestFormat));
                    break;
                default:
                    responseBuilder.append(getErrorMessage("401"))
                                    .append("type="+requestType);
                    break;
            }
        }
        responseWriter.println(responseBuilder);
		responseWriter.close();
	}

    private String studentRequest(String by, String id, String format){
        String retrievdInfo;
        if ( by == null ) {
            if ( id == null ) {
                retrievdInfo = getErrorMessage("400")+"id";
            } else {
                retrievdInfo = ss.getFullStudentInfo(parseId(id), format);
            }
        } else if ( by.equals("course") ) {
            if ( id == null ) {
                retrievdInfo = getErrorMessage("400")+"id";
            } else {
                retrievdInfo = ss.getStudentsByCourse(parseId(id), format);
            }
        } else  {
            retrievdInfo = getErrorMessage("401")+"by="+by;
        }
        return retrievdInfo;
    }

    private String courseRequest(String by, String id, String format){
        String retrievdInfo;
        if ( by == null ) {
            if ( id == null ) {
                retrievdInfo = getErrorMessage("400")+"id";
            } else {
                retrievdInfo = ss.getFullCourseInfo(parseId(id), format);
            }
        } else if ( by.equals("year") ) {
            if ( id == null ) {
                retrievdInfo = getErrorMessage("400")+"id";
            } else {
                retrievdInfo = ss.getCoursesByYear(parseId(id), format);
            }
        } else  {
            retrievdInfo = getErrorMessage("401")+"by="+by;
        }
        return retrievdInfo;
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
