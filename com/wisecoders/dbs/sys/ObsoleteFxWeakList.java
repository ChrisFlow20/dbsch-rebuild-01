package com.wisecoders.dbs.sys;

import java.lang.ref.WeakReference;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ObsoleteFxWeakList extends AbstractList {
  private ArrayList a;
  
  ObsoleteFxWeakList() {
    this.a = new ArrayList();
  }
  
  public ObsoleteFxWeakList(Collection<? extends E> paramCollection) {
    this.a = new ArrayList();
    addAll(0, paramCollection);
  }
  
  public void add(int paramInt, Object paramObject) {
    this.a.add(paramInt, new WeakReference(paramObject));
  }
  
  public Iterator iterator() {
    return new b(this);
  }
  
  public int size() {
    a();
    return this.a.size();
  }
  
  public Object get(int paramInt) {
    return ((WeakReference)this.a.get(paramInt)).get();
  }
  
  private void a() {
    for (WeakReference weakReference : this.a) {
      if (weakReference.get() == null)
        this.a.remove(weakReference); 
    } 
  }
}
