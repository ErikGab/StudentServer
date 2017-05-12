package student.servlets;
import student.main.StudentService;
import student.main.ResponseUtil;
import student.main.Debug;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import static java.nio.charset.StandardCharsets.UTF_8;

public class StudentServiceADMIN extends HttpServlet {

    private static StudentService service;
    private static Map<String,APIRequest> apiMethods;

	@Override
	public void init() {
		try {
			Class.forName("student.main.StudentService");
      service = StudentService.getInstance();
      APIRequest addStudent =  (HttpServletRequest request, HttpServletResponse response) ->
              service.addStudent(request, response);

      APIRequest addCourse = (HttpServletRequest request, HttpServletResponse response) ->
              service.addCourse(request, response);

      apiMethods = new HashMap<String, APIRequest>();
      apiMethods.put("add-student",   addStudent);
      apiMethods.put("add-course", addCourse);
		} catch (ClassNotFoundException e) {
			Debug.stderr(e.getMessage());
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
    try {
      PrintWriter responseWriter =
              new PrintWriter(new OutputStreamWriter(response.getOutputStream(),
                      java.nio.charset.StandardCharsets.UTF_8), true);
      String apiKey = String.valueOf(request.getParameter("submit"));
      Debug.stdout("ADMIN Request with apiKey = " + apiKey);
      if (apiMethods.keySet().contains(apiKey)) {
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
    }
	}

}
