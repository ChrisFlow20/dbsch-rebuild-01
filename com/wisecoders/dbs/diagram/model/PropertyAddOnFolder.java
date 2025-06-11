package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.PropertyAddOn;
import com.wisecoders.dbs.schema.Schema;
import javafx.concurrent.Task;
import javafx.util.Callback;

public class PropertyAddOnFolder extends Folder {
  public final String b;
  
  public final AbstractUnit c;
  
  private final PropertyAddOn d = new PropertyAddOnFolder$1(this, this, "Please wait...");
  
  public PropertyAddOnFolder(AbstractUnit paramAbstractUnit, String paramString1, String paramString2) {
    super(paramString1, paramAbstractUnit, PropertyAddOn.class, true);
    this.b = paramString2;
    this.c = paramAbstractUnit;
    add(this.d);
  }
  
  public boolean isLoaded() {
    return !contains(this.d);
  }
  
  public String b() {
    return (this.c instanceof Schema) ? ((Schema)this.c).getDbId() : ((Project)this.c).getDbId();
  }
  
  public synchronized Task a(Envoy paramEnvoy, Callback paramCallback1, Callback paramCallback2) {
    if (!isLoaded() && 
      paramEnvoy != null)
      return new PropertyAddOnFolder$2(this, paramEnvoy, paramCallback1, paramCallback2); 
    return null;
  }
}
