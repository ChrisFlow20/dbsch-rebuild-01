package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Relation;

public class FxHierarchyNode {
  public final Entity a;
  
  private final Relation b;
  
  FxHierarchyNode(Entity paramEntity, Relation paramRelation) {
    this.a = paramEntity;
    this.b = paramRelation;
  }
  
  public Entity a() {
    return this.a;
  }
  
  public Relation b() {
    return this.b;
  }
  
  public String toString() {
    return (this.a != null) ? (this.a.getNameWithSchemaName() + this.a.getNameWithSchemaName()) : "Selection";
  }
}
