package student.format;

import java.util.Map;
import java.util.List;

public interface Formatable {

  public Map<String,String> getProperties();
  public List<String> listMultiChildProperties();
  public Map<String, String> getMultiChildProperties(String key);
}
