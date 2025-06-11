package com.wisecoders.dbs.diagram.fx;

import com.wisecoders.dbs.dialogs.system.FxTechnicalSupportDialog;
import com.wisecoders.dbs.project.fx.FxFrame;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.store.ProjectLoader;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.fx.Alert$;
import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import javafx.concurrent.Task;
import org.xml.sax.SAXParseException;

public class FxProjectLoaderTask extends Task {
  public static final ExecutorService c = Executors.newSingleThreadExecutor();
  
  private final File a;
  
  protected final Workspace d;
  
  public FxProjectLoaderTask(Workspace paramWorkspace, File paramFile) {
    this.a = paramFile;
    this.d = paramWorkspace;
    c.submit((Runnable)this);
  }
  
  protected ProjectLoader a() {
    updateMessage("Loading Project File");
    ProjectLoader projectLoader = new ProjectLoader();
    FileInputStream fileInputStream = new FileInputStream(this.a);
    try {
      projectLoader.parse(fileInputStream);
      fileInputStream.close();
    } catch (Throwable throwable) {
      try {
        fileInputStream.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
    return projectLoader;
  }
  
  protected void succeeded() {
    Project project = ((ProjectLoader)getValue()).getProject();
    project.setFile(this.a);
    this.d.c(project);
    this.d.b(true);
    FxFrame.c.a(project.getName(), this.a);
    Log.c("Open " + project.getStatistics());
    Objects.requireNonNull(this.d);
    Platform.runLater(this.d::F);
  }
  
  protected void failed() {
    Throwable throwable = getException();
    if (throwable instanceof SAXParseException) {
      SAXParseException sAXParseException = (SAXParseException)throwable;
      Log.c(throwable);
      (new Alert$()).a(this.d)
        .a("Error Dialog")
        .b(sAXParseException.toString())
        .c("Design Model files (.dbs) are XML files saved by DbSchema.\nPlease open the file with a text editor and check the content.\nIf the file is corrupted, use the backup file '.dbs.bak' from the same folder.\n")



        
        .a(throwable)
        .a("Support", paramObject -> {
            (new FxTechnicalSupportDialog(this.d.getWindow())).showDialog();
            return null;
          }).showAndWait();
    } else {
      Log.a("Error Loading Project File", throwable);
      (new Alert$()).a(this.d).b(throwable.toString()).a(throwable).showAndWait();
    } 
  }
}
