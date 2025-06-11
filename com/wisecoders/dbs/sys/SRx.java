package com.wisecoders.dbs.sys;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.project.store.SimpleEncrypt;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;

public class SRx {
  protected final Class f;
  
  public final Object g;
  
  protected final Map h = new HashMap<>();
  
  private String a = "";
  
  private final Properties b = new Properties();
  
  public static final String i = "separator";
  
  public SRx(Class paramClass, Object paramObject) {
    this.f = paramClass;
    this.g = paramObject;
    try {
      InputStream inputStream = paramClass.getResourceAsStream("resources/" + paramClass.getSimpleName() + ".properties");
      try {
        a(this.b, inputStream);
        if (Sys.l() != null) {
          InputStream inputStream1 = paramClass.getResourceAsStream("resources/" + paramClass.getSimpleName() + "_" + Sys.l() + ".properties");
          try {
            if (inputStream1 != null)
              a(this.b, inputStream1); 
            if (inputStream1 != null)
              inputStream1.close(); 
          } catch (Throwable throwable) {
            if (inputStream1 != null)
              try {
                inputStream1.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              }  
            throw throwable;
          } 
        } 
        if (inputStream != null)
          inputStream.close(); 
      } catch (Throwable throwable) {
        if (inputStream != null)
          try {
            inputStream.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          }  
        throw throwable;
      } 
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    } 
  }
  
  public void G(String paramString) {
    if (paramString != null)
      this.a = paramString; 
  }
  
  public Properties i() {
    return this.b;
  }
  
  public String H(String paramString) {
    return a(paramString + ".text", new String[0]);
  }
  
  public String a(String paramString, String... paramVarArgs) {
    String str = null;
    if (this.a != null)
      str = this.b.getProperty(paramString + "." + paramString); 
    if (str == null)
      str = this.b.getProperty(paramString); 
    if (str != null) {
      str = StringUtil.removeCtxKeyword(str);
      byte b = 1;
      for (String str1 : paramVarArgs) {
        str = str.replaceAll(":" + b, Matcher.quoteReplacement(str1));
        b++;
      } 
    } 
    return str;
  }
  
  public String b(String paramString, String... paramVarArgs) {
    String str = a(paramString, paramVarArgs);
    return (str == null) ? paramString : str;
  }
  
  public static void a(Properties paramProperties, InputStream paramInputStream) {
    if (paramInputStream != null) {
      InputStreamReader inputStreamReader = new InputStreamReader(paramInputStream, StandardCharsets.UTF_8);
      try {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
          String str;
          while ((str = bufferedReader.readLine()) != null) {
            if (!stringBuilder.isEmpty())
              stringBuilder.append("\n"); 
            stringBuilder.append(SimpleEncrypt.b(str));
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
        StringReader stringReader = new StringReader(stringBuilder.toString());
        try {
          paramProperties.load(stringReader);
          stringReader.close();
        } catch (Throwable throwable) {
          try {
            stringReader.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          } 
          throw throwable;
        } 
        inputStreamReader.close();
      } catch (Throwable throwable) {
        try {
          inputStreamReader.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } 
  }
}
