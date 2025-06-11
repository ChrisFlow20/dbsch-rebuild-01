package com.wisecoders.dbs.dbms.driver.model;

import java.io.File;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class DriverJar {
  public final String a;
  
  public final File b;
  
  public final List c = new ArrayList();
  
  private boolean e = false;
  
  private boolean f = false;
  
  public final ClassLoader d;
  
  private boolean g = false;
  
  DriverJar(String paramString, File paramFile, ClassLoader paramClassLoader) {
    this.a = paramString;
    this.b = paramFile;
    this.d = paramClassLoader;
  }
  
  void a(String paramString, int paramInt1, int paramInt2, Driver paramDriver) {
    DriverJarClass driverJarClass = new DriverJarClass(paramString, this, paramInt1, paramInt2, paramDriver);
    this.c.add(driverJarClass);
  }
  
  public List a() {
    return this.c;
  }
  
  public boolean b() {
    return this.e;
  }
  
  public void a(boolean paramBoolean) {
    this.e = paramBoolean;
  }
  
  public boolean c() {
    return this.f;
  }
  
  public void b(boolean paramBoolean) {
    this.f = paramBoolean;
  }
  
  public boolean d() {
    return this.g;
  }
  
  public void c(boolean paramBoolean) {
    this.g = paramBoolean;
  }
  
  public String toString() {
    return this.b.getName();
  }
  
  public String e() {
    return this.b.getName();
  }
  
  public boolean a(JdbcUrlTemplate paramJdbcUrlTemplate) {
    for (DriverJarClass driverJarClass : this.c) {
      if (paramJdbcUrlTemplate.e(driverJarClass.a()))
        return true; 
    } 
    return false;
  }
  
  public List f() {
    ArrayList<String> arrayList = new ArrayList();
    ZipFile zipFile = new ZipFile(this.b);
    for (Enumeration<? extends ZipEntry> enumeration = zipFile.entries(); enumeration.hasMoreElements(); ) {
      ZipEntry zipEntry = enumeration.nextElement();
      if (!zipEntry.isDirectory()) {
        String str = zipEntry.getName();
        str = str.replace('/', '.').replace('\\', '.');
        if (str.endsWith(".class")) {
          String str1 = str.substring(0, str.length() - ".class".length());
          str1 = str1.replace('/', '.').replace('\\', '.');
          arrayList.add(str1);
        } 
      } 
    } 
    zipFile.close();
    return arrayList;
  }
}
