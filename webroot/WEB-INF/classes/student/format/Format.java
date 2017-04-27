package student.format;

import java.util.List;
import student.format.Formatable;

public interface Format {
  public String formatList(List<Formatable> listOfFormatables);
  public String formatMessage(String message);
}
