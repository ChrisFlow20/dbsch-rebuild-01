package com.wisecoders.dbs.dbms.reverseEngineer.model;

import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.ForeignKeyAction;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.StringUtil;

public class ForeignKeyEntry implements Comparable {
  public final Table a;
  
  public final Table b;
  
  public final String c;
  
  public final Column d;
  
  public final Column e;
  
  public final ForeignKeyAction f;
  
  public final ForeignKeyAction g;
  
  public final int h;
  
  public ForeignKeyEntry(Table paramTable1, Table paramTable2, String paramString, Column paramColumn1, Column paramColumn2, ForeignKeyAction paramForeignKeyAction1, ForeignKeyAction paramForeignKeyAction2, int paramInt) {
    this.a = paramTable1;
    this.b = paramTable2;
    this.c = StringUtil.isEmptyTrim(paramString) ? paramTable2.foreignKeys.proposeName("Fk_" + paramTable2.getName()) : paramString;
    this.d = paramColumn1;
    this.e = paramColumn2;
    this.f = paramForeignKeyAction1;
    this.g = paramForeignKeyAction2;
    this.h = paramInt;
  }
  
  public int a(ForeignKeyEntry paramForeignKeyEntry) {
    int i = this.a.getName().compareTo(paramForeignKeyEntry.a.getName());
    if (i != 0)
      return i; 
    i = this.c.compareTo(paramForeignKeyEntry.c);
    if (i != 0)
      return i; 
    return Integer.compare(this.h, paramForeignKeyEntry.h);
  }
  
  public String toString() {
    return this.b.ref() + " -> " + this.b.ref();
  }
}
