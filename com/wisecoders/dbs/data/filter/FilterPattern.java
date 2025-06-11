package com.wisecoders.dbs.data.filter;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.sys.EscapeChars;
import com.wisecoders.dbs.sys.StringUtil;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterPattern {
  public static final String a = "yyyy-MM-dd HH:mm:ss";
  
  public static final DateTimeFormatter b = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  
  public static String c = ":c";
  
  public static String d = ":s", e = "(.*)";
  
  public static String f = ":n", g = "(-?\\d+\\.?\\d*)";
  
  public static String h = ":d";
  
  public static String i = "(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})";
  
  public final String j;
  
  public final Class k;
  
  public final String l;
  
  public final String m;
  
  public String n;
  
  public final int o;
  
  private final Map p = new HashMap<>();
  
  public FilterPattern(Class paramClass, String paramString1, String paramString2) {
    this.j = paramString1;
    this.l = paramString2;
    this.k = paramClass;
    this.o = b();
    this

      
      .m = EscapeChars.forRegex(paramString2).replaceAll(d, e).replaceAll(f, Matcher.quoteReplacement(g)).replaceAll(h, Matcher.quoteReplacement(i));
  }
  
  private int b() {
    byte b = 0;
    Matcher matcher = Pattern.compile(d).matcher(this.l.replaceAll(f, d).replaceAll(h, d));
    while (matcher.find())
      b++; 
    return b;
  }
  
  public FilterPattern a(String paramString) {
    this.n = paramString;
    return this;
  }
  
  public boolean b(String paramString) {
    if (StringUtil.isFilledTrim(paramString)) {
      Pattern pattern = Pattern.compile("^" + this.m + "$", 2);
      Matcher matcher = pattern.matcher(paramString);
      return matcher.matches();
    } 
    return true;
  }
  
  public String a(String[] paramArrayOfString) {
    if (this.o == 0)
      return this.l; 
    String str = this.l.replaceAll(f, d).replaceAll(h, d);
    str = str.replaceAll("\\\\", "");
    for (byte b = 0; b < paramArrayOfString.length && b < this.o; b++) {
      Matcher matcher = Pattern.compile(d).matcher(str);
      str = matcher.replaceFirst(Matcher.quoteReplacement(paramArrayOfString[b]));
    } 
    return str;
  }
  
  public List c(String paramString) {
    ArrayList<String> arrayList = new ArrayList();
    if (paramString != null && paramString.length() > 0) {
      Pattern pattern = Pattern.compile("^" + this.m + "$", 2);
      Matcher matcher = pattern.matcher(paramString);
      if (matcher.find())
        for (byte b = 0; b < this.o; b++)
          arrayList.add(matcher.group(b + 1).trim());  
    } 
    return arrayList;
  }
  
  public static String a(String paramString, Attribute paramAttribute) {
    return (paramString != null) ? paramString.replaceAll(c, paramAttribute.getName()) : null;
  }
  
  public String a(String paramString1, String paramString2, String paramString3) {
    if (paramString2 != null) {
      String str1 = d(paramString1);
      paramString2 = paramString2.replaceFirst(this.m, str1);
      paramString2 = paramString2.replaceAll(c, EscapeChars.forReplacementString(paramString3));
      StringBuffer stringBuffer = new StringBuffer();
      Matcher matcher = Pattern.compile(i).matcher(paramString2);
      String str2 = "Groovy".equals(paramString1) ? " new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss').parse( '?' )" : (Dbms.get(paramString1)).toTimestamp.c_();
      while (matcher.find()) {
        String str = matcher.group(1);
        matcher.appendReplacement(stringBuffer, str2.replaceAll("\\?", Matcher.quoteReplacement(str)));
      } 
      matcher.appendTail(stringBuffer);
      return stringBuffer.toString();
    } 
    return null;
  }
  
  public FilterPattern a(String paramString1, String paramString2) {
    this.p.put(paramString1, paramString2);
    return this;
  }
  
  public String d(String paramString) {
    return this.p.containsKey(paramString) ? (String)this.p.get(paramString) : (String)this.p.get("Any");
  }
  
  public boolean a() {
    return "custom".equals(this.j);
  }
  
  public String toString() {
    return this.j;
  }
}
