package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;

@DoNotObfuscate
public final class Procedure extends AbstractFunction implements DbUnit {
  public Procedure(Schema paramSchema, String paramString) {
    super(paramSchema, paramString);
  }
  
  public void refresh() {}
  
  public String getSymbolicName() {
    return "Procedure";
  }
  
  public String getSymbolicIcon() {
    return null;
  }
  
  public Glyph getSymbolicGlyph() {
    return isSystem() ? BootstrapIcons.file_earmark_lock : BootstrapIcons.file_earmark_ppt;
  }
  
  public TreeUnit getParent() {
    return this.schema.procedures;
  }
  
  public String getDbId() {
    return this.schema.project.getDbId();
  }
  
  public AbstractTable getEntity() {
    return null;
  }
  
  public Procedure setDefaultText() {
    setText((Dbms.get(getDbId())).defaultProcedureText.c_());
    return this;
  }
  
  public Schema getSchema() {
    return this.schema;
  }
}
