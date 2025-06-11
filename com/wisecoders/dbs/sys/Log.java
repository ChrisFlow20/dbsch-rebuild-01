package com.wisecoders.dbs.sys;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.project.store.Pref;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Log {
  private static final String e = "Logger:sql";
  
  private static final CopyOnWriteArrayList f = new CopyOnWriteArrayList();
  
  private static final int g = 2000;
  
  private static PrintStream h = System.out;
  
  public static final String a = "Use RDBMS: ";
  
  private static final ByteArrayOutputStream i = new ByteArrayOutputStream();
  
  private static final PrintStream j = new PrintStream(i);
  
  private static final ByteArrayOutputStream k = new ByteArrayOutputStream();
  
  public static final PrintStream b = new PrintStream(k);
  
  public static final int c;
  
  public static final long d;
  
  private static boolean l = false, m = false, n = false, o = false;
  
  private static boolean p = Pref.b("Logger:sql", false);
  
  private static final Timeline q = new Timeline(new KeyFrame[] { new KeyFrame(
          Duration.seconds(1.0D), paramActionEvent -> {
            if (i.size() > 0) {
              a(i.toString(), LogLevel.c);
              i.reset();
            } 
            if (k.size() > 0) {
              a(k.toString(), LogLevel.c);
              k.reset();
              n = true;
            } 
          }new javafx.animation.KeyValue[0]) });
  
  private static boolean r;
  
  private static final Map s;
  
  private static long t;
  
  private static int u;
  
  private static String v;
  
  private static String w;
  
  static {
    int i = -1;
    long l = -1L;
    try {
      String str = Log.class.getPackage().getImplementationVersion();
      if (str != null && !str.isEmpty()) {
        i = Integer.parseInt(str);
        l = (new SimpleDateFormat("yyyyMMdd")).parse("20" + i).getTime();
      } 
    } catch (Throwable throwable) {
      System.out.println(throwable);
    } 
    c = i;
    d = l;
    try {
      if (Sys.i.exists()) {
        if (Sys.i.length() > 819200L)
          Sys.i.delete(); 
      } else {
        Sys.i.createNewFile();
      } 
      h = new PrintStream(new FileOutputStream(Sys.i, true), true, StandardCharsets.UTF_8);
    } catch (Throwable throwable) {
      a("Cannot initialize output logs file '" + String.valueOf(Sys.i.toURI()) + "'.\nEDIT config/DbSchema.properties AND MODIFY DbSchema.config.path TO A VALID LOCATION WHERE THE DBSCHEMA CONFIGURATION CAN BE SAVED.\n" + String.valueOf(throwable), throwable);
    } 
    r = false;
    s = new HashMap<>();
  }
  
  public static void a() {
    try {
      if (!r) {
        r = true;
        if (Log.class.getPackage().getImplementationVersion() != null) {
          System.setErr(j);
          System.setOut(j);
        } 
        q.setCycleCount(-1);
        q.play();
      } 
    } catch (Throwable throwable) {
      a("Error intercepting System.out to stream.\n" + throwable.toString(), throwable);
    } 
  }
  
  public static String a(String paramString) {
    if (paramString != null && !paramString.endsWith("/") && !paramString.endsWith("\\"))
      return paramString + paramString; 
    return paramString;
  }
  
  public static void a(boolean paramBoolean) {
    p = paramBoolean;
    Pref.a("Logger:sql", paramBoolean);
  }
  
  public static boolean b() {
    return p;
  }
  
  public static void b(String paramString) {
    a(paramString, LogLevel.a);
  }
  
  public static void c(String paramString) {
    a(paramString, LogLevel.a);
  }
  
  public static void d(String paramString) {
    if (o)
      a(paramString, LogLevel.b); 
  }
  
  public static void e(String paramString) {
    if (p)
      a(paramString, LogLevel.d); 
  }
  
  public static void f(String paramString) {
    a(paramString, LogLevel.e);
    m = true;
  }
  
  public static void a(Throwable paramThrowable) {
    if (paramThrowable != null && o)
      a(StackTraceHelper.a(paramThrowable), LogLevel.b); 
  }
  
  public static void a(String paramString, Throwable paramThrowable) {
    String str = paramString;
    if (paramThrowable != null)
      str = str + "\n" + str; 
    a(str, LogLevel.e);
  }
  
  public static void b(Throwable paramThrowable) {
    if (paramThrowable != null)
      a(StackTraceHelper.a(paramThrowable), LogLevel.e); 
  }
  
  public static void c(Throwable paramThrowable) {
    b(null, paramThrowable);
  }
  
  public static void b(String paramString, Throwable paramThrowable) {
    if (paramThrowable != null) {
      l = true;
      StringBuilder stringBuilder = new StringBuilder();
      if (paramString != null)
        stringBuilder.append(paramString).append("\n"); 
      stringBuilder.append(paramThrowable).append("\n").append(StackTraceHelper.a(paramThrowable));
      if (paramThrowable.getCause() != null)
        stringBuilder.append(paramThrowable.getCause()).append("\n").append(StackTraceHelper.a(paramThrowable.getCause())); 
      a(stringBuilder.toString(), LogLevel.f);
    } 
  }
  
  public static void c() {
    l = false;
    f.clear();
  }
  
  private static void a(String paramString, LogLevel paramLogLevel) {
    if (f.size() > 2000)
      f.remove(0); 
    LogRecord logRecord = new LogRecord(paramString, paramLogLevel, System.currentTimeMillis());
    f.add(logRecord);
    if (h != null) {
      h.println(logRecord);
      h.flush();
    } 
  }
  
  public static boolean d() {
    return l;
  }
  
  public static boolean e() {
    return (m || l);
  }
  
  public static void f() {
    l = false;
    n = false;
  }
  
  public static boolean g() {
    return n;
  }
  
  public static void h() {}
  
  public static List i() {
    return f;
  }
  
  public static void a(Class paramClass) {
    ClassLoader classLoader = paramClass.getClassLoader();
    StringBuilder stringBuilder = (new StringBuilder("#### APP ")).append(paramClass).append(" ###");
    while (true) {
      stringBuilder.append("\n\t").append(classLoader).append(" ");
      if (classLoader instanceof URLClassLoader) {
        URLClassLoader uRLClassLoader = (URLClassLoader)classLoader;
        for (URL uRL : uRLClassLoader.getURLs())
          stringBuilder.append(uRL).append(" "); 
      } 
      if ((classLoader = classLoader.getParent()) == null) {
        System.out.println(stringBuilder);
        return;
      } 
    } 
  }
  
  public static void b(boolean paramBoolean) {
    o = paramBoolean;
  }
  
  public static void a(Object paramObject, String paramString) {
    k();
    t = System.currentTimeMillis();
    v = String.valueOf(paramObject);
    w = paramString;
    u = 0;
  }
  
  public static void j() {
    u++;
  }
  
  public static synchronized void k() {
    if (v != null && w != null) {
      List<a> list = s.computeIfAbsent(v, paramString -> new ArrayList());
      list.add(new a(w, System.currentTimeMillis() - t, u));
      u = 0;
      v = null;
      w = null;
    } 
  }
  
  public static synchronized void g(String paramString) {
    k();
    StringBuilder stringBuilder = new StringBuilder(paramString);
    for (String str : s.keySet()) {
      stringBuilder.append("Schema ").append(str).append("\n");
      List<Comparable> list = (List)s.get(str);
      Collections.sort(list);
      for (a a : list) {
        if (a.b > 500L)
          stringBuilder.append(String.format("\t%-30s%6s items%20s\n", new Object[] { a.a, Integer.valueOf(a.c), a(a.b, true) })); 
      } 
    } 
    c(stringBuilder.toString());
  }
  
  public static String a(long paramLong, boolean paramBoolean) {
    long l1 = paramLong / 1000L;
    long l2;
    if ((l2 = l1 / 3600L) > 1L)
      return "" + l2 + " hours " + l2 + " min"; 
    if (l2 == 1L)
      return "1 hour " + l1 % 60000L + " min"; 
    if ((l2 = l1 / 60L) > 0L)
      return "" + l2 + " min " + l2 + " sec"; 
    double d = paramBoolean ? Math.round(paramLong / 1000.0D) : (Math.ceil(paramLong / 10.0D) / 100.0D);
    return "" + d + " sec";
  }
  
  public static String a(int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = Math.max(0, f.size() - paramInt); i < f.size(); i++)
      stringBuilder.append(f.get(i)).append("\n"); 
    return stringBuilder.toString();
  }
}
