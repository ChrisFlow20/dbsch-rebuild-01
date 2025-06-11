package com.wisecoders.dbs.sys;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class InputStreamUtil {
  public static String a(InputStream paramInputStream, int paramInt) {
    if (paramInputStream != null) {
      StringBuilder stringBuilder = new StringBuilder();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(paramInputStream, StandardCharsets.UTF_8));
      try {
        String str;
        while ((str = bufferedReader.readLine()) != null && stringBuilder.length() < paramInt) {
          if (!stringBuilder.isEmpty())
            stringBuilder.append("\n"); 
          stringBuilder.append(str);
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
      return stringBuilder.toString();
    } 
    return null;
  }
  
  public static String a(Reader paramReader, int paramInt) {
    if (paramReader != null) {
      StringBuilder stringBuilder = new StringBuilder();
      char c = ' ';
      BufferedReader bufferedReader = new BufferedReader(paramReader, c);
      try {
        int i = 0;
        String str;
        while ((str = bufferedReader.readLine()) != null && ((i += str.length()) < paramInt || paramInt == -1)) {
          if (!stringBuilder.isEmpty())
            stringBuilder.append("\n"); 
          stringBuilder.append(str);
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
      if (!stringBuilder.isEmpty())
        return stringBuilder.toString(); 
    } 
    return null;
  }
  
  public static boolean a(InputStream paramInputStream) {
    if (paramInputStream != null) {
      int i = paramInputStream.available();
      if (i > 1024)
        i = 1024; 
      byte[] arrayOfByte = new byte[i];
      InputStream inputStream = paramInputStream;
      try {
        i = paramInputStream.read(arrayOfByte);
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
      byte b1 = 0;
      byte b2 = 0;
      for (byte b : arrayOfByte) {
        if (b < 9)
          return true; 
        if (b == 9 || b == 10 || b == 12 || b == 13) {
          b1++;
        } else if (b >= 32 && b <= 126) {
          b1++;
        } else {
          b2++;
        } 
      } 
      if (b2 == 0)
        return false; 
      return (100 * b2 / (b1 + b2) > 95);
    } 
    return false;
  }
}
