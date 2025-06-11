package com.wisecoders.dbs.sys.fx.glyph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BadIconFinder {
  private static Map a = new HashMap<>();
  
  public BadIconFinder(File paramFile) {
    for (File file : paramFile.listFiles()) {
      if (file.isDirectory()) {
        new BadIconFinder(file);
      } else if (file.getName().endsWith(".properties")) {
        try {
          FileInputStream fileInputStream = new FileInputStream(file);
          try {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            for (String str : properties.stringPropertyNames()) {
              if (str.endsWith(".glyph")) {
                String str1 = properties.getProperty(str);
                if (str1 != null) {
                  if (str1.contains(":"))
                    str1 = str1.substring(0, str1.indexOf(":")); 
                  if (str1.startsWith("bi-")) {
                    str1 = str1.substring("bi-".length());
                    try {
                      BootstrapIcons.valueOf(str1);
                    } catch (IllegalArgumentException illegalArgumentException) {
                      a.put("bi-" + str1, str1);
                    } 
                  } 
                } 
              } 
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
        } catch (IOException iOException) {
          iOException.printStackTrace();
        } 
      } 
    } 
  }
  
  public static void main(String[] paramArrayOfString) {
    new BadIconFinder(new File("C:/work/dbs/dbs-app/src/main/resources"));
    for (String str : a.keySet())
      System.out.println(str); 
  }
}
