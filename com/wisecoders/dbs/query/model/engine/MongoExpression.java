package com.wisecoders.dbs.query.model.engine;

import java.util.ArrayList;

public class MongoExpression extends ArrayList {
  public ArrayList a = new ArrayList();
  
  public MongoExpression a(MongoExpression$When paramMongoExpression$When, String paramString1, String paramString2) {
    this.a.add(new a(this, paramMongoExpression$When, paramString1, paramString2));
    return this;
  }
  
  public String toString() {
    return a((String)null);
  }
  
  public String a(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    for (String str1 : this) {
      if (stringBuilder.length() > 0)
        stringBuilder.append(","); 
      stringBuilder.append(str1);
      if (paramString != null)
        stringBuilder.append(paramString); 
    } 
    String str = stringBuilder.toString();
    for (int i = this.a.size() - 1; i > -1; i--) {
      a a = this.a.get(i);
      if (a.b == MongoExpression$When.a || (a.b == MongoExpression$When.b && size() > 0) || (a.b == MongoExpression$When.c && size() > 1))
        str = a.c + a.c + str; 
    } 
    return str;
  }
  
  public void a() {
    this.a.clear();
  }
}
