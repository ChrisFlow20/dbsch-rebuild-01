package com.wisecoders.dbs.sql.scheduler;

import com.wisecoders.dbs.schema.Script;
import com.wisecoders.dbs.sys.StringUtil;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Schedule {
  public static final int a = 0;
  
  public static final int b = 1;
  
  public static final int c = 2;
  
  public static final int d = 3;
  
  public static final int e = 4;
  
  public static final int f = 5;
  
  public static final int[] g = new int[] { 0, 0, 0, 1, 1, 0 };
  
  public static final int[] h = new int[] { 59, 59, 23, 31, 12, 6 };
  
  private static final String[] j = new String[] { "0", "*", "*", "*", "*", "*" };
  
  public final Script i;
  
  private String[] k = new String[j.length];
  
  public Schedule(Script paramScript) {
    this.i = paramScript;
    System.arraycopy(j, 0, this.k, 0, j.length);
  }
  
  public Schedule(Script paramScript, String paramString) {
    this.i = paramScript;
    System.arraycopy(j, 0, this.k, 0, j.length);
    if (StringUtil.isFilledTrim(paramString)) {
      String[] arrayOfString = paramString.split(" ");
      if (arrayOfString.length == this.k.length)
        System.arraycopy(arrayOfString, 0, this.k, 0, arrayOfString.length); 
    } 
  }
  
  public void a(int paramInt, String paramString) {
    if (paramInt < this.k.length)
      this.k[paramInt] = paramString; 
  }
  
  public String a(int paramInt) {
    return this.k[paramInt];
  }
  
  public boolean a() {
    boolean bool = false;
    for (byte b = 0; b < j.length; b++) {
      if (!j[b].equals(this.k[b]))
        bool = true; 
    } 
    return bool;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    if (a())
      for (String str : this.k) {
        if (stringBuilder.length() > 0)
          stringBuilder.append(" "); 
        stringBuilder.append(str);
      }  
    return stringBuilder.toString();
  }
  
  public static void b(int paramInt, String paramString) {
    if (Pattern.compile("^\\*$", 2).matcher(paramString).matches())
      return; 
    Matcher matcher = Pattern.compile("^(\\d+)$", 2).matcher(paramString);
    if (matcher.matches()) {
      int i = Integer.parseInt(matcher.group(1));
      if (!a(paramInt, i))
        throw new InvalidScheduleValue(paramInt, i); 
      return;
    } 
    if (Pattern.compile("^(\\d+)(,\\d+)*$", 2).matcher(paramString).matches()) {
      matcher = Pattern.compile("(\\d+)", 2).matcher(paramString);
      while (matcher.find()) {
        int i = Integer.parseInt(matcher.group(1));
        if (!a(paramInt, i))
          throw new InvalidScheduleValue(paramInt, i); 
      } 
      return;
    } 
    matcher = Pattern.compile("^(\\d+)-(\\d+)$", 2).matcher(paramString);
    if (matcher.matches()) {
      int i = Integer.parseInt(matcher.group(1));
      int j = Integer.parseInt(matcher.group(2));
      if (!a(paramInt, i))
        throw new InvalidScheduleValue(paramInt, i); 
      if (!a(paramInt, j))
        throw new InvalidScheduleValue(paramInt, j); 
      return;
    } 
    matcher = Pattern.compile("^\\*/(\\d+)$", 2).matcher(paramString);
    if (matcher.matches()) {
      int i = Integer.parseInt(matcher.group(1));
      if (!a(paramInt, i))
        throw new InvalidScheduleValue(paramInt, i); 
      return;
    } 
    throw new InvalidSchedulePattern();
  }
  
  public static boolean a(int paramInt1, int paramInt2) {
    return (paramInt2 >= g[paramInt1] && paramInt2 <= h[paramInt1]);
  }
  
  public int b() {
    return 1;
  }
  
  public boolean c() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(13, 1);
    return true;
  }
}
