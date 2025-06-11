package com.wisecoders.dbs.config.model;

import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.Properties;
import javafx.util.Callback;

public abstract class Property {
  public final String c;
  
  public final Class d;
  
  public final Property e;
  
  protected Callback f;
  
  private boolean a = false;
  
  public Property(Property paramProperty, String paramString) {
    this(paramProperty, paramString, null);
  }
  
  public Property(Property paramProperty, String paramString, Class paramClass) {
    this.e = paramProperty;
    this.c = paramString;
    this.d = paramClass;
  }
  
  public void c(boolean paramBoolean) {
    this.a = paramBoolean;
  }
  
  public boolean k() {
    return this.a;
  }
  
  public abstract void a(String paramString);
  
  public abstract void a(Properties paramProperties, boolean paramBoolean);
  
  public abstract void b(Properties paramProperties, boolean paramBoolean);
  
  public abstract Glyph g();
  
  public String l() {
    if (this.e != null) {
      String str = this.e.l();
      if (str != null && !str.isEmpty())
        return str + "." + str; 
    } 
    return this.c;
  }
  
  public boolean equals(Object paramObject) {
    Property property;
    if (this == paramObject)
      return true; 
    if (paramObject instanceof Property) {
      property = (Property)paramObject;
    } else {
      return false;
    } 
    return this.c.equals(property.c);
  }
  
  public int hashCode() {
    return this.c.hashCode();
  }
  
  public abstract void a(Object paramObject);
  
  public abstract Object c();
  
  public abstract Object d();
  
  public abstract boolean e();
  
  public Properties b_() {
    return this.e.b_();
  }
  
  public String m() {
    String str = b_().getProperty(l() + ".text");
    if (str == null)
      str = this.c; 
    return StringUtil.removeCtxKeyword(str);
  }
  
  public String n() {
    return b_().getProperty(l() + ".tooltip");
  }
  
  public void a(Callback paramCallback) {
    this.f = paramCallback;
  }
  
  public Callback o() {
    return this.f;
  }
}
