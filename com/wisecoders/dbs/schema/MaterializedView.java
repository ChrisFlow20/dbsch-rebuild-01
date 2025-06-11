package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;

@DoNotObfuscate
public class MaterializedView extends View {
  public MaterializedView(Schema paramSchema, String paramString) {
    super(paramSchema, paramString);
  }
  
  public String getSymbolicName() {
    return "Materialized View";
  }
  
  public Glyph getSymbolicGlyph() {
    return BootstrapIcons.binoculars;
  }
  
  public TreeUnit getParent() {
    return this.schema.materializedViews;
  }
  
  public int getDefaultSyncPriority() {
    return 8000;
  }
}
