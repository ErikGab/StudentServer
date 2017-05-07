package student.format;
import java.io.*;
import java.util.*;
import student.main.Debug;

public class FormatterFactory {

  private static Map<String, Formatter> formatsMap = new HashMap<>();
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

  /** Registers an instance of a Format to this factory
  *
  * @param key name of format to register.
  * @param formatterInstance instance of format to register.
  */
  public static void register(String key, Formatter formatterInstance) {
    Debug.stdout("New Formatter registerd: " + key);
    formatsMap.put(key,formatterInstance);
  }

  /** Returns a formatter of requested type.
  *
  * @param requestedFormatter name of format
  */
  public static Formatter getFormatter(String requestedFormatter) throws FormatException {
    String formatter = String.valueOf(requestedFormatter);
    Debug.stdout("returning formatter: " + formatter);
    if (formatsMap.containsKey(formatter)) {
      return formatsMap.get(formatter);
    } else {
      throw new FormatException("Format not supported: " + formatter);
    }
  }

}
