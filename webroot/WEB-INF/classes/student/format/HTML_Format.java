package student.format;
import java.lang.StringBuilder;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import student.format.Formatable;

class HTML_Format implements Format {

    static {
        FormatFactory.register("html", new HTML_Format());
    }

    private List<Formatable> currentList;
    private StringBuilder page;
    private List<String> allProperties;
    private List<String> allMultiChildProperties;

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
        currentList = listOfFormatables;
        findAllPropertiesFromList();
        resetPage();
        addTableHead();
        addTableRows();
        return page.toString();
    }

    private void resetPage(){
        page = new StringBuilder().append("<html>\n<head>\n<title>Requested data</title>\n")
                                    .append("<style>\ntd{ vertical-align: top; }\n</style>")
                                    .append("</head>\n<body>\n<table border=\"1\">\n<tr>");
    }
    private void addTableHead(){
        for (String property : allProperties){
            page.append("<th>"+property+"</th>");
        }
        for (String property : allMultiChildProperties){
            page.append("<th>"+property+"</th>");
        }
        page.append("</tr><tr>");
    }
    private void addTableRows(){
        for (Formatable dataCarrier : currentList){
            Map<String,String> properties = dataCarrier.getProperties();
            //for (String property : properties.keySet()){
            for (String property : allProperties){
                page.append("<td>"+properties.get(property)+"</td>");
            }
            for (String multiProp : allMultiChildProperties){
                //System.out.println(multiProp);
                Map<String,String> currentMulitProperties = dataCarrier.getMultiChildProperties(multiProp);
                page.append("<td>");
                for (String property : currentMulitProperties.keySet()){
                    page.append((property+": "+currentMulitProperties.get(property)+"<br>").replace("null:","noName:"));
                }
                page.append("</td>");
            }
            page.append("</tr><tr>");
        }
    }

    private void findAllPropertiesFromList(){
        allProperties = new ArrayList<String>();
        allMultiChildProperties = new ArrayList<String>();
        for (Formatable dataCarrier : currentList){
            Map<String,String> properties = dataCarrier.getProperties();
            for (String property : properties.keySet()){
                if (allProperties.contains(property)? false : true){
                    allProperties.add(property);
                }
            }
            List<String> multiChildProperties = dataCarrier.listMultiChildProperties();
            if (multiChildProperties.size() != 0){
                for (String property : multiChildProperties){
                    if (allMultiChildProperties.contains(property)? false : true){
                        allMultiChildProperties.add(property);
                    }
                }
            }
        }
    }

}
