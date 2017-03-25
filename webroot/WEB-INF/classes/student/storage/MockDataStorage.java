package student.storage;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import student.main.Student;
import student.main.Course;
import student.format.Formatable;

public class MockDataStorage implements StudentStorage {

    private static List<Formatable> mockDataStudentList, mockDataCourseList;
    static {
        mockDataStudentList = new ArrayList<Formatable>();
        mockDataStudentList.add(new Student(74,
                                            "Mocke",
                                            "Datasson",
                                            "34",
                                            "Heapen",
                                            "Ramminnet 123",
                                            new HashMap<String,String>() {{
                                                    put("mobile", "0707-776655");
                                                    put("fax", "08-1234567");
                                            }}));

        mockDataStudentList.add(new Student(35,
                                            "Datbert",
                                            "Mockberg",
                                            "29",
                                            "Stacken",
                                            "L2Cachen 321",
                                            new HashMap<String,String>() {{
                                                    put("home", "031-112233");
                                                    put("mobile", "0701-987654");
                                            }}));

        mockDataCourseList = new ArrayList<Formatable>();
        mockDataCourseList.add(new Course(  8,
                                            "2016-02-21",
                                            "2016-11-04",
                                            "JAVA-101_2016",
                                            "40",
                                            "Programming with Java",
                                            new HashMap<String,String>() {{
                                                    put("74", "Mocke Datasson");
                                                    put("35", "Datbert Mockberg");
                                            }}));

        mockDataCourseList.add(new Course(  12,
                                            "2017-02-18",
                                            "2017-10-29",
                                            "JAVA-102_2017",
                                            "40",
                                            "Programming with Java",
                                            new HashMap<String,String>() {{
                                                    put("33", "Mock Datgård");
                                                    put("29", "Datur Mockels");
                                            }}));
    }
    public List<Formatable> getStudent(int id) throws StudentStorageException{
        return mockDataStudentList;
    }
    public List<Formatable> getCourse(int id) throws StudentStorageException{
        return mockDataCourseList;
    }

}
