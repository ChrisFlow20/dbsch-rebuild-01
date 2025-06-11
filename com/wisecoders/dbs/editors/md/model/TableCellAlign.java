package com.wisecoders.dbs.editors.md.model;

public enum TableCellAlign {
  a("left"),
  b("center"),
  c("right"),
  d("");
  
  private String e;
  
  TableCellAlign(String paramString1) {
    this.e = paramString1;
  }
  
  public String a() {
    return this.e;
  }
}
