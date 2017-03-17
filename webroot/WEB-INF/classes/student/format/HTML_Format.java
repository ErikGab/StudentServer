package student.format;
import java.lang.StringBuilder;
import java.util.List;
import student.format.Formatable;

class HTML_Format implements Format {

  static {
    FormatFactory.register("html", new HTML_Format());
  }

  StringBuilder page;

  private HTML_Format(){};

  public String getFakeTestData(){
    writeHTML();
    return page.toString();
  }

  private void writeHTML(){
    page = new StringBuilder();
    page.append("<html>\n<head>\n<title>Hanky contact infoz</title>\n")
      .append("<style>\ntd{ vertical-align: top; }\n</style>")
      .append("</head>\n<body>\n<table>\n<tr>")
      .append("<th>first name</th><th>last name</th><th>age</th><th>address</th><th>state</th><th>phone numbers</th>")
      .append("</tr>\n<tr>")
      .append("<td>Hanky</td><td>Sandycleavage</td><td>65</td><td>Skidrow 88</td><td>VGR</td>")
      .append("<td>\n<ul>")
      .append("<ul>\n<li>0700123321</li>\n<li>031-90 51 06</li>\n</ul>")
      .append("</td>\n</tr>\n</table>\n</body>\n<html>");
  }

  public String formatList(List<Formatable> listOfFormatables){
    return getFakeTestData();
  }
}
