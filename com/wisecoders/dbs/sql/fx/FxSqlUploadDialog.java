package com.wisecoders.dbs.sql.fx;

import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterScript;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterStatement;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dialogs.layout.FxEditorFactory;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.editors.text.StyledEditor;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.VBox$;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

public class FxSqlUploadDialog extends ButtonDialog$ {
  private final WorkspaceWindow a;
  
  private final AlterScript b;
  
  private final Connector c;
  
  private final StyledEditor d;
  
  private final TextArea e = new TextArea();
  
  private final Label f;
  
  private int i = 0;
  
  private boolean j = false;
  
  public FxSqlUploadDialog(WorkspaceWindow paramWorkspaceWindow, Connector paramConnector, AlterScript paramAlterScript) {
    super(paramWorkspaceWindow);
    if (paramConnector == null)
      throw new NullPointerException("SQLUploadDialog got Null Connector"); 
    this.a = paramWorkspaceWindow;
    this.b = paramAlterScript;
    this.d = new StyledEditor(true);
    this.c = paramConnector;
    this.f = this.rx.e("sqlTitle");
    this.e.setEditable(false);
    this.rx.a("flagCanExecute", () -> (a() && !this.j));
    showDialog();
  }
  
  public Node createContentPane() {
    setRegionPrefSize((Region)this.d, 600.0D, 300.0D);
    setRegionPrefSize((Region)this.e, 600.0D, 200.0D);
    VBox$ vBox$ = (new VBox$()).l().i().c(new Node[] { (Node)this.f, (Node)this.d, (Node)this.rx.e("messageTitle"), (Node)this.e });
    a(0);
    return (Node)vBox$;
  }
  
  public void createButtons() {
    createActionButton("execute");
    createActionButton("executeAll");
    createActionButton("skip");
    createActionButton("reportABug", ButtonBar.ButtonData.LEFT);
    createCloseButton();
  }
  
  public boolean apply() {
    return true;
  }
  
  @Action(b = "flagCanExecute")
  public Task execute() {
    return new FxSqlUploadDialog$QueryExecutionEngine(this, false);
  }
  
  @Action(b = "flagCanExecute")
  public Task executeAll() {
    return new FxSqlUploadDialog$QueryExecutionEngine(this, true);
  }
  
  @Action(b = "flagCanExecute")
  public void edit() {
    AlterStatement alterStatement = b();
    if (alterStatement != null) {
      FxEditorFactory.a(this.a, alterStatement.pair.getUnit(SyncSide.left));
      this.d.b(alterStatement.getSQLWithTerminator(this.c.dbId));
    } 
  }
  
  @Action
  public void reportABug() {
    showInformation("Thank you for letting us know !", new String[0]);
  }
  
  @Action(b = "flagCanExecute")
  public void skip() {
    this.e.setText("");
    a(1);
    d();
  }
  
  private boolean a() {
    return (this.i < this.b.statements.size());
  }
  
  private AlterStatement b() {
    return a() ? this.b.statements.get(this.i) : null;
  }
  
  private String c() {
    AlterStatement alterStatement = b();
    return (alterStatement != null) ? alterStatement.toString() : null;
  }
  
  private void a(int paramInt) {
    this.i += paramInt;
    this.d.b(c());
    this.f.setText(this.rx.H("sqlTitle") + " ( " + this.rx.H("sqlTitle") + " of " + Math.min(this.i + 1, this.b.statements.size()) + " )");
    d();
  }
  
  private void d() {
    this.rx.b();
  }
}
