package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;

@DoNotObfuscate
public class Rule extends Trigger {
  public Rule(Schema paramSchema, String paramString) {
    super(paramSchema, paramString);
  }
  
  public Rule(Schema paramSchema, String paramString, Table paramTable) {
    super(paramSchema, paramString, paramTable);
  }
  
  public void refresh() {}
  
  public String getSymbolicName() {
    return "Rule";
  }
  
  public String getSymbolicIcon() {
    return isSystem() ? "procedure_system.png" : "procedure.png";
  }
  
  public Glyph getSymbolicGlyph() {
    return BootstrapIcons.check;
  }
  
  public TreeUnit getParent() {
    return this.schema.rules;
  }
  
  public String getDbId() {
    return this.schema.project.getDbId();
  }
  
  public AbstractTable getEntity() {
    return null;
  }
  
  public Rule setDefaultText() {
    setText((Dbms.get(getDbId())).defaultRuleText.c_());
    return this;
  }
  
  public Schema getSchema() {
    return this.schema;
  }
}
