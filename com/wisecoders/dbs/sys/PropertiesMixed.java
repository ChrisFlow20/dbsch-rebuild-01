package com.wisecoders.dbs.sys;

import java.util.Properties;
import java.util.regex.Matcher;

public class PropertiesMixed extends Properties {
  private final Properties a;
  
  public PropertiesMixed(Properties paramProperties) {
    this.a = paramProperties;
  }
  
  public String getProperty(String paramString) {
    String str = super.getProperty(paramString);
    if (str != null && this.a != null)
      for (String str1 : this.a.stringPropertyNames()) {
        String str2 = this.a.getProperty(str1);
        str = str.replaceAll("\\$" + str1, Matcher.quoteReplacement(str2));
        str = str.replaceAll("\\$\\{" + str1 + "\\}", Matcher.quoteReplacement(str2));
      }  
    return str;
  }
}
