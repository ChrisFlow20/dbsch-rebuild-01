package com.wisecoders.dbs.dbms.reverseEngineer.tasks;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsAdvices;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.reverseEngineer.fx.FxImportSelectionDialog;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.ConnectivityTip;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.UniqueArrayList;
import java.util.List;
import javafx.concurrent.Task;

public class FxImportSelectionTask extends Task {
  protected final Workspace a;
  
  protected final Connector b;
  
  private final Project c;
  
  private final Envoy d;
  
  private final Rx e = new Rx(FxImportSelectionDialog.class, this);
  
  public FxImportSelectionTask(Workspace paramWorkspace, Project paramProject, Connector paramConnector) {
    this.a = paramWorkspace;
    this.c = paramProject;
    this.b = paramConnector;
    this.d = paramConnector.startEnvoy("Reverse Engineer Task");
    this.d.c(true);
    this.e.G(this.d.e());
  }
  
  protected Void a() {
    updateMessage("Connecting. Please wait...");
    Log.c("Import selection task...");
    Dbms.get(this.d.e()).loadSchemasAndCatalogs(this.d, this.c);
    updateMessage("User Selection");
    Log.c("Import selection task done.");
    return null;
  }
  
  protected void succeeded() {
    FxImportSelectionDialog fxImportSelectionDialog = new FxImportSelectionDialog(this.a, this.d, this.e.H("title"), this.c, b(), false, this.e.H("select"), true);
    fxImportSelectionDialog.showDialog();
    if (fxImportSelectionDialog.b()) {
      this.d.close();
    } else {
      new FxImportTask(this.a, this.c, fxImportSelectionDialog.c, this.d);
    } 
  }
  
  protected void failed() {
    Log.b("Failure in Import Selection Dialog", getException());
    String str = DbmsAdvices.a(this.b.dbId).a(getException(), ConnectivityTip.b);
    this.a.getRx().b(this.a, str, getException());
    this.d.c(false);
    this.d.a(getException());
  }
  
  private List b() {
    UniqueArrayList uniqueArrayList = new UniqueArrayList();
    String str = (Dbms.get(this.b.dbId)).defaultSchema.c_();
    if ("INSTANCE".equals(str)) {
      str = this.b.getInstance();
    } else if ("USERNAME".equals(str)) {
      str = this.b.getUserName();
    } 
    if (str != null) {
      for (Schema schema : this.c.schemas) {
        if (schema.getName().equalsIgnoreCase(str))
          uniqueArrayList.add(schema); 
      } 
      if (uniqueArrayList.size() > 1)
        uniqueArrayList.removeIf(paramSchema -> (this.b.getInstance() == null || !this.b.getInstance().equals(paramSchema.getCatalogName()))); 
      if (uniqueArrayList.size() > 1)
        uniqueArrayList.clear(); 
    } 
    return uniqueArrayList;
  }
}
