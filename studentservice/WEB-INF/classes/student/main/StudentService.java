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
import student.main.Debug;

public class StudentService {

  private static StudentStorage storage;
  private static StudentService instance;
  private static final String DEFAULTSTORAGE = "SQL";

  static {
    instance = new StudentService();
    try {
      storage = StorageFactory.getStorage(initalStorage());
    } catch (StudentStorageException sse) {
      Debug.stderr(sse.getMessage());
    }
  }

  public static StudentService getInstance() {
    return instance;
  }

  private StudentService() {};

  /** Takes a HTTP request (for Detailed Student Information)...
  *   ...retrieves requested data and formats it to requested format.
  */
  public String getFullStudentInfo(HttpServletRequest request, HttpServletResponse response) {
    String format = request.getParameter("format");
    try {
      List<Formatable> students;
      students = storage.getStudent(parseId(request.getParameter("id")));
      return formatResponse(students, format, response);
    } catch (StudentStorageException sse) {
      Debug.stderr(sse.getMessage());
      return formatError(response.SC_INTERNAL_SERVER_ERROR, format, response);
    }
  }

  /** Takes a HTTP request (for Student Information)...
  *   ...retrieves requested data and formats it to requested format.
  */
  public String getStudentsByCourse(HttpServletRequest request, HttpServletResponse response){
    String format = request.getParameter("format");
    try {
      List<Formatable> students;
      students = storage.getStudentsByCourse(parseId(request.getParameter("id")));
      return formatResponse(students, format, response);
    } catch (StudentStorageException sse) {
      Debug.stderr(sse.getMessage());
      return formatError(response.SC_INTERNAL_SERVER_ERROR, format, response);
    }
  }

  /** Takes a HTTP request (for Detailed Course Information)...
  *   ...retrieves requested data and formats it to requested format.
  */
  public String getFullCourseInfo(HttpServletRequest request, HttpServletResponse response) {
    String format = request.getParameter("format");
    try {
      List<Formatable> courses;
      courses = storage.getCourse(parseId(request.getParameter("id")));
      return formatResponse(courses, format, response);
    } catch (StudentStorageException sse) {
      Debug.stderr(sse.getMessage());
      return formatError(response.SC_INTERNAL_SERVER_ERROR, format, response);
    }
  }

  /** Takes a HTTP request (for Course Information)...
  *   ...retrieves requested data and formats it to requested format.
  */
  public String getCoursesByYear(HttpServletRequest request, HttpServletResponse response) {
    String format = request.getParameter("format");
    try {
      List<Formatable> courses;
      courses = storage.getCoursesByYear(parseId(request.getParameter("id")));
      return formatResponse(courses, format, response);
    } catch (StudentStorageException sse) {
      Debug.stderr(sse.getMessage());
      return formatError(response.SC_INTERNAL_SERVER_ERROR, format, response);
    }
  }

  /** Takes a HTTP request to add a course...
  *   ...and persists requested data.
  */
  public String addCourse(HttpServletRequest request, HttpServletResponse response) {
    try {
      String startDate = request.getParameter("startdate");
      String endDate = request.getParameter("enddate");
      int subjectID = Integer.parseInt(request.getParameter("subject"));
      storage.addCourse(startDate, endDate, subjectID);
      return FormatService.formatMessage("COURSE ADDED", "html");
    } catch (StudentStorageException sse) {
      Debug.stderr(sse.getMessage());
      return formatError(response.SC_INTERNAL_SERVER_ERROR, "html", response);
    } catch (NumberFormatException nfe) {
      return formatError(response.SC_BAD_REQUEST, "html", response);
    } catch (FormatException fe) {
      Debug.stderr(fe.getMessage());
      response.setStatus(response.SC_UNSUPPORTED_MEDIA_TYPE);
      return ResponseUtil.getErrorMessage(response.SC_UNSUPPORTED_MEDIA_TYPE);
    }
  }

  /** Takes a HTTP request to add a student...
  *   ...and persists requested data.
  */
  public String addStudent(HttpServletRequest request, HttpServletResponse response) {
    try {
      String name = request.getParameter("name");
      String surname = request.getParameter("surname");
      String streetAddress = request.getParameter("streetaddress");
      String postAddress = request.getParameter("postaddress");
      String dateOfBirth = request.getParameter("dateofbirth");
      storage.addStudent(name, surname, streetAddress, postAddress, dateOfBirth);
      return FormatService.formatMessage("STUDENT ADDED", "html");
    } catch (StudentStorageException sse) {
      Debug.stderr(sse.getMessage());
      return formatError(response.SC_INTERNAL_SERVER_ERROR, "html", response);
    } catch (FormatException fe) {
      Debug.stderr(fe.getMessage());
      response.setStatus(response.SC_UNSUPPORTED_MEDIA_TYPE);
      return ResponseUtil.getErrorMessage(response.SC_UNSUPPORTED_MEDIA_TYPE);
    }
  }

  /** Tries to format error message
  *
  */
  public String formatError(int code, String format, HttpServletResponse response) {
    response.setStatus(code);
    response.setContentType(FormatService.getContentType(format));
    response.setCharacterEncoding("UTF-8");
    try {
      return FormatService.formatMessage(ResponseUtil.getErrorMessage(code), format);
    } catch (FormatException fe) {
      Debug.stderr(fe.getMessage());
      response.setStatus(response.SC_UNSUPPORTED_MEDIA_TYPE);
      return ResponseUtil.getErrorMessage(response.SC_UNSUPPORTED_MEDIA_TYPE);
    }
  }

  // if list has content, formats that content, if not formats error message insted.
  private String formatResponse(List<Formatable> list, String format, HttpServletResponse response) {
    String responseData;
    try {
      if (list.size() > 0) {
        responseData = FormatService.formatList(list, format);
        response.setContentType(FormatService.getContentType(format));
        response.setStatus(response.SC_OK);
      } else {
        responseData = formatError(response.SC_NOT_FOUND, format, response);
      }
      response.setCharacterEncoding("UTF-8");
    } catch (FormatException fe) {
      Debug.stderr(fe.getMessage());
      response.setStatus(response.SC_UNSUPPORTED_MEDIA_TYPE);
      response.setContentType(FormatService.getContentType(format));
      return ResponseUtil.getErrorMessage(response.SC_UNSUPPORTED_MEDIA_TYPE);
    }
    return responseData;
  }

  // parse string to int
  private int parseId(String stringId) {
    int intId;
    if (stringId == null || stringId.equals("all")) {
      intId = 0;
    } else {
      try {
        intId = Integer.parseInt(stringId);
      } catch (NumberFormatException nfe) {
        intId = -1;
      }
    }
    return intId;
  }

  //2DO this is never called. Should an APImethod be added to reach this? Add a spesific Admin-API?
  //...or should this be remvoved?
  private void changeStorage(String type) {
    try {
      storage = StorageFactory.getStorage(type);
    } catch (StudentStorageException sse) {
      Debug.stderr(sse.getMessage());
    }
  }

  //checks if  specific storagetype is requested, if not default is used.
  private static String initalStorage() {
    String storage = System.getProperty("storage");
    if (storage == null) {
      return DEFAULTSTORAGE;
    } else {
      return storage;
    }
  }

}
