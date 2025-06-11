package com.wisecoders.dbs.dbms.driver.model;

import com.wisecoders.dbs.config.system.NetworkProxy;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.sys.Log;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;

class a {
  private final DriverManager a;
  
  a(DriverManager paramDriverManager) {
    this.a = paramDriverManager;
    NetworkProxy.a();
    File file1 = Sys.e.resolve(paramDriverManager.b + "/").toFile();
    a(file1);
    File file2 = Sys.v.resolve(paramDriverManager.b + "/").toFile();
    a(file2);
  }
  
  private void a(File paramFile) {
    if (paramFile != null && paramFile.exists()) {
      File[] arrayOfFile = paramFile.listFiles();
      if (arrayOfFile != null) {
        ArrayList<URL> arrayList = new ArrayList();
        for (File file : arrayOfFile) {
          try {
            if (file.getName().toLowerCase().endsWith(".jar"))
              arrayList.add(file.toURI().toURL()); 
          } catch (MalformedURLException malformedURLException) {
            Log.b("Cannot get URL from " + file.getPath(), malformedURLException);
          } 
        } 
        URLClassLoader uRLClassLoader = new URLClassLoader(arrayList.<URL>toArray(new URL[0]), getClass().getClassLoader());
        ArrayList<DriverJar> arrayList1 = new ArrayList();
        for (File file : arrayOfFile) {
          if (!file.getName().startsWith(".") && file.getName().toLowerCase().endsWith(".jar"))
            arrayList1.add(this.a.a(file, uRLClassLoader)); 
        } 
        boolean bool = false;
        for (DriverJar driverJar : arrayList1) {
          try {
            bool |= a(driverJar, uRLClassLoader);
          } catch (Exception exception) {
            driverJar.a(true);
            Log.f(String.format("Error loading driver %s : %s.", new Object[] { driverJar.b, exception }));
          } 
        } 
        if (!bool)
          for (DriverJar driverJar : arrayList1) {
            try {
              b(driverJar, uRLClassLoader);
            } catch (Exception exception) {
              driverJar.a(true);
              Log.f(String.format("Error loading driver %s : %s.", new Object[] { driverJar.b, exception }));
            } 
          }  
      } 
    } 
  }
  
  private boolean a(DriverJar paramDriverJar, URLClassLoader paramURLClassLoader) {
    boolean bool = false;
    List list = paramDriverJar.f();
    for (String str : this.a.g()) {
      if (list.contains(str))
        bool |= a(paramDriverJar, str, paramURLClassLoader); 
    } 
    return bool;
  }
  
  private void b(DriverJar paramDriverJar, URLClassLoader paramURLClassLoader) {
    boolean bool = false;
    List list = paramDriverJar.f();
    for (String str : list) {
      if (str.toLowerCase().contains("driver"))
        bool |= a(paramDriverJar, str, paramURLClassLoader); 
    } 
    if (!bool)
      for (String str : list) {
        if (!str.toLowerCase().contains("driver"))
          a(paramDriverJar, str, paramURLClassLoader); 
      }  
  }
  
  private boolean a(DriverJar paramDriverJar, String paramString, URLClassLoader paramURLClassLoader) {
    Class<?> clazz = null;
    while (clazz == null && paramString != null && paramString.length() > 0) {
      try {
        clazz = paramURLClassLoader.loadClass(paramString);
      } catch (Throwable throwable) {
        Log.h();
      } 
      if (clazz == null) {
        int i;
        if ((i = paramString.indexOf('.')) > -1) {
          paramString = paramString.substring(i + 1);
          continue;
        } 
        paramString = null;
      } 
    } 
    if (clazz != null && Driver.class.isAssignableFrom(clazz))
      try {
        Driver driver = clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        paramDriverJar.a(paramString, driver.getMinorVersion(), driver.getMajorVersion(), driver);
        return true;
      } catch (Throwable throwable) {
        Log.b("DRIVER LOADER: cannot instantiate " + paramString + " .", throwable);
      }  
    return false;
  }
}
