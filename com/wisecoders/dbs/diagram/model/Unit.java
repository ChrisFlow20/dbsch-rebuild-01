package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.util.Map;

@DoNotObfuscate
public interface Unit {
  Entity getEntity();
  
  Entity getParentEntity();
  
  String getName();
  
  String getDisplayName(Diagram paramDiagram);
  
  String getSymbolicName();
  
  void markForDeletion();
  
  boolean isMarkedForDeletion();
  
  void setComment(String paramString);
  
  String getComment();
  
  Map getCommentTags();
  
  void setCommentTags(Map paramMap);
  
  void setCommentTag(String paramString1, String paramString2);
  
  String getCommentTag(String paramString);
  
  Boolean is(UnitProperty paramUnitProperty);
  
  String ref();
}
