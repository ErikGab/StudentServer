package student.format;
import java.lang.StringBuilder;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import student.format.Formatable;

class CSV_Format implements Format {

  static {
    FormatFactory.register("csv", new CSV_Format());
  }

  private StringBuilder page;
  private List<Formatable> currentList;
  private List<String> allProperties;
  private List<String> allMultiChildProperties;

  private CSV_Format(){};

  public String getMockData(){
    page = new StringBuilder();
    page.append("firstName,lastName,age,streetAddress,State,postalCode,Mobile,Home\n");
    page.append("Hanky,Sandycleavage,65,Skidrow 88,VGR,66613,0700123321,031-90 51 06");
    return page.toString();
  }

  public String formatList(List<Formatable> listOfFormatables){
    currentList = listOfFormatables;
    findAllPropertiesFromList();
    addDescriptionRow();
    addDataRows();
    return page.toString();
  }

    private void addDescriptionRow(){
        page = new StringBuilder();
        page.append("\n\n");
        for (String property : allProperties){
            page.append(property+",");
        }
        for (String property : allMultiChildProperties){
            page.append(property+",");
        }

        //ADD REMOVAL OF LAST "comma"

        page.append("\n\n");
    }

    private void addDataRows(){
        for (Formatable dataCarrier : currentList){
            Map<String,String> properties = dataCarrier.getProperties();
            //for (String property : properties.keySet()){
            for (String property : allProperties){
                page.append(property + "=" + properties.get(property) + ",");
            }
            for (String multiProp : allMultiChildProperties){
                Map<String,String> currentMulitProperties = dataCarrier.getMultiChildProperties(multiProp);
                for (String property : currentMulitProperties.keySet()){
                    page.append(currentMulitProperties.get(property)+":");
                }
                page.append(",");
            }
            page.append("\n");
        }

        //ADD REMOVAL OF LAST "comma"

        page.append("\n");
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
