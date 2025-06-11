package com.wisecoders.dbs.query.model.engine;

import com.wisecoders.dbs.query.model.items.AbstractQueryColumn;
import com.wisecoders.dbs.query.model.items.QueryAggregate;
import com.wisecoders.dbs.query.model.items.QueryColumn;
import com.wisecoders.dbs.query.model.items.QueryOrderBy;
import java.util.ArrayList;
import java.util.LinkedHashMap;

class c {
  public final int a;
  
  public final ArrayList b = new ArrayList();
  
  final ArrayList c = new ArrayList();
  
  public final LinkedHashMap d = new LinkedHashMap<>();
  
  public final ArrayList e = new ArrayList();
  
  public final ArrayList f = new ArrayList();
  
  public final ArrayList g = new ArrayList();
  
  c(QuerySQLGenerator paramQuerySQLGenerator) {
    this(paramQuerySQLGenerator, 0);
  }
  
  c(QuerySQLGenerator paramQuerySQLGenerator, int paramInt) {
    this.a = paramInt;
  }
  
  public void a(AbstractQueryColumn paramAbstractQueryColumn) {
    this.b.add(paramAbstractQueryColumn);
  }
  
  public void a(QueryColumn paramQueryColumn) {
    this.c.add(paramQueryColumn);
  }
  
  public void a(String paramString, int paramInt) {
    this.d.put(paramString, Integer.valueOf(paramInt));
  }
  
  public void a(String paramString) {
    this.e.add(paramString);
  }
  
  public void b(String paramString) {
    this.f.add(paramString);
  }
  
  public void a(QueryOrderBy paramQueryOrderBy) {
    this.g.add(paramQueryOrderBy);
  }
  
  public String toString() {
    StringBuilder stringBuilder1 = new StringBuilder();
    StringBuilder stringBuilder2 = new StringBuilder("\n");
    byte b;
    for (b = 0; b < this.a; b++)
      stringBuilder2.append("\t"); 
    if (this.a > 0)
      stringBuilder1.append(stringBuilder2); 
    stringBuilder1.append("SELECT ");
    if (this.b.isEmpty()) {
      if (this.a == 0) {
        stringBuilder1.append("count(*) ");
      } else {
        stringBuilder1.append("1 ");
      } 
    } else {
      b = 1;
      for (AbstractQueryColumn abstractQueryColumn : this.b) {
        if (b == 0)
          stringBuilder1.append(", "); 
        if (abstractQueryColumn instanceof QueryColumn) {
          stringBuilder1.append(abstractQueryColumn.ref());
        } else if (abstractQueryColumn instanceof QueryAggregate) {
          stringBuilder1.append(((QueryAggregate)abstractQueryColumn).b(this.h.b));
        } 
        if (abstractQueryColumn.c())
          stringBuilder1.append(" ").append(abstractQueryColumn.b()); 
        b = 0;
      } 
    } 
    stringBuilder1.append(stringBuilder2).append("FROM ");
    for (String str : this.d.keySet()) {
      int i = ((Integer)this.d.get(str)).intValue();
      if (i > 0) {
        stringBuilder1.append(stringBuilder2);
        stringBuilder1.append("\t");
      } 
      stringBuilder1.append(str).append(" ");
    } 
    if (!this.e.isEmpty()) {
      stringBuilder1.append(stringBuilder2).append("WHERE ");
      b = 1;
      for (String str : this.e) {
        if (b == 0)
          stringBuilder1.append(" AND").append(stringBuilder2).append("\t"); 
        stringBuilder1.append(str);
        b = 0;
      } 
    } 
    if ((this.h.a.d() || !this.c.equals(this.b)) && !this.c.isEmpty()) {
      stringBuilder1.append(stringBuilder2).append("GROUP BY ");
      b = 1;
      for (QueryColumn queryColumn : this.c) {
        if (b == 0)
          stringBuilder1.append(", "); 
        stringBuilder1.append(queryColumn.ref());
        b = 0;
      } 
    } 
    if (!this.f.isEmpty()) {
      stringBuilder1.append(stringBuilder2).append("HAVING ");
      b = 1;
      for (String str : this.f) {
        if (b == 0)
          stringBuilder1.append(" AND").append(stringBuilder2).append("\t"); 
        stringBuilder1.append(str);
        b = 0;
      } 
    } 
    if (!this.g.isEmpty()) {
      stringBuilder1.append(stringBuilder2).append("ORDER BY ");
      b = 1;
      for (QueryOrderBy queryOrderBy : this.g) {
        if (b == 0)
          stringBuilder1.append(", "); 
        stringBuilder1.append(queryOrderBy.b(this.h.b));
        b = 0;
      } 
    } 
    return stringBuilder1.toString();
  }
}
