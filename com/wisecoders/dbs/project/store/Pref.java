package com.wisecoders.dbs.project.store;

import com.wisecoders.dbs.sys.Log;
import java.util.prefs.Preferences;

public class Pref {
  private static final Preferences a = Preferences.userNodeForPackage(Long.class);
  
  private static final Preferences b = Preferences.userNodeForPackage(Object.class);
  
  private static final String c = "Error storing information in registry. If Windows than in RegEditor right-click and give write permissions on HKEY_LOCAL_MACHINE\\SOFTWARE\\JavaSoft\\Prefs. If Linux or Mac please check the write rights in home folder.";
  
  public static void a(String paramString1, String paramString2) {
    a(a, paramString1, paramString2);
  }
  
  public static void b(String paramString1, String paramString2) {
    a(b, paramString1, paramString2);
  }
  
  private static void a(Preferences paramPreferences, String paramString1, String paramString2) {
    try {
      if (paramString2 == null) {
        paramPreferences.remove(paramString1);
      } else {
        paramPreferences.put(paramString1, paramString2);
      } 
    } catch (Throwable throwable) {
      Log.b("Error storing information in registry. If Windows than in RegEditor right-click and give write permissions on HKEY_LOCAL_MACHINE\\SOFTWARE\\JavaSoft\\Prefs. If Linux or Mac please check the write rights in home folder.", throwable);
    } 
  }
  
  public static String a(String paramString) {
    return b(a, paramString, (String)null);
  }
  
  public static String c(String paramString1, String paramString2) {
    return b(a, paramString1, paramString2);
  }
  
  public static String d(String paramString1, String paramString2) {
    return b(b, paramString1, paramString2);
  }
  
  private static String b(Preferences paramPreferences, String paramString1, String paramString2) {
    try {
      return paramPreferences.get(paramString1, paramString2);
    } catch (Throwable throwable) {
      Log.b("Error storing information in registry. If Windows than in RegEditor right-click and give write permissions on HKEY_LOCAL_MACHINE\\SOFTWARE\\JavaSoft\\Prefs. If Linux or Mac please check the write rights in home folder.", throwable);
      return paramString2;
    } 
  }
  
  public static void a(String paramString, long paramLong) {
    b(a, paramString, paramLong);
  }
  
  public static void b(String paramString, long paramLong) {
    b(b, paramString, paramLong);
  }
  
  private static void b(Preferences paramPreferences, String paramString, long paramLong) {
    try {
      if (paramLong == -1L) {
        paramPreferences.remove(paramString);
      } else {
        paramPreferences.putLong(paramString, paramLong);
      } 
    } catch (Throwable throwable) {
      Log.b("Error storing information in registry. If Windows than in RegEditor right-click and give write permissions on HKEY_LOCAL_MACHINE\\SOFTWARE\\JavaSoft\\Prefs. If Linux or Mac please check the write rights in home folder.", throwable);
    } 
  }
  
  public static long c(String paramString, long paramLong) {
    return a(a, paramString, paramLong);
  }
  
  public static long d(String paramString, long paramLong) {
    return a(b, paramString, paramLong);
  }
  
  public static long a(Preferences paramPreferences, String paramString, long paramLong) {
    try {
      return paramPreferences.getLong(paramString, paramLong);
    } catch (Throwable throwable) {
      Log.b("Error storing information in registry. If Windows than in RegEditor right-click and give write permissions on HKEY_LOCAL_MACHINE\\SOFTWARE\\JavaSoft\\Prefs. If Linux or Mac please check the write rights in home folder.", throwable);
      return paramLong;
    } 
  }
  
  public static void b(String paramString) {
    try {
      a.remove(paramString);
    } catch (Throwable throwable) {
      Log.b("Error storing information in registry. If Windows than in RegEditor right-click and give write permissions on HKEY_LOCAL_MACHINE\\SOFTWARE\\JavaSoft\\Prefs. If Linux or Mac please check the write rights in home folder.", throwable);
    } 
  }
  
  public static void c(String paramString) {
    try {
      b.remove(paramString);
    } catch (Throwable throwable) {
      Log.b("Error storing information in registry. If Windows than in RegEditor right-click and give write permissions on HKEY_LOCAL_MACHINE\\SOFTWARE\\JavaSoft\\Prefs. If Linux or Mac please check the write rights in home folder.", throwable);
    } 
  }
  
  public static void a(String paramString, int paramInt) {
    try {
      if (paramInt == -1) {
        a.remove(paramString);
      } else {
        a.putInt(paramString, paramInt);
      } 
    } catch (Throwable throwable) {
      Log.b("Error storing information in registry. If Windows than in RegEditor right-click and give write permissions on HKEY_LOCAL_MACHINE\\SOFTWARE\\JavaSoft\\Prefs. If Linux or Mac please check the write rights in home folder.", throwable);
    } 
  }
  
  public static int b(String paramString, int paramInt) {
    try {
      return a.getInt(paramString, paramInt);
    } catch (Throwable throwable) {
      Log.b("Error storing information in registry. If Windows than in RegEditor right-click and give write permissions on HKEY_LOCAL_MACHINE\\SOFTWARE\\JavaSoft\\Prefs. If Linux or Mac please check the write rights in home folder.", throwable);
      return paramInt;
    } 
  }
  
  public static void a(String paramString, double paramDouble) {
    try {
      if (paramDouble == -1.0D) {
        a.remove(paramString);
      } else {
        a.putDouble(paramString, paramDouble);
      } 
    } catch (Throwable throwable) {
      Log.b("Error storing information in registry. If Windows than in RegEditor right-click and give write permissions on HKEY_LOCAL_MACHINE\\SOFTWARE\\JavaSoft\\Prefs. If Linux or Mac please check the write rights in home folder.", throwable);
    } 
  }
  
  public static double b(String paramString, double paramDouble) {
    try {
      return a.getDouble(paramString, paramDouble);
    } catch (Throwable throwable) {
      Log.b("Error storing information in registry. If Windows than in RegEditor right-click and give write permissions on HKEY_LOCAL_MACHINE\\SOFTWARE\\JavaSoft\\Prefs. If Linux or Mac please check the write rights in home folder.", throwable);
      return paramDouble;
    } 
  }
  
  public static void a(String paramString, boolean paramBoolean) {
    try {
      a.putBoolean(paramString, paramBoolean);
    } catch (Throwable throwable) {
      Log.b("Error storing information in registry. If Windows than in RegEditor right-click and give write permissions on HKEY_LOCAL_MACHINE\\SOFTWARE\\JavaSoft\\Prefs. If Linux or Mac please check the write rights in home folder.", throwable);
    } 
  }
  
  public static boolean b(String paramString, boolean paramBoolean) {
    try {
      return a.getBoolean(paramString, paramBoolean);
    } catch (Throwable throwable) {
      Log.b("Error storing information in registry. If Windows than in RegEditor right-click and give write permissions on HKEY_LOCAL_MACHINE\\SOFTWARE\\JavaSoft\\Prefs. If Linux or Mac please check the write rights in home folder.", throwable);
      return paramBoolean;
    } 
  }
  
  public static void a() {
    try {
      a.clear();
    } catch (Throwable throwable) {
      Log.b("Error storing information in registry. If Windows than in RegEditor right-click and give write permissions on HKEY_LOCAL_MACHINE\\SOFTWARE\\JavaSoft\\Prefs. If Linux or Mac please check the write rights in home folder.", throwable);
    } 
  }
  
  public static boolean c(String paramString, int paramInt) {
    int i = b(paramString, 0);
    if (i < paramInt) {
      a(paramString, i + 1);
      return true;
    } 
    return false;
  }
  
  public static int d(String paramString) {
    int i = b(paramString, 0);
    a(paramString, i + 1);
    return i;
  }
}
