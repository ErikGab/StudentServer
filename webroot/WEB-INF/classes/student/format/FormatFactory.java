package student.format;
import java.io.*;
import java.util.*;

public class FormatFactory {

    private static Map<String, Format> formatsMap = new HashMap<>();
    static private final String FORMATS_XML = "webroot/WEB-INF/classes/student/format/formats.xml";
    static{
        try{
            Properties formats = new Properties();
            formats.loadFromXML(new FileInputStream(FORMATS_XML));
            for(String format : formats.stringPropertyNames()){
                String className = formats.getProperty(format);
                System.out.println(format + " formatter found, loading class: " + className);
                try{
                    Class.forName(className); // will trigger the static block of this class!
                } catch (ClassNotFoundException cnfe){ System.err.println(cnfe.getMessage()); }
            }
        }catch(FileNotFoundException fne){
            System.err.println(fne.getMessage());
        }catch(IOException ioe){
            System.err.println(ioe.getMessage());
        }
    }

    public static void register(String key, Format formatInstance){
        System.out.println("New Format registerd: " + key);
        formatsMap.put(key,formatInstance);
    }

    public static Format getFormat(String userRequest) throws FormatException{
        String format = String.valueOf(userRequest);
        System.out.println(format);
        if (formatsMap.containsKey(format)){
            return formatsMap.get(format);
        } else {
            throw new FormatException("Format not supported.");
        }
    }
}
