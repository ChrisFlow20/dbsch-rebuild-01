package com.wisecoders.dbs.config.model;

import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.Properties;

public class StringProperty extends Property {
  String a;
  
  String b;
  
  private String g;
  
  private final String h = "<empty>";
  
  StringProperty(Property paramProperty, String paramString) {
    super(paramProperty, paramString, String.class);
    this.h = "<empty>";
  }
  
  StringProperty(Property paramProperty, String paramString, Class paramClass) {
    super(paramProperty, paramString, paramClass);
    this.h = "<empty>";
  }
  
  public void b(String paramString) {
    if (paramString == null || paramString.isEmpty()) {
      this.a = null;
    } else {
      this.a = paramString;
    } 
  }
  
  public boolean j() {
    return (this.a != null);
  }
  
  public String c_() {
    return (this.a != null) ? this.a : this.g;
  }
  
  public boolean p() {
    return (this.a != null);
  }
  
  public boolean q() {
    return (this.a == null);
  }
  
  public void a(Properties paramProperties, boolean paramBoolean) {
    String str = paramProperties.getProperty(l());
    if (str != null) {
      if ("<empty>".equals(str))
        str = null; 
      b(str);
      if (paramBoolean)
        this.b = this.a; 
    } 
  }
  
  public void b(Properties paramProperties, boolean paramBoolean) {
    if (paramBoolean || !e())
      paramProperties.put(l(), (this.a != null) ? this.a : "<empty>"); 
  }
  
  public void a(Object paramObject) {
    b((paramObject != null) ? String.valueOf(paramObject) : null);
  }
  
  public Object c() {
    return this.a;
  }
  
  public void a(String paramString) {
    this.b = paramString;
  }
  
  public Object d() {
    return this.b;
  }
  
  public String toString() {
    return this.a;
  }
  
  public StringProperty e(String paramString) {
    this.g = paramString;
    return this;
  }
  
  public String r() {
    return this.g;
  }
  
  public boolean e() {
    return StringUtil.areEqual(this.b, this.a);
  }
  
  public Glyph g() {
    return BootstrapIcons.fonts;
  }
}
