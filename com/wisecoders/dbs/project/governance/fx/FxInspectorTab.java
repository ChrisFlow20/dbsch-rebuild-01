package com.wisecoders.dbs.project.governance.fx;

import com.wisecoders.dbs.config.fx.FxSettingsDialog;
import com.wisecoders.dbs.config.fx.FxSettingsDialog$SelectTab;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.project.governance.model.FieldDefinition;
import com.wisecoders.dbs.project.governance.model.FilterDefinition;
import com.wisecoders.dbs.project.governance.model.Inspector;
import com.wisecoders.dbs.project.governance.model.InspectorDefinition;
import com.wisecoders.dbs.project.governance.model.InspectorFolder;
import com.wisecoders.dbs.project.governance.model.InspectorNode;
import com.wisecoders.dbs.project.governance.model.InspectorRoot;
import com.wisecoders.dbs.project.governance.model.Mode;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.ContextMenu$;
import com.wisecoders.dbs.sys.fx.RowPane$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Objects;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class FxInspectorTab extends Tab {
  private final FxInspectorDialog a;
  
  private final TreeTableView b = new TreeTableView();
  
  private final TreeTableColumn c = new TreeTableColumn("Value");
  
  private final Rx d = new Rx(FxInspectorTab.class, this);
  
  private final ComboBox e = new ComboBox();
  
  private final Mode f;
  
  private final InspectorRoot g;
  
  private static final String h = "${value},";
  
  private static final String i = "${value}|";
  
  private static final String j = "<tag>${value}</tag>";
  
  public FxInspectorTab(FxInspectorDialog paramFxInspectorDialog, InspectorRoot paramInspectorRoot, Mode paramMode) {
    this.g = paramInspectorRoot;
    this.a = paramFxInspectorDialog;
    this.f = paramMode;
    setText(paramInspectorRoot.o());
    setGraphic((Node)BootstrapIcons.stoplights.glyph(new String[0]));
    this.d.G(String.valueOf(paramMode));
    this.d.a("flagSelectedFolder", () -> (this.b.getSelectionModel().getSelectedItem() != null && ((InspectorNode)((TreeItem)this.b.getSelectionModel().getSelectedItem()).getValue()).b()));
    this.d.a("flagSelectedValidator", () -> (this.b.getSelectionModel().getSelectedItem() != null && ((TreeItem)this.b.getSelectionModel().getSelectedItem()).getValue() instanceof Inspector));
    this.d.a("flagSelectedLeafValidator", () -> (this.b.getSelectionModel().getSelectedItem() != null && ((InspectorNode)((TreeItem)this.b.getSelectionModel().getSelectedItem()).getValue()).f()));
    this.d.a("flagSelectedParamValidator", () -> (this.b.getSelectionModel().getSelectedItem() != null && ((TreeItem)this.b.getSelectionModel().getSelectedItem()).getValue() instanceof com.wisecoders.dbs.project.governance.model.InspectorParameter));
    Objects.requireNonNull(paramInspectorRoot);
    this.d.a("flagParamsAreSet", paramInspectorRoot::i);
    this.d.a("flagIsUseAsFilter", () -> (this.b.getSelectionModel().getSelectedItem() != null && ((TreeItem)this.b.getSelectionModel().getSelectedItem()).getValue() instanceof Inspector && ((Inspector)((TreeItem)this.b.getSelectionModel().getSelectedItem()).getValue()).h()));
    this.d.a("flagCanMoveUp", () -> {
          TreeItem treeItem = (TreeItem)this.b.getSelectionModel().getSelectedItem();
          return (treeItem != null && treeItem.getParent() != null && treeItem.getParent().getValue() instanceof InspectorFolder && ((InspectorNode)treeItem.getParent().getValue()).g().indexOf(treeItem.getValue()) > 0);
        });
    this.d.a("flagCanMoveDown", () -> {
          TreeItem treeItem = (TreeItem)this.b.getSelectionModel().getSelectedItem();
          return (treeItem != null && treeItem.getParent() != null && treeItem.getParent().getValue() instanceof InspectorFolder && ((InspectorNode)treeItem.getParent().getValue()).g().indexOf(treeItem.getValue()) < ((InspectorNode)treeItem.getParent().getValue()).g().size() - 1);
        });
    TreeTableColumn treeTableColumn = new TreeTableColumn("Conditions");
    treeTableColumn.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue().getValue()));
    treeTableColumn.setCellFactory(paramTreeTableColumn -> new c());
    this.c.setCellValueFactory(paramCellDataFeatures -> new SimpleObjectProperty((paramCellDataFeatures.getValue().getValue() instanceof com.wisecoders.dbs.project.governance.model.InspectorParameter) ? paramCellDataFeatures.getValue().getValue() : null));
    this.c.setEditable(true);
    this.c.setCellFactory(paramTreeTableColumn -> new FxInspectorTab$1(this));
    this.c.setGraphic(Rx.a());
    this.b.getColumns().addAll((Object[])new TreeTableColumn[] { treeTableColumn, this.c });
    this.b.setRoot(new FxInspectorTab$ValidatorTreeItem(paramInspectorRoot));
    this.b.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
    this.b.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramTreeItem1, paramTreeItem2) -> this.d.b());
    this.b.setEditable(true);
    this.b.setContextMenu((new ContextMenu$()).a(this.d, b()));
    this.e.setValue(paramInspectorRoot.n());
    this.e.getItems().addAll((Object[])new String[] { "${value},", "${value}|", "<tag>${value}</tag>" });
    this.e.setCellFactory(paramListView -> new FxInspectorTab$PatternListCell());
    this.e.setEditable(true);
    this.e.valueProperty().addListener((paramObservableValue, paramString1, paramString2) -> this.d.b());
    RowPane$ rowPane$ = (new RowPane$()).a();
    rowPane$.b((Node)this.b);
    rowPane$.a(this.d.c(b()));
    if (paramMode == Mode.b)
      rowPane$.a(new Node[] { (Node)this.d.e("formatLabel"), (Node)this.e, (Node)this.d.j("export") }); 
    setContent((Node)rowPane$);
    this.d.b();
    ContextMenu contextMenu = new ContextMenu();
    contextMenu.getItems().addAll(this.d.e(new String[] { "renameInspector", "dropInspector" }));
    setContextMenu(contextMenu);
  }
  
  private String[] b() {
    if (this.f == Mode.b)
      return new String[] { "addIteration", "addField", "addFilter", "edit", "moveUp", "moveDown", "drop", "settings" }; 
    return new String[] { "addIteration", "addCheck", "addFilter", "edit", "moveUp", "moveDown", "drop", "settings", "validate" };
  }
  
  public void a() {
    if (this.f == Mode.b)
      this.g.b((String)this.e.getValue()); 
  }
  
  @Action(b = "flagSelectedFolder")
  public void addIteration() {
    InspectorFolder inspectorFolder = (InspectorFolder)((TreeItem)this.b.getSelectionModel().getSelectedItem()).getValue();
    Objects.requireNonNull(inspectorFolder);
    (new FxChooseOperationDialog(this.a, inspectorFolder)).showDialog().ifPresent(inspectorFolder::a);
    ((FxInspectorTab$ValidatorTreeItem)this.b.getRoot()).a();
  }
  
  @Action(b = "flagSelectedFolder")
  public void addFilter() {
    InspectorFolder inspectorFolder = (InspectorFolder)((TreeItem)this.b.getSelectionModel().getSelectedItem()).getValue();
    (new FxChooseInspectorDialog(this.a, inspectorFolder.k(), "filter"))
      .showDialog().ifPresent(paramInspectorDefinition -> paramInspectorFolder.a((FilterDefinition)paramInspectorDefinition, true));
    ((FxInspectorTab$ValidatorTreeItem)this.b.getRoot()).a();
  }
  
  @Action(b = "flagSelectedFolder")
  public void addCheck() {
    InspectorFolder inspectorFolder = (InspectorFolder)((TreeItem)this.b.getSelectionModel().getSelectedItem()).getValue();
    (new FxChooseInspectorDialog(this.a, inspectorFolder.k(), null))
      .showDialog().ifPresent(paramInspectorDefinition -> paramInspectorFolder.a((FilterDefinition)paramInspectorDefinition, false));
    ((FxInspectorTab$ValidatorTreeItem)this.b.getRoot()).a();
  }
  
  @Action(b = "flagSelectedFolder")
  public void addField() {
    InspectorFolder inspectorFolder = (InspectorFolder)((TreeItem)this.b.getSelectionModel().getSelectedItem()).getValue();
    (new FxChooseInspectorDialog(this.a, inspectorFolder.l(), "exporter"))
      .showDialog().ifPresent(paramInspectorDefinition -> paramInspectorFolder.a((FieldDefinition)paramInspectorDefinition));
    ((FxInspectorTab$ValidatorTreeItem)this.b.getRoot()).a();
  }
  
  @Action
  public void renameInspector() {
    String str = this.d.b(this.a.getDialogScene(), "renameInspector");
    if (str != null) {
      this.g.c(str);
      setText(str);
    } 
    this.a.apply();
  }
  
  @Action
  public void dropInspector() {
    if (this.d.a(this.a.getDialogScene(), "confirmDropTab")) {
      InspectorRoot.d.remove(this.g);
      InspectorRoot.e.remove(this.g);
      this.a.apply();
      getTabPane().getTabs().remove(this);
    } 
  }
  
  @Action(b = "flagSelectedParamValidator")
  public void edit() {
    this.b.edit(this.b.getSelectionModel().getSelectedIndex(), this.c);
  }
  
  @Action(b = "flagSelectedLeafValidator")
  public void drop() {
    ((InspectorNode)((TreeItem)this.b.getSelectionModel().getSelectedItem()).getValue()).d();
    ((InspectorNode)this.b.getRoot().getValue()).c();
    ((FxInspectorTab$ValidatorTreeItem)this.b.getRoot()).a();
  }
  
  @Action(b = "flagCanMoveUp")
  public void moveUp() {
    TreeItem treeItem = (TreeItem)this.b.getSelectionModel().getSelectedItem();
    ((InspectorFolder)treeItem.getParent().getValue()).a((InspectorNode)treeItem.getValue());
    ((FxInspectorTab$ValidatorTreeItem)this.b.getRoot()).a();
  }
  
  @Action(b = "flagCanMoveDown")
  public void moveDown() {
    InspectorNode inspectorNode = (InspectorNode)((TreeItem)this.b.getSelectionModel().getSelectedItem()).getParent().getValue();
    ((InspectorFolder)inspectorNode).b((InspectorNode)((TreeItem)this.b.getSelectionModel().getSelectedItem()).getValue());
    ((FxInspectorTab$ValidatorTreeItem)this.b.getRoot()).a();
  }
  
  @Action(b = "flagParamsAreSet")
  public void validate() {
    InspectorRoot inspectorRoot = (InspectorRoot)this.b.getRoot().getValue();
    try {
      inspectorRoot.a(this.a.getWorkspace().m(), (StringWriter)null);
      (new FxValidationResultDialog(this.a, false, Collections.singletonList(inspectorRoot))).showDialog();
    } catch (IOException|ClassNotFoundException iOException) {
      this.d.a(this.a.getDialogScene(), iOException);
    } 
  }
  
  @Action(b = "flagParamsAreSet")
  public void export() {
    if (StringUtil.isEmpty(this.e.getValue()))
      this.d.c(this.a.getDialogScene(), "missingPattern"); 
    a();
    try {
      StringWriter stringWriter = new StringWriter();
      InspectorRoot inspectorRoot = (InspectorRoot)this.b.getRoot().getValue();
      inspectorRoot.a(this.a.getWorkspace().m(), stringWriter);
      (new FxExportResultDialog(this.a.getWorkspace().getWindow(), stringWriter.toString())).showDialog();
    } catch (IOException|ClassNotFoundException iOException) {
      this.d.a(this.a.getDialogScene(), iOException);
    } 
  }
  
  @Action(b = "flagSelectedValidator", d = "flagIsUseAsFilter")
  public void useAsFilter() {
    Inspector inspector = (Inspector)((TreeItem)this.b.getSelectionModel().getSelectedItem()).getValue();
    inspector.a(!inspector.h());
    this.d.b();
    this.b.refresh();
  }
  
  @Action
  public void settings() {
    (new FxSettingsDialog(this.a.getWorkspace(), FxSettingsDialog$SelectTab.a, Sys.B.validateBeforeSave)).showDialog();
  }
}
