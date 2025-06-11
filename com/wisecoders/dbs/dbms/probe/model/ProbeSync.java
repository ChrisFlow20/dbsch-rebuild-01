package com.wisecoders.dbs.dbms.probe.model;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.dbms.reverseEngineer.model.StructureImporter;
import com.wisecoders.dbs.dbms.reverseEngineer.tasks.GeneralSyncFilter;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.model.TreeSelection;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;

public class ProbeSync extends ProbeOperation {
  private final Schema b;
  
  public SyncPair a;
  
  public ProbeSync(Schema paramSchema) {
    this.b = paramSchema;
  }
  
  public String toString() {
    return "Synchronization";
  }
  
  public void a(Envoy paramEnvoy) {
    try {
      Project project = new Project("Import", this.b.project.getDbId());
      Dbms.get(paramEnvoy.e()).loadSchemasAndCatalogs(paramEnvoy, project);
      Schema schema = project.getSchema(this.b.getNameWithCatalog());
      if (schema != null) {
        a(ProbeStatus.b);
        TreeSelection treeSelection = new TreeSelection();
        new StructureImporter(schema, paramEnvoy);
        treeSelection.select(schema);
        new Importer(project, treeSelection, paramEnvoy);
        this.a = new SyncPair(this.b.project, paramEnvoy.a.mapping, project);
        this.a.filter(new GeneralSyncFilter(treeSelection, null, paramEnvoy.e()));
        SyncUtil.a(project, treeSelection);
        if (this.a.getDiffCount() == 0) {
          a(ProbeStatus.c);
        } else {
          a(ProbeStatus.d);
          a("Found " + this.a.getDiffCount() + " differences. Double-click to view.");
        } 
      } 
    } catch (Exception exception) {
      a(ProbeStatus.d);
      a(exception.toString());
    } 
  }
}
