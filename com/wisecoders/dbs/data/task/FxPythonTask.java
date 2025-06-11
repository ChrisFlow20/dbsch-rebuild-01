package com.wisecoders.dbs.data.task;

import com.wisecoders.dbs.data.fx.FxAbstractSqlEditor;
import com.wisecoders.dbs.data.fx.FxScriptResultPane;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;

public class FxPythonTask extends FxGroovyScriptTask {
  public FxPythonTask(FxAbstractSqlEditor paramFxAbstractSqlEditor, Envoy paramEnvoy, String paramString, FxScriptResultPane paramFxScriptResultPane) {
    super(paramFxAbstractSqlEditor, paramEnvoy, paramString, paramFxScriptResultPane);
  }
  
  protected String a() {
    return "Done";
  }
}
