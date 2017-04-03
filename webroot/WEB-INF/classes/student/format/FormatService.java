package student.format;
import java.io.*;
import java.util.*;
import java.util.List;

public class FormatService {

    public static String formatList(List<Formatable> listOfFormatables, String format) throws FormatException {
        Format requestedFormat = FormatFactory.getFormat(format);
        return requestedFormat.formatList(listOfFormatables);
    }

    static private final String CONTENT_TYPE_XML = "webroot/WEB-INF/classes/student/format/formatResponseType.xml";

    public static String getContentType(String format){
        String contentTypeString = "";
        try {
            Properties formats = new Properties();
            formats.loadFromXML(new FileInputStream(CONTENT_TYPE_XML));
            contentTypeString = String.valueOf(formats.getProperty(format));
        }catch(FileNotFoundException fne){
            System.err.println(fne.getMessage());
        }catch(IOException ioe){
            System.err.println(ioe.getMessage());
        }
        return contentTypeString;
    }

}
