package com.wisecoders.dbs.sql.fx;

import com.wisecoders.dbs.data.fx.FxScriptResultPane;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.reverseEngineer.tasks.SyncMode;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.fx.SyncDispatcher;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Point;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.SchemaMapping;
import com.wisecoders.dbs.sql.parser.DDLParser;
import com.wisecoders.dbs.sys.SqlUtil;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;

public class FxDDLParserTask extends Task {
  private final FxSqlEditor a;
  
  private final FxScriptResultPane b;
  
  private final Project c;
  
  private final DDLParser d;
  
  private final ByteArrayOutputStream e = new ByteArrayOutputStream();
  
  private final PrintStream f = new PrintStream(this.e);
  
  private final boolean g;
  
  public FxDDLParserTask(FxSqlEditor paramFxSqlEditor, boolean paramBoolean) {
    this.a = paramFxSqlEditor;
    this.b = paramFxSqlEditor.d(false);
    this.g = paramBoolean;
    this.c = paramBoolean ? new Project("SQL Parser", paramFxSqlEditor.d()) : paramFxSqlEditor.x();
    this.d = new DDLParser(this.c, this.f);
    this.b.a(false);
    paramFxSqlEditor.f(false);
    messageProperty().addListener((paramObservableValue, paramString1, paramString2) -> paramFxSqlEditor.s());
  }
  
  protected Void a() {
    this.a.l();
    byte b = 0;
    String str;
    while (!StringUtil.isEmptyTrim(str = this.a.r())) {
      updateMessage("Statement " + b++);
      if (!SqlUtil.isDmlBlock(str)) {
        str = Dbms.get(this.a.d()).formatQueryForExecution(str);
        if (StringUtil.isFilledTrim(str))
          this.d.parse(str); 
      } 
      this.b.c(this.e.toString());
      this.e.reset();
    } 
    this.b.c("Done\n");
    return null;
  }
  
  protected void succeeded() {
    this.f.close();
    Workspace workspace = this.a.getWorkspace();
    this.d.generateForeignKeys();
    this.c.refresh();
    if (this.g) {
      boolean bool = this.c.schemas.isEmpty();
      SyncPair syncPair = new SyncPair(workspace.m(), new SchemaMapping(), this.c);
      syncPair.filter(paramAbstractDiff -> ((paramAbstractDiff instanceof com.wisecoders.dbs.dbms.sync.engine.diffs.SchemaExistsDiff || paramAbstractDiff instanceof com.wisecoders.dbs.dbms.sync.engine.diffs.TableExistsDiff) && paramAbstractDiff.pair.right == null));
      Platform.runLater(() -> {
            SyncDispatcher.a(paramWorkspace, paramSyncPair, SyncMode.a, false, "sqlScript", null, true);
            Layout layout = paramWorkspace.p();
            if (paramBoolean && layout != null) {
              layout.rename("Parsed Script Layout");
              Depict depict = layout.diagram.getMostReferredDepict();
              if (depict != null)
                layout.diagram.createCallout(this.a.u().H("parseScriptCallout"), new Point(depict.getPosition().a(), depict.getPosition().b() - 100.0D)); 
            } 
            b();
          });
    } 
  }
  
  protected void failed() {
    this.f.close();
    this.b.c(getException().toString() + "\n" + getException().toString());
    b();
  }
  
  private void b() {
    this.b.c().q();
    this.b.a(true);
    this.a.f(true);
    this.a.s();
    this.a.u().b();
  }
}
