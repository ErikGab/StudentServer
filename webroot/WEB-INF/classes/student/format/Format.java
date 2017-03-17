package student.format;

import java.util.List;
import student.format.Formatable;

public interface Format {

  public String getFakeTestData();
  public String formatList(List<Formatable> listOfFormatables);

}
