package com.wisecoders.dbs.diagram.fx.print;

import javafx.beans.property.SimpleBooleanProperty;

public class PrintPreviewPage {
  public final int a;
  
  public final int b;
  
  final int c;
  
  public final SimpleBooleanProperty d = new SimpleBooleanProperty(true);
  
  PrintPreviewPage(int paramInt1, int paramInt2, int paramInt3) {
    this.a = paramInt1;
    this.b = paramInt2;
    this.c = paramInt3;
  }
  
  public int a() {
    return (this.b + 1) * (this.a + 1);
  }
  
  public boolean b() {
    return this.d.get();
  }
  
  public void a(boolean paramBoolean) {
    this.d.set(paramBoolean);
  }
}
