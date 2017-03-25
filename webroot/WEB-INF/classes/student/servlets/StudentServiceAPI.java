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
        String requestId = request.getParameter("id");          //studentId/courseId

        System.out.println("RF: " + requestFormat + "   RID: " + requestId);

        if (requestId == null || requestId.equals("")) {
            responseBuilder.append(getErrorMessage("400"))
                            .append("'id'");
        } else if (requestFormat == null) {
            responseBuilder.append(getErrorMessage("400"))
                            .append("'format'");
        } else if (requestType == null) {
            responseBuilder.append(getErrorMessage("400"))
                            .append("'type'");
        } else {
            //responseBuilder.append(getErrorMessage("200"));
            //responseBuilder.append(ss.testMe());
            switch (requestType) {
                case "student":
                    responseBuilder.append(ss.getStudent(parseId(requestId), requestFormat));
                    break;
                case "course":
                    responseBuilder.append(ss.getCourse(parseId(requestId), requestFormat));
                    break;
                default:
                    responseBuilder.append(getErrorMessage("600"))
                                    .append(requestType);
                    break;
            }
        }

        responseWriter.println(responseBuilder);
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
