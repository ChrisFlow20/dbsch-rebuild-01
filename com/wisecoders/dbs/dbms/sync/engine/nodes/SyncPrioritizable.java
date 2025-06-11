package com.wisecoders.dbs.dbms.sync.engine.nodes;

import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public interface SyncPrioritizable {
  public static final int NO_PRIORITY = -1;
  
  public static final int DROP_PLSQL = 0;
  
  public static final int DROP_FOREIGN_KEY = 100;
  
  public static final int DROP_CONSTRAINT = 200;
  
  public static final int DROP_VIEW = 300;
  
  public static final int DROP = 400;
  
  public static final int CHANGE = 500;
  
  public static final int RENAME = 600;
  
  public static final int CREATE_SCHEMA = 700;
  
  public static final int CREATE_SEQUENCES = 800;
  
  public static final int CREATE_USER_DATA_TYPES = 900;
  
  public static final int CREATE_TABLES = 1000;
  
  public static final int CREATE_COLUMNS = 2000;
  
  public static final int CREATE_CONSTRAINT = 3000;
  
  public static final int CREATE_CLUSTERED_INDEX = 4000;
  
  public static final int CREATE_INDEX = 5000;
  
  public static final int CREATE_FOREIGN_KEY = 6000;
  
  public static final int CREATE_FUNCTIONS = 7000;
  
  public static final int CREATE_VIEWS = 8000;
  
  public static final int CREATE_PLSQL = 9000;
  
  public static final int CREATE_TRIGGERS = 10000;
  
  public static final int COMMENT = 11000;
  
  public static final int DROP_LAST = 12000;
  
  public static final int FINAL = 13000;
  
  void setSyncPriority(int paramInt);
  
  int getSyncPriority();
  
  int getDefaultSyncPriority();
}
