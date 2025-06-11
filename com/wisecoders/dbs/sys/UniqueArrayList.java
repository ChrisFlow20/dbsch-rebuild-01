package com.wisecoders.dbs.sys;

import java.util.ArrayList;
import java.util.Collection;

public class UniqueArrayList extends ArrayList {
  public UniqueArrayList() {}
  
  public UniqueArrayList(Object[] paramArrayOfObject) {
    a(paramArrayOfObject);
  }
  
  public boolean add(Object paramObject) {
    if (paramObject != null && !contains(paramObject))
      super.add(paramObject); 
    return true;
  }
  
  public boolean a(Object[] paramArrayOfObject) {
    if (paramArrayOfObject != null)
      for (Object object : paramArrayOfObject)
        add(object);  
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
