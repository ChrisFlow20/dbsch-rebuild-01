package com.wisecoders.dbs.config.model;

import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.Properties;

public class LongProperty extends Property {
  private final long a;
  
  private final long b;
  
  private long g;
  
  private long h;
  
  LongProperty(Property paramProperty, String paramString, long paramLong1, long paramLong2) {
    super(paramProperty, paramString, Integer.class);
    this.a = paramLong1;
    this.b = paramLong2;
  }
  
  public void a(long paramLong) {
    this.g = paramLong;
    this.g = Math.max(this.g, this.a);
    this.g = Math.min(this.g, this.b);
  }
  
  public void a(Object paramObject) {
    if (paramObject instanceof Integer)
      this.g = ((Integer)paramObject).intValue(); 
  }
  
  public long a() {
    return this.g;
  }
  
  public Object c() {
    return Long.valueOf(this.g);
  }
  
  public void a(String paramString) {
    if (StringUtil.isFilled(paramString))
      this.h = Long.parseLong(paramString); 
  }
  
  public Object d() {
    return Long.valueOf(this.h);
  }
  
  public void a(Properties paramProperties, boolean paramBoolean) {
    String str = paramProperties.getProperty(l());
    if (str != null && str.trim().length() > 0) {
      this.g = Long.parseLong(str);
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
