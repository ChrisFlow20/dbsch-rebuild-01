package com.wisecoders.dbs.sys.fx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class HtmlLabel extends Control implements EventTarget {
  private final boolean a;
  
  private final StringProperty b;
  
  private ObjectProperty c;
  
  public HtmlLabel(String paramString) {
    this(paramString, true);
  }
  
  public HtmlLabel(String paramString, boolean paramBoolean) {
    this.b = (StringProperty)new SimpleStringProperty(this, "text");
    this.a = paramBoolean;
    a(paramString);
    getStyleClass().addAll((Object[])new String[] { "label" });
  }
  
  protected Skin createDefaultSkin() {
    return (Skin)new f(this, this.a);
  }
  
  public final StringProperty a() {
    return this.b;
  }
  
  public final String b() {
    return (String)this.b.get();
  }
  
  public final void a(String paramString) {
    this.b.set(paramString);
  }
  
  public final ObjectProperty c() {
    if (this.c == null)
      this.c = (ObjectProperty)new SimpleObjectProperty(this, "onAction"); 
    return this.c;
  }
  
  public final void a(EventHandler paramEventHandler) {
    c().set(paramEventHandler);
  }
  
  public final EventHandler d() {
    return (this.c == null) ? null : (EventHandler)this.c.get();
  }
}
