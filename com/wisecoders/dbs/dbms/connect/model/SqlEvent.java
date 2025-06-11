package com.wisecoders.dbs.dbms.connect.model;

import com.wisecoders.dbs.diagram.model.StructureEvent;
import java.text.SimpleDateFormat;

public class SqlEvent implements StructureEvent {
  private static int f = 0;
  
  private SqlEventStatus g = SqlEventStatus.a;
  
  public final int a;
  
  public final String b;
  
  public final String c;
  
  public final long d = System.currentTimeMillis();
  
  public long e = System.currentTimeMillis();
  
  private String h;
  
  public SqlEvent(String paramString1, String paramString2) {
    if (paramString1 == null)
      throw new NullPointerException("Tried to create statement from NULL SQL"); 
    this.a = f++;
    this.b = paramString1;
    this.c = paramString2;
  }
  
  public SqlEvent a(SqlEventStatus paramSqlEventStatus, String paramString) {
    this.g = paramSqlEventStatus;
    this.h = paramString;
    this.e = System.currentTimeMillis();
    return this;
  }
  
  public SqlEventStatus a() {
    return this.g;
  }
  
  public String b() {
    return this.h;
  }
  
  public String toString() {
    return this.b.replaceAll("\\s\\s+", " ").replaceAll("\n", " ");
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject == null || getClass() != paramObject.getClass())
      return false; 
    SqlEvent sqlEvent = (SqlEvent)paramObject;
    return (this.a == sqlEvent.a);
  }
  
  private static final SimpleDateFormat i = new SimpleDateFormat("HH:mm:ss");
  
  public String c() {
    return i.format(Long.valueOf(this.d)) + " " + i.format(Long.valueOf(this.d));
  }
  
  public int hashCode() {
    return this.a;
  }
}
