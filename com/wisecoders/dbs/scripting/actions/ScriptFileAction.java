package com.wisecoders.dbs.scripting.actions;

import com.wisecoders.dbs.data.groovy.GroovyConfig;
import com.wisecoders.dbs.scripting.ScriptEngine;
import com.wisecoders.dbs.sys.FileUtils;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import java.io.File;
import java.io.PrintStream;

public class ScriptFileAction implements ScriptAction {
  public void a(ScriptEngine paramScriptEngine, String[] paramArrayOfString) {
    PrintStream printStream = paramScriptEngine.g();
    try {
      String str1 = paramArrayOfString[0];
      if (str1 == null || str1.isEmpty()) {
        printStream.println("Please specify the script scriptFile name.");
        printStream.println("Exit.");
        return;
      } 
      File file = paramScriptEngine.b() ? new File(paramScriptEngine.a(), str1) : new File(str1);
      paramScriptEngine.a(file.getParent());
      if (!file.exists()) {
        printStream.println("File does not exists: " + str1);
        printStream.println("Exit.");
        return;
      } 
      printStream.println();
      String str2 = FileUtils.a(file);
      printStream.println("Execute Script '" + str1 + "' as Java Groovy.\n");
      Binding binding = new Binding();
      binding.setProperty("out", System.out);
      binding.setProperty("parameters", paramArrayOfString);
      GroovyShell groovyShell = new GroovyShell(binding, new GroovyConfig(file));
      Script script = groovyShell.parse(str2);
      script.run();
      paramScriptEngine.c();
    } catch (Throwable throwable) {
      throwable.printStackTrace(printStream);
    } 
  }
}
