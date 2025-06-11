package com.wisecoders.dbs.sys;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.io.filefilter.WildcardFileFilter;

public class FileUtils {
  private static final String a = "#%&{}\\<>*?/$!'\":@´ <=";
  
  public static List a(String[] paramArrayOfString) {
    ArrayList<File> arrayList = new ArrayList();
    for (String str : paramArrayOfString) {
      int i = str.lastIndexOf("/");
      if (i > 0) {
        File file = Paths.get(str.substring(0, i), new String[0]).toFile();
        if (!file.exists())
          throw new IOException("Cannot access folder " + String.valueOf(file)); 
        WildcardFileFilter wildcardFileFilter = new WildcardFileFilter(str.substring(i + 1));
        File[] arrayOfFile = file.listFiles((FileFilter)wildcardFileFilter);
        if (arrayOfFile != null)
          arrayList.addAll(Arrays.asList(arrayOfFile)); 
      } else {
        File file = new File(str);
        if (!file.exists())
          throw new IOException("Cannot find file '" + str + "'."); 
        if (file.isDirectory())
          throw new IOException("Expected file, but got directory: '" + str + "'."); 
        arrayList.add(file);
      } 
    } 
    return arrayList;
  }
  
  public static String a(File paramFile) {
    char[] arrayOfChar = new char[1024];
    StringBuilder stringBuilder = new StringBuilder();
    BufferedReader bufferedReader = new BufferedReader(new FileReader(paramFile));
    try {
      int i;
      while ((i = bufferedReader.read(arrayOfChar)) != -1)
        stringBuilder.append(arrayOfChar, 0, i); 
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
  
  public static void a(File paramFile1, File paramFile2) {
    BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(paramFile1));
    try {
      BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(paramFile2));
      try {
        byte[] arrayOfByte = new byte[1024];
        int i;
        while ((i = bufferedInputStream.read(arrayOfByte)) > 0) {
          bufferedOutputStream.write(arrayOfByte, 0, i);
          bufferedOutputStream.flush();
        } 
        bufferedOutputStream.close();
      } catch (Throwable throwable) {
        try {
          bufferedOutputStream.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
      bufferedInputStream.close();
    } catch (Throwable throwable) {
      try {
        bufferedInputStream.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
  }
  
  public static String a(String paramString) {
    if (paramString != null)
      for (byte b = 0; b < "#%&{}\\<>*?/$!'\":@´ <=".length(); b++)
        paramString = paramString.replaceAll(Pattern.quote("" + "#%&{}\\<>*?/$!'\":@´ <=".charAt(b)), "");  
    return paramString;
  }
  
  public static void a(InputStream paramInputStream, OutputStream paramOutputStream) {
    byte[] arrayOfByte = new byte[4096];
    int i;
    while ((i = paramInputStream.read(arrayOfByte)) != -1)
      paramOutputStream.write(arrayOfByte, 0, i); 
  }
  
  public static void b(File paramFile) {
    try {
      byte b = 10;
      while (paramFile.exists() && !paramFile.delete() && --b > 0)
        Thread.sleep(500L); 
    } catch (InterruptedException interruptedException) {}
  }
  
  public static void b(File paramFile1, File paramFile2) {
    try {
      byte b = 10;
      while (paramFile1.exists() && !paramFile1.renameTo(paramFile2) && --b > 0)
        Thread.sleep(500L); 
      Thread.sleep(500L);
    } catch (InterruptedException interruptedException) {}
  }
}
