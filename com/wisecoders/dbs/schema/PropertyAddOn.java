package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.PropertyAddOnFolder;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.LinkedHashMap;
import java.util.Set;

public class PropertyAddOn extends Sql {
  private final LinkedHashMap a = new LinkedHashMap<>();
  
  public final PropertyAddOnFolder b;
  
  public PropertyAddOn(PropertyAddOnFolder paramPropertyAddOnFolder, String paramString) {
    super(paramString);
    this.b = paramPropertyAddOnFolder;
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
  
  public void a(String paramString, Object paramObject) {
    if (paramString != null && paramObject != null)
      this.a.put(paramString, paramObject); 
  }
  
  public Object a(String paramString) {
    return this.a.get(paramString);
  }
  
  public Set a() {
    return this.a.keySet();
  }
  
  public String getDefaultKey() {
    return "PropertyAddOn-" + Integer.toHexString((int)(9999.0D * Math.random()));
  }
  
  public String getDbId() {
    return this.b.b();
  }
}
