package com.wisecoders.dbs.loader.tasks;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.UpdateStatement;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.loader.engine.AbstractLoader;
import com.wisecoders.dbs.loader.model.LoaderMeta;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.StringUtil;
import javafx.concurrent.Task;

public class DataLoaderTask extends Task {
  private final WorkspaceWindow a;
  
  private final Connector b;
  
  private final AbstractLoader c;
  
  private final LoaderMeta d;
  
  private final boolean e;
  
  private final Envoy f;
  
  protected DataLoaderTask(WorkspaceWindow paramWorkspaceWindow, LoaderMeta paramLoaderMeta, AbstractLoader paramAbstractLoader, Connector paramConnector, boolean paramBoolean) {
    this.a = paramWorkspaceWindow;
    this.b = paramConnector;
    this.d = paramLoaderMeta;
    this.c = paramAbstractLoader;
    this.e = paramBoolean;
    this.f = paramConnector.startEnvoy("Data Loader");
  }
  
  protected Void a() {
    if (this.e) {
      if (this.b.isMongo()) {
        updateMessage("Drop Collection " + this.d.d.getName() + " ...");
        UpdateStatement updateStatement = this.f.b(this.d.d.ref() + ".drop()", new Object[0]);
        try {
          updateStatement.a();
          if (updateStatement != null)
            updateStatement.close(); 
        } catch (Throwable throwable) {
          if (updateStatement != null)
            try {
              updateStatement.close();
            } catch (Throwable throwable1) {
              throwable.addSuppressed(throwable1);
            }  
          throw throwable;
        } 
      } else {
        updateMessage("Truncate Table " + this.d.d.getName() + " ...");
        String str = (Dbms.get(this.f.e())).truncateTable.c_();
        if (StringUtil.isFilledTrim(str))
          this.f.g(str.replaceAll("\\$\\{name}", this.d.d.ref())); 
      } 
      this.f.p();
    } 
    updateMessage("Load data...");
    DataLoaderTask$1 dataLoaderTask$1 = new DataLoaderTask$1(this, this.f, this.a.getWorkspace().m().getDbId(), this.c, this.d);
    try {
      dataLoaderTask$1.a();
      dataLoaderTask$1.close();
    } catch (Throwable throwable) {
      try {
        dataLoaderTask$1.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
    this.f.p();
    return null;
  }
  
  protected String b() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Inserted ").append(this.d.c()).append(" rows. ");
    if (this.d.d() > 0)
      stringBuilder.append("Failed ").append(this.d.d()).append(" rows."); 
    return stringBuilder.toString();
  }
  
  protected void succeeded() {
    this.f.close();
  }
  
  protected void failed() {
    this.f.a(getException());
  }
}
