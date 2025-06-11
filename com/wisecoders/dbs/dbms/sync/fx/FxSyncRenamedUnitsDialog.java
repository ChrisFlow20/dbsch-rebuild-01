package com.wisecoders.dbs.dbms.sync.fx;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.project.fx.FxTreeTableCell;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.ToggleGroup$;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import org.controlsfx.control.SegmentedButton;

public class FxSyncRenamedUnitsDialog extends ButtonDialog$ {
  private final Rx a = new Rx(FxSyncRenamedUnitsDialog.class, this);
  
  private final TreeTableView b = new TreeTableView();
  
  private final FxSynchronizationDialog c;
  
  private final TreeTableColumn d;
  
  private final FxSyncRenamedUnitsDialog$FxTreeItem e;
  
  private final ToggleButton f;
  
  private final ToggleButton i;
  
  public FxSyncRenamedUnitsDialog(FxSynchronizationDialog paramFxSynchronizationDialog) {
    super(paramFxSynchronizationDialog);
    this.c = paramFxSynchronizationDialog;
    this.a.a("flagCanEdit", () -> (this.b.getSelectionModel().getSelectedItem() != null && ((TreeItem)this.b.getSelectionModel().getSelectedItem()).getValue() instanceof AbstractUnit));
    TreeTableColumn treeTableColumn = new TreeTableColumn("Name");
    treeTableColumn.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue().getValue()));
    treeTableColumn.setCellFactory(paramTreeTableColumn -> new FxTreeTableCell(false));
    treeTableColumn.setEditable(false);
    this.d = new TreeTableColumn("Previous Name");
    this.d.setGraphic((Node)BootstrapIcons.pencil.glyph(new String[] { "glyph-orange" }));
    this.d.setCellValueFactory(paramCellDataFeatures -> {
          Object object = paramCellDataFeatures.getValue().getValue();
          return (ObservableValue)new SimpleStringProperty((object instanceof AbstractUnit) ? ((AbstractUnit)object).getString(UnitProperty.a) : null);
        });
    this.d.setEditable(true);
    this.d.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    this.d.setOnEditCommit(paramCellEditEvent -> ((AbstractUnit)paramCellEditEvent.getRowValue().getValue()).setUnitProperty(UnitProperty.a, paramCellEditEvent.getNewValue()));
    this.b.getColumns().addAll((Object[])new TreeTableColumn[] { treeTableColumn, this.d });
    this.b.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
    this.b.setShowRoot(true);
    this.b.setRoot(this.e = new FxSyncRenamedUnitsDialog$FxTreeItem(this, paramFxSynchronizationDialog.getWorkspace().m()));
    this.b.setEditable(true);
    this.b.getStyleClass().add("project-structure");
    this.b.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramTreeItem1, paramTreeItem2) -> this.a.b());
    this.f = this.a.n("viewRenamed");
    this.i = this.a.n("viewAll");
    this.f.setSelected(true);
    new ToggleGroup$(new ToggleButton[] { this.f, this.i });
    a();
    showDialog();
  }
  
  void a() {
    this.e.setExpanded(true);
    for (TreeItem treeItem : this.e.getChildren()) {
      if (treeItem.getValue() instanceof Folder && ((Folder)treeItem.getValue()).childClass == Schema.class) {
        treeItem.setExpanded(true);
        for (TreeItem treeItem1 : treeItem.getChildren())
          treeItem1.setExpanded(true); 
      } 
    } 
  }
  
  public Node createContentPane() {
    SegmentedButton segmentedButton = new SegmentedButton();
    segmentedButton.getButtons().addAll((Object[])new ToggleButton[] { this.f, this.i });
    GridPane$ gridPane$ = (new GridPane$()).l();
    gridPane$.a((Node)segmentedButton, "0,0,l,c");
    gridPane$.a((Node)this.b, "0,1,f,f");
    gridPane$.a((Node)(new FlowPane$()).a().a(this.a.c(new String[] { "editCell", "clearCell" }, )), "0,2,l,c");
    return (Node)gridPane$;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean apply() {
    this.c.a();
    return true;
  }
  
  @Action
  public void viewAll() {
    b();
  }
  
  @Action
  public void viewRenamed() {
    b();
  }
  
  @Action(b = "flagCanEdit")
  public void editCell() {
    this.b.edit(this.b.getSelectionModel().getSelectedIndex(), this.d);
  }
  
  @Action(b = "flagCanEdit")
  public void clearCell() {
    ((AbstractUnit)((TreeItem)this.b.getSelectionModel().getSelectedItem()).getValue()).removeUnitProperty(UnitProperty.a);
    this.b.refresh();
  }
  
  private void b() {
    this.e.a();
    this.b.refresh();
  }
}
