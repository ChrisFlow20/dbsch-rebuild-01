package com.wisecoders.dbs.query.model.engine;

import com.wisecoders.dbs.sys.Log;
import java.util.LinkedHashMap;
import java.util.Map;

public enum Aggregate {
  a("sum(?)"),
  b("avg(?)"),
  c("count(*)"),
  d("count(distinct ?)"),
  e("min(?)"),
  f("max(?)");
  
  private final Map h;
  
  public final String g;
  
  Aggregate(String paramString1) {
    this.h = new LinkedHashMap<>();
    this.g = paramString1;
  }
  
  static {
    a.a("Any", "sum(?)");
    b.a("Any", "avg(?)");
    c.a("Any", "count(*)");
    d.a("Any", "count( distinct ?)");
    e.a("Any", "min(?)");
    f.a("Any", "max(?)");
    a.a("MongoDb", "total_:{$sum:'$?'}");
    c.a("MongoDb", "total_:{$sum:1}");
    e.a("MongoDb", "min_:{$min:'$?'}");
    f.a("MongoDb", "max_:{$max:'$?'}");
    b.a("MongoDb", "avg_:{$avg:'$?'}");
  }
  
  public void a(String paramString1, String paramString2) {
    this.h.put(paramString1, paramString2);
  }
  
  public String a(String paramString) {
    return this.h.containsKey(paramString) ? (String)this.h.get(paramString) : (String)this.h.get("Any");
  }
  
  public static Aggregate b(String paramString) {
    try {
      return valueOf(paramString);
    } catch (IllegalArgumentException illegalArgumentException) {
      Log.b(illegalArgumentException);
      if (paramString == null)
        return a; 
      if (paramString.startsWith("sum"))
        return a; 
      if (paramString.startsWith("avg"))
        return b; 
      if (paramString.startsWith("min"))
        return e; 
      if (paramString.startsWith("max"))
        return f; 
      if (paramString.startsWith("count"))
        return c; 
      return c;
    } 
  }
}
