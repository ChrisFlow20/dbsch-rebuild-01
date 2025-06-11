package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.sql.parser.DQLParser;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.List;

@DoNotObfuscate
public class View extends AbstractTable implements DbUnit {
  public final Schema schema;
  
  public final Folder columns = new Folder("Columns", "Column", this, Column.class, false);
  
  private final Folder a = new Folder("Virtual Foreign Keys", "Virtual Foreign Keys", this, ForeignKey.class);
  
  private final Folder[] b = new Folder[] { this.columns, this.a };
  
  private String c;
  
  public View(Schema paramSchema, String paramString) {
    super(paramString);
    this.schema = paramSchema;
  }
  
  public Schema getSchema() {
    return this.schema;
  }
  
  public Folder getAttributes() {
    return this.columns;
  }
  
  public Folder getRelations() {
    return this.a;
  }
  
  public int getChildrenCount() {
    return this.b.length;
  }
  
  public TreeUnit getChildAt(int paramInt) {
    return this.b[paramInt];
  }
  
  public String getSymbolicName() {
    return "View";
  }
  
  public String getSymbolicIcon() {
    return null;
  }
  
  public Glyph getSymbolicGlyph() {
    return BootstrapIcons.eye;
  }
  
  public TreeUnit getParent() {
    return this.schema.views;
  }
  
  public String getScript() {
    return this.c;
  }
  
  public void setScript(String paramString) {
    this.c = paramString;
    TreeUnit.touch(this);
  }
  
  public Column createColumn(String paramString, DataType paramDataType) {
    Column column = new Column(this, paramString, paramDataType);
    this.columns.add(column);
    return column;
  }
  
  public Column getColumn(String paramString) {
    return (Column)this.columns.getByName(paramString);
  }
  
  public boolean isView() {
    return true;
  }
  
  public ForeignKey createRelation(String paramString) {
    ForeignKey foreignKey = new ForeignKey(this, paramString);
    foreignKey.setVirtual(true);
    this.a.add(foreignKey);
    return foreignKey;
  }
  
  public void deduceVirtualFks() {
    if (this.c != null) {
      for (ForeignKey foreignKey : this.a) {
        if (foreignKey.isVirtual() && foreignKey.isDeduced())
          foreignKey.markForDeletion(); 
      } 
      DQLParser dQLParser = new DQLParser((getSchema()).project);
      dQLParser.a(this.c);
      dQLParser.a(this);
    } 
  }
  
  public void refresh() {
    this.columns.refresh();
    this.a.refresh();
    this.importedRelations.removeIf(paramRelation -> (paramRelation.isMarkedForDeletion() || paramRelation.getTargetEntity() != this || paramRelation.getEntity().isMarkedForDeletion()));
  }
  
  public boolean sameAs(AbstractUnit paramAbstractUnit, boolean paramBoolean) {
    if (paramAbstractUnit instanceof View) {
      View view = (View)paramAbstractUnit;
      return getName().equalsIgnoreCase(view.getName());
    } 
    return false;
  }
  
  public String getDbId() {
    return this.schema.getDbId();
  }
  
  public int getDefaultSyncPriority() {
    return 8000;
  }
}
