package com.wisecoders.dbs.sys;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public class UniqueCopyOnWriteArrayList extends CopyOnWriteArrayList {
  public boolean add(Object paramObject) {
    if (paramObject != null && !contains(paramObject))
      super.add(paramObject); 
    return true;
  }
  
  public boolean addAll(Collection paramCollection) {
    if (paramCollection == null)
      return false; 
    for (Object object : paramCollection) {
      if (object != null && !contains(object))
        super.add(object); 
    } 
    return true;
  }
}
