package com.wisecoders.dbs.scripting.actions;

import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.store.ProjectLoader;
import com.wisecoders.dbs.scripting.ScriptEngine;
import java.io.File;
import java.io.FileInputStream;

public class ScriptOpenProjectAction implements ScriptAction {
  public void a(ScriptEngine paramScriptEngine, String[] paramArrayOfString) {
    String str = paramArrayOfString[0];
    Project project = a(paramScriptEngine, str);
    paramScriptEngine.a(project);
    if (project != null)
      paramScriptEngine.b("Project opened"); 
  }
  
  public static Project a(ScriptEngine paramScriptEngine, String paramString) {
    File file = new File(paramString);
    if (!file.exists())
      file = new File(paramScriptEngine.a(), paramString); 
    if (file.exists()) {
      ProjectLoader projectLoader = new ProjectLoader();
      try {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
          projectLoader.parse(fileInputStream);
          Project project = projectLoader.getProject();
          fileInputStream.close();
          return project;
        } catch (Throwable throwable) {
          try {
            fileInputStream.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          } 
          throw throwable;
        } 
      } catch (Exception exception) {
        paramScriptEngine.b("Erorr: Failed to load project scriptFile.");
        exception.printStackTrace(paramScriptEngine.g());
      } 
    } else {
      paramScriptEngine.b("Error: File does not exists.");
    } 
    return null;
  }
}
