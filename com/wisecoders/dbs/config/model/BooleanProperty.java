package com.wisecoders.dbs.config.model;

import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.Properties;

public class BooleanProperty extends Property {
  private boolean a = false;
  
  private boolean b = false;
  
  private boolean g = false;
  
  public BooleanProperty(Property paramProperty, String paramString) {
    super(paramProperty, paramString, Boolean.class);
  }
  
  public void a() {
    this.a = !this.a;
  }
  
  public void a(boolean paramBoolean) {
    this.a = paramBoolean;
  }
  
  public boolean b() {
    return this.a;
  }
  
  public void a(Object paramObject) {
    if (paramObject instanceof Boolean)
      a(((Boolean)paramObject).booleanValue()); 
  }
  
  public Object c() {
    return Boolean.valueOf(this.a);
  }
  
  public void a(String paramString) {
    if (paramString != null)
      this.b = Boolean.parseBoolean(paramString); 
  }
  
  public Object d() {
    return Boolean.valueOf(this.b);
  }
  
  public void a(Properties paramProperties, boolean paramBoolean) {
    String str = paramProperties.getProperty(l());
    if (str != null) {
      boolean bool = Boolean.parseBoolean(str);
      a(bool);
      if (paramBoolean)
        this.b = bool; 
    } 
  }
  
  public boolean e() {
    return (this.a == this.b);
  }
  
  public void b(Properties paramProperties, boolean paramBoolean) {
    if (paramBoolean || !e())
      paramProperties.put(l(), "" + b()); 
  }
  
  public void b(boolean paramBoolean) {
    this.g = paramBoolean;
  }
  
  public boolean f() {
    return this.g;
  }
  
  public Glyph g() {
    return BootstrapIcons.check;
  }
}
