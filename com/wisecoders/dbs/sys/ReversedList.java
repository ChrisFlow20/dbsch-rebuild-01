package com.wisecoders.dbs.sys;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ReversedList implements Iterable {
  private final List a;
  
  public ReversedList(List paramList) {
    this.a = paramList;
  }
  
  public Iterator iterator() {
    ListIterator listIterator = this.a.listIterator(this.a.size());
    return new ReversedList$1(this, listIterator);
  }
  
  public static ReversedList a(List paramList) {
    return new ReversedList(paramList);
  }
}
