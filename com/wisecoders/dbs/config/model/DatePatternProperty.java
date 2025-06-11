package com.wisecoders.dbs.config.model;

import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class DatePatternProperty extends Property {
  private String a = null;
  
  private String b = null;
  
  private SimpleDateFormat g = new SimpleDateFormat();
  
  public DatePatternProperty(Property paramProperty, String paramString) {
    super(paramProperty, paramString, String.class);
  }
  
  public void b(String paramString) {
    if (paramString != null && paramString.length() == 0)
      paramString = null; 
    a(paramString);
  }
  
  public String a() {
    return this.a;
  }
  
  public boolean b() {
    return (this.a != null && this.a.trim().length() > 0);
  }
  
  public void a(Properties paramProperties, boolean paramBoolean) {
    String str = paramProperties.getProperty(l() + ".pattern");
    if (str != null) {
      a(str);
      if (paramBoolean)
        this.b = this.a; 
    } 
  }
  
  public void b(Properties paramProperties, boolean paramBoolean) {
    if ((paramBoolean || !e()) && this.a != null)
      paramProperties.put(l() + ".pattern", this.a); 
  }
  
  public void a(Object paramObject) {
    if (paramObject instanceof String)
      try {
        this.g = new SimpleDateFormat((String)paramObject);
        this.a = (String)paramObject;
      } catch (Exception exception) {
        Log.b(exception);
      }  
  }
  
  public Object c() {
    return this.a;
  }
  
  public void a(String paramString) {
    if (StringUtil.isFilled(paramString))
      this.b = paramString; 
  }
  
  public Object d() {
    return this.b;
  }
  
  public SimpleDateFormat f() {
    return this.g;
  }
  
  public String a(Date paramDate) {
    return (paramDate != null) ? this.g.format(paramDate) : "";
  }
  
  public boolean e() {
    return StringUtil.areEqual(this.b, this.a);
  }
  
  public Glyph g() {
    return BootstrapIcons.clock;
  }
}
