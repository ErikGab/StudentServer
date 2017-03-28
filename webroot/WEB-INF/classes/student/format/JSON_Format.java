package student.format;
import java.io.*;
import java.lang.StringBuilder;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;
import javax.json.JsonWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import student.format.Formatable;

class JSON_Format implements Format {

    static {
        FormatFactory.register("json", new JSON_Format());
    }

    private StringBuilder page;
    private Map<String,List<Formatable>> sortedFormatables;

    private JSON_Format(){};

    public String formatList(List<Formatable> listOfFormatables){
        page = new StringBuilder();
        sortFormatables(listOfFormatables);
        StringWriter writer = new StringWriter();
        JsonWriter jWriter = Json.createWriter(writer);
        JsonObjectBuilder job = Json.createObjectBuilder();
        for (String type:sortedFormatables.keySet()){
            job.add(type,listOfFormatables2JasonArrayBuilder(sortedFormatables.get(type)));
        }
        JsonObject jo = job.build();
        jWriter.writeObject(jo);
        jWriter.close();
        page.append(writer.toString());
        return page.toString();
        //return testJsonObjects();
    }

    private JsonArrayBuilder listOfFormatables2JasonArrayBuilder(List<Formatable> list){
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (Formatable item:list){
            jab.add(formatable2JasonObject(item));
        }
        return jab;
    }

    private JsonObject formatable2JasonObject(Formatable item){
        Map<String,String> properties = item.getProperties();
        List<Formatable> subItems = item.getSubItems();

        JsonObjectBuilder ob = Json.createObjectBuilder();
        for (String key:properties.keySet()){
            //System.out.println(key+"  :  "+ properties.get(key));
            String value = properties.get(key);
            if (key == null){
                if (value != null){ ob.add("",value); }
            } else {
                if (value == null){ ob.add(key,""); }
                else {              ob.add(key,value); }
            }
        }
        for (Formatable subItem:subItems){
            ob.add(subItem.getItemType(), Json.createArrayBuilder()
                .add(formatable2JasonObject(subItem)));
        }
        return ob.build();
    }

    private void sortFormatables(List<Formatable> currentList){
        sortedFormatables = new HashMap<>();
        for (Formatable dataCarrier : currentList){
            String type = dataCarrier.getItemType();
            if (sortedFormatables.keySet().contains(type)){
                sortedFormatables.get(type).add(dataCarrier);
            } else {
                sortedFormatables.put(type, new ArrayList<Formatable>());
                sortedFormatables.get(type).add(dataCarrier);
            }
        }
    }

    public String getMockData(){
        page = new StringBuilder();
        StringWriter writer = new StringWriter();
        JsonWriter jWriter = Json.createWriter(writer);
        JsonObject jo = Json.createObjectBuilder()
            .add("firstName","Hanky")
            .add("lastName","Sandycleavage")
            .add("age",65)
            .add("streetAddress","Skidrow 88")
            .add("State","VGR")
            .add("postalCode","66613")
            .add("phoneNumbers", Json.createArrayBuilder()
            .add(Json.createObjectBuilder()
                .add("Mobile", "0700123321"))
            .add(Json.createObjectBuilder()
                .add("Home", "031-90 51 06")))
            .build();
        jWriter.writeObject(jo);
        jWriter.close();
        page.append(writer.toString());
        return page.toString();
    }

    private String getMockData2(){
        page = new StringBuilder();
        StringWriter writer = new StringWriter();
        JsonWriter jWriter = Json.createWriter(writer);

        JsonObject student1 = Json.createObjectBuilder()
            .add("firstName","Hanky")
            .add("lastName","Sandycleavage")
            .add("age",65)
            .add("streetAddress","Skidrow 88")
            .add("State","VGR")
            .add("postalCode","66613")
            .add("phoneNumbers", Json.createArrayBuilder()
            .add(Json.createObjectBuilder()
                .add("Mobile", "0700123321"))
            .add(Json.createObjectBuilder()
                .add("Home", "031-90 51 06")))
            .build();

        JsonObject student2 = Json.createObjectBuilder()
            .add("firstName","bosse")
            .add("lastName","bildoktorn")
            .add("age",77)
            .add("streetAddress","hjulgången 33")
            .add("State","VGR")
            .add("postalCode","66613")
            .add("phoneNumbers", Json.createArrayBuilder()
            .add(Json.createObjectBuilder()
                .add("Mobile", "0700123321"))
            .add(Json.createObjectBuilder()
                .add("Home", "031-90 51 06")))
            .build();

        JsonObject jo = Json.createObjectBuilder()
            .add("student", Json.createArrayBuilder()
            .add(student1)
            .add(student2))
            .build();
        jWriter.writeObject(jo);
        jWriter.close();
        page.append(writer.toString());
        return page.toString();
    }

}
