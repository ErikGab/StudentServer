package student.main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Debug {

  private static final String DELIMITER = ":    ";

  private Debug() {}

  /** Prints to STDOUT if Debug is active ( -Ddebug=true )
  *
  * @param message debugmessage.
  */
  public static void stdout(String message) {
    if (doDebug()) {
      System.out.println(timeStamp() + DELIMITER + message);
    }
  }

  /** Prints to STDERR if Debug is active ( -Ddebug=true )
  *
  * @param errorMessage errormessage
  */
  public static void stderr(String errorMessage) {
    if (doDebug()) {
      System.err.println(timeStamp() + DELIMITER + errorMessage);
    }
  }

  // Checks if Debug should be active
  private static boolean doDebug(){
    String doDebugString = System.getProperty("debug");
    if (doDebugString != null && doDebugString.equals("true")) {
      return true;
    } else {
      return false;
    }
  }

  private static String timeStamp() {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    return dtf.format(LocalDateTime.now());
  }

}
