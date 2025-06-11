package com.wisecoders.dbs.editors.md;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
  private static final String a = "<([^>]*)>";
  
  public static List a(String paramString) {
    ArrayList<String> arrayList = new ArrayList();
    try {
      BufferedReader bufferedReader = new BufferedReader(new StringReader(paramString));
      try {
        String str = bufferedReader.readLine();
        while (str != null) {
          arrayList.add(str);
          str = bufferedReader.readLine();
        } 
        bufferedReader.close();
      } catch (Throwable throwable) {
        try {
          bufferedReader.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
    return arrayList;
  }
  
  public static String b(String paramString) {
    Pattern pattern = Pattern.compile("<([^>]*)>");
    Matcher matcher = pattern.matcher(paramString);
    StringBuffer stringBuffer = new StringBuffer();
    boolean bool = matcher.find();
    while (bool) {
      matcher.appendReplacement(stringBuffer, "");
      bool = matcher.find();
    } 
    matcher.appendTail(stringBuffer);
    return stringBuffer.toString();
  }
  
  public static String c(String paramString) {
    for (Map.Entry entry : MDToken.t.entrySet()) {
      String str = ((String)entry.getKey()).substring(1);
      paramString = paramString.replace((CharSequence)entry.getValue(), str);
    } 
    return paramString;
  }
  
  public static String d(String paramString) {
    for (Map.Entry entry : MDToken.t.entrySet())
      paramString = paramString.replace((CharSequence)entry.getKey(), (CharSequence)entry.getValue()); 
    return paramString;
  }
  
  public static void main(String[] paramArrayOfString) {
    String str = "1. 123\n2. 123123\n\t3. 12312\n\t4. 123123\n\t5. 12312\n1. 13123";
    System.out.println(str.indexOf("\t"));
    System.out.println(str.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;"));
  }
}
