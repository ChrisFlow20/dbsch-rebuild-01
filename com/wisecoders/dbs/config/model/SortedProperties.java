package com.wisecoders.dbs.config.model;

import com.wisecoders.dbs.diagram.util.compatibility.ArrayEnumerator;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;

public class SortedProperties extends Properties {
  public synchronized Enumeration keys() {
    String[] arrayOfString = keySet().<String>toArray(new String[0]);
    Arrays.sort(arrayOfString, (paramString1, paramString2) -> {
          String str1 = String.valueOf(paramString1).replaceAll("text", "__text").replaceAll("tooltip", "_tooltip");
          String str2 = String.valueOf(paramString2).replaceAll("text", "__text").replaceAll("tooltip", "_tooltip");
          return str1.compareToIgnoreCase(str2);
        });
    return new ArrayEnumerator((Object[])arrayOfString);
  }
}
