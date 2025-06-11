package com.wisecoders.dbs.sys;

public class StringAsRegEx {
  private final StringBuilder a = new StringBuilder();
  
  private static final String b = "\\s*";
  
  public StringAsRegEx(String paramString) {
    a("\\s*");
    for (char c : paramString.toCharArray()) {
      switch (c) {
        case ' ':
          a("\\s*");
          break;
        case ',':
        case ':':
        case '=':
        case '[':
        case ']':
        case '{':
        case '}':
          a("\\s*").a(EscapeChars.forRegex("" + c)).a("\\s*");
          break;
        default:
          a(EscapeChars.forRegex("" + c));
          break;
      } 
    } 
    a("\\s*");
  }
  
  private StringAsRegEx a(String paramString) {
    if (!"\\s*".equalsIgnoreCase(paramString) || !this.a.toString().endsWith("\\s*"))
      this.a.append(paramString); 
    return this;
  }
  
  public String toString() {
    return this.a.toString();
  }
}
