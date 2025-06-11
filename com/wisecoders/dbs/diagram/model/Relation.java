package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.schema.RelationCardinality;
import com.wisecoders.dbs.schema.RelationType;
import java.util.List;

public interface Relation extends Unit {
  public static final int b = 1;
  
  public static final int c = 2;
  
  public static final int d = 4;
  
  String getName();
  
  Entity getEntity();
  
  Entity getTargetEntity();
  
  List getSourceAttributes();
  
  List getTargetAttributes();
  
  Attribute getColumnAt(int paramInt, boolean paramBoolean);
  
  String getColumnsName();
  
  String getCascadeText();
  
  int getColumnCount();
  
  boolean isAutoReference();
  
  Attribute getLastSourceAttribute();
  
  Attribute getLastTargetAttribute();
  
  boolean isVirtual();
  
  boolean isDeduced();
  
  void setFlag(int paramInt);
  
  void resetFlag(int paramInt);
  
  boolean hasFlag(int paramInt);
  
  RelationType getRelationType();
  
  RelationCardinality getRelationCardinality();
}
