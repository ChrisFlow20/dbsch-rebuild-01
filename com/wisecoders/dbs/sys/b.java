package com.wisecoders.dbs.sys;

import java.util.Iterator;

class b implements Iterator {
  private int b;
  
  private int c;
  
  b(ObsoleteFxWeakList paramObsoleteFxWeakList) {
    this.b = paramObsoleteFxWeakList.size();
    this.c = 0;
  }
  
  public boolean hasNext() {
    return (this.c < this.b);
  }
  
  public Object next() {
    return this.a.get(this.c++);
  }
  
  public void remove() {
    throw new UnsupportedOperationException();
  }
}
