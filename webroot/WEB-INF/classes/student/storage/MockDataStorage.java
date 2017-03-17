package student.storage;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import student.main.Student;
import student.format.Formatable;

public class MockDataStorage implements StudentStorage {

    private static List<Formatable> mockDataList;
    static {
        mockDataList = new ArrayList<Formatable>();

        mockDataList.add(new Student(74, "Mocke", "Datasson", "34", "Heapen", "Ramminnet 123", new HashMap<String,String>() {{
                                                                                                    put("mobile", "0707-776655");
                                                                                                    put("fax", "08-1234567");
                                                                                                }}));

        mockDataList.add(new Student(35, "Datbert", "Mockberg", "29", "Stacken", "L2Cachen 321", new HashMap<String,String>() {{
                                                                                                    put("home", "031-112233");
                                                                                                    put("mobile", "0701-987654");
                                                                                                }}));
    }
    public List<Formatable> getStudent(int id) throws StudentStorageException{
        return mockDataList;
    }
    public void removeStudent(int id) throws StudentStorageException{

    }
    public int addStudent(String name, String surName, String dateOfBirth) throws StudentStorageException{
        return 5;
    }
}
