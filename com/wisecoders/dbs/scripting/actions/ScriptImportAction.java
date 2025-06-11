package com.wisecoders.dbs.scripting.actions;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.dbms.reverseEngineer.model.StructureImporter;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.model.TreeSelection;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.scripting.ScriptEngine;

public class ScriptImportAction implements ScriptAction {
  public void a(ScriptEngine paramScriptEngine, String[] paramArrayOfString) {
    Project project = paramScriptEngine.d();
    Connector connector = paramScriptEngine.e();
    if (connector == null || project == null) {
      paramScriptEngine.b("Error : at least one database connection should be defined.");
    } else {
      try {
        Schema schema = a(project, connector, paramArrayOfString[0]);
        if (schema == null) {
          paramScriptEngine.b("Error: Schema " + paramArrayOfString[0] + " not found.");
        } else {
          paramScriptEngine.b("... Ok");
        } 
      } catch (Exception exception) {
        paramScriptEngine.b("Error: Failed to load project scriptFile.");
        exception.printStackTrace(paramScriptEngine.g());
      } 
    } 
  }
  
  public static Schema a(Project paramProject, Connector paramConnector, String paramString) {
    Envoy envoy = paramConnector.startEnvoy("Script reverse engineering task");
    try {
      Dbms.get(envoy.e()).loadSchemasAndCatalogs(envoy, paramProject);
      TreeSelection treeSelection = new TreeSelection();
      Schema schema1 = (Schema)paramProject.schemas.getByName(paramString);
      if (schema1 != null) {
        new StructureImporter(schema1, envoy);
        treeSelection.select(schema1);
      } 
      new Importer(paramProject, treeSelection, envoy);
      SyncUtil.a(paramProject, treeSelection);
      Schema schema2 = schema1;
      if (envoy != null)
        envoy.close(); 
      return schema2;
    } catch (Throwable throwable) {
      if (envoy != null)
        try {
          envoy.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
  }
}
