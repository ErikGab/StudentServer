package student.format;
import java.io.*;
import java.util.*;
import java.util.List;
import student.main.Debug;

public class FormatService {

  static private final String CONTENT_TYPE_XML =
          "studentservice/WEB-INF/classes/student/format/formatResponseType.xml";

  private FormatService() {}

  /** Turns a List of Formatables to a string formatted in requested format
  *
  * @param listOfFormatables a list of formatables that shuld be formatted to requested format.
  * @param format the format that should be returned.
  */
  public static String formatList(List<Formatable> listOfFormatables, String format)
          throws FormatException {
    Formatter requestedFormatter = FormatterFactory.getFormatter(format);
    return requestedFormatter.formatList(listOfFormatables);
  }

  /** Wraps a Message as a formatted string in requested format
  *
  * @param message the message that should be formatted to requested format.
  * @param format the format that should be returned.
  */
  public static String formatMessage(String message, String format)
          throws FormatException {
    Formatter requestedFormatter = FormatterFactory.getFormatter(format);
    return requestedFormatter.formatMessage(message);
  }

  /** Returns contenttpe as a string for requested format
  *
  * @param format the format which content type should be returned.
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
