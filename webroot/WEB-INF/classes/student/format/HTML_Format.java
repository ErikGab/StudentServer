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

    private HTML_Format(){};

    public String getMockData(){
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
        return page.toString();
    }

    public String formatList(List<Formatable> listOfFormatables){
        resetPage();
        sortFormatables(listOfFormatables);
        for (String type:sortedFormatables.keySet()){
            page.append(type)
                .append("\n")
                .append("<table border=\"1\">\n");
            addDescriptionRow(sortedFormatables.get(type));
            addDataRows(sortedFormatables.get(type));
            page.append("</table>\n");
        }
        return page.toString();
    }

    private void resetPage(){
        page = new StringBuilder().append("<html>\n<head>\n<title>Requested data</title>\n")
                                    .append("<style>\ntd{ vertical-align: top; }\n</style>")
                                    .append("</head>\n<body>\n");
    }


    private void addDescriptionRow(List<Formatable> list){
        headers = new ArrayList<>();
        subItemHeaders = new ArrayList<>();
        for (Formatable item:list){
            for (String key:item.getProperties().keySet()){
                if (headers.contains(key)? false : true){
                    headers.add(key);
                }
            }
            for (Formatable subitem:item.getSubItems()){
                if (subItemHeaders.contains(subitem.getItemType())? false : true){
                    subItemHeaders.add(subitem.getItemType());
                }
            }
        }
        for (String header:headers){
            page.append("<th>"+header+"</th>");
        }
        for (String header:subItemHeaders){
            page.append("<th>"+header+"</th>");
        }
        page.append("<br>");
    }

    private void addDataRows(List<Formatable> list){
        for (Formatable item : list){
            Map<String,String> properties = item.getProperties();
            List<Formatable> subitems = item.getSubItems();
            page.append("<tr>");
            for (String header:headers){
                if (properties.keySet().contains(header)){
                    page.append("<td>"+properties.get(header)+"</td>");
                } else {
                    page.append("<td></td>");
                }
            }
            for (String header:subItemHeaders){
                page.append("<td>");
                for (Formatable subitem:subitems){
                    if (header.equals(subitem.getItemType())){
                        Map<String,String> subProperties = subitem.getProperties();
                        for (String key:subProperties.keySet()){
                            page.append(key+"="+subProperties.get(key)+", ");
                        }
                        page.append("<br>");
                    }
                }
                page.append("</td>");
            }
            page.append("</tr>");
        }
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
