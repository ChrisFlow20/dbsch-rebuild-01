package com.wisecoders.dbs.diagram.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

class a {
  a(List<?> paramList, ArrangerSpacing paramArrangerSpacing) {
    int i = Diagram.cell * paramArrangerSpacing.d;
    ArrayList arrayList = new ArrayList(paramList);
    ArrayList<Depictable> arrayList1 = new ArrayList();
    ArrayList<Depictable> arrayList2 = new ArrayList();
    arrayList.sort((paramDepictable1, paramDepictable2) -> {
          Double double_1 = Double.valueOf(paramDepictable1.getGlobalCost(null));
          Double double_2 = Double.valueOf(paramDepictable2.getGlobalCost(null));
          return double_2.compareTo(double_1);
        });
    for (Depictable depictable : arrayList) {
      Rect rect = depictable.getPosition();
      rect.e(i, i);
      arrayList2.sort((paramDepictable2, paramDepictable3) -> {
            Double double_1 = Double.valueOf(paramDepictable2.getDependencyCost(paramDepictable1));
            Double double_2 = Double.valueOf(paramDepictable3.getDependencyCost(paramDepictable1));
            return double_2.compareTo(double_1);
          });
      for (Iterator<Depictable> iterator = arrayList2.iterator(); iterator.hasNext(); ) {
        Depictable depictable1 = iterator.next();
        Rect rect1 = depictable1.getPosition();
        depictable.moveTo(rect1.a() + rect1.c(), rect1.b());
        if (a(depictable, arrayList1))
          break; 
        depictable.moveTo(rect1.a() + rect1.c(), rect1.b() + rect1.d() - depictable.getPosition().d());
        if (a(depictable, arrayList1))
          break; 
        depictable.moveTo(rect1.a() + rect1.c(), rect1.b() + rect1.d());
        if (a(depictable, arrayList1))
          break; 
        depictable.moveTo(rect1.a(), rect1.b() + rect1.d());
        if (a(depictable, arrayList1))
          break; 
        depictable.moveTo(rect1.a() + rect1.c() - (int)depictable.getPosition().c(), rect1.b() + rect1.d());
        if (a(depictable, arrayList1))
          break; 
        depictable.moveTo(rect1.a() - rect.c(), rect1.b() + rect1.d());
        if (a(depictable, arrayList1))
          break; 
        depictable.moveTo(rect1.a() - rect.c(), rect1.b() + rect1.d() - (int)depictable.getPosition().d());
        if (a(depictable, arrayList1))
          break; 
        depictable.moveTo(rect1.a() - rect.c(), rect1.b());
        if (a(depictable, arrayList1))
          break; 
        depictable.moveTo(rect1.a() - rect.c(), rect1.b() - rect.d());
        if (a(depictable, arrayList1))
          break; 
        depictable.moveTo(rect1.a(), rect1.b() - rect.d());
        if (a(depictable, arrayList1))
          break; 
        depictable.moveTo(rect1.a() + rect1.c(), rect1.b() - rect.d());
        if (a(depictable, arrayList1))
          break; 
        iterator.remove();
      } 
      arrayList1.add(depictable);
      arrayList2.add(depictable);
    } 
  }
  
  public static boolean a(Depictable paramDepictable, Collection paramCollection) {
    for (Depictable depictable : paramCollection) {
      if (paramDepictable != depictable && depictable.getPosition().b(paramDepictable.getPosition()))
        return false; 
    } 
    return true;
  }
}
