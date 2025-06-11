package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;

@DoNotObfuscate
public final class Sequence extends AbstractUnit implements DbUnit {
  private final Schema a;
  
  private String b;
  
  public Sequence(Schema paramSchema, String paramString) {
    super(paramString);
    this.a = paramSchema;
  }
  
  public void refresh() {}
  
  public String getSymbolicName() {
    return "Sequence";
  }
  
  public String getSymbolicIcon() {
    return null;
  }
  
  public Glyph getSymbolicGlyph() {
    return BootstrapIcons.sort_numeric_down;
  }
  
  public TreeUnit getParent() {
    return this.a.sequences;
  }
  
  public Schema getSchema() {
    return this.a;
  }
  
  public AbstractTable getEntity() {
    return null;
  }
  
  public String getOptions() {
    return this.b;
  }
  
  public void setOptions(String paramString) {
    this.b = c(paramString);
  }
  
  public String getDbId() {
    return this.a.getDbId();
  }
}
