package com.wisecoders.dbs.data.filter;

import com.wisecoders.dbs.diagram.model.Attribute;

public interface Filterable {
  Object c(Attribute paramAttribute, String paramString);
  
  void b(Attribute paramAttribute, String paramString);
  
  void a(Attribute paramAttribute, boolean paramBoolean);
  
  void b(Attribute paramAttribute);
  
  int a(Attribute paramAttribute);
  
  String a(String paramString);
}
