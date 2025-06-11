package com.wisecoders.dbs.loader.tasks;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.dbms.reverseEngineer.model.StructureImporter;
import com.wisecoders.dbs.dbms.reverseEngineer.tasks.SyncMode;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.fx.SyncDispatcher;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.model.TreeSelection;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import javafx.concurrent.Task;

public class LoaderSyncTask extends Task {
  private final WorkspaceWindow a;
  
  private final Envoy b;
  
  private final Table c;
  
  private final Connector d;
  
  private boolean e = false;
  
  private final TreeSelection f;
  
  protected LoaderSyncTask(WorkspaceWindow paramWorkspaceWindow, Table paramTable, Connector paramConnector) {
    this.f = new TreeSelection();
    this.a = paramWorkspaceWindow;
    this.d = paramConnector;
    this.c = paramTable;
    this.b = paramConnector.startEnvoy("Data Loader Sync");
  }
  
  protected Project a() {
    Project project = new Project("Import", this.d.dbId);
    updateMessage("Connect to the database");
    updateMessage("Synchronize " + this.c.getName());
    Dbms.get(this.b.e()).loadSchemasAndCatalogs(this.b, project);
    Schema schema = project.getSchema(this.c.schema.getCatalogName(), this.c.schema.getName());
    if (schema != null) {
      new StructureImporter(schema, this.b);
      for (Table table : schema.tables) {
        if (table.getName().equalsIgnoreCase(this.c.getName()))
          this.f.select(table, true); 
      } 
      if (this.f.hasNoSelectedUnits())
        this.f.select(schema, true); 
    } 
    new Importer(project, this.f, this.b);
    return project;
  }
  
  protected void succeeded() {
    this.b.close();
    Project project = (Project)getValue();
    SyncUtil.a(project, this.f);
    SyncPair syncPair = new SyncPair(this.a.getWorkspace().m(), this.d.mapping, project);
    syncPair.filter(paramAbstractDiff -> (paramAbstractDiff.pair.left instanceof Schema) ? false : (



        
        (paramAbstractDiff.pair.left == null || paramAbstractDiff.pair.left.getEntity() != this.c)));
    this.e = syncPair.hasDifferences();
    SyncDispatcher.a(this.a.getWorkspace(), syncPair, SyncMode.b, false, "dataLoader", null, true);
  }
  
  protected void failed() {
    this.b.a(getException());
  }
  
  protected boolean b() {
    return this.e;
  }
}
