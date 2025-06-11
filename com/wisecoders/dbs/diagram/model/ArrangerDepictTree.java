package com.wisecoders.dbs.diagram.model;

import java.util.List;

public class ArrangerDepictTree {
  private final List a;
  
  private final ArrangerSpacing b;
  
  public ArrangerDepictTree(List paramList, ArrangerSpacing paramArrangerSpacing) {
    this.a = paramList;
    this.b = paramArrangerSpacing;
    Depict depict = null;
    for (Depict depict1 : paramList) {
      if (a(depict1.getEntity()) == null)
        depict = depict1; 
    } 
    if (depict != null)
      (new b(this, depict)).b(); 
  }
  
  private Entity a(Entity paramEntity) {
    for (Relation relation : paramEntity.getRelations()) {
      if (!relation.isVirtual())
        return relation.getTargetEntity(); 
    } 
    return null;
  }
  
  private Depict b(Entity paramEntity) {
    for (Depict depict : this.a) {
      if (depict.getEntity() == paramEntity)
        return depict; 
    } 
    return null;
  }
}
