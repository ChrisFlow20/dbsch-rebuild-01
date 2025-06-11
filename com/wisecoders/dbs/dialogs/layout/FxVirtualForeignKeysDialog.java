package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.config.fx.FxSettingsDialog;
import com.wisecoders.dbs.config.fx.FxSettingsDialog$SelectTab;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.VirtualForeignKeySuggestion;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.ContextMenu$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.RowPane$;
import java.util.List;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Region;

public class FxVirtualForeignKeysDialog extends ButtonDialog$ implements WorkspaceWindow {
  private final Workspace a;
  
  private final ObservableList b = FXCollections.observableArrayList();
  
  private final RadioButton c;
  
  private final RadioButton d;
  
  private final Label e = new Label();
  
  private final CheckBox f;
  
  private final RadioButton i;
  
  private final RadioButton j;
  
  private final TextField k;
  
  private final TextField l;
  
  private final Label m;
  
  private final Label n;
  
  private final TableView o = new TableView();
  
  private final TableColumn p = new TableColumn("Create");
  
  private final TableColumn q = new TableColumn("From Table");
  
  private final TableColumn r = new TableColumn("References (PK Table)");
  
  private final TableColumn s = new TableColumn("Query");
  
  private final TableColumn t = new TableColumn("Status");
  
  private final boolean u;
  
  private static final String v = "findVirtualFkPkPattern";
  
  private static final String w = "findVirtualFkFkPattern";
  
  private static final String x = "findVirtualFkReferredIsPk";
  
  private static final String y = "findVirtualFkUseRegExp";
  
  public FxVirtualForeignKeysDialog(Workspace paramWorkspace, boolean paramBoolean) {
    super(paramWorkspace);
    this.a = paramWorkspace;
    this.u = paramBoolean;
    this.c = this.rx.i("radioByName", true);
    this.d = this.rx.i("radioBySQLHistory", false);
    this.f = this.rx.h("referredIsPk", Pref.b("findVirtualFkReferredIsPk", true));
    this.i = this.rx.w("useRegExp");
    this.j = this.rx.i("useSameNames", true);
    this.rx.a("flagHasSelection", this::a);
    this.rx.a("flagRecordHasVirtualFk", () -> (this.o.getSelectionModel().getSelectedItem() != null && ((VirtualForeignKeySuggestion)this.o.getSelectionModel().getSelectedItem()).foreignKey != null));
    this.m = this.rx.e("pkPatternLabel");
    this.n = this.rx.e("fkPatternLabel");
    this.k = this.rx.t("pkPattern");
    this.l = this.rx.t("fkPattern");
    this.k.setText(Pref.a("findVirtualFkPkPattern"));
    this.l.setText(Pref.a("findVirtualFkFkPattern"));
    this.o.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramVirtualForeignKeySuggestion1, paramVirtualForeignKeySuggestion2) -> this.rx.b());
    this.o.setContextMenu((new ContextMenu$()).a(this.rx, new String[] { "editVirtualFk", "separator", "editVirtualFkSourceTable", "editVirtualFkTargetTable" }));
    ToggleGroup toggleGroup1 = new ToggleGroup();
    toggleGroup1.getToggles().addAll((Object[])new Toggle[] { (Toggle)this.c, (Toggle)this.d });
    ToggleGroup toggleGroup2 = new ToggleGroup();
    toggleGroup2.getToggles().addAll((Object[])new Toggle[] { (Toggle)this.j, (Toggle)this.i });
    this.i.setSelected(Pref.b("findVirtualFkUseRegExp", false));
    this.e.setGraphic(null);
    if ((Dbms.get(paramWorkspace.m().getDbId())).statementsHistoryQuery.j()) {
      this.d.setText(this.rx.H("radioBySQLHistory"));
      this.d.setSelected(true);
      this.e.setText((Dbms.get(paramWorkspace.m().getDbId())).statementsHistoryAdvice.c_());
    } else {
      this.d.setText(this.rx.H("radioBySQLHistoryDisabled"));
      this.d.setDisable(true);
      this.c.setSelected(true);
    } 
    for (Schema schema : (paramWorkspace.m()).schemas) {
      for (Entity entity : schema.getEntities()) {
        for (Relation relation : entity.getRelations()) {
          if (relation.isVirtual() && relation instanceof ForeignKey)
            this.b.add(new VirtualForeignKeySuggestion(null, (ForeignKey)relation, true)); 
        } 
      } 
    } 
    this.c.selectedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> b());
    this.i.selectedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> b());
    b();
    if (paramBoolean)
      this.rx.a(b(false)); 
    setOnCloseRequest(paramDialogEvent -> a(false));
  }
  
  @Action
  public Task find() {
    if (this.c.isSelected())
      return b(true); 
    return c();
  }
  
  public boolean apply() {
    return true;
  }
  
  private boolean a() {
    for (VirtualForeignKeySuggestion virtualForeignKeySuggestion : this.b) {
      if (virtualForeignKeySuggestion.isSelected() && virtualForeignKeySuggestion.foreignKey != null)
        return true; 
    } 
    return false;
  }
  
  public boolean cancel() {
    a(true);
    return true;
  }
  
  private void a(boolean paramBoolean) {
    for (VirtualForeignKeySuggestion virtualForeignKeySuggestion : this.b) {
      if ((!virtualForeignKeySuggestion.isSelected() || paramBoolean) && virtualForeignKeySuggestion.foreignKey != null)
        virtualForeignKeySuggestion.foreignKey.markForDeletion(); 
    } 
    this.a.y();
  }
  
  public Node createContentPane() {
    this.o.setItems(this.b);
    this.o.setEditable(true);
    setRegionPrefSize((Region)this.o, 800.0D, 400.0D);
    this.p.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue()));
    this.p.setCellFactory(paramTableColumn -> new FxVirtualForeignKeysDialog$1(this));
    this.q.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(((VirtualForeignKeySuggestion)paramCellDataFeatures.getValue()).getFromTableString()));
    this.r.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(((VirtualForeignKeySuggestion)paramCellDataFeatures.getValue()).getToTableString()));
    this.s.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue()));
    this.s.setCellFactory(paramTableColumn -> new aa());
    this.t.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue()));
    this.t.setCellFactory(paramTableColumn -> new ab());
    this.o.getColumns().addAll((Object[])new TableColumn[] { this.p, this.q, this.r, this.s, this.t });
    Rx.a(this.o, new double[] { 0.07D, 0.15D, 0.15D, 0.33D, 0.3D });
    MenuButton menuButton = this.rx.f("selectAll", true);
    menuButton.getItems().addAll(this.rx.e(new String[] { "selectAll", "selectNone", "separator", "selectFksInCurrentLayout" }));
    GridPane$ gridPane$ = (new GridPane$()).l().a(new int[] { -2, -1 }).b(new int[] { -2, -2, -2, -2, -2, -2, -1 });
    gridPane$.a((Node)this.c, "0,0,l,c");
    gridPane$.a((Node)(new RowPane$()).a().c().a((Node)this.f).a((Node)this.j).a(new Node[] { (Node)this.i, (Node)this.m, (Node)this.k, (Node)this.n, (Node)this.l }, ), "0,1,l,c");
    gridPane$.a((Node)(new HBox$()).d().a(new Node[] { (Node)this.d, (Node)this.rx.j("dbmsSettings") }, ), "0,2,l,c");
    gridPane$.a((Node)this.e, "1,2,r,c");
    gridPane$.a((Node)this.rx.h("foundSeparator"), "0,4,1,4,f,c");
    gridPane$.a((Node)menuButton, "1,5,r,c");
    gridPane$.a((Node)this.o, "0,6,1,6,f,f");
    return (Node)gridPane$;
  }
  
  private void b() {
    this.f.setDisable(!this.c.isSelected());
    this.i.setDisable(!this.c.isSelected());
    this.j.setDisable(!this.c.isSelected());
    boolean bool = (!this.c.isSelected() || !this.i.isSelected()) ? true : false;
    this.m.setDisable(bool);
    this.n.setDisable(bool);
    this.k.setDisable(bool);
    this.l.setDisable(bool);
  }
  
  public void createButtons() {
    createActionButton("find", ButtonBar.ButtonData.YES);
    createOkButton();
    createCancelButton();
  }
  
  @Action
  public void selectAll() {
    for (VirtualForeignKeySuggestion virtualForeignKeySuggestion : this.b)
      virtualForeignKeySuggestion.setSelected(true); 
    this.o.refresh();
  }
  
  @Action
  public void selectNone() {
    for (VirtualForeignKeySuggestion virtualForeignKeySuggestion : this.b)
      virtualForeignKeySuggestion.setSelected(false); 
    this.o.refresh();
  }
  
  @Action
  public void selectFksInCurrentLayout() {
    Layout layout = this.a.p();
    if (layout != null) {
      List list = layout.getEntities();
      this.b.forEach(paramVirtualForeignKeySuggestion -> paramVirtualForeignKeySuggestion.setSelected(
            (paramVirtualForeignKeySuggestion.foreignKey != null && paramList.contains(paramVirtualForeignKeySuggestion.foreignKey.getEntity()) && paramList.contains(paramVirtualForeignKeySuggestion.foreignKey.getTargetEntity()))));
    } 
    this.o.refresh();
  }
  
  @Action(b = "flagRecordHasVirtualFk")
  public void editVirtualFk() {
    FxEditorFactory.a(this.a, ((VirtualForeignKeySuggestion)this.o.getSelectionModel().getSelectedItem()).foreignKey);
  }
  
  @Action(b = "flagRecordHasVirtualFk")
  public void editVirtualFkSourceTable() {
    FxEditorFactory.a(this.a, ((VirtualForeignKeySuggestion)this.o.getSelectionModel().getSelectedItem()).foreignKey.getEntity());
  }
  
  @Action(b = "flagRecordHasVirtualFk")
  public void editVirtualFkTargetTable() {
    FxEditorFactory.a(this.a, ((VirtualForeignKeySuggestion)this.o.getSelectionModel().getSelectedItem()).foreignKey.getTargetEntity());
  }
  
  @Action
  public void dbmsSettings() {
    (new FxSettingsDialog(this.a, FxSettingsDialog$SelectTab.b, (Dbms.get(this.a.m().getDbId())).statementsHistoryQuery)).showDialog();
  }
  
  private Task b(boolean paramBoolean) {
    this.b.clear();
    Pref.a("findVirtualFkPkPattern", this.k.getText());
    Pref.a("findVirtualFkFkPattern", this.l.getText());
    Pref.a("findVirtualFkReferredIsPk", this.f.isSelected());
    Pref.a("findVirtualFkUseRegExp", this.i.isSelected());
    return new FxVirtualForeignKeysDialog$2(this, paramBoolean);
  }
  
  private void a(List paramList) {
    if (paramList != null)
      for (VirtualForeignKeySuggestion virtualForeignKeySuggestion : paramList) {
        if (!this.b.contains(virtualForeignKeySuggestion))
          this.b.add(virtualForeignKeySuggestion); 
      }  
  }
  
  private Task c() {
    this.b.clear();
    String str = (Dbms.get(this.a.m().getDbId())).statementsHistoryQuery.c_();
    if (StringUtil.isEmptyTrim(str)) {
      this.rx.d(getDialogScene(), "missingConfigurationSetting");
      return null;
    } 
    Connector connector = this.a.a(this);
    if (connector != null)
      return new FxVirtualForeignKeysDialog$3(this, connector, str); 
    return null;
  }
  
  public Workspace getWorkspace() {
    return this.a;
  }
}
