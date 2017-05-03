package student.main;

public class Debug {

  private Debug() {}

  /** Prints to STDOUT if Debug is active ( -Ddebug=true )
  */
  public static void stdout(String message) {
    if (doDebug()) {
      System.out.println(message);
    }
  }

  /** Prints to STDERR if Debug is active ( -Ddebug=true )
  */
  public static void stderr(String message) {
    if (doDebug()) {
      System.err.println(message);
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

}
