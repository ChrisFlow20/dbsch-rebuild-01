package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;

@DoNotObfuscate
public class Function extends AbstractFunction implements DbUnit {
  public Function(Schema paramSchema, String paramString) {
    super(paramSchema, paramString);
  }
  
  public void refresh() {}
  
  public String getSymbolicName() {
    return "Function";
  }
  
  public String getSymbolicIcon() {
    return null;
  }
  
  public Glyph getSymbolicGlyph() {
    return isSystem() ? BootstrapIcons.file_earmark_lock : BootstrapIcons.file_earmark_code;
  }
  
  public TreeUnit getParent() {
    return this.schema.functions;
  }
  
  public String getDbId() {
    return this.schema.project.getDbId();
  }
  
  public AbstractTable getEntity() {
    return null;
  }
  
  public Function setDefaultText() {
    setText((Dbms.get(getDbId())).defaultFunctionText.c_());
    return this;
  }
  
  public Schema getSchema() {
    return this.schema;
  }
}
