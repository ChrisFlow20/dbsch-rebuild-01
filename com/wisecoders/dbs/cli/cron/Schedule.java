package com.wisecoders.dbs.cli.cron;

import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sys.StringUtil;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Schedule {
  public static final String a = "_";
  
  private static final Pattern b = Pattern.compile("each(\\d+)", 2);
  
  private static final Pattern c = Pattern.compile("(\\d+)-(\\d+)", 2);
  
  private static final Pattern d = Pattern.compile("(\\d+)", 2);
  
  private final String e;
  
  private final List f = new ArrayList();
  
  private static final Pattern g = Pattern.compile("(.*)\\.schedule_(.+)\\.(.*)", 2);
  
  public Schedule(String paramString) {
    this.e = a(paramString, 80);
    Matcher matcher;
    if (!(matcher = g.matcher(paramString)).matches())
      throw new InvalidParameterException("No schedule pattern found in file '" + paramString + "'"); 
    String str = matcher.group(2);
    for (String str1 : str.split("_")) {
      if (!str1.startsWith("("))
        throw new InvalidParameterException("Invalid Pattern: '" + str1 + "'. The pattern should start with an '())'."); 
      Schedule$Filter schedule$Filter = null;
      for (Schedule$TimePart schedule$TimePart : Schedule$TimePart.values()) {
        if (str1.endsWith(")" + String.valueOf(schedule$TimePart)))
          this.f.add(schedule$Filter = new Schedule$Filter(schedule$TimePart, str1.substring("(".length(), str1.length() - String.valueOf(schedule$TimePart).length() - 1))); 
      } 
      if (schedule$Filter == null)
        throw new InvalidParameterException("Invalid Pattern: '" + str1 + "'."); 
    } 
  }
  
  public boolean a() {
    LocalDateTime localDateTime = LocalDateTime.now();
    for (Schedule$Filter schedule$Filter : this.f) {
      if (!schedule$Filter.a(localDateTime))
        return false; 
    } 
    long l;
    if ((l = Pref.c("cron:" + this.e, -1L)) > -1L && l > System.currentTimeMillis())
      return false; 
    Pref.a("cron:" + this.e, b());
    return true;
  }
  
  public long b() {
    Schedule$TimePart schedule$TimePart = null;
    for (Schedule$Filter schedule$Filter : this.f) {
      if (schedule$TimePart == null || schedule$Filter.a.h > schedule$TimePart.h)
        schedule$TimePart = schedule$Filter.a; 
    } 
    if (schedule$TimePart == null)
      schedule$TimePart = Schedule$TimePart.d; 
    switch (Schedule$1.a[schedule$TimePart.ordinal()]) {
      case 1:
        return System.currentTimeMillis() + 60000L;
      case 2:
        return System.currentTimeMillis() + 3600000L;
    } 
    return System.currentTimeMillis() + 86400000L;
  }
  
  public String a(String paramString, int paramInt) {
    byte b = 5;
    if (paramString.length() > paramInt) {
      String str = StringUtil.MD5(paramString);
      return paramString.substring(0, paramInt - 5) + paramString.substring(0, paramInt - 5);
    } 
    return paramString;
  }
}
