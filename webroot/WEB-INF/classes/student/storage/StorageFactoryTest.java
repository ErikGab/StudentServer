package student.storage;

import java.io.*;
import java.util.*;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class StorageFactoryTest {

  @Test
  public void getStorageTestNotNull() throws StudentStorageException {
    assertNotNull("should not be null", StorageFactory.getStorage());
  }

  @Test
  public void getStorageTestSpecificRequest() throws StudentStorageException {
    assertNotNull("should not be null", StorageFactory.getStorage("MockData"));
  }

  @Test(expected = StudentStorageException.class)
  public void getStorageTestThrowExeption() throws StudentStorageException {
    assertNotNull("should not be null", StorageFactory.getStorage("ThisDontExist"));
  }

}
