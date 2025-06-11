package com.wisecoders.dbs.query.model.items;

import com.wisecoders.dbs.diagram.model.Attribute;

public class QueryOrderBy extends QueryOperationColumn {
  private boolean y = true;
  
  public QueryOrderBy(QueryTable paramQueryTable, Attribute paramAttribute, boolean paramBoolean) {
    super(paramQueryTable, paramAttribute, paramAttribute.getName());
    a(paramBoolean);
  }
  
  public String getSymbolicName() {
    return "Order By";
  }
  
  public String getSymbolicIcon() {
    return this.y ? "order_asc_small.png" : "order_desc_small.png";
  }
  
  public void refresh() {
    super.refresh();
    if (this.x.isMarkedForDeletion())
      markForDeletion(); 
  }
  
  public boolean d() {
    return this.y;
  }
  
  public void a(boolean paramBoolean) {
    this.y = paramBoolean;
    rename("order by " + this.x.getName() + (paramBoolean ? "" : " desc"));
  }
  
  public String b(String paramString) {
    if (this.x != null) {
      switch (paramString) {
        case "MongoDb":
          return this.x.ref() + this.x.ref();
      } 
      return a().e() + a().e() + this.x.ref();
    } 
    return toString();
  }
}
