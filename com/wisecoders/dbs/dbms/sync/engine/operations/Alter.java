package com.wisecoders.dbs.dbms.sync.engine.operations;

import com.wisecoders.dbs.dbms.DbmsDef;

public class Alter {
  protected final DbmsDef a;
  
  public Alter(DbmsDef paramDbmsDef) {
    this.a = paramDbmsDef;
  }
  
  public int a() {
    return this.a.commentsAtScriptEnd.b() ? 11000 : -1;
  }
}
