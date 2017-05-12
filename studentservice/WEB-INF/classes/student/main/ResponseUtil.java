package student.main;
import java.io.*;
import java.util.*;

public class ResponseUtil {

  private ResponseUtil() {}

  /** Returns a string containing errormessage for requested error code
  *
  * @param code int ie 404
  */
  public static String getErrorMessage(int code) {
    return getErrorMessage(String.valueOf(code));
  }

  /** Returns a string containing errormessage for requested error code
  *
  * @param code string ie "404"
  */
  public static String getErrorMessage(String code) {
    String message;
    try {
      Properties errorXML = new Properties();
      errorXML.loadFromXML(new FileInputStream(
              "studentservice/WEB-INF/classes/student/main/responseMessages.xml"));
      message = errorXML.getProperty(code) + "\n";
    } catch (IOException ioe) {
      Debug.stderr("Failed to load responseMessages");
      message = "error: " + code;
    }
    return message;
  }

}
