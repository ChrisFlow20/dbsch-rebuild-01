package com.wisecoders.dbs.sys;

import java.io.File;
import java.util.Comparator;

public class FileAlphanumericComparator implements Comparator {
  private final boolean a;
  
  public FileAlphanumericComparator(boolean paramBoolean) {
    this.a = paramBoolean;
  }
  
  public int a(File paramFile1, File paramFile2) {
    return b(paramFile1, paramFile2) * (this.a ? 1 : -1);
  }
  
  public static int b(File paramFile1, File paramFile2) {
    String str1 = paramFile1.getName();
    String str2 = paramFile2.getName();
    int i = 0;
    int j = 0;
    int k = str1.length();
    int m = str2.length();
    while (i < k && j < m) {
      String str3 = a(str1, k, i);
      i += str3.length();
      String str4 = a(str2, m, j);
      j += str4.length();
      int n = 0;
      if (a(str3.charAt(0)) && a(str4.charAt(0))) {
        int i1 = str3.length();
        n = i1 - str4.length();
        if (n == 0)
          for (byte b = 0; b < i1; b++) {
            n = str3.charAt(b) - str4.charAt(b);
            if (n != 0)
              return n; 
          }  
      } else {
        n = str3.compareTo(str4);
      } 
      if (n != 0)
        return n; 
    } 
    return k - m;
  }
  
  private static boolean a(char paramChar) {
    return (paramChar >= '0' && paramChar <= '9');
  }
  
  private static String a(String paramString, int paramInt1, int paramInt2) {
    StringBuilder stringBuilder = new StringBuilder();
    char c = paramString.charAt(paramInt2);
    stringBuilder.append(c);
    paramInt2++;
    if (a(c)) {
      while (paramInt2 < paramInt1) {
        c = paramString.charAt(paramInt2);
        if (!a(c))
          break; 
        stringBuilder.append(c);
        paramInt2++;
      } 
    } else {
      while (paramInt2 < paramInt1) {
        c = paramString.charAt(paramInt2);
        if (a(c))
          break; 
        stringBuilder.append(c);
        paramInt2++;
      } 
    } 
    return stringBuilder.toString();
  }
}
