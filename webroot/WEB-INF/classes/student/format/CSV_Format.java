package student.format;
import java.lang.StringBuilder;
import java.util.List;
import java.util.Map;
import student.format.Formatable;

class CSV_Format implements Format {

  static {
    FormatFactory.register("csv", new CSV_Format());
  }

  StringBuilder page;

  private CSV_Format(){};

  public String getFakeTestData(){
    writeCSV();
    return page.toString();
  }

  private void writeCSV(){
    page = new StringBuilder();
    page.append("firstName,lastName,age,streetAddress,State,postalCode,Mobile,Home\n");
    page.append("Hanky,Sandycleavage,65,Skidrow 88,VGR,66613,0700123321,031-90 51 06");
  }

  public String formatList(List<Formatable> listOfFormatables){
    StringBuilder sb = new StringBuilder();
    for (Formatable dataCarrier : listOfFormatables){

      Map<String,String> properties = dataCarrier.getProperties();

      for (String property : properties.keySet()){
        sb.append(property + "=" + properties.get(property) + ",");
      }

      sb.append("\n");
    }
    return sb.toString();
  }

}
