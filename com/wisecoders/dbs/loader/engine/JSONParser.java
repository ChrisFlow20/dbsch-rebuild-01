package com.wisecoders.dbs.loader.engine;

import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class JSONParser {
  public static Object parse(String paramString) {
    return parse(paramString, null);
  }
  
  public static Object parse(String paramString, JSONCallback paramJSONCallback) {
    if (paramString == null || paramString.trim().equals(""))
      return null; 
    a a = new a(paramString, paramJSONCallback);
    return a.a();
  }
  
  static void a(StringBuilder paramStringBuilder, String paramString) {
    paramStringBuilder.append("\"");
    for (byte b = 0; b < paramString.length(); b++) {
      char c = paramString.charAt(b);
      if (c == '\\') {
        paramStringBuilder.append("\\\\");
      } else if (c == '"') {
        paramStringBuilder.append("\\\"");
      } else if (c == '\n') {
        paramStringBuilder.append("\\n");
      } else if (c == '\r') {
        paramStringBuilder.append("\\r");
      } else if (c == '\t') {
        paramStringBuilder.append("\\t");
      } else if (c == '\b') {
        paramStringBuilder.append("\\b");
      } else if (c >= ' ') {
        paramStringBuilder.append(c);
      } 
    } 
    paramStringBuilder.append("\"");
  }
}
