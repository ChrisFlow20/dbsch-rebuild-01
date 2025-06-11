package com.wisecoders.dbs.cli.command.groovy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroovySys {
  public String a(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    try {
      Process process = Runtime.getRuntime().exec(paramString);
      process.waitFor();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String str = bufferedReader.readLine();
      while (str != null) {
        if (stringBuilder.length() > 0)
          stringBuilder.append("\n"); 
        stringBuilder.append(str);
        str = bufferedReader.readLine();
      } 
    } catch (Exception exception) {
      stringBuilder.append(exception);
    } 
    return stringBuilder.toString();
  }
  
  private static final Pattern a = Pattern.compile(".*name = (\\S*)\\s.*", 42);
  
  public String a(Object paramObject) {
    if (paramObject != null) {
      String str = a("nslookup " + String.valueOf(paramObject));
      if (str != null && str.length() > 0) {
        Matcher matcher = a.matcher(str);
        if (matcher.matches())
          return matcher.group(1); 
      } 
      return str;
    } 
    return null;
  }
}
