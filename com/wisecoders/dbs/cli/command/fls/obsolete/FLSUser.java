package com.wisecoders.dbs.cli.command.fls.obsolete;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class FLSUser {
  public final String a;
  
  public final String b;
  
  public final Map c = new HashMap<>();
  
  private long d = -1L;
  
  public FLSUser(String paramString1, String paramString2) {
    this.a = paramString1;
    this.b = paramString2;
  }
  
  public boolean a() {
    return (this.d != -1L);
  }
  
  public void b() {
    this.d = Instant.now().toEpochMilli();
    FLSUsers.c();
  }
  
  public void c() {
    if (this.d == -1L)
      return; 
    LocalDate localDate = LocalDate.now();
    int i = (int)((System.currentTimeMillis() - this.d) / 60000L);
    if (this.c.containsKey(localDate))
      i += ((Integer)this.c.get(localDate)).intValue(); 
    this.c.put(localDate, Integer.valueOf(i));
    FLSUsers.c();
  }
}
