package com.wisecoders.dbs.scripting.actions;

import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterScript;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterStatement;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.scripting.ScriptEngine;

public class ScriptUploadSchemaAction implements ScriptAction {
  public void a(ScriptEngine paramScriptEngine, String[] paramArrayOfString) {
    Project project = paramScriptEngine.d();
    Envoy envoy = paramScriptEngine.f();
    if (project == null || envoy == null) {
      paramScriptEngine.b("Empty project or invalid connection.");
    } else {
      String str1 = project.getDbId();
      SyncPair syncPair = new SyncPair(new Project(project.getName(), str1), project);
      AlterScript alterScript = syncPair.generateCommitScript(str1, null, SyncSide.right);
      String str2 = null;
      byte b = 0;
      try {
        paramScriptEngine.c("" + alterScript.statements.size() + " changes");
        for (AlterStatement alterStatement : alterScript.statements) {
          str2 = alterStatement.getSQLWithTerminator(str1);
          Result result = new Result();
          envoy.b(str2, result);
          envoy.p();
          result.q();
          b++;
        } 
        paramScriptEngine.b("... Ok");
        envoy.close();
      } catch (Exception exception) {
        envoy.a(exception);
        if (str2 != null) {
          paramScriptEngine.b("\nAfter " + b + " executed steps failed to run : ");
          paramScriptEngine.b(str2);
        } 
        paramScriptEngine.b("Error : " + String.valueOf(exception));
      } 
    } 
  }
}
