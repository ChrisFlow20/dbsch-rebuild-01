package com.wisecoders.dbs.dbms.sync.fx;

import com.wisecoders.dbs.dbms.reverseEngineer.fx.FxDbSynchronizationTask;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPrioritizable;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.dialogs.system.FxAutomationScriptsDialog;
import com.wisecoders.dbs.editors.text.StyledEditor;
import com.wisecoders.dbs.project.fx.FxTreeTableCell;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Language;
import com.wisecoders.dbs.sql.completion.GroovyCompletion;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.VBox$;
import java.io.ByteArrayOutputStream;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.Priority;

public class FxSyncSettingsDialog extends ButtonDialog$ implements WorkspaceWindow {
  private final Workspace a;
  
  private final StyledEditor b;
  
  private final StyledEditor c;
  
  private final StyledEditor d;
  
  private final StyledEditor e;
  
  private final SyncPair f;
  
  private final TreeTableView i = new TreeTableView();
  
  private final TreeTableColumn j = new TreeTableColumn();
  
  private final TextField k = new TextField();
  
  private String l;
  
  public FxSyncSettingsDialog(WorkspaceWindow paramWorkspaceWindow) {
    this(paramWorkspaceWindow, (SyncPair)null);
  }
  
  public FxSyncSettingsDialog(WorkspaceWindow paramWorkspaceWindow, SyncPair paramSyncPair) {
    super(paramWorkspaceWindow);
    this.a = paramWorkspaceWindow.getWorkspace();
    this.f = paramSyncPair;
    this.b = new StyledEditor(Language.b, true);
    this.c = new StyledEditor(Language.b, true);
    this.d = new StyledEditor(Language.a, false);
    this.e = new StyledEditor(Language.a, false);
    this.b.e(true);
    this.rx.a("flagSelectedPrioritizable", () -> (this.i.getSelectionModel().getSelectedItem() != null && ((TreeItem)this.i.getSelectionModel().getSelectedItem()).getValue() instanceof SyncPrioritizable));
    this.rx.a("flagCanExecuteSyncFilter", () -> StringUtil.isFilledTrim(this.b.t()));
    this.rx.a("flagCanExecuteSyncInitScript", () -> StringUtil.isFilledTrim(this.c.t()));
    this.c.b(this.a.m().getSyncInitScript());
    this.b.c(this.rx.H("syncFilterPromptText.text"));
    this.b.b(this.a.m().getSyncFilter());
    this.b.a((paramObservableValue, paramInteger1, paramInteger2) -> this.rx.b());
    this.b.J.a(new GroovyCompletion());
    this.c.J.a(new GroovyCompletion());
    this.d.b(this.a.m().getDDLPreScript());
    this.e.b(this.a.m().getDDLPostScript());
    this.j.setText(this.rx.H("priorityColumn"));
    this.i.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramTreeItem1, paramTreeItem2) -> this.rx.b());
    this.rx.b();
    setInitFocusedNode((Node)this.b);
  }
  
  public Node createContentPane() {
    TabPane tabPane = new TabPane();
    GridPane$ gridPane$1 = (new GridPane$()).l().a(new int[] { -1 }).b(new int[] { -2, -1, -2 });
    gridPane$1.a((Node)this.rx.e("syncFilterLabel"), "0,0,l,c");
    gridPane$1.a((Node)this.b, "0,1,f,f");
    gridPane$1.a((Node)(new FlowPane$()).a().a(new Node[] { (Node)this.rx.j("showSampleFilterScript"), (Node)this.rx.j("testSyncFilter") }, ), "0,2,l,c");
    b();
    this.k.getStyleClass().add("search-field");
    this.k.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> a());
    tabPane.getTabs().add(this.rx.b("syncPriority", (Node)(new VBox$()).k().c(new Node[] { (Node)this.rx.e("priorityLabel"), (Node)this.k, (Node)this.i, (Node)(new FlowPane$()).a().a(new Node[] { (Node)this.rx.j("editPriority") }) })));
    tabPane.getTabs().add(this.rx.b("filterScriptTab", (Node)gridPane$1));
    GridPane$ gridPane$2 = (new GridPane$()).l().a(new int[] { -1 }).b(new int[] { -2, -1, -2 });
    gridPane$2.a((Node)this.rx.g("syncInitScriptLabel"), "0,0,l,c");
    gridPane$2.a((Node)this.c, "0,1,f,f");
    gridPane$2.a((Node)(new FlowPane$()).a().a(new Node[] { (Node)this.rx.j("showSampleFilterScript"), (Node)this.rx.j("testSyncInitScript") }, ), "0,2,r,c");
    tabPane.getTabs().add(this.rx.b("initScriptTab", (Node)gridPane$2));
    GridPane$ gridPane$3 = (new GridPane$()).l().a(new int[] { -1, -1 }).b(new int[] { -2, -1, -2 });
    gridPane$3.a((Node)this.rx.e("ddlPreScriptLabel"), "0,0,l,c");
    gridPane$3.a((Node)this.d, "0,1,f,f");
    gridPane$3.a((Node)this.rx.e("ddlPostScriptLabel"), "1,0,l,c");
    gridPane$3.a((Node)this.e, "1,1,f,f");
    tabPane.getTabs().add(this.rx.b("sqlScriptsTab", (Node)gridPane$3));
    gridPane$1.getStyleClass().add("search-field");
    tabPane.setPrefSize(950.0D, 650.0D);
    return (Node)tabPane;
  }
  
  public void createButtons() {
    createActionButton("save");
    createCancelButton();
    createHelpButton("schema-synchronization.html#sync_filter");
  }
  
  @Action(b = "flagCanExecuteSyncFilter")
  public void testSyncFilter() {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    FxDbSynchronizationTask.a(this.f, this.b.t(), byteArrayOutputStream);
    this.rx.a(getDialogScene(), byteArrayOutputStream.toString(), new String[0]);
  }
  
  @Action(b = "flagCanExecuteSyncInitScript")
  public void testSyncInitScript() {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    this.a.m().setSyncInitScript(this.c.t());
    FxDbSynchronizationTask.a(this.a.m(), byteArrayOutputStream);
    this.rx.a(getDialogScene(), byteArrayOutputStream.toString(), new String[0]);
  }
  
  @Action(b = "flagSelectedPrioritizable")
  public void editPriority() {
    this.i.edit(this.i.getSelectionModel().getSelectedIndex(), this.j);
  }
  
  @Action
  public void save() {
    this.a.m().setSyncFilter(this.b.t());
    this.a.m().setDDLPreScript(this.d.t());
    this.a.m().setDDLPostScript(this.e.t());
    this.a.m().setSyncInitScript(this.c.t());
    this.a.u();
    close();
  }
  
  @Action
  public void showSampleFilterScript() {
    (new FxAutomationScriptsDialog(this, "Synchronization Filter")).showDialog();
  }
  
  public boolean apply() {
    return true;
  }
  
  public Workspace getWorkspace() {
    return this.a;
  }
  
  private void b() {
    TreeTableColumn treeTableColumn1 = new TreeTableColumn(this.rx.H("itemColumn"));
    treeTableColumn1.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue().getValue()));
    treeTableColumn1.setCellFactory(paramTreeTableColumn -> new FxTreeTableCell(true));
    TreeTableColumn treeTableColumn2 = new TreeTableColumn(this.rx.H("defaultPriorityColumn"));
    treeTableColumn2.setCellValueFactory(paramCellDataFeatures -> {
          Object object = paramCellDataFeatures.getValue().getValue();
          SyncPrioritizable syncPrioritizable = (SyncPrioritizable)object;
          return (ObservableValue)new ReadOnlyStringWrapper((object instanceof SyncPrioritizable) ? ("" + syncPrioritizable.getDefaultSyncPriority()) : null);
        });
    treeTableColumn2.setStyle("-fx-alignment: center-right;");
    treeTableColumn2.setCellFactory(paramTreeTableColumn -> new FxSyncSettingsDialog$1(this));
    this.j.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue().getValue()));
    this.j.setEditable(true);
    this.j.setGraphic(Rx.a());
    this.j.setStyle("-fx-alignment: center-right;");
    this.j.setCellFactory(paramTreeTableColumn -> new FxSyncSettingsDialog$2(this));
    this.i.getColumns().addAll((Object[])new TreeTableColumn[] { treeTableColumn1, treeTableColumn2, this.j });
    FxSyncSettingsDialog$FxTreeItem fxSyncSettingsDialog$FxTreeItem = new FxSyncSettingsDialog$FxTreeItem(this, this.a.m());
    this.i.setRoot(fxSyncSettingsDialog$FxTreeItem);
    this.i.setEditable(true);
    this.i.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
    VBox$.setVgrow((Node)this.i, Priority.ALWAYS);
  }
  
  public void a() {
    String str = this.k.getText();
    this.l = (str != null) ? str.toLowerCase().trim() : null;
    TreeItem treeItem = this.i.getRoot();
    if (treeItem instanceof FxSyncSettingsDialog$FxTreeItem) {
      FxSyncSettingsDialog$FxTreeItem fxSyncSettingsDialog$FxTreeItem = (FxSyncSettingsDialog$FxTreeItem)treeItem;
      fxSyncSettingsDialog$FxTreeItem.a();
    } 
  }
}
