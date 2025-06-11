package com.wisecoders.dbs.diagram.fx.notation;

import com.wisecoders.dbs.schema.RelationCardinality;

public class IEWithArrowsNotation extends IENotation {
  public static final String b = "IEWithArrows";
  
  public RelationCardinality a(RelationCardinality paramRelationCardinality, boolean paramBoolean1, boolean paramBoolean2) {
    return paramBoolean1 ? RelationCardinality.a : RelationCardinality.f;
  }
  
  public boolean a() {
    return true;
  }
  
  public String toString() {
    return "IEWithArrows";
  }
}
