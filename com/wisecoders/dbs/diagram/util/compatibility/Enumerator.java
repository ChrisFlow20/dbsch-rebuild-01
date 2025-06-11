package com.wisecoders.dbs.diagram.util.compatibility;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

public class Enumerator implements Enumeration {
  private final Iterator a;
  
  public Enumerator(Collection paramCollection) {
    this.a = paramCollection.iterator();
  }
  
  public boolean hasMoreElements() {
    return this.a.hasNext();
  }
  
  public Object nextElement() {
    return this.a.next();
  }
}
