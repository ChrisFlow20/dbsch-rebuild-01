package com.wisecoders.dbs.cli.command.fls.obsolete;

import java.util.ArrayList;
import java.util.List;

public class FLSUsers {
  public static List a = new ArrayList();
  
  public static boolean a(String paramString1, String paramString2) {
    if (a.isEmpty())
      b(); 
    for (FLSUser fLSUser : a) {
      if (fLSUser.a.equals(paramString1) && fLSUser.b.equals(paramString2))
        return true; 
    } 
    return false;
  }
  
  public static FLSUser b(String paramString1, String paramString2) {
    if (a.isEmpty())
      b(); 
    for (FLSUser fLSUser1 : a) {
      if (fLSUser1.a.equals(paramString1) && fLSUser1.b.equals(paramString2))
        return fLSUser1; 
    } 
    FLSUser fLSUser = new FLSUser(paramString1, paramString2);
    a.add(fLSUser);
    c();
    return fLSUser;
  }
  
  public static int a() {
    byte b = 0;
    for (FLSUser fLSUser : a) {
      if (fLSUser.a())
        b++; 
    } 
    return b;
  }
  
  public static void b() {}
  
  public static void c() {}
}
