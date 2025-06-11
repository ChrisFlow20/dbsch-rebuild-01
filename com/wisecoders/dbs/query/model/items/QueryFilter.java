package com.wisecoders.dbs.query.model.items;

import com.wisecoders.dbs.data.filter.FilterPattern;
import com.wisecoders.dbs.data.filter.Filters;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.sys.StringUtil;

public class QueryFilter extends QueryOperationColumn {
  public QueryFilter(QueryTable paramQueryTable, Attribute paramAttribute, String paramString) {
    super(paramQueryTable, paramAttribute, paramString);
  }
  
  public String getSymbolicName() {
    return "Column";
  }
  
  public String getSymbolicIcon() {
    return "marker_column.png";
  }
  
  public QueryTable a() {
    return super.a();
  }
  
  public String getName() {
    return (this.x != null) ? StringUtil.cutOfWithDots(d().replaceAll(":c", this.x.getName()), 40) : d();
  }
  
  public String d() {
    return super.getName();
  }
  
  public String b(String paramString) {
    FilterPattern filterPattern = Filters.a(this.x, d());
    if (filterPattern != null) {
      if ("MongoDb".equals(paramString))
        return filterPattern.a(paramString, d(), this.x.ref()); 
      if (this.x instanceof QueryAggregate)
        return filterPattern.a(paramString, d(), this.x.ref()); 
      return filterPattern.a(paramString, d(), a().e() + a().e());
    } 
    return d();
  }
  
  public String ref() {
    return (this.x != null) ? getName().replace(this.x.getName(), a().e() + a().e()) : getName();
  }
}
