package com.wisecoders.dbs.project.convert.model;

import com.wisecoders.dbs.sys.StringUtil;
import java.util.Objects;

public class NameConverter {
  private String a;
  
  private String b;
  
  public NameConverter() {}
  
  public NameConverter(String paramString1, String paramString2) {
    this.a = paramString1;
    this.b = paramString2;
  }
  
  public String a() {
    return this.a;
  }
  
  public void a(String paramString) {
    this.a = paramString;
  }
  
  public String b() {
    return this.b;
  }
  
  public void b(String paramString) {
    this.b = paramString;
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject == null || getClass() != paramObject.getClass())
      return false; 
    NameConverter nameConverter = (NameConverter)paramObject;
    return Objects.equals(this.a, nameConverter.a);
  }
  
  public boolean c() {
    return (!StringUtil.isFilled(this.a) || !StringUtil.isFilled(this.b));
  }
  
  public int hashCode() {
    return Objects.hash(new Object[] { this.a });
  }
  
  public String toString() {
    return this.a + ":" + this.a;
  }
}
