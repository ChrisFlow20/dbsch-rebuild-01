package com.wisecoders.dbs.data.fx;

import com.wisecoders.dbs.config.fx.FxSettingsDialog;
import com.wisecoders.dbs.config.fx.FxSettingsDialog$SelectTab;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.data.model.result.ResultColumn;
import com.wisecoders.dbs.data.model.result.ResultRow;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.sys.fx.ContextMenu$;
import com.wisecoders.dbs.sys.fx.FxUtil;
import com.wisecoders.dbs.sys.fx.MenuItem$;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;

public abstract class FxResultTableView extends TableView implements FxUpdatableTableView {
  private final FxResultPane a;
  
  protected final RowNumberTableColumn c;
  
  private boolean b = true;
  
  public boolean d = true;
  
  protected final FilteredList e;
  
  public FxResultTableView(FxResultPane paramFxResultPane) {
    this.a = paramFxResultPane;
    this.c = new RowNumberTableColumn(paramFxResultPane.d);
    this.e = new FilteredList(paramFxResultPane.d.g);
    setItems((ObservableList)this.e);
    setEditable(true);
    setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    setTableMenuButtonVisible(true);
    getStyleClass().add("data-pane");
    getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    getSelectionModel().setCellSelectionEnabled(true);
    getFocusModel().focusedCellProperty().addListener((paramObservableValue, paramTablePosition1, paramTablePosition2) -> {
          if (b())
            paramFxResultPane.a((paramTablePosition2 != null && paramTablePosition2.getTableColumn() != null) ? paramTablePosition2.getTableColumn().getCellData(paramTablePosition2.getRow()) : null); 
        });
    getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramResultRow1, paramResultRow2) -> Platform.runLater(()));
    getItems().addListener(paramChange -> {
          if (getItems().isEmpty())
            this.b = true; 
          if (!getItems().isEmpty() && this.b) {
            Platform.runLater(());
            this.b = false;
          } 
        });
  }
  
  protected void a() {
    getColumns().clear();
    getColumns().add(this.c);
    for (byte b = 0; b < this.a.d.C(); b++) {
      ResultColumn resultColumn = this.a.d.d(b);
      TableColumn tableColumn = new TableColumn(resultColumn.a);
      tableColumn.setEditable(resultColumn.e());
      ContextMenu$ contextMenu$ = new ContextMenu$();
      MenuItem$ menuItem$ = new MenuItem$("Format...", () -> (new FxSettingsDialog(getScene(), FxSettingsDialog$SelectTab.a, Sys.B.format)).showDialog());
      contextMenu$.getItems().setAll((Object[])new MenuItem[] { menuItem$ });
      tableColumn.setContextMenu(contextMenu$);
      Label label = new Label(null, (Node)DataType.getSymbolicGlyph(resultColumn.b).glyph(new String[] { "font-smallest", "text-gray" }));
      label.setTooltip(new Tooltip(resultColumn.a));
      tableColumn.setGraphic((Node)label);
      FxUtil.a(this, tableColumn, resultColumn, true);
      tableColumn.setUserData(resultColumn);
      tableColumn.setCellValueFactory(paramCellDataFeatures -> new SimpleObjectProperty(((ResultRow)paramCellDataFeatures.getValue()).a(paramResultColumn)));
      tableColumn.setCellFactory(paramTableColumn -> new e(this, paramResultColumn));
      tableColumn.widthProperty().addListener((paramObservableValue, paramNumber1, paramNumber2) -> FxUtil.a(paramTableColumn));
      getColumns().add(tableColumn);
    } 
  }
  
  public void a(boolean paramBoolean) {
    getSelectionModel().setSelectionMode(paramBoolean ? SelectionMode.MULTIPLE : SelectionMode.SINGLE);
    getSelectionModel().setCellSelectionEnabled(paramBoolean);
  }
  
  public boolean b() {
    return getSelectionModel().isCellSelectionEnabled();
  }
  
  public void b(boolean paramBoolean) {
    this.d = paramBoolean;
    if (paramBoolean) {
      if (!getColumns().contains(this.c))
        getColumns().add(0, this.c); 
    } else {
      getColumns().remove(this.c);
    } 
  }
  
  public boolean c() {
    return this.d;
  }
}
