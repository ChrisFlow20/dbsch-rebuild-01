package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.ScriptAddOnFolder;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;

public class ScriptAddOn extends Sql {
  public final ScriptAddOnFolder b;
  
  public ScriptAddOn(ScriptAddOnFolder paramScriptAddOnFolder, String paramString) {
    super(paramString);
    this.b = paramScriptAddOnFolder;
  }
  
  public void refresh() {}
  
  public TreeUnit getParent() {
    return this.b;
  }
  
  public String getSymbolicName() {
    return this.b.getName();
  }
  
  public String getSymbolicIcon() {
    return null;
  }
  
  public Glyph getSymbolicGlyph() {
    return BootstrapIcons.file_earmark_text;
  }
  
  public Entity getEntity() {
    return null;
  }
  
  public String getDbId() {
    return this.b.b();
  }
  
  public String getDefaultKey() {
    return "ScriptAddOn-" + Integer.toHexString((int)(9999.0D * Math.random()));
  }
}
