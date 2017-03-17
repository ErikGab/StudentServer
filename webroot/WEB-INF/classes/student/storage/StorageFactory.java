package student.storage;

public class StorageFactory {

  private static StorageFactory instance;

  private StorageFactory(){};

  public static StudentStorage getStorage() throws StudentStorageException{
      //return new SQLStorage();
      return new MockDataStorage();
  }

}
