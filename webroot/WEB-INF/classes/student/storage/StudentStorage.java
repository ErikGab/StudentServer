package student.storage;

import java.util.List;
import student.format.Formatable;

public interface StudentStorage {

  public List<Formatable> getStudent(int id) throws StudentStorageException;
  public List<Formatable> getStudentsByCourse(int id) throws StudentStorageException;
  //public void removeStudent(int id) throws StudentStorageException;
  //public int addStudent(String name, String surName, String dateOfBirth) throws StudentStorageException;

  public List<Formatable> getCourse(int id) throws StudentStorageException;
  public List<Formatable> getCoursesByYear(int id) throws StudentStorageException;
  //public void removeCourse(int id) throws StudentStorageException;
  //public int addCourse(String name, String surName, String dateOfBirth) throws StudentStorageException;

}
