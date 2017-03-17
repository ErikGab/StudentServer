package student.format;
import java.io.*;
import java.lang.StringBuilder;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import java.util.List;
import student.format.Formatable;

class JSON_Format implements Format {

    static {
        FormatFactory.register("json", new JSON_Format());
    }

    StringBuilder page;

    private JSON_Format(){};

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

    public String formatList(List<Formatable> listOfFormatables){
        return getMockData();
    }
}
