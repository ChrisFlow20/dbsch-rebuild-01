package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;

@DoNotObfuscate
public class Trigger extends Sql implements DbUnit {
  public final Schema schema;
  
  private final Table a;
  
  public Trigger(Schema paramSchema, String paramString) {
    super(paramString);
    this.schema = paramSchema;
    this.a = null;
  }
  
  public Trigger(Schema paramSchema, String paramString, Table paramTable) {
    super(paramString);
    this.schema = paramSchema;
    this.a = paramTable;
    if (paramTable != null)
      paramTable.triggers.add(this); 
  }
  
  public String getSymbolicName() {
    return "Trigger";
  }
  
  public String getSymbolicIcon() {
    return null;
  }
  
  public Glyph getSymbolicGlyph() {
    return BootstrapIcons.file_font;
  }
  
  public TreeUnit getParent() {
    return this.schema.triggers;
  }
  
  public String getDbId() {
    return this.schema.project.getDbId();
  }
  
  public AbstractTable getEntity() {
    return null;
  }
  
  public Trigger setDefaultText() {
    setText((Dbms.get(this.schema.project.getDbId())).defaultTriggerText.c_());
    return this;
  }
  
  public Table getOwningTable() {
    return this.a;
  }
  
  public void refresh() {
    if (this.a != null && this.a.isMarkedForDeletion())
      markForDeletion(); 
  }
  
  public boolean sameAs(AbstractUnit paramAbstractUnit, boolean paramBoolean) {
    if (paramAbstractUnit instanceof Trigger) {
      Trigger trigger = (Trigger)paramAbstractUnit;
      return (getName().equalsIgnoreCase(trigger.getName()) && ((this.a == null && trigger.a == null) || (this.a != null && trigger.a != null && this.a
        
        .sameAs(trigger.a, paramBoolean))));
    } 
    return false;
  }
  
  public Schema getSchema() {
    return this.schema;
  }
}
