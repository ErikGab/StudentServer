package student.storage;

import java.io.*;
import java.util.*;
import student.main.Debug;

public class StorageFactory {

  private static Map<String, StudentStorage> storageMap = new HashMap<>();
  static private final String STORAGE_XML = "webroot/WEB-INF/classes/student/storage/storages.xml";

  private StorageFactory() {};

  static {
    try {
      Properties storages = new Properties();
      storages.loadFromXML(new FileInputStream(STORAGE_XML));
      for (String storage : storages.stringPropertyNames()) {
        String className = storages.getProperty(storage);
        Debug.stdout(storage + " storage found, loading class: " + className);
        try {
          Class.forName(className);
        } catch (ClassNotFoundException cnfe) {
          Debug.stderr(cnfe.getMessage());
        }
      }
    } catch (FileNotFoundException fne) {
      Debug.stderr(fne.getMessage());
    } catch (IOException ioe) {
      Debug.stderr(ioe.getMessage());
    }
  }

  public static StudentStorage getStorage() throws StudentStorageException {
    if (storageMap.isEmpty()) {
      throw new StudentStorageException("No storage availible");
    } else {
      return storageMap.get(storageMap.keySet().toArray()[0]);
    }
  }

  public static StudentStorage getStorage(String type) throws StudentStorageException {
    if (storageMap.containsKey(type)) {
      return storageMap.get(type);
    } else {
      throw new StudentStorageException("No storage of type " + type + " availible");
    }
  }

  public static void register(String key, StudentStorage storageInstance) {
    Debug.stdout("New storage registerd: " + key);
    storageMap.put(key,storageInstance);
  }

}
