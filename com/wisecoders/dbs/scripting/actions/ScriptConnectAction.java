package com.wisecoders.dbs.scripting.actions;

import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.ConnectorManager;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.scripting.ScriptEngine;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptConnectAction implements ScriptAction {
  public void a(ScriptEngine paramScriptEngine, String[] paramArrayOfString) {
    String str = paramArrayOfString[0];
    String[] arrayOfString1 = { "database", "driver_class", "driver_jar", "host", "port", "instance", "user", "password" };
    String[] arrayOfString2 = new String[arrayOfString1.length];
    byte b = 0;
    for (String str1 : arrayOfString1) {
      Pattern pattern = Pattern.compile("(.*)" + str1 + "(\\s*)=(\\s*)(\\S+)(.*)", 2);
      Matcher matcher = pattern.matcher(str);
      if (matcher.matches()) {
        String str2 = matcher.group(4);
        arrayOfString2[b] = str2;
      } 
      b++;
    } 
    Project project = paramScriptEngine.d();
    if (project == null) {
      project = new Project("testCase", arrayOfString2[0]);
      paramScriptEngine.a(project);
    } 
    Connector connector = ConnectorManager.createConnector("conn", arrayOfString2[0], arrayOfString2[1], arrayOfString2[2], null, arrayOfString2[3], 
        Integer.parseInt(arrayOfString2[4]), arrayOfString2[5], arrayOfString2[6], false);
    connector.setPassword(arrayOfString2[7]);
    try {
      Envoy envoy = connector.startEnvoy("Script connect action");
      try {
        envoy.a();
        paramScriptEngine.a(connector);
        paramScriptEngine.b("... Ok");
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
      paramScriptEngine.b("Error : " + String.valueOf(exception));
      exception.printStackTrace(paramScriptEngine.g());
    } 
  }
}
