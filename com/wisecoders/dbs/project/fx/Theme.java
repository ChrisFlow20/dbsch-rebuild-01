package com.wisecoders.dbs.project.fx;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sys.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Theme {
  a(new String[] { "fx.css", "base.css", "base-light.css", "custom.css" }, new String[0], "preview/architect.png", false),
  b(new String[] { "fx.css", "base.css", "base-light.css", "theme-classic.css", "custom.css" }, new String[0], "preview/classic.png", false),
  c(new String[] { "fx.css", "base.css", "base-light.css", "theme-caspian.css", "custom.css" }, new String[0], "preview/caspian.png", false),
  d(new String[] { "fx.css", "base.css", "base-dark.css", "theme-dark.css", "custom.css" }, new String[] { "theme-dark.css" }, "preview/dark.png", true),
  e(new String[] { "fx.css", "base.css", "base-dark.css", "theme-dark.css", "theme-astral.css", "custom.css" }, new String[] { "theme-dark.css", "theme-astral.css" }, "preview/dark.png", true);
  
  public final List f = new ArrayList();
  
  public static final String g = "theme";
  
  public final String[] h;
  
  public final String i;
  
  public final boolean j;
  
  public static final String k;
  
  Theme(String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString1, boolean paramBoolean) {
    this.f.addAll(Arrays.asList(a(paramArrayOfString1)));
    if (Sys.x.resolve("custom-stable.css").toFile().exists()) {
      this.f.remove(Sys.x.resolve("custom.css").toString());
      this.f.add(Sys.x.resolve("custom-stable.css").toString());
    } 
    this.h = a(paramArrayOfString2);
    this.i = Sys.x.resolve(paramString1).toUri().toASCIIString();
    this.j = paramBoolean;
  }
  
  private String[] a(String[] paramArrayOfString) {
    String[] arrayOfString = new String[paramArrayOfString.length];
    for (byte b = 0; b < paramArrayOfString.length; b++)
      arrayOfString[b] = Sys.x.resolve(paramArrayOfString[b]).toUri().toASCIIString(); 
    return arrayOfString;
  }
  
  public static Theme a() {
    try {
      String str = Pref.c("theme", a.toString());
      if ("Default".equals(str) || "Oxygen".equals(str)) {
        str = "Architect";
      } else if ("Moonlight".equals(str)) {
        str = "Dark";
      } 
      Theme theme = valueOf(str);
      theme.b();
      return theme;
    } catch (IllegalArgumentException illegalArgumentException) {
      Log.h();
      return b;
    } 
  }
  
  public void b() {
    Pref.a("theme", toString());
  }
  
  public boolean c() {
    return toString().equals(Pref.a("theme"));
  }
  
  static {
    k = Sys.x.resolve("webview-dark.css").toUri().toString();
  }
}
