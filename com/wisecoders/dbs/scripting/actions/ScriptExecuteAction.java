package com.wisecoders.dbs.scripting.actions;

import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.scripting.ScriptEngine;

public class ScriptExecuteAction implements ScriptAction {
  public void a(ScriptEngine paramScriptEngine, String[] paramArrayOfString) {
    if (paramScriptEngine.f() == null) {
      paramScriptEngine.b("Error : no available connection.");
    } else {
      Envoy envoy = paramScriptEngine.f();
      try {
        Result result = new Result();
        envoy.b(paramArrayOfString[0], result);
        result.q();
        paramScriptEngine.b("... Ok");
        envoy.close();
      } catch (Exception exception) {
        exception.printStackTrace(paramScriptEngine.g());
        envoy.a(exception);
      } 
    } 
  }
}
