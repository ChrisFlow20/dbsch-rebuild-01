package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.dbms.connect.fx.FxConnectorEditor;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.sync.engine.nodes.EditorSyncRoot;
import com.wisecoders.dbs.schema.ConnectivityTip;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.Log;
import javafx.concurrent.Task;
import javafx.scene.control.ButtonType;

class d extends Task {
  private final Connector b;
  
  private Envoy c;
  
  private d(FxDbDialog paramFxDbDialog) {
    this.b = paramFxDbDialog.b.s();
    if (this.b.needsEdit()) {
      (new FxConnectorEditor(paramFxDbDialog.b, this.b)).showDialog().ifPresent(paramConnector -> this.c = paramConnector.startEnvoy("Sync Schema changes"));
    } else {
      this.c = this.b.startEnvoy("Synchronize schema changes");
    } 
  }
  
  protected Void a() {
    Thread.currentThread().setName("DbSchema: Schema Synchronization Commit Changes Task");
    if (this.c != null) {
      updateMessage("Applying changes... Details in SQL History pane.");
      for (EditorSyncRoot editorSyncRoot : this.a.a) {
        editorSyncRoot.a(this.c);
        editorSyncRoot.a(this.a.b.o());
      } 
    } 
    return null;
  }
  
  protected void succeeded() {
    if (this.c != null) {
      for (EditorSyncRoot editorSyncRoot : this.a.a) {
        if (editorSyncRoot.a && editorSyncRoot.right != null)
          editorSyncRoot.right.markForDeletion(); 
      } 
      this.c.close();
    } 
    this.a.saveSucceeded();
    this.a.setResult(ButtonType.OK);
    this.a.close();
    this.a.b.u();
    this.a.b.y();
    this.a.c.set(false);
  }
  
  protected void failed() {
    Throwable throwable = getException();
    if (this.c != null)
      this.c.a(throwable); 
    Log.a("Error during sync commit", throwable);
    String str = this.b.getHTMLMessageAndAdvice(throwable, "Error From the Database", null, ConnectivityTip.c);
    this.a.showError(str);
    this.a.b.y();
    this.a.c.set(false);
  }
}
