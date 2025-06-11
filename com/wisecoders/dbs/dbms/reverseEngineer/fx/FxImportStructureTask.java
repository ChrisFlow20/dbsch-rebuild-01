package com.wisecoders.dbs.dbms.reverseEngineer.fx;

import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.fx.Dialog$;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Task;

public class FxImportStructureTask extends Task {
  private final Envoy a;
  
  private final Dialog$ b;
  
  private final List e = new ArrayList();
  
  int c;
  
  int d;
  
  FxImportStructureTask(Envoy paramEnvoy, List paramList, Dialog$ paramDialog$) {
    this.a = paramEnvoy;
    this.b = paramDialog$;
    this.e.addAll(paramList);
    updateMessage("Load schema");
  }
  
  public Void a() {
    Thread.currentThread().setName("DbSchema: Reverse Engineering Schema Structure Task");
    for (Schema schema : this.e)
      new FxImportStructureTask$1(this, schema, this.a); 
    return null;
  }
  
  protected void succeeded() {}
  
  protected void failed() {
    Throwable throwable = getException();
    this.b.rx.a(this.b.getDialogPane().getScene(), throwable);
    Log.b(throwable);
  }
}
