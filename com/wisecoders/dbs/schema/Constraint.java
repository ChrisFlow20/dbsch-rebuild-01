package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;

@DoNotObfuscate
public class Constraint extends AbstractUnit implements DbUnit {
  private final Table a;
  
  private String b = null;
  
  private String c = null;
  
  private String d = null;
  
  public Constraint(Table paramTable, String paramString) {
    super(paramString);
    this.a = paramTable;
  }
  
  public final void refresh() {}
  
  public String getSymbolicName() {
    return "Constraint";
  }
  
  public final String getSymbolicIcon() {
    return "constraint.png";
  }
  
  public Glyph getSymbolicGlyph() {
    return BootstrapIcons.link;
  }
  
  public Table getEntity() {
    return this.a;
  }
  
  public String getText() {
    return this.b;
  }
  
  public TreeUnit getParent() {
    return this.a.constraints;
  }
  
  public void setText(String paramString) {
    this.b = paramString;
  }
  
  public boolean sameAs(AbstractUnit paramAbstractUnit, boolean paramBoolean) {
    if (paramAbstractUnit instanceof Constraint) {
      Constraint constraint = (Constraint)paramAbstractUnit;
      if (StringUtil.stringsAreEqualIgnoreCasesAndSpaces(this.b, constraint.getText()) && StringUtil.areEqual(this.d, constraint.getType()))
        return true; 
    } 
    return super.sameAs(paramAbstractUnit, paramBoolean);
  }
  
  public String getDbId() {
    return this.a.getDbId();
  }
  
  public Schema getSchema() {
    return this.a.getSchema();
  }
  
  public void setOptions(String paramString) {
    this.c = paramString;
  }
  
  public String getOptions() {
    return this.c;
  }
  
  public void setType(String paramString) {
    this.d = paramString;
  }
  
  public String getType() {
    return this.d;
  }
}
