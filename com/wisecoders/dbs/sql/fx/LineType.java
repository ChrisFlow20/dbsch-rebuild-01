package com.wisecoders.dbs.sql.fx;

import java.util.regex.Pattern;

public enum LineType {
  a, b, c, d, e, f;
  
  private static final Pattern g;
  
  public boolean a() {
    return (this == d || this == b);
  }
  
  public boolean b() {
    return (this == b);
  }
  
  public boolean c() {
    return (this == c);
  }
  
  public boolean d() {
    return (this == d || this == f || this == e);
  }
  
  public boolean e() {
    return (this == a || this == f);
  }
  
  public boolean f() {
    return (this == a);
  }
  
  static {
    g = Pattern.compile(".*;[\\s]*//[^\"']*", 2);
  }
  
  public static LineType a(String paramString) {
    if (paramString != null) {
      String str = paramString.trim().toLowerCase();
      if (str.isEmpty())
        return a; 
      if (str.startsWith("rem") || str.startsWith("--") || str.startsWith("//") || str.startsWith("#"))
        return f; 
      if (g.matcher(str).matches())
        return b; 
      if (str.equalsIgnoreCase("/") || str.equalsIgnoreCase("go") || str.equalsIgnoreCase("$$;"))
        return c; 
      if (str.endsWith(";") || str.endsWith("*/"))
        return b; 
      if (str.startsWith("create") || str.startsWith("alter") || str.startsWith("drop") || str.startsWith("truncate"))
        return e; 
      return d;
    } 
    return a;
  }
}
