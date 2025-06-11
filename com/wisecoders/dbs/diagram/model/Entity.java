package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.schema.Schema;
import java.util.List;

public interface Entity extends TreeUnit {
  String getName();
  
  boolean rename(String paramString);
  
  String getNameWithSchemaName();
  
  List getAttributes();
  
  List getRelations();
  
  List getImportedRelations();
  
  boolean isView();
  
  boolean isChildEntity();
  
  boolean isChildEntityArray();
  
  void refresh();
  
  Schema getSchema();
  
  boolean isVirtual();
}
