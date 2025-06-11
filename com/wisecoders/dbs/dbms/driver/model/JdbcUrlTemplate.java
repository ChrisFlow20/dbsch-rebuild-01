package com.wisecoders.dbs.dbms.driver.model;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.sys.StringUtil;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JdbcUrlTemplate {
  public static final int a = 1;
  
  public static final int b = 2;
  
  public static final int c = 4;
  
  public static final int d = 8;
  
  public static final int e = 16;
  
  public static final int f = 32;
  
  public static final int g = 64;
  
  public static final int h = 128;
  
  public static final int i = 256;
  
  public static final int j = 512;
  
  public static final int k = 1024;
  
  public static final int l = 2048;
  
  public static final int m = 4096;
  
  public static final int n = 8192;
  
  public static final int o = 16384;
  
  public final String p;
  
  public final String[] q;
  
  private String r;
  
  private final String s;
  
  private final String t;
  
  private final String u;
  
  private final String v;
  
  private final String w;
  
  private String x;
  
  private final String y;
  
  private final String z;
  
  private String A;
  
  private final String B;
  
  private final String C;
  
  private boolean D = true;
  
  private int E;
  
  private int F;
  
  private boolean G = true;
  
  private boolean H = false;
  
  private boolean I = false;
  
  private boolean J = false;
  
  private boolean K;
  
  JdbcUrlTemplate(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, boolean paramBoolean) {
    if (paramString2 == null || paramString1 == null || paramString3 == null)
      throw new NullPointerException("JdbcURL class initialized with null parameters"); 
    this.p = paramString1;
    this.r = paramString3;
    this.q = paramString2.split(";");
    this.z = paramString11;
    this.y = paramString12;
    this.B = paramString4;
    this.C = paramString5;
    this.s = paramString6;
    this.t = paramString7;
    this.u = paramString8;
    this.v = paramString9;
    this.w = paramString10;
    this.K = paramBoolean;
    a("", 120, "", "", "", "", "", "", "", "");
  }
  
  public void a(boolean paramBoolean) {
    this.D = paramBoolean;
  }
  
  public boolean a() {
    return this.D;
  }
  
  public boolean b() {
    return (this.r != null && this.r.length() > 0);
  }
  
  public String c() {
    return this.r;
  }
  
  public void a(String paramString) {
    if (paramString != null && paramString.trim().length() == 0)
      paramString = null; 
    this.r = paramString;
  }
  
  public void b(boolean paramBoolean) {
    this.G = paramBoolean;
  }
  
  public boolean d() {
    return this.G;
  }
  
  public boolean e() {
    return (this.G || (!Sys.j() && !Sys.k()));
  }
  
  public String toString() {
    return this.y;
  }
  
  public String a(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9) {
    this.E = this.F = 0;
    if (paramString2 != null) {
      String str1 = Sys.b.toString();
      str1 = str1.replaceAll("/C:", "C:");
      if (paramString2.startsWith("./"))
        paramString2 = paramString2.replaceAll("\\./", str1); 
    } 
    String str = (this.r != null) ? this.r : "";
    str = a(str, "HOST", paramString1, 1);
    str = a(str, "HOSTS", paramString1, 2);
    str = a(str, "PORT", paramInt, 4);
    str = a(str, "USER", paramString8, 8);
    str = a(str, "PASSWORD", paramString9, 16);
    str = a(str, "DB", paramString2, 32);
    str = a(str, "WIN_FILE", paramString2, 64);
    str = a(str, "FILE", f(paramString2), 128);
    str = a(str, "WIN_FOLDER", paramString2, 256);
    str = a(str, "FOLDER", f(paramString2), 512);
    str = a(str, "PARAM", StringUtil.encodeURLBasic(paramString3), 1024);
    str = a(str, "PARAM2", StringUtil.encodeURLBasic(paramString4), 2048);
    str = a(str, "PARAM3", StringUtil.encodeURLBasic(paramString5), 4096);
    str = a(str, "PARAM4", StringUtil.encodeURLBasic(paramString6), 8192);
    str = a(str, "PARAM5", StringUtil.encodeURLBasic(paramString7), 16384);
    return str;
  }
  
  private String f(String paramString) {
    if (paramString != null)
      paramString = paramString.replaceAll("\\\\", "/"); 
    return paramString;
  }
  
  private String a(String paramString1, String paramString2, int paramInt1, int paramInt2) {
    return a(paramString1, paramString2, (paramInt1 > -1) ? ("" + paramInt1) : "", paramInt2);
  }
  
  private String a(String paramString1, String paramString2, String paramString3, int paramInt) {
    Matcher matcher = Pattern.compile("\\[([^\\]\\[]*)<" + paramString2 + ">([^\\]\\[]*)\\]", 2).matcher(paramString1);
    if (paramString3 != null)
      if ("PASSWORD".equals(paramString2) && this.K) {
        paramString3 = URLEncoder.encode(paramString3, StandardCharsets.UTF_8);
      } else {
        paramString3 = paramString3.trim();
      }  
    StringBuilder stringBuilder = new StringBuilder();
    while (matcher.find()) {
      this.E |= paramInt;
      this.F |= paramInt;
      if (StringUtil.isFilledTrim(paramString3)) {
        matcher.appendReplacement(stringBuilder, "$1" + b(paramString3) + "$2");
        continue;
      } 
      matcher.appendReplacement(stringBuilder, "");
    } 
    matcher.appendTail(stringBuilder);
    return b(stringBuilder.toString(), paramString2, paramString3, paramInt);
  }
  
  private String b(String paramString1, String paramString2, String paramString3, int paramInt) {
    Matcher matcher = Pattern.compile("<" + paramString2 + ">", 2).matcher(paramString1);
    StringBuilder stringBuilder = new StringBuilder();
    while (matcher.find()) {
      this.E |= paramInt;
      matcher.appendReplacement(stringBuilder, Matcher.quoteReplacement((paramString3 != null) ? b(paramString3) : ""));
    } 
    matcher.appendTail(stringBuilder);
    return stringBuilder.toString();
  }
  
  public static String b(String paramString) {
    paramString = paramString.replaceAll("=", "%3D");
    return paramString;
  }
  
  public boolean a(int paramInt) {
    return ((this.E & paramInt) > 0);
  }
  
  public boolean b(int paramInt) {
    return ((this.F & paramInt) > 0);
  }
  
  public String f() {
    return this.B;
  }
  
  public String g() {
    return this.C;
  }
  
  public String h() {
    return this.y;
  }
  
  public String i() {
    return this.z;
  }
  
  public boolean j() {
    return (this.z != null);
  }
  
  public String k() {
    return this.s;
  }
  
  public String l() {
    return this.t;
  }
  
  public String m() {
    return this.u;
  }
  
  public String n() {
    return this.v;
  }
  
  public String o() {
    return this.w;
  }
  
  public String p() {
    return this.A;
  }
  
  public void c(String paramString) {
    this.A = paramString;
  }
  
  public void d(String paramString) {
    this.x = paramString;
  }
  
  public String q() {
    return this.x;
  }
  
  public void c(boolean paramBoolean) {
    this.H = paramBoolean;
  }
  
  public boolean r() {
    return this.H;
  }
  
  public void d(boolean paramBoolean) {
    this.I = paramBoolean;
  }
  
  public boolean s() {
    return this.I;
  }
  
  public boolean e(String paramString) {
    for (String str : this.q) {
      if (str.equalsIgnoreCase(paramString))
        return true; 
    } 
    return false;
  }
  
  public void e(boolean paramBoolean) {
    this.K = paramBoolean;
  }
  
  public boolean t() {
    return this.J;
  }
  
  public void f(boolean paramBoolean) {
    this.J = paramBoolean;
  }
}
