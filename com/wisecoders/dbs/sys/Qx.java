package com.wisecoders.dbs.sys;

import java.io.InputStream;
import java.util.Properties;

public class Qx extends Properties {
  public Qx(Class paramClass) {
    try {
      a(paramClass, "resources/" + paramClass.getSimpleName() + ".sql");
      a(paramClass, "resources/" + paramClass.getSimpleName() + "-2.sql");
    } catch (Throwable throwable) {
      Log.b(throwable);
    } 
  }
  
  private void a(Class paramClass, String paramString) {
    InputStream inputStream = paramClass.getResourceAsStream(paramString);
    if (inputStream != null) {
      String str1 = StringUtil.readStringFromInputStream(inputStream);
      StringBuilder stringBuilder = new StringBuilder();
      String str2 = null;
      for (String str : str1.split("\r?\n")) {
        if (str.startsWith(":::")) {
          a(str2, stringBuilder);
          str2 = str.substring(":::".length()).trim();
        } else if (str2 != null) {
          stringBuilder.append(str).append("\n");
        } 
      } 
      a(str2, stringBuilder);
    } 
  }
  
  private void a(String paramString, StringBuilder paramStringBuilder) {
    if (paramString != null) {
      put(paramString, paramStringBuilder.toString());
      paramStringBuilder.delete(0, paramStringBuilder.length() - 1);
    } 
  }
  
  public String a(String paramString) {
    return getProperty(paramString);
  }
}
