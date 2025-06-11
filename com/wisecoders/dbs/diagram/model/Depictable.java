package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.schema.GroovyMethod;

public interface Depictable {
  void moveTo(double paramDouble1, double paramDouble2);
  
  void translate(double paramDouble1, double paramDouble2);
  
  @GroovyMethod
  Rect getPosition();
  
  boolean isMarkedForDeletion();
  
  void refresh();
  
  double getGlobalCost(String paramString);
  
  double getDependencyCost(Depictable paramDepictable);
}
