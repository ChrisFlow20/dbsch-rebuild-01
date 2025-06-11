package com.wisecoders.dbs.query.model.engine;

import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.query.model.items.AbstractQueryColumn;
import com.wisecoders.dbs.query.model.items.Query;
import com.wisecoders.dbs.query.model.items.QueryAggregate;
import com.wisecoders.dbs.query.model.items.QueryFilter;
import com.wisecoders.dbs.query.model.items.QueryOrderBy;
import com.wisecoders.dbs.query.model.items.QueryRelation;
import com.wisecoders.dbs.query.model.items.QueryTable;
import java.util.ArrayList;

public class QueryMongoGenerator implements QueryGenerator {
  private final Query a;
  
  private final String b;
  
  public QueryMongoGenerator(Query paramQuery, String paramString) {
    this.a = paramQuery;
    this.b = paramString;
  }
  
  public String a() {
    Depict depict = this.a.a();
    if (depict != null) {
      b b = new b(this);
      a((QueryTable)depict.getEntity(), b, new ArrayList());
      return b.c();
    } 
    return null;
  }
  
  private void a(QueryTable paramQueryTable, b paramb, ArrayList<QueryTable> paramArrayList) {
    if (paramQueryTable != null) {
      paramArrayList.add(paramQueryTable);
      String str = this.a.a.project.getDbId();
      for (AbstractQueryColumn abstractQueryColumn : paramQueryTable.b()) {
        if (abstractQueryColumn instanceof com.wisecoders.dbs.query.model.items.QueryColumn) {
          String str1 = abstractQueryColumn.x.ref();
          if (abstractQueryColumn.isTicked()) {
            paramb.a.add((E)str1);
            paramb.c.add((E)(str1 + ":'$" + str1 + "'"));
            continue;
          } 
          paramb.b.add((E)str1);
          continue;
        } 
        if (abstractQueryColumn.isTicked() && abstractQueryColumn instanceof QueryAggregate) {
          QueryAggregate queryAggregate = (QueryAggregate)abstractQueryColumn;
          paramb.d.add((E)queryAggregate.b("MongoDb"));
        } 
      } 
      for (AbstractQueryColumn abstractQueryColumn : paramQueryTable.b()) {
        if (abstractQueryColumn.isTicked() && abstractQueryColumn instanceof QueryFilter) {
          QueryFilter queryFilter = (QueryFilter)abstractQueryColumn;
          paramb.e.add((E)queryFilter.b(str));
        } 
        if (abstractQueryColumn.isTicked() && abstractQueryColumn instanceof QueryOrderBy)
          paramb.f.add((E)((QueryOrderBy)abstractQueryColumn).b(str)); 
      } 
      for (QueryRelation queryRelation : paramQueryTable.getImportedRelations()) {
        if (!paramArrayList.contains(queryRelation.b()))
          a(queryRelation.b(), paramb, paramArrayList); 
      } 
    } 
  }
}
