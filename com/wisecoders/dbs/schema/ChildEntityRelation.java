package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.ArrayList;
import java.util.List;

public class ChildEntityRelation extends AbstractUnit implements Relation {
  private final List a = new ArrayList();
  
  private final List e = new ArrayList();
  
  private final Column f;
  
  private final Entity g;
  
  public ChildEntityRelation(Entity paramEntity, Column paramColumn) {
    super("Json");
    this.g = paramEntity;
    this.f = paramColumn;
    this.a.add(paramColumn);
  }
  
  public String getName() {
    return "JSon";
  }
  
  public void refresh() {
    if (this.f.isMarkedForDeletion())
      markForDeletion(); 
  }
  
  public TreeUnit getParent() {
    return this.f.getCreateChildEntity();
  }
  
  public String getSymbolicIcon() {
    return "field_json.png";
  }
  
  public Entity getEntity() {
    return this.f.getChildEntity();
  }
  
  public Entity getTargetEntity() {
    return this.g;
  }
  
  public List getSourceAttributes() {
    return this.e;
  }
  
  public List getTargetAttributes() {
    return this.a;
  }
  
  public Column a(int paramInt, boolean paramBoolean) {
    return paramBoolean ? null : this.a.get(paramInt);
  }
  
  public String getColumnsName() {
    return "";
  }
  
  public String getCascadeText() {
    return "";
  }
  
  public boolean isAutoReference() {
    return false;
  }
  
  public Attribute getLastSourceAttribute() {
    return null;
  }
  
  public Attribute getLastTargetAttribute() {
    return this.f;
  }
  
  public void setFlag(int paramInt) {}
  
  public void resetFlag(int paramInt) {}
  
  public boolean hasFlag(int paramInt) {
    switch (paramInt) {
      case 1:
      case 2:
      
    } 
    return false;
  }
  
  public String getSymbolicName() {
    return "Json";
  }
  
  public Glyph getSymbolicGlyph() {
    return BootstrapIcons.key_fill;
  }
  
  public void markForDeletion() {}
  
  public boolean isMarkedForDeletion() {
    return false;
  }
  
  public void setComment(String paramString) {}
  
  public String getComment() {
    return null;
  }
  
  public int getColumnCount() {
    return Math.min(getSourceAttributes().size(), getTargetAttributes().size());
  }
  
  public RelationType getRelationType() {
    return RelationType.b;
  }
  
  public RelationCardinality getRelationCardinality() {
    return this.f.getDataType().isJsonMap() ? RelationCardinality.c : RelationCardinality.d;
  }
  
  public boolean isDeduced() {
    return false;
  }
}
