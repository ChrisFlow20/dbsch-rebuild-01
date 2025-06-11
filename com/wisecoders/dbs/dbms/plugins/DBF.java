package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.schema.Column;

public class DBF extends Dbms {
  public static final String a = "DBF";
  
  public DBF() {
    super("DBF");
  }
  
  protected DBF(String paramString) {
    super(paramString, "DBF");
  }
  
  public void setImportedColumnType(Column paramColumn, int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2) {
    int i;
    if (paramString1 != null && (i = paramString1.toLowerCase().indexOf(" auto_increment")) > 0) {
      paramString1 = paramString1.substring(0, i);
      paramColumn.setIdentity((Dbms.get(this.dbId)).columnIdentityOptions.c_());
    } 
    super.setImportedColumnType(paramColumn, paramInt1, paramString1, paramInt2, paramInt3, paramString2);
  }
}
