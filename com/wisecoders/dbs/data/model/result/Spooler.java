package com.wisecoders.dbs.data.model.result;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wisecoders.dbs.sys.EscapeChars;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Spooler {
  public final Spooler$Format a;
  
  private final File b;
  
  private HashMap c;
  
  private Gson d;
  
  private Sheet e;
  
  private Row f;
  
  private PrintWriter g;
  
  private int h = 0;
  
  public Spooler(Spooler$Format paramSpooler$Format, File paramFile) {
    this.a = paramSpooler$Format;
    this.b = paramFile;
    switch (Spooler$1.a[paramSpooler$Format.ordinal()]) {
      case 1:
        this.e = (Sheet)(new XSSFWorkbook()).createSheet("Result");
        return;
      case 2:
        this.e = (Sheet)(new HSSFWorkbook()).createSheet("Result");
        return;
    } 
    this.g = new PrintWriter(paramFile, StandardCharsets.UTF_8);
  }
  
  public Spooler(Spooler$Format paramSpooler$Format, PrintWriter paramPrintWriter) {
    this.a = paramSpooler$Format;
    this.g = paramPrintWriter;
    this.b = null;
  }
  
  public void a(String paramString) {
    switch (Spooler$1.a[this.a.ordinal()]) {
      case 1:
      case 2:
        this.f.createCell(this.h).setCellValue(paramString);
        break;
      case 3:
      case 4:
      case 5:
      case 6:
        if (this.h > 0)
          this.g.write(this.a.j); 
        this.g.write(c(paramString));
        break;
      case 7:
        this.g.write(this.a.j);
        this.g.write(c(paramString));
        break;
    } 
    this.h++;
  }
  
  public void b(String paramString) {
    if (this.a == Spooler$Format.f) {
      if (paramString == null)
        paramString = ""; 
      this.g.write(this.a.j);
      for (byte b = 1; b < paramString.length() + 1; b++)
        this.g.write("-"); 
    } 
    this.h++;
  }
  
  public void a(String paramString, Object paramObject) {
    switch (Spooler$1.a[this.a.ordinal()]) {
      case 1:
      case 2:
        if (paramObject instanceof Number) {
          this.f.createCell(this.h).setCellValue(((Number)paramObject).doubleValue());
          break;
        } 
        if (paramObject instanceof Date) {
          this.f.createCell(this.h).setCellValue((Date)paramObject);
          break;
        } 
        this.f.createCell(this.h).setCellValue(StringUtil.cutOfWithDots(String.valueOf(paramObject), 32760));
        break;
      case 8:
        this.g.println(String.format("\t<%s>%s</%s>", new Object[] { EscapeChars.forHtml(paramString), 
                EscapeChars.forHtml(String.valueOf(paramObject)), 
                EscapeChars.forHtml(paramString) }));
        break;
      case 9:
        if (paramObject != null)
          this.c.put(paramString, paramObject); 
        break;
      case 7:
        this.g.print(this.a.j);
        if (paramObject != null)
          this.g.print(c(String.valueOf(paramObject))); 
        break;
      default:
        if (this.h > 0)
          this.g.print(this.a.j); 
        if (paramObject != null)
          this.g.print(c(String.valueOf(paramObject))); 
        break;
    } 
    this.h++;
  }
  
  public void a() {
    switch (Spooler$1.a[this.a.ordinal()]) {
      case 1:
      case 2:
        this.f = this.e.createRow(this.e.getLastRowNum() + 1);
        break;
      case 8:
        this.g.println("<record>");
        break;
      case 9:
        if (this.c == null)
          this.c = new HashMap<>(); 
        this.c.clear();
        break;
      case 4:
      case 5:
      case 6:
      case 7:
        this.g.println();
        break;
    } 
    this.h = 0;
  }
  
  public void b() {
    switch (Spooler$1.a[this.a.ordinal()]) {
      case 8:
        this.g.println("</record>");
        break;
      case 9:
        if (this.d == null)
          this.d = (new GsonBuilder()).setPrettyPrinting().create(); 
        if (!this.c.isEmpty())
          this.g.println(this.d.toJson(this.c)); 
        this.g.println(",");
        this.c.clear();
        break;
      case 7:
        this.g.write(this.a.j);
        break;
    } 
    this.h = 0;
  }
  
  public void c() {
    switch (Spooler$1.a[this.a.ordinal()]) {
      case 1:
      case 2:
        if (this.e != null)
          try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.b);
            try {
              this.e.getWorkbook().write(fileOutputStream);
              this.e = null;
              fileOutputStream.close();
            } catch (Throwable throwable) {
              try {
                fileOutputStream.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              } 
              throw throwable;
            } 
          } catch (IOException iOException) {
            Log.a("Error in Spool to Excel", iOException);
          }  
        return;
    } 
    if (this.g != null) {
      this.g.flush();
      this.g.close();
      this.g = null;
    } 
  }
  
  public String c(String paramString) {
    if (paramString != null) {
      String str;
      switch (Spooler$1.a[this.a.ordinal()]) {
        case 3:
          return "\"" + paramString.replaceAll("\"", "").replaceAll("\n", "\\n") + "\"";
        case 7:
          return " " + paramString.replaceAll("\\|", "\\|").replaceAll("\n", "") + " ";
        case 4:
          str = paramString.replaceAll("\n", "\\n").replaceAll("\r", "\\r").replaceAll("\"", "\"\"");
          return paramString.contains(",") ? ("\"" + str + "\"") : str;
        case 6:
          return paramString.replaceAll("\n", "\\n").replaceAll("\r", "\\r").replaceAll("\t", "\\t");
        case 5:
          return paramString.replaceAll("\n", "\\n").replaceAll("\r", "\\r").replaceAll("\\|", "||");
      } 
      return paramString;
    } 
    return "";
  }
  
  public void a(Result paramResult) {
    a();
    for (ResultColumn resultColumn : paramResult.f)
      a(resultColumn.a); 
    b();
    if (this.a == Spooler$Format.f) {
      a();
      for (ResultColumn resultColumn : paramResult.f)
        b(resultColumn.a); 
      b();
    } 
    for (ResultRow resultRow : paramResult.g) {
      a();
      for (ResultColumn resultColumn : paramResult.f)
        a(resultColumn.a, resultRow.a(resultColumn)); 
      b();
    } 
    c();
  }
}
