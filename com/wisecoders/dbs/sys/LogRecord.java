package com.wisecoders.dbs.sys;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class LogRecord {
  private static final SimpleDateFormat d = new SimpleDateFormat("HH:mm:ss");
  
  public final long a;
  
  public final String b;
  
  public final LogLevel c;
  
  LogRecord(String paramString, LogLevel paramLogLevel, long paramLong) {
    this.b = paramString.trim();
    this.c = paramLogLevel;
    this.a = paramLong;
  }
  
  public String a() {
    return d.format(new Date(this.a)) + " ";
  }
  
  public String toString() {
    return a() + " " + a();
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject == null || getClass() != paramObject.getClass())
      return false; 
    LogRecord logRecord = (LogRecord)paramObject;
    if (this.a != logRecord.a)
      return false; 
    if (this.c != logRecord.c)
      return false; 
    return Objects.equals(this.b, logRecord.b);
  }
  
  public int hashCode() {
    int i = Long.hashCode(this.a);
    i = 31 * i + ((this.b != null) ? this.b.hashCode() : 0);
    i = 31 * i + ((this.c != null) ? this.c.hashCode() : 0);
    return i;
  }
}
