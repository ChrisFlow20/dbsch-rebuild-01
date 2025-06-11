package com.wisecoders.dbs.loader.engine;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JSONLoaderCallback implements JSONCallback {
  public final Map a = new LinkedHashMap<>();
  
  private final List b = new ArrayList();
  
  private Object h() {
    return this.b.get(this.b.size() - 1);
  }
  
  private void b(String paramString, Object paramObject) {
    if (h() instanceof Map) {
      ((Map<String, Object>)h()).put(paramString, paramObject);
    } else if (h() instanceof List) {
      ((List<Object>)h()).add(paramObject);
    } 
  }
  
  public JSONLoaderCallback() {
    this.b.add(this.a);
  }
  
  public void a() {}
  
  public void a(String paramString) {
    LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
    b(paramString, linkedHashMap);
    this.b.add(linkedHashMap);
  }
  
  public Object b() {
    if (this.b.size() > 1) {
      Object object = this.b.get(this.b.size() - 1);
      this.b.remove(this.b.size() - 1);
      return object;
    } 
    return null;
  }
  
  public void c() {
    this.a.clear();
    this.b.clear();
    this.b.add(this.a);
  }
  
  public Object d() {
    return this.b.get(this.b.size() - 1);
  }
  
  public JSONCallback e() {
    return null;
  }
  
  public void f() {
    System.out.println("arrayStart");
  }
  
  public void b(String paramString) {
    ArrayList arrayList = new ArrayList();
    b(paramString, arrayList);
    this.b.add(arrayList);
  }
  
  public Object g() {
    return b();
  }
  
  public void c(String paramString) {}
  
  public void d(String paramString) {}
  
  public void e(String paramString) {}
  
  public void f(String paramString) {}
  
  public void a(String paramString, boolean paramBoolean) {
    b(paramString, Boolean.valueOf(paramBoolean));
  }
  
  public void a(String paramString, double paramDouble) {
    b(paramString, Double.valueOf(paramDouble));
  }
  
  public void a(String paramString, int paramInt) {
    b(paramString, Integer.valueOf(paramInt));
  }
  
  public void a(String paramString, long paramLong) {
    b(paramString, Long.valueOf(paramLong));
  }
  
  public void b(String paramString, long paramLong) {
    b(paramString, new Date(paramLong));
  }
  
  public void a(String paramString1, String paramString2) {
    b(paramString1, paramString2);
  }
  
  public void b(String paramString1, String paramString2) {
    b(paramString1, paramString2);
  }
  
  public void a(String paramString1, String paramString2, String paramString3) {
    b(paramString1, paramString2);
  }
  
  public void a(String paramString, int paramInt1, int paramInt2) {
    b(paramString, Integer.valueOf(paramInt1));
  }
  
  public void a(String paramString, Object paramObject) {
    b(paramString, paramObject);
  }
  
  public void a(String paramString1, String paramString2, Object paramObject) {
    b(paramString1, paramObject);
  }
  
  public void a(String paramString, byte[] paramArrayOfbyte) {
    b(paramString, paramArrayOfbyte);
  }
  
  public void a(String paramString, byte paramByte, byte[] paramArrayOfbyte) {
    b(paramString, paramArrayOfbyte);
  }
  
  public void a(String paramString, long paramLong1, long paramLong2) {
    b(paramString, Long.valueOf(paramLong1));
  }
  
  public void c(String paramString1, String paramString2) {
    b(paramString1, paramString2);
  }
  
  public void b(String paramString1, String paramString2, Object paramObject) {
    b(paramString1, paramString2);
  }
}
