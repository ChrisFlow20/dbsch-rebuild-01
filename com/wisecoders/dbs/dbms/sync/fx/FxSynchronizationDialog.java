package com.wisecoders.dbs.dbms.sync.fx;

import com.wisecoders.dbs.config.fx.FxSettingsDialog;
import com.wisecoders.dbs.config.fx.FxSettingsDialog$SelectTab;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractExistsDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterScript;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncAction;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncDiff;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncDiffFilter;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.diagram.model.ArrangerMode;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.dialogs.layout.FxEditorFactory;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.editors.text.StyledEditor;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sql.fx.FxSqlEditor;
import com.wisecoders.dbs.sql.fx.FxSqlUploadDialog;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.WebBrowserExternal;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.HGrowBox$;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import org.controlsfx.control.PopOver;

public class FxSynchronizationDialog extends ButtonDialog$ implements WorkspaceWindow {
  private final Workspace a;
  
  private final String b;
  
  private final TreeTableView c = new TreeTableView();
  
  private final SyncPair d;
  
  private final StyledEditor e = new StyledEditor();
  
  private final StyledEditor f = new StyledEditor();
  
  private final boolean i;
  
  private final Button j;
  
  private final SyncDiffFilter k = new SyncDiffFilter();
  
  private final String l;
  
  private final CheckBox m = this.rx.h("mergeVirtualCheckBox", false);
  
  private int n;
  
  public FxSynchronizationDialog(Workspace paramWorkspace, SyncPair paramSyncPair, boolean paramBoolean, String paramString1, String paramString2) {
    super(paramWorkspace);
    this.n = -1;
    this.a = paramWorkspace;
    this.d = paramSyncPair;
    this.i = paramBoolean;
    this.b = paramString1;
    this.l = paramString2;
    this.rx.a("hasCommit", this::e);
    this.rx.a("hasCommitNotReadOnly", () -> (e() && !Sys.B.readOnly.b()));
    this.rx.a("hasRefresh", this::d);
    this.rx.a("flagSelectedItems", () -> !this.c.getSelectionModel().getSelectedItems().isEmpty());
    initModality(Modality.NONE);
    setHeaderText(this.rx.H(((paramString1 != null) ? paramString1 : "") + "Header"));
    this.d.setAction(SyncAction.noAction, true);
    a(this.d, true, true);
    this.e.e(false);
    this.f.e(false);
    this.e.c("Select a difference row/action to activate");
    this.f.c("Select a difference row/action to activate");
    this.c.setRoot(new SyncTreeItem(this.d));
    ContextMenu contextMenu = new ContextMenu();
    this.c.setContextMenu(contextMenu);
    contextMenu.getItems().addAll(this.rx.e(new String[] { "selectedRefresh", "selectedNothing", "selectedCommit" }));
    this.j = this.rx.j("filter");
    b b = new b(this, SyncSide.left, SyncAction.toLeft);
    TreeTableColumn treeTableColumn1 = new TreeTableColumn(this.rx.H("leftTitle"));
    treeTableColumn1.getColumns().addAll((Object[])new TreeTableColumn[] { new SyncTextColumn(SyncSide.left), b });
    TreeTableColumn treeTableColumn2 = new TreeTableColumn(b());
    treeTableColumn2.getColumns().addAll((Object[])new TreeTableColumn[] { new b(this, SyncSide.right, SyncAction.toRight), new SyncTextColumn(SyncSide.right) });
    if (Pref.c("tipSyncAction1", 7))
      Platform.runLater(() -> this.rx.a("selectActionTip.text", paramTreeTableColumn.getGraphic(), PopOver.ArrowLocation.BOTTOM_CENTER, new String[0])); 
    this.c.getColumns().addAll((Object[])new TreeTableColumn[] { new SyncUnitColumn(), treeTableColumn1, treeTableColumn2 });
    this.c.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
    this.c.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramTreeItem1, paramTreeItem2) -> a(paramTreeItem2));
    this.c.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    Dialog$.setRegionPrefSize((Region)this.e, 280.0D, 40.0D);
    Dialog$.setRegionPrefSize((Region)this.f, 280.0D, 40.0D);
    this.m.selectedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          Object object = ((TreeItem)this.c.getSelectionModel().getSelectedItem()).getValue();
          if (object instanceof AbstractExistsDiff) {
            AbstractExistsDiff abstractExistsDiff = (AbstractExistsDiff)object;
            abstractExistsDiff.setMergeVirtual(paramBoolean2.booleanValue());
          } 
        });
    this.k.a("syncFilter" + ((paramWorkspace.m() != null) ? paramWorkspace.m().getKey() : ""));
    c().a(this.k);
    if (this.k.p())
      this.j.setId("buy-now-button"); 
    this.j.setText(this.k.toString());
    a((TreeItem)null);
  }
  
  private void a(TreeItem paramTreeItem) {
    this.rx.b();
    Platform.runLater(() -> {
          this.m.setDisable(true);
          if (this.c.getSelectionModel().getSelectedItems().size() == 1) {
            Object object = ((TreeItem)this.c.getSelectionModel().getSelectedItem()).getValue();
            if (object instanceof AbstractExistsDiff) {
              AbstractExistsDiff abstractExistsDiff = (AbstractExistsDiff)object;
              this.m.setDisable(false);
              this.m.setSelected(abstractExistsDiff.isMergeVirtual());
            } 
          } 
          a((paramTreeItem != null) ? (SyncDiff)paramTreeItem.getValue() : null);
        });
  }
  
  private String b() {
    return (this.l != null) ? this.l : this.rx.H(((this.b != null) ? this.b : "") + "RightTitle");
  }
  
  @Action
  public void filter() {
    (new FxSynchronizationFilterDialog(getDialogScene(), this.k)).showDialog();
    c().a(this.k);
    this.j.setText(this.k.toString());
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$1 = (new GridPane$()).e().g().a(new int[] { -1, -1 }).b(new int[] { -2, -1 });
    FlowPane$ flowPane$1 = (new FlowPane$()).b().a(new Node[] { (Node)this.rx.e("allLabel"), (Node)this.rx.j("allRefresh"), (Node)this.rx.j("allNothing"), (Node)this.rx.j("allCommit") });
    flowPane$1.getChildren().add(this.j);
    FlowPane$ flowPane$2 = (new FlowPane$()).b().a(this.rx.c(new String[] { "syncSettings", "manageRenamedUnits" }));
    gridPane$1.a((Node)flowPane$2, "0,0,l,c");
    gridPane$1.a((Node)flowPane$1, "1,0,l,c");
    gridPane$1.a((Node)this.c, "0,1,1,1,f,f");
    GridPane$ gridPane$2 = (new GridPane$()).e().g().a(new int[] { -1, -1 }).b(new int[] { -2, -2, -1 });
    gridPane$2.a((Node)this.rx.h("diffPaneLabel"), "0,0,1,0,c,c");
    gridPane$2.a((Node)(new HBox$()).a(new Node[] { (Node)new HGrowBox$(), (Node)this.rx.e("leftTitle"), (Node)new HGrowBox$(), (Node)this.m }, ), "0,1,c,c");
    Label label = new Label(b());
    Rx.a((Control)label, this.rx.a("RightTitle.tooltip", new String[0]));
    gridPane$2.a((Node)label, "1,1,c,c");
    gridPane$2.a((Node)this.e, "0,2,f,f");
    gridPane$2.a((Node)this.f, "1,2,f,f");
    this.rx.b();
    SplitPane splitPane = new SplitPane(new Node[] { (Node)gridPane$1, (Node)gridPane$2 });
    setRegionPrefSize((Region)splitPane, 1100.0D, 600.0D);
    splitPane.setOrientation(Orientation.VERTICAL);
    Platform.runLater(() -> paramSplitPane.setDividerPositions(new double[] { 0.7D }));
    this.rx.b();
    return (Node)splitPane;
  }
  
  public void createButtons() {
    Button button = createCloseButton();
    String str = this.rx.H(((this.b != null) ? this.b : "") + "CloseButton");
    if (str != null)
      button.setText(str); 
    createHelpButton("schema-synchronization.html");
    createActionButton("mergeInLocalProject");
    if (!this.d.getUnit(SyncSide.left).is(UnitProperty.g).booleanValue()) {
      createActionButton("generateScript");
      if (!this.i)
        createActionButton("commitInDatabase"); 
    } 
  }
  
  @Action
  public boolean apply() {
    return true;
  }
  
  public Workspace getWorkspace() {
    return this.a;
  }
  
  private void a(SyncDiff paramSyncDiff) {
    if (paramSyncDiff != null) {
      this.e.b((String)null);
      this.f.b((String)null);
      this.e.c((String)null);
      this.f.c((String)null);
      String str = this.a.m().getDbId();
      AlterScript alterScript1 = new AlterScript(str);
      a(alterScript1, SyncSide.right, paramSyncDiff);
      this.e.b(alterScript1.sort().toString());
      AlterScript alterScript2 = new AlterScript(str);
      a(alterScript2, SyncSide.left, paramSyncDiff);
      this.f.b(alterScript2.sort().toString());
    } 
  }
  
  private void a(AlterScript paramAlterScript, SyncSide paramSyncSide, SyncDiff paramSyncDiff) {
    if (paramSyncDiff instanceof SyncPair) {
      SyncPair syncPair = (SyncPair)paramSyncDiff;
      for (SyncPair syncPair1 : syncPair.getChildrenPairs())
        a(paramAlterScript, paramSyncSide, syncPair1); 
      for (AbstractDiff abstractDiff : syncPair.getDifferences()) {
        if ((abstractDiff.getAction() == SyncAction.toLeft && paramSyncSide == SyncSide.right) || (abstractDiff.getAction() == SyncAction.toRight && paramSyncSide == SyncSide.left))
          paramAlterScript.addAll(abstractDiff.commitInto(paramAlterScript.dbId, paramSyncSide, (Dbms.get(paramAlterScript.dbId)).foreignKeysInline.b())); 
      } 
    } else if (paramSyncDiff.getNodeDiff() != null) {
      paramAlterScript.addAll(paramSyncDiff.getNodeDiff().commitInto(paramAlterScript.dbId, paramSyncSide, (Dbms.get(paramAlterScript.dbId)).foreignKeysInline.b()));
    } 
  }
  
  private void a(SyncPair paramSyncPair, boolean paramBoolean1, boolean paramBoolean2) {
    if (paramBoolean1 && paramSyncPair.getAction() == SyncAction.toLeft)
      paramSyncPair.setAction(SyncAction.noAction, true); 
    if (paramBoolean2 && paramSyncPair.getAction() == SyncAction.toRight)
      paramSyncPair.setAction(SyncAction.noAction, true); 
    for (AbstractDiff abstractDiff : paramSyncPair.getDifferences()) {
      if (paramBoolean1 && abstractDiff.getAction() == SyncAction.toLeft)
        abstractDiff.setAction(SyncAction.noAction, true); 
      if (paramBoolean2 && abstractDiff.getAction() == SyncAction.toRight)
        abstractDiff.setAction(SyncAction.noAction, true); 
    } 
    for (SyncPair syncPair : paramSyncPair.getChildrenPairs())
      a(syncPair, paramBoolean1, paramBoolean2); 
  }
  
  private SyncTreeItem c() {
    return (SyncTreeItem)this.c.getRoot();
  }
  
  @Action
  public void allRefresh() {
    c().a(SyncAction.toLeft);
    this.c.refresh();
    this.rx.b();
  }
  
  @Action
  public void allCommit() {
    c().a(SyncAction.toRight);
    this.c.refresh();
    this.rx.b();
  }
  
  @Action
  public void allNothing() {
    this.d.setAction(SyncAction.noAction, true);
    this.c.refresh();
    this.rx.b();
  }
  
  @Action(b = "flagSelectedItems")
  public void selectedRefresh() {
    for (TreeItem treeItem : this.c.getSelectionModel().getSelectedItems())
      ((SyncDiff)treeItem.getValue()).setAction(SyncAction.toLeft, true); 
    this.c.refresh();
    this.rx.b();
  }
  
  @Action(b = "flagSelectedItems")
  public void selectedCommit() {
    for (TreeItem treeItem : this.c.getSelectionModel().getSelectedItems())
      ((SyncDiff)treeItem.getValue()).setAction(SyncAction.toRight, true); 
    this.c.refresh();
    this.rx.b();
  }
  
  @Action(b = "flagSelectedItems")
  public void selectedNothing() {
    for (TreeItem treeItem : this.c.getSelectionModel().getSelectedItems())
      ((SyncDiff)treeItem.getValue()).setAction(SyncAction.noAction, true); 
    this.c.refresh();
    this.rx.b();
  }
  
  void a() {
    this.d.synchronize();
    this.c.setRoot(new SyncTreeItem(this.d));
  }
  
  void a(SyncAction paramSyncAction) {
    this.d.removeChildrenWithAction(paramSyncAction);
    this.c.setRoot(new SyncTreeItem(this.d));
  }
  
  @Action(b = "hasRefresh")
  public void mergeInLocalProject() {
    if (f() && d()) {
      boolean bool = (this.a.p() != null && (this.a.p()).diagram.depicts.isEmpty()) ? true : false;
      this.d.mergeInto(SyncSide.left, SyncAction.toLeft, this.a.o());
      if (bool)
        (this.a.o()).c.a(ArrangerMode.SIMPLE); 
      if (!e())
        this.rx.a(getDialogScene(), "Modifications successfully applied.", new String[0]); 
      a(SyncAction.toLeft);
      this.rx.b();
    } 
  }
  
  @Action(b = "hasCommitNotReadOnly")
  public void commitInDatabase() {
    if (e()) {
      boolean bool = this.a.t();
      Connector connector = this.a.a(this);
      if (connector != null) {
        AlterScript alterScript = this.d.generateCommitScript(connector.dbId, SyncAction.toRight, SyncSide.left);
        this.a.m().injectDDLPrePostScripts(alterScript);
        Platform.runLater(() -> {
              new FxSqlUploadDialog(this, paramConnector, paramAlterScript);
              a(SyncAction.toRight);
              if (!paramBoolean)
                this.a.b((Connector)null); 
            });
      } 
      this.rx.b();
    } 
  }
  
  @Action(b = "hasCommit")
  public void generateScript() {
    Optional optional = (new FxGenerateScriptTargetDialog(this)).showDialog();
    optional.ifPresent(this::a);
  }
  
  private void a(String paramString) {
    if (e()) {
      StringWriter stringWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(stringWriter);
      try {
        this.d.toSql(this.a.m().getDbId(), SyncAction.toRight, SyncSide.left, printWriter);
      } catch (IOException iOException) {
        Log.a("Error generating synchronization scripts", iOException);
      } 
      if (StringUtil.isEmpty(paramString)) {
        FxSqlEditor fxSqlEditor = (FxSqlEditor)FxEditorFactory.a(this, (this.a.q()).d.scripts, true);
        if (fxSqlEditor != null) {
          fxSqlEditor.i.b(stringWriter.toString());
          fxSqlEditor.i.c(true);
          this.rx.a(getDialogScene(), "Script successfully generated", new String[0]);
        } 
      } else {
        try {
          PrintWriter printWriter1 = new PrintWriter(paramString);
          try {
            printWriter1.print(stringWriter);
            this.rx.a(getDialogScene(), "Script successfully generated", new String[0]);
            printWriter1.close();
          } catch (Throwable throwable) {
            try {
              printWriter1.close();
            } catch (Throwable throwable1) {
              throwable.addSuppressed(throwable1);
            } 
            throw throwable;
          } 
        } catch (IOException iOException) {
          this.rx.a(getDialogScene(), iOException);
        } 
      } 
      this.rx.b();
    } 
  }
  
  private boolean d() {
    return this.d.hasNodesWithAction(SyncAction.toLeft);
  }
  
  private boolean e() {
    return this.d.hasNodesWithAction(SyncAction.toRight);
  }
  
  @Action
  public void syncSettings() {
    (new FxSyncSettingsDialog(this, this.d)).showDialog();
    this.c.refresh();
  }
  
  @Action
  public void dbmsSettings() {
    (new FxSettingsDialog(getDialogScene(), FxSettingsDialog$SelectTab.b)).showDialog();
  }
  
  @Action
  public void manageRenamedUnits() {
    new FxSyncRenamedUnitsDialog(this);
  }
  
  private boolean f() {
    int i = this.d.actionWillDrop(SyncAction.toLeft, SyncSide.left, false);
    int j = this.d.actionWillDrop(SyncAction.toRight, SyncSide.right, true);
    if (i > 0 || j > 0) {
      StringBuilder stringBuilder = new StringBuilder();
      if (j > 0)
        stringBuilder.append(j).append(" items will be dropped in database "); 
      if (j > 0 && i > 0)
        stringBuilder.append(" and "); 
      if (i > 0)
        stringBuilder.append(i).append(" items will be dropped in the design model "); 
      return this.rx.a(getDialogScene(), stringBuilder.toString(), "Continue");
    } 
    if (!d() && !e()) {
      this.rx.c(getDialogScene(), "Please select at least one operation by clicking the buttons in the 'Action' column.");
      return false;
    } 
    return true;
  }
  
  @Action
  public void automate() {
    WebBrowserExternal.a(getWorkspace(), "https://dbschema.com/documentation/automation-api.html");
  }
}
