package student.main;
import java.io.*;
import java.util.*;

public class ResponseUtil {

  public static String getErrorMessage(int code) {
    return getErrorMessage(String.valueOf(code));
  }

  public static String getErrorMessage(String code) {
    String message;
    try {
      Properties errorXML = new Properties();
      errorXML.loadFromXML(new FileInputStream(
              "webroot/WEB-INF/classes/student/main/responseMessages.xml"));
      message = errorXML.getProperty(code) + "\n";
    } catch (IOException ioe) {
      Debug.stderr("Failed to load responseMessages");
      message = "error: " + code;
    }
    return message;
  }

}
