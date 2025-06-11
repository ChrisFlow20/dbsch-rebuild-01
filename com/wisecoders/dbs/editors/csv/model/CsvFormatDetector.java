package com.wisecoders.dbs.editors.csv.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class CsvFormatDetector {
  private int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0, h = 0;
  
  private int i;
  
  public void a(File paramFile, Charset paramCharset) {
    FileInputStream fileInputStream = new FileInputStream(paramFile);
    try {
      InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, paramCharset);
      try {
        byte b = 0;
        int i;
        while ((i = inputStreamReader.read()) != -1 && b++ < ' ') {
          char c = (char)i;
          switch (c) {
            case ',':
              this.a++;
            case '|':
              this.b++;
            case ';':
              this.c++;
            case '"':
              this.e++;
            case '\'':
              this.d++;
            case '\r':
              this.f++;
            case '\n':
              this.g++;
            case '\t':
              this.h++;
            case '\\':
              this.i++;
          } 
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
      fileInputStream.close();
    } catch (Throwable throwable) {
      try {
        fileInputStream.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
  }
  
  public String a() {
    if (this.f > 0 && this.g > 0)
      return "\r\n"; 
    return "\n";
  }
  
  public String b() {
    if (this.c > this.a)
      return ";"; 
    if (this.b > this.a)
      return "|"; 
    if (this.h > this.a)
      return "\t"; 
    return ",";
  }
  
  public char c() {
    return (this.e > this.d) ? '"' : '\'';
  }
  
  public String d() {
    return (this.i > 0) ? "\\" : "";
  }
}
