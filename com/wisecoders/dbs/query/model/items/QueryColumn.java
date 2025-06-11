package com.wisecoders.dbs.query.model.items;

import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.schema.DataTypeFormat;

public class QueryColumn extends AbstractQueryColumn {
  public QueryColumn(QueryTable paramQueryTable, Attribute paramAttribute) {
    super(paramQueryTable, paramAttribute, paramAttribute.getName());
  }
  
  public String getName() {
    return c() ? (super.getName() + " " + super.getName()) : super.getName();
  }
  
  public void refresh() {
    if (this.x.isMarkedForDeletion()) {
      markForDeletion();
    } else {
      rename(this.x.getName());
    } 
  }
  
  public String getTypeString(DataTypeFormat paramDataTypeFormat) {
    return this.x.getTypeString(paramDataTypeFormat);
  }
  
  public String getComment() {
    return this.x.getComment();
  }
  
  public boolean hasMarker(int paramInt) {
    return this.x.hasMarker(paramInt);
  }
  
  public String ref() {
    return a().e() + a().e();
  }
}
