package com.wisecoders.dbs.query.model.items;

import com.wisecoders.dbs.data.filter.Filterable;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Diagram;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.query.model.engine.Aggregate;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.ChildEntity;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class QueryTable extends AbstractUnit implements Filterable, Entity {
  public final Query a;
  
  public final Entity b;
  
  public final Folder c = new Folder("Columns", "Column", this, AbstractQueryColumn.class, false);
  
  public final Folder d = new Folder("Foreign Keys", "Foreign Key", this, QueryRelation.class, false);
  
  private final Folder[] f = new Folder[] { this.c, this.d };
  
  final List e = new CopyOnWriteArrayList();
  
  private String g;
  
  public QueryTable(Query paramQuery, Entity paramEntity, String paramString) {
    super(paramEntity.getName());
    this.a = paramQuery;
    this.g = paramString;
    this.b = paramEntity;
    for (Attribute attribute : paramEntity.getAttributes()) {
      QueryColumn queryColumn = new QueryColumn(this, attribute);
      this.c.add(queryColumn);
    } 
  }
  
  public QueryTable a(Relation paramRelation) {
    return a(paramRelation, (String)null);
  }
  
  public QueryTable a(Relation paramRelation, String paramString) {
    Entity entity = (paramRelation.getEntity() == f()) ? paramRelation.getTargetEntity() : paramRelation.getEntity();
    QueryTable queryTable = this.a.a(entity);
    QueryRelation queryRelation = new QueryRelation(queryTable, this, paramRelation);
    if (paramString != null)
      queryRelation.rename(paramString); 
    queryTable.d.add(queryRelation);
    return queryTable;
  }
  
  public void b(String paramString) {
    if (paramString != null)
      this.g = paramString; 
  }
  
  public void a(boolean paramBoolean1, boolean paramBoolean2) {
    for (AbstractQueryColumn abstractQueryColumn : b()) {
      abstractQueryColumn.setTicked((paramBoolean1 && abstractQueryColumn.x.getSpec() != AttributeSpec.functional));
      if (paramBoolean2 && abstractQueryColumn.x instanceof Column) {
        ChildEntity childEntity = ((Column)abstractQueryColumn.x).getChildEntity();
        if (childEntity != null)
          for (Depict depict : this.a.getDepicts()) {
            if (((QueryTable)depict.getEntity()).b == childEntity)
              ((QueryTable)depict.getEntity()).a(paramBoolean1, paramBoolean2); 
          }  
      } 
    } 
  }
  
  public QueryTable a() {
    double d1 = 0.0D, d2 = 0.0D;
    for (Depict depict : this.a.b.depicts) {
      d1 = Math.max(d1, depict.getPosition().a() + depict.getPosition().c());
      d2 = Math.max(d2, depict.getPosition().b());
    } 
    this.a.b.attach(this, d1 + 100.0D, 0.0D);
    a(true, false);
    return this;
  }
  
  public Folder b() {
    return this.c;
  }
  
  public Folder c() {
    return this.d;
  }
  
  public int getChildrenCount() {
    return this.f.length;
  }
  
  public TreeUnit getChildAt(int paramInt) {
    return this.f[paramInt];
  }
  
  public String toString() {
    return (this.g != null) ? (getName() + " " + getName()) : getName();
  }
  
  public String d() {
    return this.g;
  }
  
  public String e() {
    return StringUtil.isFilledTrim(this.g) ? (this.g + ".") : "";
  }
  
  public String getDisplayName(Diagram paramDiagram) {
    return getName() + getName();
  }
  
  public Entity f() {
    return this.b;
  }
  
  public Schema getSchema() {
    return this.b.getSchema();
  }
  
  public Folder g() {
    return null;
  }
  
  public boolean isView() {
    return this.b.isView();
  }
  
  public boolean isChildEntity() {
    return false;
  }
  
  public boolean isChildEntityArray() {
    return false;
  }
  
  public QueryFilter a(Attribute paramAttribute, String paramString) {
    QueryFilter queryFilter = new QueryFilter(this, paramAttribute, paramString);
    queryFilter.setTicked(true);
    int i = this.c.size();
    while (i > 0 && this.c.get(i - 1) instanceof QueryOrderBy)
      i--; 
    this.c.add(i, queryFilter);
    return queryFilter;
  }
  
  public void b(Attribute paramAttribute, String paramString) {
    for (AbstractQueryColumn abstractQueryColumn : this.c) {
      if (abstractQueryColumn.x == paramAttribute && abstractQueryColumn instanceof QueryFilter) {
        QueryFilter queryFilter = (QueryFilter)abstractQueryColumn;
        if (paramString != null && paramString.equals(queryFilter.d())) {
          abstractQueryColumn.markForDeletion();
          refresh();
        } 
      } 
    } 
  }
  
  public QueryOrderBy b(Attribute paramAttribute, boolean paramBoolean) {
    for (AbstractQueryColumn abstractQueryColumn : this.c) {
      if (abstractQueryColumn instanceof QueryOrderBy) {
        QueryOrderBy queryOrderBy = (QueryOrderBy)abstractQueryColumn;
        if (queryOrderBy.x == paramAttribute)
          queryOrderBy.a(paramBoolean); 
        return queryOrderBy;
      } 
    } 
    if (this.b.getAttributes().contains(paramAttribute)) {
      QueryOrderBy queryOrderBy = new QueryOrderBy(this, paramAttribute, paramBoolean);
      this.c.add(queryOrderBy);
      return queryOrderBy;
    } 
    return null;
  }
  
  public void a(Attribute paramAttribute, boolean paramBoolean) {
    b(paramAttribute, paramBoolean);
  }
  
  public void b(Attribute paramAttribute) {
    for (AbstractQueryColumn abstractQueryColumn : this.c) {
      if (abstractQueryColumn instanceof QueryOrderBy && abstractQueryColumn.x == paramAttribute) {
        abstractQueryColumn.markForDeletion();
        refresh();
      } 
    } 
  }
  
  public int a(Attribute paramAttribute) {
    return 0;
  }
  
  public QueryAggregate a(Attribute paramAttribute, Aggregate paramAggregate) {
    QueryAggregate queryAggregate = new QueryAggregate(this, paramAttribute, paramAggregate);
    byte b = 0;
    while (b < this.c.size() && this.c.get(b) instanceof QueryColumn)
      b++; 
    this.c.add(b, queryAggregate);
    return queryAggregate;
  }
  
  public QueryColumn c(Attribute paramAttribute) {
    for (AbstractQueryColumn abstractQueryColumn : this.c) {
      if (abstractQueryColumn instanceof QueryColumn) {
        QueryColumn queryColumn = (QueryColumn)abstractQueryColumn;
        if (queryColumn.x == paramAttribute)
          return queryColumn; 
      } 
    } 
    return null;
  }
  
  public Column d(String paramString) {
    return null;
  }
  
  public TreeUnit getParent() {
    return this.a;
  }
  
  public String getSymbolicName() {
    return this.b.getSymbolicName();
  }
  
  public String getSymbolicIcon() {
    return "table.png";
  }
  
  public Glyph getSymbolicGlyph() {
    return BootstrapIcons.tablet_fill;
  }
  
  public void refresh() {
    if (this.b.isMarkedForDeletion())
      markForDeletion(); 
    for (AbstractQueryColumn abstractQueryColumn : b()) {
      if (abstractQueryColumn.isMarkedForDeletion()) {
        b().remove(abstractQueryColumn);
        continue;
      } 
      abstractQueryColumn.refresh();
    } 
    for (QueryRelation queryRelation : this.d) {
      if (queryRelation.isMarkedForDeletion() || queryRelation.a() == null || queryRelation
        .a().isMarkedForDeletion() || queryRelation
        .b().isMarkedForDeletion()) {
        this.d.remove(queryRelation);
        continue;
      } 
      queryRelation.refresh();
      if (queryRelation.isMarkedForDeletion())
        this.d.remove(queryRelation); 
    } 
    this.e.removeIf(paramQueryRelation -> (paramQueryRelation.isMarkedForDeletion() || paramQueryRelation.a() != this || paramQueryRelation.b().isMarkedForDeletion()));
  }
  
  public String e(String paramString) {
    return this.b.ref() + " " + this.b.ref();
  }
  
  public List getImportedRelations() {
    return this.e;
  }
  
  public String getNameWithSchemaName() {
    return this.b.getNameWithSchemaName() + " " + this.b.getNameWithSchemaName();
  }
  
  public Entity getEntity() {
    return this;
  }
  
  public String a(String paramString) {
    return Dbms.get(this.a.a.project.getDbId()).escapeString(paramString);
  }
}
