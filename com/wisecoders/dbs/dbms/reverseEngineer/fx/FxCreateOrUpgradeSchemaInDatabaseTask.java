package com.wisecoders.dbs.dbms.reverseEngineer.fx;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.reverseEngineer.model.StructureImporter;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterScript;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.diagram.model.TreeSelection;
import com.wisecoders.dbs.dialogs.system.FxSchemaSelectionDialog;
import com.wisecoders.dbs.project.fx.FxFrame;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.sql.fx.FxSqlUploadDialog;
import com.wisecoders.dbs.sys.Log;
import javafx.concurrent.Task;

public class FxCreateOrUpgradeSchemaInDatabaseTask extends Task {
  protected final FxFrame a;
  
  protected final Project b;
  
  protected final Connector c;
  
  private final Envoy d;
  
  private final Project e;
  
  private final TreeSelection f;
  
  public FxCreateOrUpgradeSchemaInDatabaseTask(FxFrame paramFxFrame, Project paramProject, Connector paramConnector) {
    this.a = paramFxFrame;
    this.b = paramProject;
    this.c = paramConnector;
    this.d = paramConnector.startEnvoy("Reverse Engineering Task");
    this.e = new Project("New model", paramConnector.dbId);
    FxSchemaSelectionDialog fxSchemaSelectionDialog = new FxSchemaSelectionDialog(paramFxFrame, "Commit Changes into the Database", paramProject, "Choose the schemas/tables to be created in database");
    fxSchemaSelectionDialog.a(paramProject.schemas);
    fxSchemaSelectionDialog.showDialog();
    this.f = fxSchemaSelectionDialog.c;
    if (!fxSchemaSelectionDialog.b())
      paramFxFrame.getRx().a(this); 
  }
  
  protected Void a() {
    Thread.currentThread().setName("DbSchema: Schema Sync Create or Upgrade Schema Task");
    updateMessage("Connecting. Please wait...");
    Log.c("Reverse engineering from " + this.c.dbId);
    Dbms.get(this.d.e()).loadSchemasAndCatalogs(this.d, this.e);
    TreeSelection treeSelection = new TreeSelection();
    for (Schema schema1 : this.e.schemas) {
      Schema schema2 = this.b.getSchema(schema1.getCatalogName(), schema1.getName());
      if (schema2 != null && this.f.hasSelectedChildren(schema2)) {
        new StructureImporter(schema1, this.d);
        treeSelection.select(schema1);
      } 
    } 
    new FxCreateOrUpgradeSchemaInDatabaseTask$1(this, this.e, treeSelection, this.d);
    return null;
  }
  
  protected void succeeded() {
    this.d.close();
    SyncPair syncPair = new SyncPair(this.b, this.c.mapping, this.e);
    syncPair.filter(paramAbstractDiff -> (paramAbstractDiff.pair.left == null || !this.f.isSelected(paramAbstractDiff.pair.left)));
    AlterScript alterScript = syncPair.generateCommitScript(this.c.dbId, null, SyncSide.left);
    if (alterScript.isEmpty()) {
      this.a.a.a(this.a, "Schemas are up-to-date in the database", new String[0]);
    } else {
      new FxSqlUploadDialog(this.a, this.c, alterScript);
    } 
  }
  
  protected void failed() {
    Throwable throwable = getException();
    this.d.a(throwable);
    Log.a("Error creating or upgrading schema in the database", throwable);
    String str = this.c.getHTMLMessageAndAdvice(throwable, null, null, null) + " \n\nJDBC URL: " + this.c.getHTMLMessageAndAdvice(throwable, null, null, null);
    this.a.getRx().b(this.a, str, throwable);
  }
}
