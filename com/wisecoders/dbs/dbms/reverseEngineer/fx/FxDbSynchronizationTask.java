package com.wisecoders.dbs.dbms.reverseEngineer.fx;

import com.wisecoders.dbs.data.groovy.Groovy;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsAdvices;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.reverseEngineer.model.StructureImporter;
import com.wisecoders.dbs.dbms.reverseEngineer.tasks.GeneralSyncFilter;
import com.wisecoders.dbs.dbms.reverseEngineer.tasks.SyncMode;
import com.wisecoders.dbs.dbms.reverseEngineer.tasks.VirtualSyncFilter;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.fx.SyncDispatcher;
import com.wisecoders.dbs.dbms.sync.model.SyncFilter;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.model.TreeSelection;
import com.wisecoders.dbs.project.fx.FxFrame;
import com.wisecoders.dbs.schema.ConnectivityTip;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.FxUtil;
import groovy.lang.Binding;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class FxDbSynchronizationTask extends Task {
  private final List a;
  
  private final FxFrame b;
  
  private final Project c;
  
  private final SyncMode d;
  
  private final Connector e;
  
  private final Project f;
  
  private final Envoy g;
  
  private TreeSelection h;
  
  private final boolean i;
  
  private final boolean j;
  
  public FxDbSynchronizationTask(FxFrame paramFxFrame, Project paramProject, Connector paramConnector, SyncMode paramSyncMode, boolean paramBoolean1, List paramList, boolean paramBoolean2) {
    this.b = paramFxFrame;
    this.c = paramProject;
    this.e = paramConnector;
    this.f = new Project("Import model", paramConnector.dbId);
    this.f.setSyncProject(true);
    this.d = paramSyncMode;
    this.i = paramBoolean2;
    this.j = paramBoolean1;
    this.a = SyncUtil.a(paramList);
    this.g = paramConnector.startEnvoy("Reverse engineering task");
    Log.c("Synchronize schemas in " + String.valueOf(paramSyncMode) + " mode selection " + String.valueOf(paramList));
  }
  
  public Void a() {
    Thread.currentThread().setName("DbSchema: Reverse Engineering Pre-Fetch Task");
    updateMessage("Connecting. Please wait...");
    Dbms.get(this.g.e()).loadSchemasAndCatalogs(this.g, this.f);
    boolean bool = this.e.mapping.needEdit(this.c);
    if (!this.a.isEmpty() || this.c.schemas
      .isEmpty() || bool || this.j) {
      FxUtil.a(() -> {
            FxImportSelectionDialog fxImportSelectionDialog = new FxImportSelectionDialog(this.b, this.g, "Select Database Catalogs & Schemes to Synchronize", this.f, this.a, paramBoolean, "Select the schemas to reverse engineer." + (paramBoolean ? "\nCreate model schemas into the database using 'Schema/Create into the Database...'." : ""), false);
            fxImportSelectionDialog.showDialog();
            this.h = fxImportSelectionDialog.b() ? null : fxImportSelectionDialog.c;
          });
      if (this.h == null) {
        cancel(true);
        return null;
      } 
    } else {
      this.h = new TreeSelection();
      for (Schema schema : this.f.schemas) {
        if (this.e.mapping.isRemoteSchemaMapped(this.c, schema)) {
          new StructureImporter(schema, this.g);
          this.h.select(schema);
        } 
      } 
    } 
    new FxDbSynchronizationTask$1(this, this.f, this.h, this.g);
    return null;
  }
  
  public void succeeded() {
    this.g.a.resetResyncFlag();
    this.g.close();
    if (!isCancelled()) {
      Project project = this.f;
      a(this.c, System.out);
      SyncPair syncPair = new SyncPair(this.c, this.e.mapping, project);
      syncPair.filter(new VirtualSyncFilter());
      syncPair.filter(new GeneralSyncFilter(this.h, this.a, this.g.e()));
      SyncUtil.a(project, this.h);
      a(syncPair, this.c.getSyncFilter(), System.out);
      Platform.runLater(() -> SyncDispatcher.a(this.b, paramSyncPair, this.d, false, null, null, this.i));
    } 
  }
  
  protected void failed() {
    Throwable throwable = getException();
    this.g.a(throwable);
    Log.a("Error during schema synchronization", throwable);
    if (!isCancelled()) {
      String str = DbmsAdvices.a(this.e.dbId).a(throwable, ConnectivityTip.a) + " \n\nJDBC URL: " + DbmsAdvices.a(this.e.dbId).a(throwable, ConnectivityTip.a);
      this.b.a.b(this.b, str, throwable);
    } 
  }
  
  public static void a(SyncPair paramSyncPair, String paramString, OutputStream paramOutputStream) {
    if (StringUtil.isFilledTrim(paramString)) {
      Binding binding = new Binding();
      binding.setProperty("out", paramOutputStream);
      try {
        Object object = Groovy.a.a(binding, paramString);
        if (object instanceof SyncFilter && paramSyncPair != null)
          paramSyncPair.filter((SyncFilter)object); 
        paramOutputStream.write("Ok".getBytes(StandardCharsets.UTF_8));
      } catch (Throwable throwable) {
        try {
          if (paramOutputStream != null)
            paramOutputStream.write(throwable.getMessage().getBytes(StandardCharsets.UTF_8)); 
          Log.b(throwable);
        } catch (IOException iOException) {
          Log.b(iOException);
        } 
      } 
    } 
  }
  
  public static void a(Project paramProject, OutputStream paramOutputStream) {
    if (StringUtil.isFilledTrim(paramProject.getSyncInitScript())) {
      Binding binding = new Binding();
      binding.setProperty("out", paramOutputStream);
      binding.setVariable("project", paramProject);
      try {
        Groovy.a.a(binding, paramProject.getSyncInitScript());
        paramOutputStream.write("Ok".getBytes(StandardCharsets.UTF_8));
      } catch (Throwable throwable) {
        try {
          if (paramOutputStream != null)
            paramOutputStream.write(throwable.getMessage().getBytes(StandardCharsets.UTF_8)); 
          Log.b(throwable);
        } catch (IOException iOException) {
          Log.b(iOException);
        } 
      } 
    } 
  }
}
