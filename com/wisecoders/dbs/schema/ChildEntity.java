package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@DoNotObfuscate
public class ChildEntity extends AbstractUnit implements Entity {
  public final Column ownerColumn;
  
  public final ChildEntityRelation parentRelation;
  
  public final Folder columns = new Folder("Field", this, Column.class, false);
  
  public final Folder relations = new Folder("Relations", this, ChildEntityRelation.class, false);
  
  public final List importedRelations = new CopyOnWriteArrayList();
  
  public ChildEntity(Column paramColumn) {
    super(paramColumn.getName());
    this.ownerColumn = paramColumn;
    if (paramColumn.getParentColumn() == null) {
      this.parentRelation = new ChildEntityRelation(paramColumn.table, paramColumn);
      this.relations.add(this.parentRelation);
      paramColumn.table.importedRelations.add(this.parentRelation);
    } else {
      this.parentRelation = new ChildEntityRelation(paramColumn.getParentColumn().getChildEntity(), paramColumn);
      this.relations.add(this.parentRelation);
      (paramColumn.getParentColumn().getChildEntity()).importedRelations.add(this.parentRelation);
    } 
  }
  
  public void refresh() {
    if (this.ownerColumn.isMarkedForDeletion())
      markForDeletion(); 
    if (isMarkedForDeletion()) {
      if (!this.ownerColumn.isMarkedForDeletion())
        this.ownerColumn.markForDeletion(); 
      for (Column column : this.columns)
        column.markForDeletion(); 
    } 
    this.relations.refresh();
    this.columns.refresh();
  }
  
  @GroovyMethod
  public Column createColumn(String paramString1, String paramString2) {
    return createColumn(paramString1, DbmsTypes.get((getEntity().getSchema()).project.getDbId()).getType(paramString2));
  }
  
  @GroovyMethod
  public Column createColumn(String paramString, DataType paramDataType) {
    Column column = new Column(this.ownerColumn.table, paramString, paramDataType);
    column.setUnitProperty(UnitProperty.c, this);
    this.columns.add(column);
    return column;
  }
  
  public TreeUnit getParent() {
    return (this.ownerColumn.getParentColumn() != null) ? this.ownerColumn.getParentColumn() : this.ownerColumn.table.getAttributes();
  }
  
  public String getSymbolicName() {
    return "Sub-document";
  }
  
  public String getSymbolicIcon() {
    return "table_small.png";
  }
  
  public Glyph getSymbolicGlyph() {
    return BootstrapIcons.tablet_fill;
  }
  
  public String getNameWithSchemaName() {
    return getName();
  }
  
  public List getAttributes() {
    return this.columns;
  }
  
  public List getRelations() {
    return this.relations;
  }
  
  public List getImportedRelations() {
    return this.importedRelations;
  }
  
  public boolean isView() {
    return false;
  }
  
  public boolean isChildEntity() {
    return true;
  }
  
  public boolean isChildEntityArray() {
    return this.ownerColumn.hasMarker(32768);
  }
  
  public Schema getSchema() {
    return this.ownerColumn.table.getSchema();
  }
  
  public Entity getEntity() {
    return this.ownerColumn.table;
  }
  
  public Entity getParentEntity() {
    return this;
  }
  
  public String getComment() {
    return this.ownerColumn.getComment();
  }
  
  public void setComment(String paramString) {
    this.ownerColumn.setComment(paramString);
  }
}
