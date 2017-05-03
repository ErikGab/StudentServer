package student.format;
import java.io.*;
import java.util.*;
import java.util.List;
import student.main.Debug;

public class FormatService {

  static private final String CONTENT_TYPE_XML =
          "webroot/WEB-INF/classes/student/format/formatResponseType.xml";

  private FormatService() {}

  /** Turns a List of Formatables to a string formatted in requested format
  *
  */
  public static String formatList(List<Formatable> listOfFormatables, String format)
          throws FormatException {
    Format requestedFormat = FormatFactory.getFormat(format);
    return requestedFormat.formatList(listOfFormatables);
  }

  /** Wraps a Message as a formatted string in requested format
  *
  */
  public static String formatMessage(String message, String format)
          throws FormatException {
    Format requestedFormat = FormatFactory.getFormat(format);
    return requestedFormat.formatMessage(message);
  }

  /** Returns contenttpe as a string for requested format
  *
  */
  public static String getContentType(String format) {
    String contentTypeString = "";
    try {
      Properties formats = new Properties();
      formats.loadFromXML(new FileInputStream(CONTENT_TYPE_XML));
      contentTypeString = String.valueOf(formats.getProperty(format));
    } catch (FileNotFoundException fne) {
      Debug.stderr(fne.getMessage());
    } catch (IOException ioe) {
      Debug.stderr(ioe.getMessage());
    } catch (NullPointerException npe) {
      Debug.stderr(npe.getMessage());
    }
    return contentTypeFallback(contentTypeString);
  }

  //returns a fallback (dafult) contentype if input is invalid
  private static String contentTypeFallback(String contentType) {
    if (contentType == null || contentType.equals("") || contentType.equals("null")) {
      return "text/plain";
    } else {
      return contentType;
    }
  }

}
