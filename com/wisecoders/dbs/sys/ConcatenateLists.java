package com.wisecoders.dbs.sys;

import java.util.Iterator;
import java.util.List;

public class ConcatenateLists implements Iterable, Iterator {
  private final List a;
  
  private final List b;
  
  private int c = 0;
  
  public ConcatenateLists(List paramList1, List paramList2) {
    this.a = paramList1;
    this.b = paramList2;
  }
  
  public Iterator iterator() {
    return this;
  }
  
  public boolean hasNext() {
    return (this.c < this.a.size() + this.b.size());
  }
  
  public Object next() {
    int i = this.a.size();
    if (this.c < i)
      return this.a.get(this.c++); 
    if (this.c - i < this.b.size())
      return this.b.get(this.c++ - i); 
    return null;
  }
  
  public void remove() {}
}
