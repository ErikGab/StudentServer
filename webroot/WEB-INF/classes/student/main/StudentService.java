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

  static {
    instance = new StudentService();
    try {
      //2DO remove hardcoding of SQL. make -D input set storagetype
      storage = StorageFactory.getStorage("SQL");
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
      response.setStatus(response.SC_NOT_IMPLEMENTED);
      return ResponseUtil.getErrorMessage(response.SC_NOT_IMPLEMENTED);
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
      response.setStatus(response.SC_NOT_IMPLEMENTED);
      response.setContentType(FormatService.getContentType(format));
      return ResponseUtil.getErrorMessage(response.SC_NOT_IMPLEMENTED);
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

  //2DO this is never called. Should an APImethod be added to reach this?
  //...or should this be remvoved?
  private void changeStorage(String type) {
    try {
      storage = StorageFactory.getStorage(type);
    } catch (StudentStorageException sse) {
      Debug.stderr(sse.getMessage());
    }
  }

}
