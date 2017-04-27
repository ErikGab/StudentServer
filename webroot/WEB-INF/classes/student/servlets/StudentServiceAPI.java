package student.servlets;
import student.main.StudentService;
import student.main.ResponseUtil;
import student.main.Debug;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import static java.nio.charset.StandardCharsets.UTF_8;

public class StudentServiceAPI extends HttpServlet {

    private static StudentService service;
    private static Map<String,APIRequest> apiMethods;

	@Override
	public void init() {
		try {
			Class.forName("student.main.StudentService");
      service = StudentService.getInstance();
      APIRequest getFullStudentInfo =  (HttpServletRequest request, HttpServletResponse response) ->
              service.getFullStudentInfo(request, response);

      APIRequest getStudentsByCourse = (HttpServletRequest request, HttpServletResponse response) ->
              service.getStudentsByCourse(request, response);

      APIRequest getFullCourseInfo =   (HttpServletRequest request, HttpServletResponse response) ->
              service.getFullCourseInfo(request, response);

      APIRequest getCoursesByYear =    (HttpServletRequest request, HttpServletResponse response) ->
              service.getCoursesByYear(request, response);

      apiMethods = new HashMap<String, APIRequest>();
      apiMethods.put("student-null",   getFullStudentInfo);
      apiMethods.put("student-course", getStudentsByCourse);
      apiMethods.put("course-null",    getFullCourseInfo);
      apiMethods.put("course-year",    getCoursesByYear);
		} catch (ClassNotFoundException e) {
			Debug.stderr(e.getMessage());
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
    try {
      PrintWriter responseWriter =
              new PrintWriter(new OutputStreamWriter(response.getOutputStream(),
                      java.nio.charset.StandardCharsets.UTF_8), true);
      String apiKey = String.valueOf(request.getParameter("type")) + "-" +
              String.valueOf(request.getParameter("by"));
      Debug.stdout("Request with apiKey = " + apiKey);
      if (apiMethods.keySet().contains(apiKey) && request.getParameter("format") != null ) {
        responseWriter.println(apiMethods.get(apiKey).respondToRequest(request, response));
      } else {
        responseWriter.println(service.formatError(response.SC_BAD_REQUEST,
                request.getParameter("format"), response));
      }
      responseWriter.close();
    } catch (IOException ioe) {
      Debug.stderr("IOException: " + ioe);
      //2DO How can a message be sent to client if an IOException is caught?
      //2DO doGet previously throwed ServletException, why? seems ok without it?
      //response.sendError(response.SC_INTERNAL_SERVER_ERROR);
    }
	}

}
