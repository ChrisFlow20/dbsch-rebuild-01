package com.wisecoders.dbs.cli.csv;

public class CSVStringBuilder {
  private final char b;
  
  private final boolean c;
  
  private final StringBuilder d = new StringBuilder();
  
  boolean a = false;
  
  public CSVStringBuilder(char paramChar, boolean paramBoolean) {
    this.b = paramChar;
    this.c = paramBoolean;
  }
  
  public void a() {
    this.d.append("\n");
    this.a = false;
  }
  
  public void a(String paramString) {
    if (this.a)
      this.d.append(this.b); 
    if (paramString != null) {
      if (this.c)
        this.d.append('"'); 
      for (byte b = 0; b < paramString.length(); b++) {
        char c = paramString.charAt(b);
        if (c == '\n') {
          this.d.append("\\n");
        } else if (c == '\r') {
          this.d.append("\\r");
        } else if (c == '"') {
          this.d.append('"').append('"');
        } else {
          this.d.append(c);
        } 
      } 
      if (this.c)
        this.d.append('"'); 
    } 
    this.a = true;
  }
  
  public int b() {
    return this.d.length();
  }
  
  public String toString() {
    return this.d.toString();
  }
}
