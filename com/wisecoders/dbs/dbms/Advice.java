package com.wisecoders.dbs.dbms;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Advice {
  private Pattern a;
  
  private String b;
  
  private boolean c = false;
  
  private boolean d = false;
  
  private boolean e = false;
  
  private boolean f = false;
  
  private boolean g = false;
  
  private String h;
  
  public void a(String paramString) {
    this.b = paramString;
  }
  
  public String a() {
    if (this.b != null)
      return this.b.replace("JAVA_VERSION", System.getProperty("java.version")); 
    return this.b;
  }
  
  public void b(String paramString) {
    if (paramString != null)
      try {
        this.a = Pattern.compile(paramString, 34);
      } catch (PatternSyntaxException patternSyntaxException) {
        Log.a("Error loading advice pattern " + paramString, patternSyntaxException);
      }  
  }
  
  public void b() {
    this.c = true;
  }
  
  public void c() {
    this.d = true;
  }
  
  public void c(String paramString) {
    this.h = paramString;
  }
  
  public void d() {
    this.e = true;
  }
  
  public void e() {
    this.f = true;
  }
  
  public void f() {
    this.g = true;
  }
  
  public String a(Connector paramConnector, String paramString) {
    String str = System.getProperty("java.version");
    if (this.e && str != null && !str.startsWith("1.4") && !str.startsWith("1.5"))
      return null; 
    if (this.c && paramConnector != null && paramConnector.isLocalhost())
      return null; 
    if (this.d && paramConnector != null && !paramConnector.isLocalhost())
      return null; 
    if (this.h != null && paramConnector != null && this.h.equalsIgnoreCase(paramConnector.getUserName()))
      return null; 
    if (this.f && !Sys.i())
      return null; 
    if (this.g && !Sys.k())
      return null; 
    return (this.a != null && this.b != null && StringUtil.isFilledTrim(paramString) && this.a.matcher(paramString).find()) ? this.b : null;
  }
  
  public String toString() {
    return this.b;
  }
}
