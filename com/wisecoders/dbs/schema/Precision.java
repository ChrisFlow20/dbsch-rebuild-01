package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public enum Precision {
  NONE, LENGTH, PRECISION, DECIMAL, ENUMERATION;
  
  public boolean usesLength() {
    return (this == LENGTH || this == PRECISION || this == DECIMAL);
  }
  
  public boolean usesDecimal() {
    return (this == DECIMAL);
  }
}
