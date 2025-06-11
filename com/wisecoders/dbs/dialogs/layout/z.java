package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.reverseEngineer.model.ViewImporter;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import javafx.concurrent.Task;

class z extends Task {
  private final View b;
  
  private final Envoy c;
  
  z(FxViewEditor paramFxViewEditor, View paramView, Envoy paramEnvoy) {
    this.b = paramView;
    this.c = paramEnvoy;
    if (paramFxViewEditor.m != null)
      paramFxViewEditor.m.setText(paramFxViewEditor.rx.H("validateViewStop")); 
  }
  
  protected Void a() {
    Thread.currentThread().setName("DbSchema: Validate View Script Task");
    updateMessage("Text view script...");
    this.c.b(false);
    ViewImporter.b(this.c, this.b);
    ViewImporter.a(this.c, this.b);
    return null;
  }
  
  protected void succeeded() {
    this.c.close();
    this.a.o = null;
    if (this.a.m != null)
      this.a.m.setText(this.a.rx.H("validateView")); 
    this.a.showInformation("validationSucceed", new String[0]);
    this.b.markForDeletion();
    this.b.schema.refresh();
  }
  
  protected void failed() {
    Throwable throwable = getException();
    this.c.a(throwable);
    this.a.o = null;
    Log.b(throwable);
    if (this.a.m != null)
      this.a.m.setText(this.a.rx.H("validateView")); 
    this.a.showError("Error executing:\n" + StringUtil.cutOfWithDots(ViewImporter.a(this.b), 150) + "\n\n" + throwable.toString(), throwable);
    this.b.markForDeletion();
    this.b.schema.refresh();
  }
}
