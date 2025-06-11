package com.wisecoders.dbs.sys;

public class StackTraceHelper {
  public static String a(Throwable paramThrowable) {
    UniqueArrayList uniqueArrayList = new UniqueArrayList();
    byte b = 4;
    Throwable throwable = paramThrowable;
    while (throwable != null && --b > 0) {
      uniqueArrayList.add(String.valueOf(throwable) + "\n" + String.valueOf(throwable));
      throwable = (throwable.getCause() != throwable) ? throwable.getCause() : null;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    for (String str : uniqueArrayList)
      stringBuilder.append(str); 
    return stringBuilder.toString();
  }
  
  public static String a(StackTraceElement[] paramArrayOfStackTraceElement) {
    boolean bool1 = false, bool2 = false;
    StringBuilder stringBuilder = new StringBuilder();
    for (StackTraceElement stackTraceElement : paramArrayOfStackTraceElement) {
      if (stackTraceElement.toString().contains("wisecoders")) {
        bool1 = true;
      } else if (bool1) {
        bool2 = true;
      } 
      if (!bool2) {
        stringBuilder.append("  at ");
        stringBuilder.append(stackTraceElement);
        stringBuilder.append("\n");
      } 
    } 
    return stringBuilder.toString();
  }
  
  public static boolean b(StackTraceElement[] paramArrayOfStackTraceElement) {
    for (StackTraceElement stackTraceElement : paramArrayOfStackTraceElement) {
      if (stackTraceElement.toString().contains("wisecoders"))
        return true; 
    } 
    return false;
  }
}
