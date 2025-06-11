package com.wisecoders.dbs.config.model;

import com.wisecoders.dbs.sys.ColorUtil;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.Properties;
import javafx.scene.paint.Color;

public class ColorProperty extends Property {
  private Color a = Color.WHITE, b = Color.WHITE;
  
  public ColorProperty(Property paramProperty, String paramString) {
    super(paramProperty, paramString, Color.class);
  }
  
  public void a(Color paramColor) {
    if (paramColor != null)
      this.a = paramColor; 
  }
  
  public Color a() {
    return this.a;
  }
  
  public void a(Properties paramProperties, boolean paramBoolean) {
    String str = paramProperties.getProperty(l() + ".color");
    if (str != null) {
      a(Color.web("#" + str));
      if (paramBoolean)
        this.b = this.a; 
    } 
  }
  
  public void b(Properties paramProperties, boolean paramBoolean) {
    if (paramBoolean || !e())
      paramProperties.put(l() + ".color", ColorUtil.b(this.a)); 
  }
  
  public void a(Object paramObject) {
    if (paramObject instanceof Color)
      a((Color)paramObject); 
  }
  
  public boolean e() {
    return ((this.b == null && this.a == null) || (this.b != null && this.b.equals(this.a)));
  }
  
  public Object c() {
    return this.a;
  }
  
  public void a(String paramString) {
    if (StringUtil.isFilled(paramString))
      this.b = Color.web(paramString); 
  }
  
  public Object d() {
    return this.b;
  }
  
  public Glyph g() {
    return BootstrapIcons.palette;
  }
}
