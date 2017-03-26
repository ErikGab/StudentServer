package student.main;

import student.format.Formatable;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;

public class Course implements Formatable {

    private int id;
    private Map<String, Map<String,String>> multiChlidProperties = new TreeMap<>();
    private Map<String, String> properties = new TreeMap<>();

    public Course(int courseId, String startDate, String endDate, String name, String points, String description, List<Map<String,String>> students){
        this.id = courseId;
        properties.put("courseID", String.valueOf(courseId));
        properties.put("startDate", startDate);
        properties.put("endDate", endDate);
        properties.put("name", name);
        properties.put("points", points);
        properties.put("description", description);
        for (Map m:students){
            multiChlidProperties.put("studentId_"+m.get("id"), m);
        }
    }

    public Course(int courseId, String name){
        this.id = courseId;
        properties.put("courseID", String.valueOf(courseId));
        properties.put("name", name);
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
