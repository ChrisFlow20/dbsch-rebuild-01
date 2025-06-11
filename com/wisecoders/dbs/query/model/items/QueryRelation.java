package com.wisecoders.dbs.query.model.items;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.schema.RelationCardinality;
import com.wisecoders.dbs.schema.RelationType;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QueryRelation extends AbstractUnit implements Relation {
  protected final List g = new ArrayList();
  
  protected final List h = new ArrayList();
  
  public final Relation a;
  
  protected final QueryTable e;
  
  protected QueryTable f;
  
  public QueryRelation(QueryTable paramQueryTable1, QueryTable paramQueryTable2, Relation paramRelation) {
    super("Inner Join");
    this.e = paramQueryTable1;
    this.f = paramQueryTable2;
    this.a = paramRelation;
    this.f.e.add(this);
    Iterator<Attribute> iterator1 = paramRelation.getSourceAttributes().iterator();
    for (Iterator<Attribute> iterator2 = paramRelation.getTargetAttributes().iterator(); iterator1.hasNext() && iterator2.hasNext(); ) {
      Attribute attribute1 = iterator1.next(), attribute2 = iterator2.next();
      QueryColumn queryColumn1 = paramQueryTable1.c(attribute1);
      QueryColumn queryColumn2 = paramQueryTable2.c(attribute2);
      if (queryColumn1 == null || queryColumn2 == null) {
        queryColumn1 = paramQueryTable1.c(attribute2);
        queryColumn2 = paramQueryTable2.c(attribute1);
      } 
      if (queryColumn1 != null && queryColumn2 != null)
        a(queryColumn1, queryColumn2); 
    } 
  }
  
  public AbstractQueryColumn a(int paramInt, boolean paramBoolean) {
    return paramBoolean ? this.h.get(paramInt) : getTargetAttributes().get(paramInt);
  }
  
  public TreeUnit getParent() {
    return this.e.d;
  }
  
  public void refresh() {
    if (this.a.isMarkedForDeletion() || this.f == null || this.f
      .isMarkedForDeletion() || this.e.isMarkedForDeletion())
      markForDeletion(); 
  }
  
  public String getSymbolicName() {
    return "Join";
  }
  
  public String getSymbolicIcon() {
    return "marker_fk.png";
  }
  
  public Glyph getSymbolicGlyph() {
    return BootstrapIcons.arrow_up_right;
  }
  
  public void a(AbstractQueryColumn paramAbstractQueryColumn1, AbstractQueryColumn paramAbstractQueryColumn2) {
    if (!i && (paramAbstractQueryColumn1 == null || paramAbstractQueryColumn2 == null))
      throw new AssertionError(); 
    if (this.f == null)
      this.f = paramAbstractQueryColumn2.a(); 
    this.h.add(paramAbstractQueryColumn1);
    this.g.add(paramAbstractQueryColumn2);
  }
  
  public List getTargetAttributes() {
    return this.g;
  }
  
  public List getSourceAttributes() {
    return this.h;
  }
  
  public QueryTable a() {
    return this.f;
  }
  
  public QueryTable b() {
    return this.e;
  }
  
  public String getColumnsName() {
    return this.a.getColumnsName();
  }
  
  public String getCascadeText() {
    return this.a.getCascadeText();
  }
  
  public boolean isAutoReference() {
    return this.a.isAutoReference();
  }
  
  public AbstractQueryColumn c() {
    if (this.h.isEmpty())
      return null; 
    return this.h.get(this.h.size() - 1);
  }
  
  public AbstractQueryColumn d() {
    if (this.g.isEmpty())
      return null; 
    return this.g.get(this.g.size() - 1);
  }
  
  public void setFlag(int paramInt) {}
  
  public void resetFlag(int paramInt) {}
  
  public boolean hasFlag(int paramInt) {
    return ((paramInt & 0x3) > 0);
  }
  
  public int getColumnCount() {
    return Math.min(getSourceAttributes().size(), getTargetAttributes().size());
  }
  
  public RelationType getRelationType() {
    return RelationType.b;
  }
  
  public RelationCardinality getRelationCardinality() {
    return RelationCardinality.d;
  }
  
  public boolean isDeduced() {
    return false;
  }
}
