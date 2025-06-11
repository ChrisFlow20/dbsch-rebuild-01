package com.wisecoders.dbs.diagram.model;

public enum PainterStatus {
  a(false),
  b(false),
  c(true),
  d(true),
  e(false);
  
  private final boolean f;
  
  PainterStatus(boolean paramBoolean) {
    this.f = paramBoolean;
  }
  
  public boolean a() {
    return this.f;
  }
}
