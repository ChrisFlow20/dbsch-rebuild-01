package com.wisecoders.dbs.scripting.actions;

import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.scripting.ScriptEngine;

public class ScriptDiffAction implements ScriptAction {
  public void a(ScriptEngine paramScriptEngine, String[] paramArrayOfString) {
    if (paramScriptEngine.d() == null) {
      paramScriptEngine.b("Error : no DbSchema model is defined.");
    } else {
      Project project = ScriptOpenProjectAction.a(paramScriptEngine, paramArrayOfString[0]);
      if (project == null)
        paramScriptEngine.b("Could not open remote project"); 
    } 
  }
}
