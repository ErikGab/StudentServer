package student.storage;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import student.main.Student;
import student.main.Course;
import student.format.Formatable;

public class MockDataStorage implements StudentStorage {

    private static List<Formatable> mockDataFullStudent, mockDataStudent, mockDataFullCourse, mockDataCourse;
    static {
        mockDataFullStudent = new ArrayList<Formatable>();
        mockDataFullStudent.add(new Student(74,
                                            "Mocke",
                                            "Datasson",
                                            "34",
                                            "Heapen",
                                            "Ramminnet 123",
                                            new HashMap<String,String>() {{
                                                    put("mobile", "0707-776655");
                                                    put("fax", "08-1234567");
                                            }},
                                            new ArrayList<Map<String,String>>() {{
                                                    add(new HashMap<String,String>(){{
                                                        put("id", "8");
                                                        put("name", "JAVA-101_2016");
                                                        put("status", "complete");
                                                        put("grade", "vg");
                                                    }});
                                                    add(new HashMap<String,String>(){{
                                                        put("id", "12");
                                                        put("name", "JAVA-102_2017");
                                                        put("status", "ongoing");
                                                        put("grade", "");
                                                    }});
                                            }}));

        mockDataFullCourse = new ArrayList<Formatable>();
        mockDataFullCourse.add(new Course(  8,
                                            "2016-02-21",
                                            "2016-11-04",
                                            "JAVA-101_2016",
                                            "40",
                                            "Programming with Java",
                                            new ArrayList<Map<String,String>>() {{
                                                add(new HashMap<String,String>(){{
                                                    put("id", "74");
                                                    put("name", "Mocke");
                                                    put("surname", "Datasson");
                                                    put("status", "complete");
                                                    put("grade", "vg");
                                                }});
                                                add(new HashMap<String,String>(){{
                                                    put("id", "35");
                                                    put("name", "Datbert");
                                                    put("surname", "Mockberg");
                                                    put("status", "complete");
                                                    put("grade", "g");
                                                }});
                                            }}));


        mockDataStudent = new ArrayList<Formatable>();
        mockDataStudent.add(new Student(74, "Mocke", "Datasson"));
        mockDataStudent.add(new Student(35, "Datbert", "Mockberg"));

        mockDataCourse = new ArrayList<Formatable>();
        mockDataCourse.add(new Course(  8, "JAVA-101_2016"));
        mockDataCourse.add(new Course(  12, "JAVA-102_2017"));
    }
    public List<Formatable> getStudent(int id) throws StudentStorageException{
        return mockDataFullStudent;
    }
    public List<Formatable> getCourse(int id) throws StudentStorageException{
        return mockDataFullCourse;
    }
    public List<Formatable> getStudentsByCourse(int id) throws StudentStorageException{
        return mockDataStudent;
    }
    public List<Formatable> getCoursesByYear(int id) throws StudentStorageException{
        return mockDataCourse;
    }
}
