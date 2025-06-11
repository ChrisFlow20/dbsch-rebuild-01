package com.wisecoders.dbs.query.model.items;

import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.schema.AttributeSpec;

public class QueryOperationColumn extends AbstractQueryColumn {
  QueryOperationColumn(QueryTable paramQueryTable, Attribute paramAttribute, String paramString) {
    super(paramQueryTable, paramAttribute, paramString);
    setTicked(true);
  }
  
  public void refresh() {
    if (this.x != null && this.x.isMarkedForDeletion())
      markForDeletion(); 
  }
  
  public AttributeSpec getSpec() {
    return AttributeSpec.functional;
  }
  
  public String getNameWithPath() {
    return getName();
  }
}
