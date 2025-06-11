package com.wisecoders.dbs.config.model;

import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LOVProperty extends Property {
  private String a;
  
  private String b;
  
  private final List g = new ArrayList();
  
  LOVProperty(Property paramProperty, String paramString) {
    super(paramProperty, paramString, String.class);
  }
  
  public void b(String paramString) {
    this.g.clear();
    if (paramString == null || paramString.length() == 0) {
      this.a = null;
    } else {
      this.a = paramString;
      for (String str : paramString.split(","))
        this.g.add(str.trim().toLowerCase()); 
    } 
  }
  
  public boolean c(String paramString) {
    return (paramString != null && this.g.contains(paramString.toLowerCase()));
  }
  
  public String a() {
    int i;
    if (this.a != null && (i = this.a.indexOf("...")) > -1)
      return this.a.substring(0, i); 
    return this.a;
  }
  
  public String f() {
    int i;
    if (this.a != null && (i = this.a.indexOf("...")) > -1)
      return this.a.substring(i + "...".length()); 
    return this.a;
  }
  
  public String h() {
    return this.a;
  }
  
  public void a(Properties paramProperties, boolean paramBoolean) {
    String str = paramProperties.getProperty(l() + ".list");
    if (str != null) {
      b(str);
      if (paramBoolean)
        this.b = this.a; 
    } 
  }
  
  public void b(Properties paramProperties, boolean paramBoolean) {
    if ((paramBoolean || !e()) && this.a != null)
      paramProperties.put(l() + ".list", this.a); 
  }
  
  public void a(Object paramObject) {
    if (paramObject instanceof String)
      b((String)paramObject); 
  }
  
  public boolean e() {
    return StringUtil.areEqual(this.b, this.a);
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
  
  public List i() {
    return this.g;
  }
  
  public Glyph g() {
    return BootstrapIcons.list_check;
  }
}
