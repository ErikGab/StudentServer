package student.format;
import java.lang.StringBuilder;
import java.util.List;
import student.format.Formatable;

class XML_Format implements Format {

  private static XML_Format instance;
  static {
    //System.out.println("Static block of XML_Format running...");
    instance = new XML_Format();
    FormatFactory.register("xml", instance);
  }
  private XML_Format(){};

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
    return getMockData();
  }
}
