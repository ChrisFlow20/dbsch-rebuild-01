package com.wisecoders.dbs.config.model;

import com.wisecoders.dbs.sys.StringUtil;
import java.util.Properties;

public class OptionsProperty extends StringProperty {
  private SyntaxOption g;
  
  private String h;
  
  private String i;
  
  public OptionsProperty() {
    super((Property)null, "syntax");
  }
  
  OptionsProperty(Property paramProperty, String paramString) {
    super(paramProperty, paramString);
  }
  
  public void a(Properties paramProperties, boolean paramBoolean) {
    String str = paramProperties.getProperty(l());
    if (str != null) {
      b(str);
      if (paramBoolean)
        this.b = str; 
    } 
    d(paramProperties.getProperty(l() + ".title"));
    c(paramProperties.getProperty(l() + ".helpURL"));
    e(paramProperties.getProperty(l() + ".default"));
  }
  
  public String a() {
    return this.h;
  }
  
  public void b(String paramString) {
    this.a = paramString;
    this.g = null;
    if (paramString != null)
      this.g = new SyntaxOption(null, paramString, SyntaxOption$Type.a); 
  }
  
  public void c(String paramString) {
    if (paramString != null)
      this.h = paramString; 
  }
  
  public void d(String paramString) {
    if (paramString != null)
      this.i = paramString; 
  }
  
  public boolean e() {
    return StringUtil.areEqual(this.b, this.a);
  }
  
  public boolean f() {
    return (this.g != null);
  }
  
  public SyntaxOption h() {
    return this.g;
  }
  
  public String i() {
    return this.i;
  }
}
