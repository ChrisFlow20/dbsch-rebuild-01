package com.wisecoders.dbs.dbms.sync.fx;

import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncDiffFilter;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.Separator$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;

public class FxSynchronizationFilterDialog extends Dialog$ {
  private final SyncDiffFilter a;
  
  private final CheckBox b;
  
  private final CheckBox c;
  
  private final CheckBox d;
  
  private final CheckBox e;
  
  private final CheckBox f;
  
  private final CheckBox i;
  
  private final CheckBox j;
  
  private final CheckBox k;
  
  private final CheckBox l;
  
  private final CheckBox m;
  
  private final CheckBox n;
  
  private final CheckBox o;
  
  private final CheckBox p;
  
  private final CheckBox q;
  
  private final TextField r;
  
  public FxSynchronizationFilterDialog(Scene paramScene, SyncDiffFilter paramSyncDiffFilter) {
    super(paramScene.getWindow());
    setGraphic(BootstrapIcons.search);
    this.a = paramSyncDiffFilter;
    this.r = this.rx.t("filterField");
    this.b = this.rx.h("tableCheck", paramSyncDiffFilter.b());
    this.c = this.rx.h("viewCheck", paramSyncDiffFilter.c());
    this.d = this.rx.h("columnCheck", paramSyncDiffFilter.d());
    this.e = this.rx.h("indexCheck", paramSyncDiffFilter.e());
    this.f = this.rx.h("foreignKeyCheck", paramSyncDiffFilter.f());
    this.i = this.rx.h("constraintCheck", paramSyncDiffFilter.g());
    this.j = this.rx.h("sequenceCheck", paramSyncDiffFilter.h());
    this.k = this.rx.h("triggerCheck", paramSyncDiffFilter.i());
    this.l = this.rx.h("procedureCheck", paramSyncDiffFilter.j());
    this.m = this.rx.h("functionCheck", paramSyncDiffFilter.k());
    this.n = this.rx.h("commentsCheck", paramSyncDiffFilter.l());
    this.o = this.rx.h("existsLeftCheck", paramSyncDiffFilter.n());
    this.p = this.rx.h("existsRightCheck", paramSyncDiffFilter.o());
    this.q = this.rx.h("persistFilterCheck", paramSyncDiffFilter.p());
    this.r.setText(paramSyncDiffFilter.m());
  }
  
  @Action
  public void reset() {
    a(true);
    this.r.setText(null);
    this.o.setSelected(false);
    this.p.setSelected(false);
  }
  
  @Action
  public void selectAll() {
    a(true);
  }
  
  @Action
  public void selectNone() {
    a(false);
  }
  
  private void a(boolean paramBoolean) {
    this.b.setSelected(paramBoolean);
    this.c.setSelected(paramBoolean);
    this.d.setSelected(paramBoolean);
    this.e.setSelected(paramBoolean);
    this.f.setSelected(paramBoolean);
    this.i.setSelected(paramBoolean);
    this.j.setSelected(paramBoolean);
    this.k.setSelected(paramBoolean);
    this.l.setSelected(paramBoolean);
    this.m.setSelected(paramBoolean);
    this.n.setSelected(paramBoolean);
  }
  
  public Node createContentPane() {
    SplitMenuButton splitMenuButton = this.rx.g("selectAll", true);
    splitMenuButton.getItems().addAll(this.rx.e(new String[] { "selectAll", "selectNone" }));
    GridPane$ gridPane$ = (new GridPane$()).l().a(new int[] { -2, -2, -2, -2, -1 }).a((Node)this.r, "1,0,4,0,f,c").a((Node)new Separator$("Include"), "0,1,4,1,f,c").a((Node)splitMenuButton, "1,2,l,c").a((Node)this.b, "1,3,l,c").a((Node)this.c, "1,4,l,c").a((Node)this.d, "1,5,l,c").a((Node)this.e, "1,6,l,c").a((Node)this.f, "2,3,l,c").a((Node)this.i, "2,4,l,c").a((Node)this.j, "2,5,l,c").a((Node)this.k, "2,6,l,c").a((Node)this.l, "3,3,l,c").a((Node)this.m, "3,4,l,c").a((Node)this.n, "3,5,l,c").a((Node)new Separator$("Item Should Exist In"), "0,7,4,7,f,c").a((Node)this.o, "1,8,l,c").a((Node)this.p, "2,8,l,c").a((Node)new Separator$("Persists"), "0,9,4,9,f,c").a((Node)this.q, "1,10,l,c");
    gridPane$.setPrefWidth(400.0D);
    return (Node)gridPane$;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
    createActionButton("reset", ButtonBar.ButtonData.LEFT);
  }
  
  public boolean apply() {
    this.a.a(this.b.isSelected());
    this.a.c(this.d.isSelected());
    this.a.d(this.e.isSelected());
    this.a.g(this.j.isSelected());
    this.a.b(this.c.isSelected());
    this.a.e(this.f.isSelected());
    this.a.f(this.i.isSelected());
    this.a.h(this.k.isSelected());
    this.a.i(this.l.isSelected());
    this.a.j(this.m.isSelected());
    this.a.k(this.n.isSelected());
    this.a.b(StringUtil.nullIfEmpty(this.r.getText()));
    this.a.l(this.o.isSelected());
    this.a.m(this.p.isSelected());
    this.a.n(this.q.isSelected());
    this.a.a();
    return true;
  }
}
