package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;

public class LayoutDepict extends AbstractUnit {
  private final Layout b;
  
  public final Depict a;
  
  public LayoutDepict(Layout paramLayout, Depict paramDepict) {
    super(paramDepict.getName());
    this.b = paramLayout;
    this.a = paramDepict;
  }
  
  public void refresh() {
    rename(this.a.getName());
    if (this.a.isMarkedForDeletion())
      markForDeletion(); 
  }
  
  public TreeUnit getParent() {
    return this.b;
  }
  
  public String getSymbolicName() {
    return "Tables & Views";
  }
  
  public String getSymbolicIcon() {
    return "table_small.png";
  }
  
  public String a() {
    return this.a.getEntity().getNameWithSchemaName();
  }
  
  public Glyph getSymbolicGlyph() {
    return (this.a.getEntity() instanceof AbstractUnit) ? ((AbstractUnit)this.a.getEntity()).getSymbolicGlyph() : BootstrapIcons.file_spreadsheet;
  }
  
  public Entity getEntity() {
    return this.a.getEntity();
  }
}
