package com.wisecoders.dbs.generator.engine.generators;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GeneratorTable;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ValuesFromFileGenerator extends Generator {
  public static final String a = "list:";
  
  private final List b = new ArrayList();
  
  private String c;
  
  public ValuesFromFileGenerator(String paramString, int paramInt1, int paramInt2) {
    super(paramInt1, paramInt2);
    parseProperties(paramString);
    if (StringUtil.isEmptyTrim(this.c))
      throw new ParseException("Missing file property", 0); 
    try {
      InputStream inputStream = b(this.c);
      try {
        if (inputStream == null)
          throw new ParseException("File not found : " + paramString, "list:file=".length()); 
        a(inputStream);
        if (paramInt2 != -1)
          this.f.setSeed(paramInt2); 
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
    } catch (IOException iOException) {
      throw new ParseException(iOException.toString(), "list:file=".length());
    } 
  }
  
  public void addProperty(String paramString1, String paramString2, int paramInt) {
    if ("file".equalsIgnoreCase(paramString1)) {
      this.c = paramString2;
      if (!a(this.c))
        throw new ParseException("File '" + this.c + "' cannot be found.", paramInt + "file=".length()); 
    } else {
      throw new ParseException("Unknown property " + paramString1, paramInt);
    } 
  }
  
  private boolean a(String paramString) {
    return ((new File(paramString)).exists() || (new File(
        Sys.d().toFile(), paramString)).exists() || ValuesFromFileGenerator.class
      .getResourceAsStream("/generator/" + paramString) != null);
  }
  
  private InputStream b(String paramString) {
    File file = Paths.get(paramString, new String[0]).toFile();
    if (file.exists())
      return new FileInputStream(file); 
    file = new File(Sys.d().toFile(), paramString);
    if (file.exists())
      return new FileInputStream(file); 
    return ValuesFromFileGenerator.class.getResourceAsStream("/generator/" + paramString);
  }
  
  private void a(InputStream paramInputStream) {
    InputStreamReader inputStreamReader = new InputStreamReader(paramInputStream);
    try {
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      try {
        String str;
        while ((str = bufferedReader.readLine()) != null) {
          if (!str.isEmpty())
            this.b.add(str); 
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
  
  public Object generate(GeneratorTable paramGeneratorTable, Column paramColumn) {
    if (this.b.isEmpty())
      return ""; 
    int i = this.f.nextInt(this.b.size());
    return this.b.get(i);
  }
  
  public String toString() {
    return "Values from file '" + this.c + "' generator";
  }
}
