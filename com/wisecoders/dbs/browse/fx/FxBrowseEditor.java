package com.wisecoders.dbs.browse.fx;

import com.wisecoders.dbs.browse.flow.FxFlowFrame;
import com.wisecoders.dbs.browse.flow.FxFlowPane;
import com.wisecoders.dbs.browse.model.Browse;
import com.wisecoders.dbs.browse.model.BrowseTable;
import com.wisecoders.dbs.browse.model.Cascade;
import com.wisecoders.dbs.browse.store.FxBrowseManager;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.data.filter.fx.FxFilterEditor;
import com.wisecoders.dbs.dbms.connect.fx.FxConnectorManagerDialog;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.diagram.fx.FxDnd;
import com.wisecoders.dbs.diagram.fx.FxLayoutPane;
import com.wisecoders.dbs.diagram.fx.FxSplitWithTabPane;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Diagram;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.FxToolEditor;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.Selection;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.dialogs.system.FxHierarchicalSelectionDialog;
import com.wisecoders.dbs.dialogs.system.FxShortcutsDialog;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.query.fx.FxQueryEditor;
import com.wisecoders.dbs.query.model.items.Query;
import com.wisecoders.dbs.query.model.items.QueryColumn;
import com.wisecoders.dbs.query.model.items.QueryTable;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.ConnectorManager;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.WebBrowserExternal;
import com.wisecoders.dbs.sys.fx.ChoiceDialogWithFilterableCombo;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import com.wisecoders.dbs.tips.fx.FxTipsDialog;
import com.wisecoders.dbs.tips.tips.BrowseTips;
import com.wisecoders.dbs.tips.tips.Tips;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import org.controlsfx.control.PopOver;

public class FxBrowseEditor extends FxSplitWithTabPane implements FxToolEditor {
  private final FxLayoutPane e;
  
  public final Browse a;
  
  public final Rx b = new Rx(FxBrowseEditor.class, this);
  
  public final FxFlowPane c = new FxFlowPane();
  
  public FxBrowseManager d;
  
  private final a g;
  
  private Connector h;
  
  public FxBrowseEditor(FxLayoutPane paramFxLayoutPane, Browse paramBrowse) {
    this.e = paramFxLayoutPane;
    this.a = paramBrowse;
    this.d = new FxBrowseEditor$1(this, paramFxLayoutPane.b);
    this.b.G(paramBrowse.a.project.getDbId());
    this.b.a("flagSelectedFrame", () -> (a() != null));
    Objects.requireNonNull(Sys.B.showRowNumber);
    this.b.a("flagShowRowNumber", Sys.B.showRowNumber::b);
    BorderPane borderPane = new BorderPane();
    borderPane.setTop((Node)(this.g = new a(this)));
    borderPane.setCenter((Node)this.c);
    this.c.a(this.b.H("placeholder"));
    for (BrowseTable browseTable : paramBrowse.a())
      a(browseTable); 
    getItems().add(borderPane);
    this.c.setOnDragOver(paramDragEvent -> {
          if (paramDragEvent.getGestureSource() != this.c.a && paramDragEvent.getDragboard().hasContent(FxDnd.a))
            paramDragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE); 
          paramDragEvent.consume();
        });
    this.c.setOnDragDropped(paramDragEvent -> {
          if (paramDragEvent.getDragboard().hasContent(FxDnd.a)) {
            for (Unit unit : FxDnd.b) {
              if (unit instanceof AbstractTable) {
                BrowseTable browseTable = paramBrowse.a((AbstractTable)unit);
                browseTable.a((int)paramDragEvent.getX(), (int)paramDragEvent.getY());
                a(browseTable).j();
              } 
            } 
            FxDnd.b.clear();
            paramDragEvent.setDropCompleted(true);
            paramDragEvent.consume();
          } 
        });
  }
  
  public FxBrowseFrame a() {
    for (Node node : this.c.a.getChildren()) {
      if (node instanceof FxBrowseFrame && ((FxBrowseFrame)node).k())
        return (FxBrowseFrame)node; 
    } 
    return null;
  }
  
  void a(FxBrowseFrame paramFxBrowseFrame) {
    for (Node node : this.c.a.getChildren()) {
      if (node instanceof FxBrowseFrame)
        ((FxBrowseFrame)node).b((node == paramFxBrowseFrame)); 
    } 
  }
  
  public int b() {
    byte b = 0;
    for (Node node : this.c.a.getChildren()) {
      if (node instanceof FxFlowFrame)
        b++; 
    } 
    return b;
  }
  
  private List r() {
    ArrayList<FxBrowseFrame> arrayList = new ArrayList();
    for (Node node : this.c.a.getChildren()) {
      if (node instanceof FxBrowseFrame) {
        FxBrowseFrame fxBrowseFrame = (FxBrowseFrame)node;
        if (fxBrowseFrame.h.d == null)
          arrayList.add(fxBrowseFrame); 
      } 
    } 
    return arrayList;
  }
  
  private List b(FxBrowseFrame paramFxBrowseFrame) {
    ArrayList<FxBrowseFrame> arrayList = new ArrayList();
    for (Node node : this.c.a.getChildren()) {
      if (node instanceof FxBrowseFrame) {
        FxBrowseFrame fxBrowseFrame = (FxBrowseFrame)node;
        if (fxBrowseFrame.h.b == paramFxBrowseFrame.h)
          arrayList.add(fxBrowseFrame); 
      } 
    } 
    return arrayList;
  }
  
  private FxBrowseFrame a(BrowseTable paramBrowseTable) {
    FxBrowseFrame fxBrowseFrame = b(paramBrowseTable);
    for (BrowseTable browseTable : paramBrowseTable.d())
      fxBrowseFrame.a(browseTable); 
    return fxBrowseFrame;
  }
  
  private FxBrowseFrame b(BrowseTable paramBrowseTable) {
    return new FxBrowseFrame(this, null, paramBrowseTable);
  }
  
  public Connector c() {
    return this.h;
  }
  
  public Connector d() {
    if (this.h != null)
      return this.h; 
    if (this.e.b.t())
      return this.e.b.s(); 
    return this.h = this.e.b.a(getWorkspace());
  }
  
  public Envoy a(String paramString) {
    if (this.h != null)
      return this.h.startEnvoy(paramString); 
    if (this.e.b.t())
      return this.e.b.s().startEnvoy(paramString); 
    if (ConnectorManager.getConnectors(this.e.b.m().getDbId()).isEmpty() && this.b.b(getScene(), "upgradeSchemaDialog", new String[0]).c() == ButtonBar.ButtonData.YES) {
      this.e.b.synchronizeCommit();
      return null;
    } 
    this.h = this.e.b.a(getWorkspace());
    if (this.h != null)
      return this.h.startEnvoy(paramString); 
    return null;
  }
  
  @Action
  public void reload() {
    for (FxBrowseFrame fxBrowseFrame : r())
      fxBrowseFrame.i.a(Cascade.b); 
  }
  
  @Action
  public void addTable() {
    (new FxHierarchicalSelectionDialog(getWorkspace())).showDialog().ifPresent(paramList -> a(paramList, true));
  }
  
  public Workspace getWorkspace() {
    return this.e.b;
  }
  
  public boolean e() {
    if (this.a.a().isEmpty()) {
      this.a.markForDeletion();
      this.a.a.refresh();
      return true;
    } 
    if (this.a.isConfirmed())
      return i(); 
    switch (FxBrowseEditor$2.a[this.b.b(getScene(), "saveEditorDialog", new String[0]).c().ordinal()]) {
      case 1:
        this.a.setConfirmed(true);
        return i();
      case 2:
        this.a.markForDeletion();
        this.a.a.refresh();
        return true;
    } 
    return false;
  }
  
  public void f() {
    if (this.h != null)
      this.h.closeAllEnvoysAndSsh(); 
  }
  
  public void g() {
    f();
  }
  
  public String h() {
    return this.a.getName();
  }
  
  public boolean i() {
    return true;
  }
  
  public AbstractUnit j() {
    return this.a;
  }
  
  void k() {
    for (byte b = 0; b < this.c.a.getChildren().size(); b++) {
      Node node = (Node)this.c.a.getChildren().get(b);
      if (node instanceof FxBrowseFrame) {
        FxBrowseFrame fxBrowseFrame = (FxBrowseFrame)node;
        if (fxBrowseFrame.h.isMarkedForDeletion()) {
          this.c.a.getChildren().remove(fxBrowseFrame);
          if (fxBrowseFrame.g() != null) {
            this.c.a.getChildren().remove((fxBrowseFrame.g()).b);
            this.c.a.getChildren().remove((fxBrowseFrame.g()).a);
          } 
        } else {
          fxBrowseFrame.h();
        } 
      } 
    } 
    this.b.b();
  }
  
  public void l() {
    for (Node node : this.c.a.getChildren()) {
      if (node instanceof FxFlowFrame)
        ((FxFlowFrame)node).g.set(false); 
    } 
  }
  
  public void a(List paramList, boolean paramBoolean) {
    if (!paramList.isEmpty()) {
      Selection selection = new Selection(paramList);
      if (paramBoolean)
        selection.a(); 
      ArrayList<FxBrowseFrame> arrayList = new ArrayList();
      for (Entity entity : selection.b) {
        FxBrowseFrame fxBrowseFrame1 = null;
        Relation relation = null;
        for (FxBrowseFrame fxBrowseFrame : arrayList) {
          List<Relation> list = Diagram.findRelationsWithEntity(entity, fxBrowseFrame.i.d.getEntity());
          if (!selection.d.isEmpty())
            list.retainAll(selection.d); 
          if (!list.isEmpty()) {
            fxBrowseFrame1 = fxBrowseFrame;
            relation = list.get(0);
          } 
        } 
        if (fxBrowseFrame1 == null) {
          FxBrowseFrame fxBrowseFrame = b(this.a.a(entity));
          arrayList.add(fxBrowseFrame);
          continue;
        } 
        BrowseTable browseTable = fxBrowseFrame1.i.d.a(relation);
        FxBrowseFrame fxBrowseFrame2 = fxBrowseFrame1.b(browseTable);
        arrayList.add(fxBrowseFrame2);
      } 
      if (!arrayList.isEmpty()) {
        if (b() == 1)
          ((FxBrowseFrame)arrayList.get(0)).g.set(true); 
        ((FxBrowseFrame)arrayList.get(0)).j();
      } 
      reload();
    } 
  }
  
  @Action
  public void toQueryBuilder() {
    Query query = this.e.d.createQuery(this.e.d.queries.proposeName("From " + this.a.getName()));
    for (FxBrowseFrame fxBrowseFrame : r())
      a(query, (QueryTable)null, fxBrowseFrame, (Relation)null); 
    FxQueryEditor fxQueryEditor = new FxQueryEditor(this.e.b, query);
    this.e.a(fxQueryEditor);
  }
  
  private void a(Query paramQuery, QueryTable paramQueryTable, FxBrowseFrame paramFxBrowseFrame, Relation paramRelation) {
    QueryTable queryTable = (paramQueryTable != null && paramRelation != null) ? paramQueryTable.a(paramRelation) : paramQuery.a(paramFxBrowseFrame.h.getEntity());
    paramQuery.b.attach(queryTable, (int)((paramFxBrowseFrame.getLayoutX() + 1.0D) / 1.5D), (int)((paramFxBrowseFrame.getLayoutY() + 1.0D) / 1.5D));
    for (byte b = 0; b < paramFxBrowseFrame.h.c.getAttributes().size(); b++) {
      Attribute attribute = paramFxBrowseFrame.h.c.getAttributes().get(b);
      QueryColumn queryColumn = queryTable.c(attribute);
      queryColumn.setTicked(true);
      String str = paramFxBrowseFrame.h.b(attribute);
      if (str != null)
        queryTable.a(queryColumn.x, str); 
    } 
    for (FxBrowseFrame fxBrowseFrame : b(paramFxBrowseFrame))
      a(paramQuery, queryTable, fxBrowseFrame, fxBrowseFrame.h.d); 
  }
  
  @Action(b = "flagSelectedFrame")
  public void addFilter() {
    if (this.c.a().isEmpty()) {
      this.b.a(getScene(), "infoAddTable.text", new String[0]);
    } else if (a() == null) {
      this.b.a(getScene(), "infoSelectTable.text", new String[0]);
    } else {
      ChoiceDialogWithFilterableCombo choiceDialogWithFilterableCombo = new ChoiceDialogWithFilterableCombo(getScene(), null, (a()).h.c.getAttributes());
      this.b.a("chooseColumn", choiceDialogWithFilterableCombo);
      choiceDialogWithFilterableCombo.showAndWait().ifPresent(paramAttribute -> new FxFilterEditor(getWorkspace(), (a()).i, paramAttribute, (a()).h.b(paramAttribute)));
    } 
  }
  
  @Action(b = "flagSelectedFrame")
  public void addDetailsFrame() {
    if (this.c.a().isEmpty()) {
      this.b.a(getScene(), "infoAddTable.text", new String[0]);
    } else if (a() == null) {
      this.b.a(getScene(), "infoSelectTable.text", new String[0]);
    } else {
      ChoiceDialogWithFilterableCombo choiceDialogWithFilterableCombo = new ChoiceDialogWithFilterableCombo(getScene(), null, (a()).h.c.getAttributes());
      this.b.a("chooseColumn", choiceDialogWithFilterableCombo);
      choiceDialogWithFilterableCombo.showAndWait().ifPresent(paramAttribute -> new FxBrowseDetailsDialog(getWorkspace(), (a()).i, paramAttribute));
    } 
  }
  
  @Action(b = "flagSelectedFrame")
  public void insertRecord() {
    FxBrowseFrame fxBrowseFrame = a();
    if (fxBrowseFrame != null)
      fxBrowseFrame.insertRecord(); 
  }
  
  @Action(b = "flagSelectedFrame")
  public void deleteRecord() {
    FxBrowseFrame fxBrowseFrame = a();
    if (fxBrowseFrame != null)
      fxBrowseFrame.deleteRecord(); 
  }
  
  @Action(b = "flagSelectedFrame")
  public void visibleColumns() {
    FxBrowseFrame fxBrowseFrame = a();
    if (fxBrowseFrame != null)
      fxBrowseFrame.visibleColumns(); 
  }
  
  @Action
  public void inheritConnector() {
    if (this.h != null) {
      this.h.closeAllEnvoysAndSsh();
      this.h = null;
    } 
  }
  
  @Action(d = "flagShowRowNumber")
  public void showRowNumber() {
    Sys.B.showRowNumber.a(!Sys.B.showRowNumber.b());
    Sys.B.root.j();
    reload();
  }
  
  @Action
  public void chooseConnector() {
    (new FxConnectorManagerDialog(getWorkspace(), false)).showDialog().ifPresent(paramConnector -> this.h = paramConnector);
  }
  
  @Action
  public void shortcutsManager() {
    new FxShortcutsDialog(this.e.b, this.b);
  }
  
  @Action
  public void help() {
    WebBrowserExternal.a(getWorkspace(), "relational-data-explorer.html");
  }
  
  @Action
  public void tipsTricks() {
    new FxTipsDialog(getWorkspace(), new BrowseTips());
  }
  
  public Glyph m() {
    return BootstrapIcons.database;
  }
  
  public String n() {
    return "browse.png";
  }
  
  public void a(boolean paramBoolean, int paramInt) {
    if (paramBoolean && this.a.b.isEmpty() && Tips.shouldShowTip("tipQueryAddTable4"))
      this.b.a("tipQueryAddTable.text", (Node)this, PopOver.ArrowLocation.BOTTOM_CENTER, new String[0]); 
    this.g.a();
    this.d.c();
  }
  
  public boolean o() {
    return false;
  }
  
  public void p() {}
  
  public void a(boolean paramBoolean) {}
  
  public void q() {}
}
