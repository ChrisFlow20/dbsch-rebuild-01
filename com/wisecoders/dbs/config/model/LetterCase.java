package com.wisecoders.dbs.config.model;

public enum LetterCase {
  a, b, c, d;
  
  public static LetterCase a(String paramString) {
    return valueOf(paramString.toLowerCase());
  }
}
