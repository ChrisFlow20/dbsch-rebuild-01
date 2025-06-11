package com.wisecoders.dbs.dialogs.git.model;

import javafx.beans.property.SimpleBooleanProperty;

public class GitFile {
  public static final int a = 1;
  
  public static final int b = 2;
  
  public static final int c = 4;
  
  public static final int d = 8;
  
  public static final int e = 16;
  
  public static final int f = 32;
  
  public static final int g = 64;
  
  public static final int h = 128;
  
  public final String i;
  
  private int j;
  
  private final SimpleBooleanProperty k = new SimpleBooleanProperty(false);
  
  public GitFile(String paramString, int paramInt) {
    this.i = paramString;
    this.j = paramInt;
  }
  
  public void a(int paramInt) {
    this.j |= paramInt;
  }
  
  public int a() {
    return this.j;
  }
  
  public boolean b(int paramInt) {
    return ((this.j & paramInt) > 0);
  }
  
  public void a(boolean paramBoolean) {
    this.k.set(paramBoolean);
  }
  
  public boolean b() {
    return this.k.get();
  }
  
  public SimpleBooleanProperty c() {
    return this.k;
  }
  
  public String d() {
    if ((this.j & 0x40) > 0)
      return "text-red"; 
    if ((this.j & 0x1) > 0)
      return "text-magenta"; 
    if ((this.j & 0x2) > 0)
      return "text-green"; 
    if ((this.j & 0x4) > 0)
      return "text-red"; 
    if ((this.j & 0x8) > 0)
      return "text-blue"; 
    if ((this.j & 0x20) > 0)
      return "text-blue"; 
    if ((this.j & 0x10) > 0)
      return "text-red"; 
    return null;
  }
  
  public String toString() {
    return this.i + " " + this.i;
  }
  
  public String e() {
    return this.i;
  }
}
