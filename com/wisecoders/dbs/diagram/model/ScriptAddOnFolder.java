package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.ScriptAddOn;
import javafx.concurrent.Task;
import javafx.util.Callback;

public class ScriptAddOnFolder extends Folder {
  public final String b;
  
  public final String c;
  
  public final AbstractUnit d;
  
  private final ScriptAddOn e = new ScriptAddOnFolder$1(this, this, "Please wait...");
  
  public ScriptAddOnFolder(AbstractUnit paramAbstractUnit, String paramString1, String paramString2, String paramString3) {
    super(paramString1, paramAbstractUnit, ScriptAddOn.class, true);
    this.b = paramString2;
    this.d = paramAbstractUnit;
    this.c = paramString3;
    add(this.e);
  }
  
  public boolean isLoaded() {
    return !contains(this.e);
  }
  
  public String b() {
    return (this.d instanceof Schema) ? ((Schema)this.d).getDbId() : ((Project)this.d).getDbId();
  }
  
  public synchronized Task a(Envoy paramEnvoy, Callback paramCallback1, Callback paramCallback2) {
    if (!isLoaded() && 
      paramEnvoy != null)
      return new ScriptAddOnFolder$2(this, paramEnvoy, paramCallback1, paramCallback2); 
    return null;
  }
}
