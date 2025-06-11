package com.wisecoders.dbs.scripting.actions;

import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.scripting.ScriptEngine;

public class ScriptShowTablesAction implements ScriptAction {
  public void a(ScriptEngine paramScriptEngine, String[] paramArrayOfString) {
    if (paramScriptEngine.d() == null) {
      paramScriptEngine.b("Error : no DbSchema model is defined.");
    } else {
      for (Schema schema : (paramScriptEngine.d()).schemas) {
        paramScriptEngine.b("\tSchema " + String.valueOf(schema));
        for (Table table : schema.tables)
          paramScriptEngine.b("\t\tTable " + String.valueOf(table)); 
      } 
      paramScriptEngine.b("");
    } 
  }
}
