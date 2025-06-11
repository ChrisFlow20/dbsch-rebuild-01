package com.wisecoders.dbs.scripting;

import com.wisecoders.dbs.scripting.actions.ScriptAction;
import com.wisecoders.dbs.scripting.actions.ScriptClearAction;
import com.wisecoders.dbs.scripting.actions.ScriptCommentAction;
import com.wisecoders.dbs.scripting.actions.ScriptCommitSchemaAction;
import com.wisecoders.dbs.scripting.actions.ScriptConnectAction;
import com.wisecoders.dbs.scripting.actions.ScriptDiffAction;
import com.wisecoders.dbs.scripting.actions.ScriptExecuteAction;
import com.wisecoders.dbs.scripting.actions.ScriptFileAction;
import com.wisecoders.dbs.scripting.actions.ScriptImportAction;
import com.wisecoders.dbs.scripting.actions.ScriptOpenProjectAction;
import com.wisecoders.dbs.scripting.actions.ScriptShowTablesAction;
import com.wisecoders.dbs.scripting.actions.ScriptUploadSchemaAction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ScriptCommand {
  a("#(.*)", new int[] { 0 }, new ScriptCommentAction()),
  b("open(\\s*)file(\\s*)(\\S+)", new int[] { 3 }, new ScriptOpenProjectAction()),
  c("connect(\\s*)to(.*)", new int[] { 2 }, new ScriptConnectAction()),
  d("import(\\s*)schema(\\s*)(\\S+)", new int[] { 3 }, new ScriptImportAction()),
  e("runsql(\\s*)(.*)", new int[] { 2 }, new ScriptExecuteAction()),
  f("show(\\s*)tables", new int[0], new ScriptShowTablesAction()),
  g("diff(\\s*)with(\\s*)file(\\s*)(\\S+)", new int[] { 4 }, new ScriptDiffAction()),
  h("upload(\\s*)schema(\\s*)(\\S+)", new int[] { 3 }, new ScriptUploadSchemaAction()),
  i("online(\\s*)schema(\\s*)(\\S+)", new int[] { 3 }, new ScriptCommitSchemaAction()),
  j("file(\\s*)(\\S+)", new int[] { 2 }, new ScriptFileAction()),
  k("clear", new int[0], new ScriptClearAction());
  
  public final Pattern l;
  
  public final int[] m;
  
  private final ScriptAction n;
  
  ScriptCommand(String paramString1, int[] paramArrayOfint, ScriptAction paramScriptAction) {
    this.l = Pattern.compile(paramString1, 2);
    this.m = paramArrayOfint;
    this.n = paramScriptAction;
  }
  
  public boolean a(ScriptEngine paramScriptEngine, String paramString) {
    Matcher matcher = this.l.matcher(paramString);
    if (matcher.matches()) {
      String[] arrayOfString = new String[this.m.length];
      for (byte b = 0; b < this.m.length; b++)
        arrayOfString[b] = matcher.group(this.m[b]); 
      this.n.a(paramScriptEngine, arrayOfString);
      return true;
    } 
    return false;
  }
}
