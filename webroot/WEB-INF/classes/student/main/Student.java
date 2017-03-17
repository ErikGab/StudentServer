package student.main;

import student.format.Formatable;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;

public class Student implements Formatable {

  private int id;
  private Map<String, Map> multiChlidProperties = new TreeMap<>();
  private Map<String, String> properties = new TreeMap<>();

  public Student(int studentId, String name, String surname, String age, String postAddress, String streetAdress, Map phonenumber){
    this.id = studentId;
    properties.put("studentID", String.valueOf(studentId));
    properties.put("name", name);
    properties.put("surname", surname);
    properties.put("age", age);
    properties.put("postadress", postAddress);
    properties.put("streetAdress", streetAdress);
    multiChlidProperties.put("phonenumber", phonenumber);
  }

  public int getId(){
    return id;
  }

  public Map<String,String> getProperties(){
    return properties;
  }

  public List<String> listMultiChildProperties(){
    return new ArrayList<String>(multiChlidProperties.keySet());
  }

  public Map<String, String> getMultiChildProperties(String key){
    return multiChlidProperties.get(key);
  }

}