package com.wisecoders.dbs.diagram.util.compatibility;

import java.util.Enumeration;

public class ArrayEnumerator implements Enumeration {
  private final Object[] a;
  
  private int b = 0;
  
  public ArrayEnumerator(Object[] paramArrayOfObject) {
    this.a = paramArrayOfObject;
  }
  
  public boolean hasMoreElements() {
    return (this.b < this.a.length);
  }
  
  public Object nextElement() {
    if (this.b < this.a.length)
      return this.a[this.b++]; 
    return null;
  }
}
