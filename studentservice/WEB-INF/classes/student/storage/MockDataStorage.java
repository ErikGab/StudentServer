package student.storage;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import student.format.StdItem;
import student.format.Formatable;

public class MockDataStorage implements StudentStorage {

    private MockDataStorage(){};

    private static List<Formatable> mockDataFullStudent, mockDataStudent, mockDataFullCourse, mockDataCourse;
    static {
        mockDataFullStudent = new ArrayList<Formatable>();
        mockDataFullStudent.add(new StdItem("student",
                                            74,
                                            new HashMap<String,String>() {{
                                                    put("id", "74");
                                                    put("name", "Mocke");
                                                    put("surname", "Datasson");
                                                    put("age", "34");
                                                    put("postAddress", "Heapen");
                                                    put("streetAddress", "Ramminnet 123");
                                            }},
                                            new ArrayList<Formatable>() {{
                                                add(new StdItem("phonenumbers",
                                                    1,
                                                    new HashMap<String,String>() {{
                                                            put("mobile", "0707-776655");
                                                            put("fax", "08-1234567");
                                                    }}));
                                                add(new StdItem("course",
                                                    8,
                                                    new HashMap<String,String>(){{
                                                        put("id", "8");
                                                        put("name", "JAVA-101_2016");
                                                        put("status", "complete");
                                                        put("grade", "vg");
                                                    }}));
                                                add(new StdItem("course",
                                                    12,
                                                    new HashMap<String,String>(){{
                                                        put("id", "12");
                                                        put("name", "JAVA-102_2017");
                                                        put("status", "ongoing");
                                                        put("grade", "");
                                                    }}));
                                            }}));

        mockDataFullCourse = new ArrayList<Formatable>();
        mockDataFullCourse.add(new StdItem("course",
                                            8,
                                            new HashMap<String,String>() {{
                                                    put("id", "8");
                                                    put("startDate", "2016-02-21");
                                                    put("endDate", "2016-11-04");
                                                    put("name", "JAVA-101_2016");
                                                    put("points", "40");
                                                    put("description", "Programming with Java");
                                            }},
                                            new ArrayList<Formatable>() {{
                                                add(new StdItem("student",
                                                    74,
                                                    new HashMap<String,String>(){{
                                                        put("id", "74");
                                                        put("name", "Mocke");
                                                        put("surname", "Datasson");
                                                        put("status", "complete");
                                                        put("grade", "vg");
                                                    }}));
                                                add(new StdItem("student",
                                                    35,
                                                    new HashMap<String,String>(){{
                                                        put("id", "35");
                                                        put("name", "Datbert");
                                                        put("surname", "Mockberg");
                                                        put("status", "complete");
                                                        put("grade", "g");
                                                    }}));
                                            }}));

        mockDataStudent = new ArrayList<Formatable>();
        mockDataStudent.add(new StdItem("student", 74, new HashMap<String,String>() {{
            put("id", "74");
            put("name", "Mocke");
            put("surname", "Datasson");
        }}));
        mockDataStudent.add(new StdItem("student", 35, new HashMap<String,String>() {{
            put("id", "35");
            put("name", "Datbert");
            put("surname", "Mockberg");
        }}));

        mockDataCourse = new ArrayList<Formatable>();
        mockDataCourse.add(new StdItem("course", 8, new HashMap<String,String>() {{
            put("id", "8");
            put("name", "JAVA-101_2016");
        }}));
        mockDataCourse.add(new StdItem("course", 12, new HashMap<String,String>() {{
            put("id", "12");
            put("name", "JAVA-102_2017");
        }}));
        StorageFactory.register("MockData", new MockDataStorage());
    }
    public List<Formatable> getStudent(int id) throws StudentStorageException {
        return mockDataFullStudent;
    }
    public List<Formatable> getCourse(int id) throws StudentStorageException {
        return mockDataFullCourse;
    }
    public List<Formatable> getStudentsByCourse(int id) throws StudentStorageException {
        return mockDataStudent;
    }
    public List<Formatable> getCoursesByYear(int id) throws StudentStorageException {
        return mockDataCourse;
    }
    public void addCourse(String startDate, String endDate, int id) throws StudentStorageException {
    }
    public void addStudent(String name, String surname, String streetAddress, String postAddress,
            String dateOfBirth) throws StudentStorageException {
    }
}
