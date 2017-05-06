package student.main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Debug {

  private static final String DELIMITER = ":    ";

  private Debug() {}

  /** Prints to STDOUT if Debug is active ( -Ddebug=true )
  */
  public static void stdout(String message) {
    if (doDebug()) {
      System.out.println(timeStamp() + DELIMITER + message);
    }
  }

  /** Prints to STDERR if Debug is active ( -Ddebug=true )
  */
  public static void stderr(String message) {
    if (doDebug()) {
      System.err.println(timeStamp() + DELIMITER + message);
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
    LocalDateTime now = LocalDateTime.now();
    return dtf.format(now);
  }

}
