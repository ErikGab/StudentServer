package student.format;

import student.format.Formatable;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class StdItem implements Formatable {

  private int id;
  private String itemType;
  private Map<String, String> properties;
  private List<Formatable> subItems = new ArrayList<>();

  public StdItem(String itemType, int id, Map<String, String> properties, List<Formatable> subItems) {
    this.id = id;
    this.itemType = itemType;
    this.properties = properties;
    this.subItems = subItems;
  }

  public StdItem(String itemType, int id, Map<String, String> properties) {
    this.id = id;
    this.itemType = itemType;
    this.properties = properties;
  }

  public void addSubItem(Formatable item) {
    subItems.add(item);
  }

  public int getId() {
    return id;
  }

  public String getItemType() {
    return itemType;
  }

  public Map<String, String> getProperties() {
    return properties;
  }

  public List<Formatable> getSubItems() {
    return subItems;
  }

}
