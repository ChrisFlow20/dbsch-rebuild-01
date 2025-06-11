package com.wisecoders.dbs.config.model;

import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.Properties;

public class IntProperty extends Property {
  private final int a;
  
  private final int b;
  
  private int g;
  
  private int h;
  
  IntProperty(Property paramProperty, String paramString, int paramInt1, int paramInt2) {
    super(paramProperty, paramString, Integer.class);
    this.g = this.a = paramInt1;
    this.b = paramInt2;
  }
  
  public void a(int paramInt) {
    this.g = paramInt;
    this.g = Math.max(this.g, this.a);
    this.g = Math.min(this.g, this.b);
  }
  
  public void a(Object paramObject) {
    if (paramObject instanceof Integer) {
      a(((Integer)paramObject).intValue());
    } else if (paramObject instanceof String) {
      a(Integer.parseInt((String)paramObject));
    } 
  }
  
  public int a() {
    return this.g;
  }
  
  public Object c() {
    return Integer.valueOf(this.g);
  }
  
  public void a(String paramString) {
    if (StringUtil.isFilled(paramString))
      this.h = Integer.parseInt(paramString); 
  }
  
  public Object d() {
    return Integer.valueOf(this.h);
  }
  
  public void a(Properties paramProperties, boolean paramBoolean) {
    String str = paramProperties.getProperty(l());
    if (str != null && !str.trim().isEmpty()) {
      this.g = Integer.parseInt(str);
      if (paramBoolean)
        this.h = this.g; 
    } 
  }
  
  public boolean e() {
    return (this.g == this.h);
  }
  
  public void b(Properties paramProperties, boolean paramBoolean) {
    if (paramBoolean || !e())
      paramProperties.put(l(), "" + this.g); 
  }
  
  public Glyph g() {
    return BootstrapIcons.a123;
  }
}
