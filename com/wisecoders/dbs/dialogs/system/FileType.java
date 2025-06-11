package com.wisecoders.dbs.dialogs.system;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public enum FileType {
  a("DbSchema Model", new String[] { "dbs" }),
  b("DbSchema Model Backup", new String[] { "dbs.bak" }),
  c("HTML File", new String[] { "html", "htm" }),
  d("War File", new String[] { "war" }),
  e("Properties File", new String[] { "properties" }),
  f("SVG Vector File", new String[] { "svg" }),
  g("Comma Separated File", new String[] { "csv" }),
  h("Certificate", new String[] { "crt", "cer", "der", "pem", "*" }),
  i("Text File", new String[] { "txt" }),
  j("XML File", new String[] { "xml" }),
  k("PDF File", new String[] { "pdf" }),
  l("Markdown", new String[] { "md" }),
  m("MP3", new String[] { "mp3" }),
  n("Excel File 2003", new String[] { "xls" }),
  o("Excel File", new String[] { "xlsx" }),
  p("JSon File", new String[] { "json" }),
  q("XTHML File", new String[] { "xhtml" }),
  r("SQL file", new String[] { "sql", "txt" }),
  s("Groovy file", new String[] { "groovy" }),
  t("Executable file", new String[] { "exe" }),
  u("JDBC Driver", new String[] { "jar" }),
  v("Key File", new String[] { "*" }),
  w("Database File", new String[] { "*" }),
  x("Image File", new String[] { "png", "gif", "jpg" }),
  y("GIF Image", new String[] { "gif" }),
  z("PNG Image", new String[] { "png" }),
  A("JPG Image", new String[] { "jpg", "jpeg" }),
  B("Sqlite Database File", new String[] { "sqlite", "db" }),
  C("Firebird Database File", new String[] { "fdb" }),
  D("All", new String[] { "*" });
  
  public final String E;
  
  public final String[] F;
  
  public List a() {
    ArrayList<String> arrayList = new ArrayList();
    for (String str : this.F)
      arrayList.add("*." + str); 
    return arrayList;
  }
  
  FileType(String paramString1, String[] paramArrayOfString) {
    this.E = paramString1;
    this.F = paramArrayOfString;
  }
  
  public static String a(File paramFile) {
    String str1 = null;
    String str2 = paramFile.getName();
    int i = str2.lastIndexOf('.');
    if (i > 0 && i < str2.length() - 1)
      str1 = str2.substring(i + 1).toLowerCase(); 
    return str1;
  }
  
  public static boolean b(File paramFile) {
    String str = paramFile.getName();
    return (str.lastIndexOf('.') > -1);
  }
  
  public static FileType a(String paramString) {
    if (paramString != null) {
      if (paramString.endsWith("xlsx"))
        return o; 
      if (paramString.endsWith("xls"))
        return n; 
      if (paramString.endsWith("xml"))
        return j; 
      if (paramString.endsWith("json"))
        return p; 
    } 
    return g;
  }
}
