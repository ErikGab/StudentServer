package student.main;

public class Debug {

  public static void stdout(String message) {
    if (doDebug()) {
      System.out.println(message);
    }
  }

  public static void stderr(String message) {
    if (doDebug()) {
      System.err.println(message);
    }
  }

  private static boolean doDebug(){
    String doDebugString = System.getProperty("debug");
    if (doDebugString != null && doDebugString.equals("true")) {
      return true;
    } else {
      return false;
    }
  }

}
