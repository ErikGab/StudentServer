package student.format;

import java.util.Map;
import java.util.List;

public interface Formatable {
  public int getId();
  public String getItemType();
  public Map<String,String> getProperties();
  public List<Formatable> getSubItems();
}
