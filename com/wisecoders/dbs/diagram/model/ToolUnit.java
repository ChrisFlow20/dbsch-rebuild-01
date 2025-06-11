package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.schema.Layout;

public interface ToolUnit extends Unit {
  String getName();
  
  boolean rename(String paramString);
  
  Layout getLayout();
  
  boolean isConfirmed();
  
  void setConfirmed(boolean paramBoolean);
  
  String getKey();
}
