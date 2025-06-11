package com.wisecoders.dbs.query.model.items;

import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.query.model.engine.Aggregate;
import com.wisecoders.dbs.schema.DataTypeFormat;
import java.util.regex.Matcher;

public class QueryAggregate extends QueryOperationColumn {
  public final Aggregate y;
  
  public QueryAggregate(QueryTable paramQueryTable, Attribute paramAttribute, Aggregate paramAggregate) {
    super(paramQueryTable, paramAttribute, paramAggregate.toString());
    this.y = paramAggregate;
  }
  
  public String getName() {
    return c() ? (super.getName() + " " + super.getName()) : super.getName();
  }
  
  public String getSymbolicName() {
    return "Column";
  }
  
  public String getSymbolicIcon() {
    return "marker_column.png";
  }
  
  public void refresh() {
    if (this.x == null) {
      rename(this.y.toString());
    } else if (this.x.isMarkedForDeletion()) {
      markForDeletion();
    } else {
      rename(this.y.g.replace("?", Matcher.quoteReplacement(this.x.getName())));
    } 
  }
  
  public QueryTable a() {
    return super.a();
  }
  
  public boolean isSelectable() {
    return true;
  }
  
  public String b(String paramString) {
    if (this.x != null)
      return a(this.y.a(paramString), 
          "MongoDb".equals(paramString) ? this.x.ref().replaceAll("'", "") : (
          a().e() + a().e())); 
    return this.y.toString();
  }
  
  private String a(String paramString1, String paramString2) {
    if (paramString2 != null)
      paramString1 = paramString1.replace("?", paramString2); 
    return paramString1;
  }
  
  public String getTypeString(DataTypeFormat paramDataTypeFormat) {
    return "";
  }
}
