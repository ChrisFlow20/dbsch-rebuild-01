package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.ToolUnit;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;

@DoNotObfuscate
public final class Script extends Sql implements ToolUnit {
  private final Layout a;
  
  public Script(Layout paramLayout, String paramString) {
    super(paramString);
    this.a = paramLayout;
  }
  
  public void refresh() {}
  
  public String getSymbolicName() {
    return "Editor";
  }
  
  public String getSymbolicIcon() {
    return "sql.png";
  }
  
  public Glyph getSymbolicGlyph() {
    switch (Script$1.a[getLanguage().ordinal()]) {
      case 1:
      
      case 2:
      
    } 
    return 

      
      BootstrapIcons.filetype_sql;
  }
  
  public TreeUnit getParent() {
    return this.a.scripts;
  }
  
  public Layout getLayout() {
    return this.a;
  }
  
  public String getDbId() {
    return this.a.project.getDbId();
  }
  
  public AbstractTable getEntity() {
    return null;
  }
}
