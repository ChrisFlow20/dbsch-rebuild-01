package com.wisecoders.dbs.cli.command.base;

public enum CommandType {
  a("/"),
  b(""),
  c(";");
  
  public final String d;
  
  CommandType(String paramString1) {
    this.d = paramString1;
  }
  
  public boolean a(String paramString) {
    switch (this.d) {
      case "/":
        return 
          !paramString.trim().endsWith("\n/");
      case ";":
        return !paramString.trim().endsWith(";");
    } 
    return false;
  }
  
  public String b(String paramString) {
    if (paramString.endsWith("/") || paramString.endsWith(";"))
      return paramString.substring(0, paramString.length() - 1); 
    return paramString;
  }
}
