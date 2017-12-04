package com.ebiester.organizeme.db;

import jetbrains.exodus.entitystore.PersistentEntityStore;
import jetbrains.exodus.entitystore.PersistentEntityStores;

//TODO get some basic DI in here to handle this
public class DBConfigurationHolder {
    private static String storageDir;

    public static void setStorageDir(String storageDir) {
        DBConfigurationHolder.storageDir = storageDir;
    }

    private static String getStorageDir() {
        return DBConfigurationHolder.storageDir;
    }

    public static PersistentEntityStore getPersistentEntityStore() {
        return PersistentEntityStores.newInstance(DBConfigurationHolder.getStorageDir());
    }
}
