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
import student.main.Debug;
import java.util.stream.Collectors;

class XMLFormatter implements Formatter {

  private Map<String, List<Formatable>> sortedFormatables;
  private Document doc;

  private static XMLFormatter instance;
  static {
    instance = new XMLFormatter();
    FormatterFactory.register("xml", instance);
  }

  private XMLFormatter() {};

  /** Wraps a message in XML and returns it.
  *
  * @param message string that should be wrapped in XML.
  */
  public String formatMessage(String message) {
    StringBuilder page = new StringBuilder();
    page.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
        .append("<error>\n")
        .append(" <message>" + message + "</message>\n")
        .append("</error>");
    return page.toString();
  }

  /** Returns a string containing XML describing the content if input list
  *
  * @param listOfFormatables list that shoud be formatted to XML.
  */
  public String formatList(List<Formatable> listOfFormatables) {
    sortFormatables(listOfFormatables);
    String result = "";
    try {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      doc = docBuilder.newDocument();
      Element rootElement = doc.createElement("ITEMS");
      doc.appendChild(rootElement);

      for (String type : sortedFormatables.keySet()) {
        Element typeElement = doc.createElement(type + "s");
        for ( Formatable item : sortedFormatables.get(type)) {
          typeElement.appendChild(formatable2xmlElement(item));
        }
        rootElement.appendChild(typeElement);
      }
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      StringWriter writer = new StringWriter();
      DOMSource source = new DOMSource(doc);
      transformer.transform(source, new StreamResult(writer));
      result = writer.getBuffer().toString();
    } catch (ParserConfigurationException pce) {
      Debug.stderr("ParserConfigurationException in XML_Format");
    } catch (TransformerConfigurationException tce) {
      Debug.stderr("TransformerConfigurationException in XML_Format");
    } catch (TransformerException te) {
      Debug.stderr("TransformerException in XML_Format " + te.getMessage());
    }
    return result;
  }

  // Groups list of formatables into map
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

  //Creates an element from a formatable
  private Element formatable2xmlElement(Formatable item) {
    Map<String, String> properties = item.getProperties();
    String type = item.getItemType();
    List<Formatable> subItems = item.getSubItems();
    Element itemElement = doc.createElement(type);
    itemElement.setAttribute("id", Integer.toString(item.getId()));
    for (String key : properties.keySet()) {
      Element x = doc.createElement(key);
      x.appendChild(doc.createTextNode(String.valueOf(properties.get(key))));
      itemElement.appendChild(x);
    }
    for (String currentSubItemType: collectSubItemTypes(item)) {
      List<Formatable> filterdSubItems = subItems.stream()
              .filter(i -> i.getItemType().equals(currentSubItemType)) //.filter(i -> i.getProperties().keySet().size() > 0)
              .collect(Collectors.toList());
      itemElement.appendChild(listOfSubitems2Elements(filterdSubItems, currentSubItemType));
    }
    return itemElement;
  }

  //Turns list of subitems to an element that is returned
  private Element listOfSubitems2Elements(List<Formatable> list, String name) {
    Element subItemTypeElement = doc.createElement(name);
    list.stream()
            .filter(i -> i.getProperties().keySet().size() > 0)
            .forEach(f -> subItemTypeElement.appendChild(formatable2xmlElement(f)));
    return subItemTypeElement;
  }

  // RETURNS A LIST OF STRINGS CONTAINING UNIQE SUBITEM TYPES FROM A FORMATABLE
  private List<String> collectSubItemTypes(Formatable item) {
    return item.getSubItems().stream()
            .map(i -> i.getItemType())
            .distinct()
            .collect(Collectors.toList());
  }

}
