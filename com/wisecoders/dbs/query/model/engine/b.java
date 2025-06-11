package com.wisecoders.dbs.query.model.engine;

import com.wisecoders.dbs.query.model.items.QueryTable;

class b {
  public final MongoExpression a = new MongoExpression();
  
  public final MongoExpression b = new MongoExpression();
  
  public final MongoExpression c = new MongoExpression();
  
  public final MongoExpression d = new MongoExpression();
  
  public final MongoExpression e = new MongoExpression();
  
  public final MongoExpression f = new MongoExpression();
  
  private b(QueryMongoGenerator paramQueryMongoGenerator) {}
  
  public String a() {
    d();
    this.a.a(MongoExpression$When.b, "{", "}");
    this.b.a(MongoExpression$When.b, "{", "}");
    this.f.a(MongoExpression$When.b, "\n\t.sort(\"{", "}\")");
    this.e.a(MongoExpression$When.c, "{$and:[", "]}");
    StringBuilder stringBuilder = new StringBuilder(((QueryTable)this.g.a.a().getEntity()).b.ref());
    if (this.e.isEmpty()) {
      stringBuilder.append("\n\t.find()");
    } else {
      stringBuilder.append("\n\t.find(").append(this.e).append(")");
    } 
    if (!this.b.isEmpty())
      if (this.a.size() < this.b.size()) {
        if (!this.a.isEmpty())
          stringBuilder.append("\n\t.projection(").append(this.a.a(":1")).append(")"); 
      } else {
        stringBuilder.append("\n\t.projection(").append(this.b.a(":0")).append(")");
      }  
    if (!this.f.isEmpty())
      stringBuilder.append(this.f); 
    return stringBuilder.toString();
  }
  
  public String b() {
    d();
    this.c.a(MongoExpression$When.b, "_id:{", "}");
    MongoExpression mongoExpression = new MongoExpression();
    mongoExpression.a(MongoExpression$When.b, "\n\t\t{$group:{", "}}");
    mongoExpression.add((E)this.c.toString());
    mongoExpression.add((E)this.d.toString());
    this.e.a(MongoExpression$When.b, "\n\t\t{$match:", "},");
    this.e.a(MongoExpression$When.c, "{$and:[", "]}");
    this.f.a(MongoExpression$When.b, ",\n\t\t{$sort:{", "}}");
    StringBuilder stringBuilder = new StringBuilder(((QueryTable)this.g.a.a().getEntity()).b.ref());
    stringBuilder.append("\n\t.aggregate(")
      .append(this.e)
      .append(mongoExpression)
      .append(this.f)
      .append(")");
    return stringBuilder.toString();
  }
  
  private void d() {
    this.a.a();
    this.c.a();
    this.d.a();
    this.e.a();
  }
  
  public String c() {
    return this.d.isEmpty() ? a() : b();
  }
}
