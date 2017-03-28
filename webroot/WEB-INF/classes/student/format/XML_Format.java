package student.format;
import java.lang.StringBuilder;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import student.format.Formatable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.DOMException;
import java.io.StringWriter;

class XML_Format implements Format {

    private static XML_Format instance;
    static {
        //System.out.println("Static block of XML_Format running...");
        instance = new XML_Format();
        FormatFactory.register("xml", instance);
    }
    private XML_Format(){};

    private Map<String,List<Formatable>> sortedFormatables;

    public String getMockData(){
        StringBuilder page = new StringBuilder();
        page.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
        .append("<address>\n")
        .append(" <first-name>Hanky</first-name>\n")
        .append(" <last-name>Sandycleavage</last-name>\n")
        .append(" <age>65</age>\n")
        .append(" <street-address>Skidrow 88</street-address>\n")
        .append(" <state>VGR</state>\n")
        .append(" <phone-numbers>\n")
        .append("  <mobile>0700123321</mobile>\n")
        .append("  <home>031-90 51 06</home>\n")
        .append(" </phone-numbers>\n")
        .append("</address>\n");
        return page.toString();
    }

    public String formatList(List<Formatable> listOfFormatables){
        sortFormatables(listOfFormatables);
        String result = "";
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("ITEMS");
            doc.appendChild(rootElement);

            for (String type:sortedFormatables.keySet()){
                Element typeElement = doc.createElement(type+"s");
                for ( Formatable item:sortedFormatables.get(type)){
                    Map<String,String> properties = item.getProperties();



                    Element itemElement = doc.createElement(type);
                    itemElement.setAttribute("id", Integer.toString(item.getId()));
                    for (String key:properties.keySet()){
                        Element x = doc.createElement(key);
                        x.appendChild(doc.createTextNode(properties.get(key)));
                        itemElement.appendChild(x);
                    }
                    typeElement.appendChild(itemElement);
                }
                rootElement.appendChild(typeElement);
            }


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            StringWriter writer = new StringWriter();
            DOMSource source = new DOMSource(doc);
            //StreamResult result = new StreamResult(new File("CV.xml"));
            transformer.transform(source, new StreamResult(writer));
            result = writer.getBuffer().toString();

        } catch (ParserConfigurationException pce){
            System.err.println("Oj oj ett ParserConfigurationException i XML_Format");
        } catch (TransformerConfigurationException tce){
            System.err.println("Oj oj ett TransformerConfigurationException i XML_Format");
        } catch (TransformerException te){
            System.err.println("Oj oj ett TransformerException i XML_Format");
        }
        return result;
        //return getMockData();
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
}
