package com.wisecoders.dbs.diagram.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class DependencyTree {
  final LinkedHashMap a = new LinkedHashMap<>();
  
  final List b = new ArrayList();
  
  public DependencyTree(Collection paramCollection) {
    HashMap<Object, Object> hashMap = new HashMap<>();
    for (Depictable depictable1 : paramCollection) {
      Depictable depictable2 = null;
      double d = -1.0D;
      for (Depictable depictable : paramCollection) {
        if (depictable1 != depictable) {
          double d1 = depictable1.getDependencyCost(depictable);
          if (depictable2 == null || d1 > d) {
            depictable2 = depictable;
            d = d1;
          } 
        } 
      } 
      if (depictable2 != null) {
        this.a.put(depictable1, depictable2);
        double d1 = ((Double)hashMap.getOrDefault(depictable2, Double.valueOf(0.0D))).doubleValue();
        hashMap.put(depictable2, Double.valueOf(d1 + d));
      } 
    } 
    this.b.addAll(paramCollection);
    Collections.sort(this.b, new DependencyTree$1(this, hashMap));
  }
}
