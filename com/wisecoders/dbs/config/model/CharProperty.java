package com.wisecoders.dbs.config.model;

import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.Properties;

public class CharProperty extends Property {
  private char a = Character.MIN_VALUE, b = Character.MIN_VALUE;
  
  public CharProperty(Property paramProperty, String paramString) {
    super(paramProperty, paramString, Character.class);
  }
  
  public void a(char paramChar) {
    this.a = paramChar;
  }
  
  public char a() {
    return this.a;
  }
  
  public void a(Properties paramProperties, boolean paramBoolean) {
    String str = paramProperties.getProperty(l() + ".char");
    if (str != null && str.length() > 0) {
      this.a = str.charAt(0);
      if (paramBoolean)
        this.b = this.a; 
    } 
  }
  
  public void b(Properties paramProperties, boolean paramBoolean) {
    if (paramBoolean || !e())
      paramProperties.put(l() + ".char", "" + this.a); 
  }
  
  public void a(Object paramObject) {
    if (paramObject instanceof Character) {
      this.a = ((Character)paramObject).charValue();
    } else if (paramObject instanceof String && ((String)paramObject).length() > 0) {
      this.a = ((String)paramObject).charAt(0);
    } 
  }
  
  public Object c() {
    return Character.valueOf(this.a);
  }
  
  public void a(String paramString) {
    if (StringUtil.isFilled(paramString))
      this.b = paramString.charAt(0); 
  }
  
  public Object d() {
    return Character.valueOf(this.b);
  }
  
  public boolean b() {
    return (this.a != '\000');
  }
  
  public String toString() {
    return "" + this.a;
  }
  
  public boolean e() {
    return (this.a == this.b);
  }
  
  public Glyph g() {
    return BootstrapIcons.input_cursor_text;
  }
}
