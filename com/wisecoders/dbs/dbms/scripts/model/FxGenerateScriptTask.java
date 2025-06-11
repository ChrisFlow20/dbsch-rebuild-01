package com.wisecoders.dbs.dbms.scripts.model;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.DumpStatement;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.reverseEngineer.fx.FxDbSynchronizationTask;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterScript;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.diagram.fx.FxLayoutPane;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.TreeSelection;
import com.wisecoders.dbs.dialogs.layout.FxEditorFactory;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Function;
import com.wisecoders.dbs.schema.GroovyMethod;
import com.wisecoders.dbs.schema.MaterializedView;
import com.wisecoders.dbs.schema.Procedure;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Script;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.TableDependency;
import com.wisecoders.dbs.schema.Trigger;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import javafx.concurrent.Task;

@DoNotObfuscate
public class FxGenerateScriptTask extends Task {
  private final Workspace a;
  
  private final Project b;
  
  private final File c;
  
  private final boolean d;
  
  private final boolean e;
  
  private final boolean f;
  
  private final boolean g;
  
  private final StringWriter h = new StringWriter();
  
  private final TreeSelection i;
  
  protected FxGenerateScriptTask(Workspace paramWorkspace, Project paramProject, TreeSelection paramTreeSelection, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, File paramFile) {
    this.a = paramWorkspace;
    this.b = paramProject;
    this.c = paramFile;
    this.d = paramBoolean1;
    this.e = paramBoolean2;
    this.f = paramBoolean3;
    this.g = paramBoolean4;
    this.i = paramTreeSelection;
  }
  
  protected Void a() {
    Thread.currentThread().setName("DbSchema: Export Schema & Data Task");
    updateMessage("Generate Script...");
    Writer writer = (Writer)((this.c != null) ? new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.c), StandardCharsets.UTF_8)) : new PrintWriter(this.h));
    try {
      if (this.d)
        exportSchemaDDL(this.b, this.i, this.g, writer); 
      if (this.e) {
        updateMessage("Export Data...");
        Envoy envoy = this.a.s().startEnvoy("Export schema script and database tables data");
        try {
          boolean bool = (this.g && (Dbms.get(this.b.getDbId())).alterForeignKey.c()) ? true : false;
          TableDependency tableDependency = new TableDependency(this.b.getEntities(), bool);
          for (AbstractTable abstractTable : tableDependency.getTablesInCreationOrder()) {
            if (!isCancelled() && this.i.isSelected(abstractTable) && abstractTable instanceof Table) {
              updateMessage("Table " + abstractTable.getName() + " data");
              DumpStatement dumpStatement = new DumpStatement(envoy, abstractTable, this.f, writer);
              dumpStatement.a();
              if (dumpStatement.b.D() > 0)
                writer.write("\n"); 
              writer.flush();
            } 
          } 
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
      } 
      if (writer != null)
        writer.close(); 
    } catch (Throwable throwable) {
      if (writer != null)
        try {
          writer.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
    return null;
  }
  
  protected void succeeded() {
    if (this.c == null)
      try {
        FxLayoutPane fxLayoutPane = this.a.q();
        Script script = fxLayoutPane.d.createScript(null);
        script.setFreshCreated(true);
        script.setText(this.h.toString());
        FxEditorFactory.a(this.a, script);
      } catch (OutOfMemoryError outOfMemoryError) {
        this.a.getRx().b(this.a, "Got memory error.\nPlease increase memory settings in DbSchema.vmoptions in the installation folder or \n./DbSchema.app/Contents/vmoptions.txt on Mac OS.\nPlease save the result in file. Cause: \n" + String.valueOf(outOfMemoryError), outOfMemoryError);
      }  
    this.a.getRx().d(this.a, "Scripts successfully generated !");
  }
  
  protected void failed() {
    Throwable throwable = getException();
    Log.a("Error exporting data ", throwable);
    this.a.getRx().a(this.a, throwable);
  }
  
  @GroovyMethod
  public static void exportSchemaDDL(Project paramProject, TreeSelection paramTreeSelection, boolean paramBoolean, Writer paramWriter) {
    String str = paramProject.getDbId();
    FxDbSynchronizationTask.a(paramProject, System.out);
    Project project = new Project(paramProject.getName(), str);
    (new SyncPair(paramProject, project)).mergeInto(SyncSide.right, null);
    for (Schema schema1 : paramProject.schemas) {
      Schema schema2 = project.getSchema(schema1.getCatalogName(), schema1.getName());
      if (paramTreeSelection.hasSelectedChildren(schema1)) {
        for (Table table1 : schema1.tables) {
          Table table2 = (Table)schema2.tables.getByName(table1.getName());
          if (!paramTreeSelection.isSelected(table1))
            table2.markForDeletion(); 
        } 
        for (View view1 : schema1.views) {
          View view2 = (View)schema2.views.getByName(view1.getName());
          if (!paramTreeSelection.isSelected(view1))
            view2.markForDeletion(); 
        } 
        if (schema1.userDataTypes != null && schema2.userDataTypes != null)
          for (UserDataType userDataType1 : schema1.userDataTypes) {
            UserDataType userDataType2 = (UserDataType)schema2.userDataTypes.getByName(userDataType1.getName());
            if (!paramTreeSelection.isSelected(userDataType1))
              userDataType2.markForDeletion(); 
          }  
        for (MaterializedView materializedView1 : schema1.materializedViews) {
          MaterializedView materializedView2 = (MaterializedView)schema2.materializedViews.getByName(materializedView1.getName());
          if (!paramTreeSelection.isSelected(materializedView1))
            materializedView2.markForDeletion(); 
        } 
        for (Procedure procedure1 : schema1.procedures) {
          Procedure procedure2 = (Procedure)schema2.procedures.getByName(procedure1.getName());
          if (!paramTreeSelection.isSelected(procedure1))
            procedure2.markForDeletion(); 
        } 
        for (Trigger trigger1 : schema1.triggers) {
          Trigger trigger2 = (Trigger)schema2.triggers.getByName(trigger1.getName());
          if (!paramTreeSelection.isSelected(trigger1))
            trigger2.markForDeletion(); 
        } 
        for (Function function1 : schema1.functions) {
          Function function2 = (Function)schema2.functions.getByName(function1.getName());
          if (!paramTreeSelection.isSelected(function1))
            function2.markForDeletion(); 
        } 
        for (Sequence sequence1 : schema1.sequences) {
          Sequence sequence2 = (Sequence)schema2.sequences.getByName(sequence1.getName());
          if (!paramTreeSelection.isSelected(sequence1))
            sequence2.markForDeletion(); 
        } 
        continue;
      } 
      schema2.markForDeletion();
    } 
    project.refresh();
    SyncPair syncPair = new SyncPair(project, new Project(paramProject.getName(), str));
    AlterScript alterScript = syncPair.generateCommitScript(str, null, SyncSide.left, (paramBoolean && (Dbms.get(paramProject.getDbId())).alterForeignKey.c()));
    paramProject.injectDDLPrePostScripts(alterScript);
    alterScript.writeToOutputStream(paramWriter);
  }
}
