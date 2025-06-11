package com.wisecoders.dbs.config.model;

import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.Properties;

public class ListProperty extends Property {
  public final Object[] a;
  
  public final String[] b;
  
  private int g = 0, h = 0;
  
  ListProperty(Property paramProperty, String paramString, Object[] paramArrayOfObject, String[] paramArrayOfString) {
    super(paramProperty, paramString, ListProperty.class);
    this.a = paramArrayOfObject;
    this.b = paramArrayOfString;
  }
  
  public void a(int paramInt) {
    if (paramInt > -1 && paramInt < this.a.length)
      this.g = paramInt; 
  }
  
  public int a() {
    return this.a.length;
  }
  
  public void a(Object paramObject) {
    for (byte b = 0; b < this.a.length; b++) {
      if (this.a[b] == paramObject)
        this.g = b; 
    } 
  }
  
  public String f() {
    return this.b[this.g];
  }
  
  public Object c() {
    return this.a[this.g];
  }
  
  public void a(String paramString) {
    if (StringUtil.isFilled(paramString))
      this.h = Integer.parseInt(paramString); 
  }
  
  public Object d() {
    return (this.h > -1) ? this.a[this.h] : null;
  }
  
  public int h() {
    return this.g;
  }
  
  public void a(Properties paramProperties, boolean paramBoolean) {
    String str = paramProperties.getProperty(l() + ".index");
    if (str != null && str.trim().length() > 0) {
      this.g = Integer.parseInt(str.trim());
      if (paramBoolean)
        this.h = this.g; 
    } 
  }
  
  public boolean e() {
    return (this.h == this.g);
  }
  
  public void b(Properties paramProperties, boolean paramBoolean) {
    if (paramBoolean || !e())
      paramProperties.put(l() + ".index", "" + h()); 
  }
  
  public Glyph g() {
    return BootstrapIcons.list;
  }
}
