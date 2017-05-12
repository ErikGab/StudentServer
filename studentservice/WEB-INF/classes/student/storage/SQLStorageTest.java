package student.storage;

import java.io.*;
import java.util.*;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SQLStorageTest {


  @Test
  public void verifyTypeOfStoragePart1() throws StudentStorageException {
    StudentStorage storage = StorageFactory.getStorage("SQL");
    SQLStorage verifiedStorage = (SQLStorage) storage;
    assertNotNull("should not be null", verifiedStorage.getStudent(0));
  }

  @Test(expected = java.lang.ClassCastException.class)
  public void verifyTypeOfStoragePart2() throws StudentStorageException {
    StudentStorage storage = StorageFactory.getStorage("SQL");
    MockDataStorage failingStorage = (MockDataStorage) storage;
    assertNotNull("should not be null", failingStorage.getStudent(0));
  }



  @Test
  public void getStudentTestNotNull() throws StudentStorageException {
    StudentStorage storage = StorageFactory.getStorage("SQL");
    assertNotNull("should not be null", storage.getStudent(0));
  }

  @Test
  public void getStudentTestListLenght1() throws StudentStorageException {
    StudentStorage storage = StorageFactory.getStorage("SQL");
    assertTrue("should be list with many elements", storage.getStudent(0).size() >= 5 );
  }

  @Test
  public void getStudentTestListLenght2() throws StudentStorageException {
    StudentStorage storage = StorageFactory.getStorage("SQL");
    assertTrue("should be list with only one elements", storage.getStudent(1).size() == 1 );
  }

  @Test
  public void getStudentTestListLenght3() throws StudentStorageException {
    StudentStorage storage = StorageFactory.getStorage("SQL");
    assertTrue("should be list with no elements", storage.getStudent(6666666).size() == 0 );
  }

  @Test
  public void getStudentTestNegativeIndex() throws StudentStorageException {
    StudentStorage storage = StorageFactory.getStorage("SQL");
    assertTrue("should be list with no elements", storage.getStudent(-42).size() == 0 );
  }



  @Test
  public void getCourseTestNotNull() throws StudentStorageException {
    StudentStorage storage = StorageFactory.getStorage("SQL");
    assertNotNull("should not be null", storage.getCourse(0));
  }

  @Test
  public void getCourseTestListLenght1() throws StudentStorageException {
    StudentStorage storage = StorageFactory.getStorage("SQL");
    assertTrue("should be list with many elements", storage.getCourse(0).size() >= 5 );
  }

  @Test
  public void getCourseTestListLenght2() throws StudentStorageException {
    StudentStorage storage = StorageFactory.getStorage("SQL");
    assertTrue("should be list with only one elements", storage.getCourse(1).size() == 1 );
  }

  @Test
  public void getCourseTestListLenght3() throws StudentStorageException {
    StudentStorage storage = StorageFactory.getStorage("SQL");
    assertTrue("should be list with no elements", storage.getCourse(6666666).size() == 0 );
  }



  @Test
  public void getStudentsByCourseTestNotNull() throws StudentStorageException {
    StudentStorage storage = StorageFactory.getStorage("SQL");
    assertNotNull("should not be null", storage.getStudentsByCourse(0));
  }

  @Test
  public void getStudentsByCourseTestListLenght1() throws StudentStorageException {
    StudentStorage storage = StorageFactory.getStorage("SQL");
    assertTrue("should be list with many elements", storage.getStudentsByCourse(0).size() >= 5 );
  }

  @Test
  public void getStudentsByCourseTestListLenght2() throws StudentStorageException {
    StudentStorage storage = StorageFactory.getStorage("SQL");
    assertTrue("should be list with no elements", storage.getStudentsByCourse(6666666).size() == 0 );
  }




  @Test
  public void getCoursesByYearByCourseTestNotNull() throws StudentStorageException {
    StudentStorage storage = StorageFactory.getStorage("SQL");
    assertNotNull("should not be null", storage.getCoursesByYear(0));
  }

  @Test
  public void getCoursesByYearByCourseTestListLenght1() throws StudentStorageException {
    StudentStorage storage = StorageFactory.getStorage("SQL");
    assertTrue("should be list with many elements", storage.getCoursesByYear(2016).size() >= 5 );
  }

  @Test
  public void getCoursesByYearByCourseTestListLenght2() throws StudentStorageException {
    StudentStorage storage = StorageFactory.getStorage("SQL");
    assertTrue("should be list with no elements", storage.getCoursesByYear(1942).size() == 0 );
  }

}
