package com.wisecoders.dbs.scripting.actions;

import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.scripting.ScriptEngine;

public class ScriptClearAction implements ScriptAction {
  public void a(ScriptEngine paramScriptEngine, String[] paramArrayOfString) {
    paramScriptEngine.a((Project)null);
    try {
      paramScriptEngine.a((Connector)null);
    } catch (Exception exception) {}
    paramScriptEngine.b("... Ok");
  }
}
