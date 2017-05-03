package student.format;
import java.lang.StringBuilder;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import student.format.Formatable;

class HTML_Format implements Format {

  static {
    FormatFactory.register("html", new HTML_Format());
  }

  private StringBuilder page;
  private List<String> headers;
  private List<String> subItemHeaders;
  private Map<String,List<Formatable>> sortedFormatables;

  private HTML_Format() {};

  /** Wraps the input message with html and returns it
  /
  */
  public String formatMessage(String message) {
    resetPage();
    page.append("<p>" + message + "</p></body></html>");
    return page.toString();
  }

  /** Retrns some HTML data as a String. This HTML-data does not have the same
  *   exact table-structure as the real deal.
  */
  public String getMockData() {
    page = new StringBuilder();
    page.append("<html>\n<head>\n<title>Hanky contact infoz</title>\n")
        .append("<style>\ntd{ vertical-align: top; }\n</style>")
        .append("</head>\n<body>\n<table>\n<tr>")
        .append("<th>first name</th><th>last name</th><th>age</th><th>address</th><th>state</th>")
        .append("<th>phone numbers</th>")
        .append("</tr>\n<tr>")
        .append("<td>Hanky</td><td>Sandycleavage</td><td>65</td><td>Skidrow 88</td><td>VGR</td>")
        .append("<td>\n<ul>")
        .append("<ul>\n<li>0700123321</li>\n<li>031-90 51 06</li>\n</ul>")
        .append("</td>\n</tr>\n</table>\n</body>\n<html>");
    return page.toString();
  }

  /** Formats input list of formatables into a formatted string containing HTML data.
  *
  */
  public String formatList(List<Formatable> listOfFormatables) {
    resetPage();
    sortFormatables(listOfFormatables);
    for (String type:sortedFormatables.keySet()) {
      page.append("    <p>")
          .append(type)
          .append("</p>\n")
          .append("    <table border=\"1\">\n");
      addDescriptionRow(sortedFormatables.get(type));
      addDataRows(sortedFormatables.get(type));
      page.append("    </table>\n")
          .append("  </body>\n")
          .append("</html>\n");
    }
    return page.toString();
  }

  // resets the stringbulider
  private void resetPage() {
    page = new StringBuilder().append("<html>\n  <head>\n    <title>Requested data</title>\n")
                              .append("  </head>\n  <body>\n");
  }

  // Add html table top row
  private void addDescriptionRow(List<Formatable> list) {
    headers = new ArrayList<>();
    subItemHeaders = new ArrayList<>();
    for (Formatable item : list) {
      for (String key : item.getProperties().keySet()) {
        if (headers.contains(key)? false : true) {
          headers.add(key);
        }
      }
      for (Formatable subitem : item.getSubItems()) {
        if (subItemHeaders.contains(subitem.getItemType())? false : true) {
          subItemHeaders.add(subitem.getItemType());
        }
      }
    }
    page.append("      ");
    for (String header : headers) {
      page.append("<th>" + header + "</th>");
    }
    for (String header : subItemHeaders) {
      page.append("<th>" + header + "</th>");
    }
    page.append("\n");
  }

  // Add html for reach formatable (one row in table)
  private void addDataRows(List<Formatable> list) {
    for (Formatable item : list) {
      Map<String, String> properties = item.getProperties();
      List<Formatable> subitems = item.getSubItems();
      page.append("      <tr>");
      for (String header : headers) {
        if (properties.keySet().contains(header)) {
          page.append("<td>" + properties.get(header) + "</td>");
        } else {
          page.append("<td></td>");
        }
      }
      appendSubItemData(subitems);
      page.append("</tr>\n");
    }
  }

  //Add html table column for each item in list
  private void appendSubItemData(List<Formatable> subitems) {
    for (String header : subItemHeaders) {
      page.append("<td>");
      for (Formatable subitem : subitems) {
        if (header.equals(subitem.getItemType())) {
          Map<String, String> subProperties = subitem.getProperties();
          for (String key : subProperties.keySet()) {
            page.append(key + "=" + subProperties.get(key) + ", ");
          }
          page.append("<br>");
        }
      }
      page.append("</td>");
    }
  }

  // Groups list of formatables into map
  private void sortFormatables(List<Formatable> currentList) {
    sortedFormatables = new HashMap<>();
    for (Formatable item : currentList) {
      String type = item.getItemType();
      if (sortedFormatables.keySet().contains(type)) {
        sortedFormatables.get(type).add(item);
      } else {
        sortedFormatables.put(type, new ArrayList<Formatable>());
        sortedFormatables.get(type).add(item);
      }
    }
  }

}
