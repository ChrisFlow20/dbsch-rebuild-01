package com.wisecoders.dbs.diagram.model;

public enum LineTextType {
  a("Foreign Key Name"),
  b("Column Name"),
  c("Cascade Action"),
  d("No Text");
  
  public final String e;
  
  LineTextType(String paramString1) {
    this.e = paramString1;
  }
}
