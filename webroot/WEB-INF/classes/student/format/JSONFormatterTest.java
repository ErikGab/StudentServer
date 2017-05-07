package student.format;

import java.io.*;
import java.util.*;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonStructure;
import student.storage.StudentStorage;
import student.storage.MockDataStorage;
import student.storage.StorageFactory;
import student.storage.StudentStorageException;

public class JSONFormatterTest {


  @Test
  public void formatListTestStudentsNotNull() throws StudentStorageException, FormatException {
    StudentStorage storage = StorageFactory.getStorage("MockData");
    MockDataStorage verifiedStorage = (MockDataStorage) storage;
    List<Formatable> testcase = verifiedStorage.getStudentsByCourse(5);
    String json = FormatterFactory.getFormatter("json").formatList(testcase);
    JsonReader jsonReader = Json.createReader(new StringReader(json));
    JsonObject obj = jsonReader.readObject();
    jsonReader.close();
    assertNotNull("should not be null", obj);
  }

  @Test
  public void formatListTestNrOfStudents() throws StudentStorageException, FormatException {
    StudentStorage storage = StorageFactory.getStorage("MockData");
    MockDataStorage verifiedStorage = (MockDataStorage) storage;
    List<Formatable> testcase = verifiedStorage.getStudentsByCourse(5);
    String json = FormatterFactory.getFormatter("json").formatList(testcase);
    JsonReader jsonReader = Json.createReader(new StringReader(json));
    JsonObject obj = jsonReader.readObject();
    jsonReader.close();
    JsonArray array = obj.getJsonArray("student");
    assertTrue("should be two students", 2 == array.size());
  }

  @Test
  public void formatListTestNrOfCourses() throws StudentStorageException, FormatException {
    StudentStorage storage = StorageFactory.getStorage("MockData");
    MockDataStorage verifiedStorage = (MockDataStorage) storage;
    List<Formatable> testcase = verifiedStorage.getCoursesByYear(5);
    String json = FormatterFactory.getFormatter("json").formatList(testcase);
    JsonReader jsonReader = Json.createReader(new StringReader(json));
    JsonObject obj = jsonReader.readObject();
    jsonReader.close();
    JsonArray array = obj.getJsonArray("course");
    assertTrue("should be two courses", 2 == array.size());
  }

  @Test
  public void formatListTestDetailedStudent() throws StudentStorageException, FormatException {
    StudentStorage storage = StorageFactory.getStorage("MockData");
    MockDataStorage verifiedStorage = (MockDataStorage) storage;
    List<Formatable> testcase = verifiedStorage.getStudent(5);
    String json = FormatterFactory.getFormatter("json").formatList(testcase);
    JsonReader jsonReader = Json.createReader(new StringReader(json));
    JsonObject obj = jsonReader.readObject();
    jsonReader.close();
    JsonArray array = obj.getJsonArray("student");
    assertTrue("should be one student", 1 == array.size());
  }

  @Test
  public void formatListTestDetailedStudentPhone() throws StudentStorageException, FormatException {
    StudentStorage storage = StorageFactory.getStorage("MockData");
    MockDataStorage verifiedStorage = (MockDataStorage) storage;
    List<Formatable> testcase = verifiedStorage.getStudent(5);
    String json = FormatterFactory.getFormatter("json").formatList(testcase);
    JsonReader jsonReader = Json.createReader(new StringReader(json));
    JsonObject obj = jsonReader.readObject();
    jsonReader.close();
    JsonArray array = obj.getJsonArray("student");
    JsonObject student = array.getJsonObject(0);
    String faxnumber = student.getJsonArray("phonenumbers").getJsonObject(0).getString("fax");
    assertEquals("should be the same", faxnumber, "08-1234567");
  }

}
