package student.format;
import java.io.*;
import java.lang.StringBuilder;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import student.format.Formatable;

class JSONFormatter implements Formatter {

  static {
    FormatterFactory.register("json", new JSONFormatter());
  }

  private StringBuilder page;
  private Map<String,List<Formatable>> sortedFormatables;

  private JSONFormatter() {};

  /** Wraps the input message with json and returns it
  *
  * @param message the string that should be wrapped in JSON
  */
  public String formatMessage(String message) {
    return "{\"error\":[{\"message\":\"" + message + "\"}]}";
  }

  /** Coverts a list of formatables to a JSON string...LIKA A BOSS
  *
  * @param listOfFormatables list that shoud be formatted to JSON
  */
  public String formatList(List<Formatable> listOfFormatables) {
    page = new StringBuilder();
    sortFormatables(listOfFormatables);
    Map<String, Boolean> config = new HashMap<String, Boolean>();
    config.put(JsonGenerator.PRETTY_PRINTING, true);
    StringWriter writer = new StringWriter();
    JsonWriterFactory jwf = Json.createWriterFactory(config);
    JsonWriter jWriter = jwf.createWriter(writer);
    JsonObjectBuilder job = Json.createObjectBuilder();
    for (String type : sortedFormatables.keySet()) {
      job.add(type,listOfFormatables2JasonArrayBuilder(sortedFormatables.get(type)));
    }
    JsonObject jo = job.build();
    jWriter.writeObject(jo);
    jWriter.close();
    page.append(writer.toString());
    return page.toString();
  }

  // TAKES A LIST OF FORMATABLES AND RETURNS A JSON ARRAY OF JSON OBJECTS
  // EACH FORMATABLE IN LIST GETS REPRESENTED AS AN JSON OBJECT IN THE RETURNING ARRAY
  private JsonArrayBuilder listOfFormatables2JasonArrayBuilder(List<Formatable> list) {
    JsonArrayBuilder jab = Json.createArrayBuilder();
    // for (Formatable item:list){
    //   jab.add(formatable2JasonObject(item));
    // }
    list.forEach(f -> jab.add(formatable2JasonObject(f)));
    return jab;
  }

  // TAKES A FORMATABLE AND TURNS IT INTO AN JSON OBJECT
  private JsonObject formatable2JasonObject(Formatable item) {
    Map<String,String> properties = item.getProperties();
    List<Formatable> subItems = item.getSubItems();
    JsonObjectBuilder ob = Json.createObjectBuilder();

    // CREATES JSON FROM FORMATABLE PROPERTIES
    for (String key : properties.keySet()) {
      String value = properties.get(key);
      if (key == null) {
        if (value != null) {
          ob.add("", value);
        }
      } else {
        if (value == null) {
          ob.add(key, "");
        } else {
          ob.add(key, value);
        }
      }
    }
    // FOR EVERY UNIQE "SUBITEM TYPE"...
    // ...CREATE AN ARRAY OF JSON OBJECTS FROM SUBITEMS OF THAT TYPE
    for (String currentSubItemType: collectSubItemTypes(item)) {
      List<Formatable> filterdSubItems = subItems.stream()
              .filter(i -> i.getItemType().equals(currentSubItemType))
              .collect(Collectors.toList());
      ob.add(currentSubItemType, listOfFormatables2JasonArrayBuilder(filterdSubItems));
    }
    return ob.build();
  }

  // SORTS A LIST OF FORMATABLES BY ITEM TYPE
  private void sortFormatables(List<Formatable> currentList) {
    sortedFormatables = new HashMap<>();
    for (Formatable dataCarrier : currentList) {
      String type = dataCarrier.getItemType();
      if (sortedFormatables.keySet().contains(type)) {
        sortedFormatables.get(type).add(dataCarrier);
      } else {
        sortedFormatables.put(type, new ArrayList<Formatable>());
        sortedFormatables.get(type).add(dataCarrier);
      }
    }
  }

  // RETURNS A LIST OF STRINGS CONTAINING UNIQE SUBITEM TYPES FROM A FORMATABLE
  private List<String> collectSubItemTypes(Formatable item) {
    return item.getSubItems().stream()
            .map(i -> i.getItemType())
            .distinct()
            .collect(Collectors.toList());
  }

}
