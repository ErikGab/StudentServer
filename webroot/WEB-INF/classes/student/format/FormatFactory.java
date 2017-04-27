package student.format;
import java.io.*;
import java.util.*;
import student.main.Debug;

public class FormatFactory {

  private static Map<String, Format> formatsMap = new HashMap<>();
  static private final String FORMATS_XML = "webroot/WEB-INF/classes/student/format/formats.xml";
  static {
    try {
      Properties formats = new Properties();
      formats.loadFromXML(new FileInputStream(FORMATS_XML));
      for (String format : formats.stringPropertyNames()) {
        String className = formats.getProperty(format);
        Debug.stdout(format + " formatter found, loading class: " + className);
        try {
          Class.forName(className);
        } catch (ClassNotFoundException cnfe) {
          Debug.stderr(cnfe.getMessage());
        }
      }
    } catch (FileNotFoundException fne) {
      Debug.stderr(fne.getMessage());
    } catch (IOException ioe) {
      Debug.stderr(ioe.getMessage());
    }
  }

  public static void register(String key, Format formatInstance) {
    Debug.stdout("New Format registerd: " + key);
    formatsMap.put(key,formatInstance);
  }

  public static Format getFormat(String requestedFormat) throws FormatException {
    String format = String.valueOf(requestedFormat);
    Debug.stdout("returning format: " + format);
    if (formatsMap.containsKey(format)) {
      return formatsMap.get(format);
    } else {
      throw new FormatException("Format not supported: " + format);
    }
  }

}
