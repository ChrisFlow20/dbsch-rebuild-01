package com.wisecoders.dbs.query.fx;

import com.wisecoders.dbs.data.fx.FxAbstractSqlEditor;
import com.wisecoders.dbs.dbms.connect.fx.FxConnectorManagerDialog;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.FxToolEditor;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.dialogs.system.FxShortcutsDialog;
import com.wisecoders.dbs.editors.text.StyledEditor;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.query.model.items.Query;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.WebBrowserExternal;
import com.wisecoders.dbs.sys.fx.HGrowBox$;
import com.wisecoders.dbs.sys.fx.LabeledPane$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import com.wisecoders.dbs.tips.fx.FxTipsDialog;
import com.wisecoders.dbs.tips.tips.QueryTips;
import javafx.concurrent.Task;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

public class FxQueryEditor extends FxAbstractSqlEditor implements FxToolEditor {
  final Rx g = new Rx(FxQueryEditor.class, this);
  
  public final FxQueryDiagramPane h;
  
  public final Query i;
  
  private final StyledEditor j = new StyledEditor();
  
  private final a k;
  
  private String l;
  
  public FxQueryEditor(Workspace paramWorkspace, Query paramQuery) {
    super(paramWorkspace);
    this.i = paramQuery;
    this.g.G(paramQuery.a.project.getDbId());
    this.g.a("flagIdle", () -> this.b);
    this.g.a("flagIsSplitHorizontal", () -> (getOrientation() == Orientation.HORIZONTAL));
    this.g.a("flagHasResult", () -> (getItems().size() > 1));
    this.h = new FxQueryDiagramPane(this, paramQuery);
    BorderPane borderPane = new BorderPane();
    borderPane.setTop((Node)(this.k = new a(this)));
    borderPane.setCenter((Node)this.h);
    this.j.e(false);
    this.j.a(false);
    LabeledPane$ labeledPane$ = new LabeledPane$(this.g.H("previewTitle"), (Node)this.j);
    labeledPane$.b.getItems().addAll((Object[])new Node[] { (Node)new HGrowBox$(), (Node)this.g.j("copySql") });
    SplitPane splitPane = new SplitPane();
    splitPane.getItems().addAll((Object[])new Node[] { (Node)borderPane, (Node)labeledPane$ });
    splitPane.setDividerPositions(new double[] { 0.8D });
    SplitPane.setResizableWithParent((Node)labeledPane$, Boolean.FALSE);
    getItems().add(splitPane);
    z();
    u().b();
  }
  
  @Action(d = "flagIsSplitHorizontal", b = "flagHasResult")
  public void changeSplitOrientation() {
    setOrientation((getOrientation() == Orientation.HORIZONTAL) ? Orientation.VERTICAL : Orientation.HORIZONTAL);
  }
  
  public boolean i() {
    return true;
  }
  
  public Query y() {
    return this.i;
  }
  
  public String h() {
    return (this.i != null) ? this.i.getName() : "";
  }
  
  public void l() {
    this.l = k();
  }
  
  public String r() {
    String str = this.l;
    this.l = null;
    return str;
  }
  
  public void s() {}
  
  public String t() {
    return null;
  }
  
  public void a(int paramInt, String paramString) {}
  
  public void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString) {}
  
  public Workspace getWorkspace() {
    return this.a;
  }
  
  public void a(String paramString) {}
  
  @Action(b = "flagIdle")
  public Task run() {
    String str = k();
    if (str == null)
      a("Nothing to execute"); 
    return a(str, e(false));
  }
  
  @Action(b = "flagIdle")
  public Task runScript() {
    return c();
  }
  
  @Action(b = "flagIdle")
  public Task explain() {
    return super.explain();
  }
  
  @Action(c = "flagIdle")
  public Task stop() {
    return new FxQueryEditor$1(this);
  }
  
  @Action
  public void addResultPane() {
    e(true);
  }
  
  @Action
  public void inheritConnector() {
    if (this.c != null) {
      this.c.closeAllEnvoysAndSsh();
      this.c = null;
    } 
  }
  
  @Action
  public void chooseConnector() {
    (new FxConnectorManagerDialog(getWorkspace(), false)).showDialog().ifPresent(paramConnector -> this.c = paramConnector);
  }
  
  void z() {
    String str = this.h.P();
    this.j.b(str);
  }
  
  public void c(boolean paramBoolean) {}
  
  public void g() {
    f();
  }
  
  public String k() {
    return this.h.P();
  }
  
  @Action
  public void copySql() {
    this.j.l();
    this.j.copy();
  }
  
  public boolean e() {
    if (this.i.a() == null) {
      this.i.markForDeletion();
      this.a.m().refresh();
      return true;
    } 
    if (this.i.isConfirmed())
      return i(); 
    switch (FxQueryEditor$2.a[this.g.b(getWorkspace(), "saveEditorMessage", new String[0]).c().ordinal()]) {
      case 1:
        this.i.setConfirmed(true);
        return i();
      case 2:
        this.i.markForDeletion();
        getWorkspace().m().refresh();
        return true;
    } 
    return false;
  }
  
  public Rx u() {
    return this.g;
  }
  
  @Action
  public void shortcutsManager() {
    new FxShortcutsDialog(this.a, this.g);
  }
  
  @Action
  public void help() {
    WebBrowserExternal.a(getWorkspace(), "query-builder.html");
  }
  
  @Action
  public void tipsTricks() {
    new FxTipsDialog(getWorkspace(), new QueryTips());
  }
  
  public Glyph m() {
    return BootstrapIcons.journal_check;
  }
  
  public String n() {
    return "query.png";
  }
  
  public void a(boolean paramBoolean, int paramInt) {
    b();
    String str = (a() == null) ? "Inherit" : a().getName();
    if (!StringUtil.areEqual(this.k.a.getText(), str))
      this.k.a.setText(str); 
  }
  
  public boolean o() {
    return false;
  }
  
  public void p() {}
  
  public void a(boolean paramBoolean) {
    this.h.setVisible(paramBoolean);
  }
  
  public void q() {
    this.h.k();
  }
}
