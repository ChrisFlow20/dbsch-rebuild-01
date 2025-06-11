package com.wisecoders.dbs.editors.md;

import com.wisecoders.dbs.editors.md.model.Analyzer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MDTool {
  private static final String a = "UTF-8";
  
  public static String a(File paramFile) {
    return a(paramFile, "UTF-8");
  }
  
  public static String a(File paramFile, String paramString) {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(paramFile), paramString));
    try {
      StringBuffer stringBuffer = new StringBuffer();
      String str1;
      while ((str1 = bufferedReader.readLine()) != null)
        stringBuffer.append(str1).append("\n"); 
      String str2 = a(stringBuffer.toString());
      bufferedReader.close();
      return str2;
    } catch (Throwable throwable) {
      try {
        bufferedReader.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
  }
  
  public static String a(String paramString) {
    if (paramString == null)
      return null; 
    List list = Analyzer.a(paramString);
    HTMLDecorator hTMLDecorator = new HTMLDecorator();
    hTMLDecorator.a(list);
    return hTMLDecorator.a();
  }
  
  public static void main(String[] paramArrayOfString) {
    System.out.println(a("1. 123\n2. 123123\n\t[ ] 12312\n\t[x] 123123\n\t[ ] 12312\n1. 13123"));
  }
}
