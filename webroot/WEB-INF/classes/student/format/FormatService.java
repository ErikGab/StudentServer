package student.format;

import java.util.List;

public class FormatService {

    public static String formatList(List<Formatable> listOfFormatables, String format) throws FormatException {
        Format requestedFormat = FormatFactory.getFormat(format);
        return requestedFormat.formatList(listOfFormatables);
    }
}
