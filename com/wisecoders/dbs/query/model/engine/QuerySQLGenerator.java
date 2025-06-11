package com.wisecoders.dbs.query.model.engine;

import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.query.model.items.AbstractQueryColumn;
import com.wisecoders.dbs.query.model.items.Query;
import com.wisecoders.dbs.query.model.items.QueryColumn;
import com.wisecoders.dbs.query.model.items.QueryFilter;
import com.wisecoders.dbs.query.model.items.QueryOrderBy;
import com.wisecoders.dbs.query.model.items.QueryRelation;
import com.wisecoders.dbs.query.model.items.QueryTable;
import java.util.ArrayList;
import java.util.Iterator;

public class QuerySQLGenerator implements QueryGenerator {
  private final Query a;
  
  private final String b;
  
  public QuerySQLGenerator(Query paramQuery) {
    this.a = paramQuery;
    this.b = paramQuery.a.project.getDbId();
  }
  
  public String a() {
    Depict depict = this.a.a();
    if (depict != null)
      return a((QueryTable)depict.getEntity(), null, 0, new c(this), new ArrayList()); 
    return null;
  }
  
  private String a(QueryTable paramQueryTable, QueryRelation paramQueryRelation, int paramInt, c paramc, ArrayList<QueryTable> paramArrayList) {
    if (paramQueryTable == null)
      return ""; 
    paramArrayList.add(paramQueryTable);
    for (AbstractQueryColumn abstractQueryColumn : paramQueryTable.b()) {
      if (abstractQueryColumn.isTicked()) {
        if (abstractQueryColumn instanceof QueryColumn) {
          paramc.a(abstractQueryColumn);
          paramc.a((QueryColumn)abstractQueryColumn);
          continue;
        } 
        if (abstractQueryColumn instanceof com.wisecoders.dbs.query.model.items.QueryAggregate)
          paramc.a(abstractQueryColumn); 
      } 
    } 
    if (paramQueryRelation == null) {
      paramc.a(paramQueryTable.e(this.b), paramInt);
    } else if (paramc.d.isEmpty()) {
      paramc.a(paramQueryTable.e(this.b), 0);
      Iterator<AbstractQueryColumn> iterator;
      Iterator iterator1;
      for (iterator = paramQueryRelation.getSourceAttributes().iterator(), iterator1 = paramQueryRelation.getTargetAttributes().iterator(); iterator.hasNext() && iterator1.hasNext();)
        paramc.a(((AbstractQueryColumn)iterator.next()).ref() + " = " + ((AbstractQueryColumn)iterator.next()).ref() + " "); 
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramQueryRelation.getName().toUpperCase()).append(" ");
      stringBuilder.append(paramQueryTable.e(this.b)).append(" ON ( ");
      boolean bool = true;
      for (Iterator<AbstractQueryColumn> iterator1 = paramQueryRelation.getSourceAttributes().iterator(), iterator2 = paramQueryRelation.getTargetAttributes().iterator(); iterator1.hasNext() && iterator2.hasNext(); ) {
        if (!bool)
          stringBuilder.append("AND "); 
        stringBuilder.append(((AbstractQueryColumn)iterator1.next()).ref())
          .append(" = ")
          .append(((AbstractQueryColumn)iterator2.next()).ref())
          .append(" ");
        bool = false;
      } 
      stringBuilder.append(" ) ");
      paramc.a(stringBuilder.toString(), paramInt);
    } 
    for (AbstractQueryColumn abstractQueryColumn : paramQueryTable.b()) {
      if (abstractQueryColumn.isTicked() && abstractQueryColumn instanceof QueryFilter) {
        QueryFilter queryFilter = (QueryFilter)abstractQueryColumn;
        if (queryFilter.x instanceof com.wisecoders.dbs.query.model.items.QueryAggregate) {
          paramc.b(queryFilter.b(this.b));
        } else {
          paramc.a(queryFilter.b(this.b));
        } 
      } 
      if (abstractQueryColumn.isTicked() && abstractQueryColumn instanceof QueryOrderBy)
        paramc.a((QueryOrderBy)abstractQueryColumn); 
    } 
    for (QueryRelation queryRelation : paramQueryTable.getImportedRelations()) {
      if (!paramArrayList.contains(queryRelation.b())) {
        if ("INNER JOIN".equalsIgnoreCase(queryRelation.getName()) || "LEFT OUTER JOIN"
          .equalsIgnoreCase(queryRelation.getName()) || "FULL OUTER JOIN"
          .equalsIgnoreCase(queryRelation.getName())) {
          a(queryRelation.b(), queryRelation, paramInt + 1, paramc, paramArrayList);
          continue;
        } 
        c c1 = new c(this, paramc.a + 1);
        a(queryRelation.b(), queryRelation, 0, c1, paramArrayList);
        paramc.a(" " + queryRelation.getName().toUpperCase() + " ( " + String.valueOf(c1) + " ) ");
      } 
    } 
    return paramc.toString();
  }
}
