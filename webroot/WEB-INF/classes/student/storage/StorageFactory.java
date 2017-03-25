package student.storage;

public class StorageFactory {

  private static StorageFactory instance;

  private StorageFactory(){};

  //
  // 2DO: Make -Dstorage=... select what the factory returns
  //
  //
  //

  public static StudentStorage getStorage() throws StudentStorageException{
      return new SQLStorage();
      //return new MockDataStorage();
  }

}
