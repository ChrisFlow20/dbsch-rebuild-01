package com.wisecoders.dbs.dbms.driver.model;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.driver.store.JdbcUrlTemplateLoader;
import com.wisecoders.dbs.dbms.driver.store.JdbcUrlTemplateStore;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.FileAlphanumericComparator;
import com.wisecoders.dbs.sys.UniqueArrayList;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class DriverManager {
  public static final File a = Sys.a().resolve("drivers.xml").toFile();
  
  private static final HashMap c = new HashMap<>();
  
  public final String b;
  
  private String d;
  
  static {
    new JdbcUrlTemplateLoader();
  }
  
  private boolean e = true;
  
  private String f;
  
  private String g;
  
  private String h;
  
  private final List i = new CopyOnWriteArrayList();
  
  private final List j = new ArrayList();
  
  public static DriverManager a(String paramString) {
    DriverManager driverManager = (DriverManager)c.get(paramString);
    if (driverManager == null) {
      driverManager = new DriverManager(paramString);
      c.put(paramString, driverManager);
    } 
    return driverManager;
  }
  
  public static Collection a() {
    return c.values();
  }
  
  public static void b() {
    FileWriter fileWriter = new FileWriter(a);
    try {
      new JdbcUrlTemplateStore(fileWriter);
      fileWriter.close();
    } catch (Throwable throwable) {
      try {
        fileWriter.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
  }
  
  public static Set c() {
    return c.keySet();
  }
  
  public static void d() {
    c.clear();
    new JdbcUrlTemplateLoader();
  }
  
  private DriverJarClass d(String paramString1, String paramString2) {
    for (DriverJar driverJar : this.i) {
      if (driverJar.a.equals(paramString1))
        for (DriverJarClass driverJarClass : driverJar.c) {
          if (driverJarClass.a().equals(paramString2))
            return driverJarClass; 
        }  
    } 
    return null;
  }
  
  public DriverManager(String paramString) {
    this.b = paramString;
  }
  
  public JdbcUrlTemplate a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, boolean paramBoolean) {
    JdbcUrlTemplate jdbcUrlTemplate = new JdbcUrlTemplate(this.b, paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramBoolean);
    this.j.add(jdbcUrlTemplate);
    return jdbcUrlTemplate;
  }
  
  public static String a(String paramString1, String paramString2) {
    for (JdbcUrlTemplate jdbcUrlTemplate : (a(paramString1)).j) {
      if (jdbcUrlTemplate.p.equals(paramString1) && jdbcUrlTemplate.e(paramString2) && jdbcUrlTemplate.j())
        return jdbcUrlTemplate.i(); 
    } 
    return null;
  }
  
  public static boolean b(String paramString1, String paramString2) {
    return (a(paramString1, paramString2) != null);
  }
  
  DriverJar a(File paramFile, ClassLoader paramClassLoader) {
    DriverJar driverJar = new DriverJar(this.b, paramFile, paramClassLoader);
    this.i.add(driverJar);
    this.i.sort((paramDriverJar1, paramDriverJar2) -> -1 * FileAlphanumericComparator.b(paramDriverJar1.b, paramDriverJar2.b));
    return driverJar;
  }
  
  public List e() {
    if (this.j.isEmpty() && this.d != null)
      return (a(this.d)).j; 
    return this.j;
  }
  
  public List f() {
    return this.j;
  }
  
  public List g() {
    UniqueArrayList uniqueArrayList = new UniqueArrayList();
    for (JdbcUrlTemplate jdbcUrlTemplate : e())
      Collections.addAll(uniqueArrayList, jdbcUrlTemplate.q); 
    return uniqueArrayList;
  }
  
  public List h() {
    q();
    return this.i;
  }
  
  public List i() {
    q();
    ArrayList arrayList = new ArrayList();
    for (DriverJar driverJar : this.i)
      arrayList.addAll(driverJar.a()); 
    return arrayList;
  }
  
  public List j() {
    q();
    ArrayList<DriverJarClass> arrayList = new ArrayList();
    for (DriverJar driverJar : this.i) {
      for (DriverJarClass driverJarClass : driverJar.c) {
        if (c(driverJarClass.a(), null) != null)
          arrayList.add(driverJarClass); 
      } 
    } 
    return arrayList;
  }
  
  public DriverJarClass a(Connector paramConnector) {
    q();
    DriverJarClass driverJarClass = null;
    byte b = 0;
    for (DriverJarClass driverJarClass1 : i()) {
      if (driverJarClass1.a().equalsIgnoreCase(paramConnector.getDriverJarClassName())) {
        if (paramConnector.getDriverJarFileName() == null || paramConnector.getDriverJarFileName().equals((driverJarClass1.b()).b.getName()))
          return driverJarClass1; 
        driverJarClass = driverJarClass1;
        b++;
      } 
    } 
    return (b == 1) ? driverJarClass : null;
  }
  
  public JdbcUrlTemplate c(String paramString1, String paramString2) {
    if (paramString2 != null)
      for (JdbcUrlTemplate jdbcUrlTemplate : e()) {
        for (String str : jdbcUrlTemplate.q) {
          if (str.equalsIgnoreCase(paramString1) && 
            paramString2.equals(jdbcUrlTemplate.h()))
            return jdbcUrlTemplate; 
        } 
      }  
    return null;
  }
  
  private void q() {
    if (this.i.isEmpty())
      k(); 
  }
  
  public void k() {
    r();
    new a(this);
  }
  
  private void r() {
    for (DriverJar driverJar : this.i) {
      driverJar.b(false);
      driverJar.a(false);
    } 
    this.i.clear();
  }
  
  public DriverJar a(File paramFile) {
    for (DriverJar driverJar : this.i) {
      if (paramFile.equals(driverJar.b))
        return driverJar; 
    } 
    return null;
  }
  
  public void b(String paramString) {
    this.f = paramString;
  }
  
  public void c(String paramString) {
    this.g = paramString;
  }
  
  public void d(String paramString) {
    this.h = paramString;
  }
  
  public String l() {
    return this.f;
  }
  
  public String m() {
    return this.g;
  }
  
  public String n() {
    return this.h;
  }
  
  public String o() {
    return this.d;
  }
  
  public void e(String paramString) {
    this.d = paramString;
  }
  
  public void a(boolean paramBoolean) {
    this.e = paramBoolean;
  }
  
  public boolean p() {
    return this.e;
  }
  
  public boolean a(DriverJarClass paramDriverJarClass) {
    for (JdbcUrlTemplate jdbcUrlTemplate : this.j) {
      if (jdbcUrlTemplate.t())
        for (String str : jdbcUrlTemplate.q) {
          if (paramDriverJarClass.a().equals(str))
            return true; 
        }  
    } 
    return false;
  }
}
