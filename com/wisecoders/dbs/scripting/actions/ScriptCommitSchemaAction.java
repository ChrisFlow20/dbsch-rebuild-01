package com.wisecoders.dbs.scripting.actions;

import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.scripting.ScriptEngine;
import java.util.ArrayList;

public class ScriptCommitSchemaAction implements ScriptAction {
  private static final int b = 300;
  
  public void a(ScriptEngine paramScriptEngine, String[] paramArrayOfString) {
    Project project = paramScriptEngine.d();
    Connector connector = paramScriptEngine.e();
    if (project == null || connector == null) {
      paramScriptEngine.b("Empty project or invalid connection.");
    } else {
      Project project1 = new Project("Imported", project.getDbId());
      try {
        Envoy envoy = connector.startEnvoy("Script commit schema action");
        try {
          Schema schema = ScriptImportAction.a(project1, connector, paramArrayOfString[0]);
          if (schema == null) {
            paramScriptEngine.b("Error: Schema " + paramArrayOfString[0] + " not found.");
            if (envoy != null)
              envoy.close(); 
            return;
          } 
          paramScriptEngine.c("" + schema.tables.size() + " tables imported ");
          ArrayList arrayList = new ArrayList();
          paramScriptEngine.c("" + arrayList.size() + " changes");
          byte b = 0;
          try {
            for (String str : arrayList) {
              try {
                envoy.g(str);
              } catch (Exception exception) {
                if (str != null) {
                  paramScriptEngine.b("");
                  paramScriptEngine.c("\t" + str);
                  paramScriptEngine.b("\t" + String.valueOf(exception));
                } 
                b++;
                if (b >= 'Ĭ') {
                  paramScriptEngine.b("" + b + " encountered. Exit Commit schema. ");
                  if (envoy != null)
                    envoy.close(); 
                  return;
                } 
              } 
            } 
            if (b == 0)
              paramScriptEngine.b("... Ok"); 
          } catch (Exception exception) {
            paramScriptEngine.b("Error : " + String.valueOf(exception));
          } 
          if (envoy != null)
            envoy.close(); 
        } catch (Throwable throwable) {
          if (envoy != null)
            try {
              envoy.close();
            } catch (Throwable throwable1) {
              throwable.addSuppressed(throwable1);
            }  
          throw throwable;
        } 
      } catch (Exception exception) {
        exception.printStackTrace(paramScriptEngine.g());
      } 
    } 
  }
}
