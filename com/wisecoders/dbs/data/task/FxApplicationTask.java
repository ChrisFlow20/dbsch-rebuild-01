package com.wisecoders.dbs.data.task;

import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.sys.StringUtil;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.sql.Sql;
import java.io.File;
import java.io.FileReader;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class FxApplicationTask extends Task {
  private final WorkspaceWindow a;
  
  private final File b;
  
  public FxApplicationTask(WorkspaceWindow paramWorkspaceWindow, File paramFile) {
    this.a = paramWorkspaceWindow;
    this.b = paramFile;
  }
  
  protected Void a() {
    updateMessage("Starting '" + StringUtil.getFileNameWithoutExtension(this.b) + "' app...");
    Binding binding = new Binding();
    if (this.a.getWorkspace().s() != null) {
      Sql sql = new Sql(this.a.getWorkspace().s().startEnvoy("App").c());
      binding.setVariable("sql", sql);
    } 
    binding.setVariable("project", this.a.getWorkspace().m());
    GroovyShell groovyShell = new GroovyShell();
    FileReader fileReader = new FileReader(this.b);
    try {
      Script script = groovyShell.parse(fileReader, binding);
      Platform.runLater(() -> {
            paramScript.run();
            if (paramBinding.getVariable("sql") != null)
              ((Sql)paramBinding.getVariable("sql")).close(); 
          });
      fileReader.close();
    } catch (Throwable throwable) {
      try {
        fileReader.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
    return null;
  }
  
  protected void failed() {
    Throwable throwable = getException();
    this.a.getRx().a(this.a.getDialogScene(), throwable);
  }
}
