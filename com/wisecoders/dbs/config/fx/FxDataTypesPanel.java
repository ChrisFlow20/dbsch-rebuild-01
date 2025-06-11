package com.wisecoders.dbs.config.fx;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.DbmsLocation;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.connect.fx.FxConnectorEditor;
import com.wisecoders.dbs.dbms.store.DataTypeLoader;
import com.wisecoders.dbs.dbms.store.DataTypeStore;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.JavaType;
import com.wisecoders.dbs.schema.Precision;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import java.io.File;
import java.io.IOException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;

public class FxDataTypesPanel extends BorderPane {
  private final Scene a;
  
  private final TableView b = new TableView();
  
  private final Rx c = new Rx(FxDataTypesPanel.class, this);
  
  private DbmsTypes d = DbmsTypes.get("MySql");
  
  FxDataTypesPanel(Scene paramScene) {
    this.a = paramScene;
    this.c.a("flagSelectedDataType", () -> (this.b.getSelectionModel().getSelectedItem() != null));
    a();
    FlowPane$ flowPane$ = (new FlowPane$()).h();
    flowPane$.getChildren().addAll(this.c.c(new String[] { "addDataType", "editDataType", "learnDbms", "dropDataType", "importDataTypes", "exportDataTypes" }));
    setCenter((Node)this.b);
    setBottom((Node)flowPane$);
  }
  
  private void a() {
    TableColumn tableColumn1 = new TableColumn("Data Type");
    tableColumn1.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(((DataType)paramCellDataFeatures.getValue()).toString()));
    tableColumn1.setEditable(false);
    TableColumn tableColumn2 = new TableColumn("Precision");
    tableColumn2.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(((DataType)paramCellDataFeatures.getValue()).getPrecision()));
    tableColumn2.setCellFactory(ComboBoxTableCell.forTableColumn((Object[])Precision.values()));
    tableColumn2.setEditable(true);
    tableColumn2.setOnEditCommit(paramCellEditEvent -> {
          ((DataType)paramCellEditEvent.getRowValue()).setPrecision((Precision)paramCellEditEvent.getNewValue());
          b();
        });
    tableColumn2.setGraphic(Rx.a());
    TableColumn tableColumn3 = new TableColumn("Equivalent Java Type");
    tableColumn3.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(JavaType.getFromDataType((DataType)paramCellDataFeatures.getValue())));
    tableColumn3.setCellFactory(ComboBoxTableCell.forTableColumn(JavaType.KNOWN));
    tableColumn3.setEditable(true);
    tableColumn3.setOnEditCommit(paramCellEditEvent -> {
          ((DataType)paramCellEditEvent.getRowValue()).setJavaType(((JavaType)paramCellEditEvent.getNewValue()).javaType);
          b();
        });
    tableColumn3.setGraphic(Rx.a());
    TableColumn tableColumn4 = new TableColumn("Options");
    tableColumn4.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(((DataType)paramCellDataFeatures.getValue()).options.c_()));
    tableColumn4.setCellFactory(TextFieldTableCell.forTableColumn());
    tableColumn4.setEditable(true);
    tableColumn4.setOnEditCommit(paramCellEditEvent -> {
          ((DataType)paramCellEditEvent.getRowValue()).options.b((String)paramCellEditEvent.getNewValue());
          b();
        });
    tableColumn4.setGraphic(Rx.a());
    TableColumn tableColumn5 = new TableColumn(null);
    tableColumn5.setGraphic((Node)this.c.e("regExpMatchesColumnHeader"));
    tableColumn5.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(((DataType)paramCellDataFeatures.getValue()).getRegExMatchers()));
    tableColumn5.setCellFactory(TextFieldTableCell.forTableColumn());
    tableColumn5.setEditable(true);
    tableColumn5.setOnEditCommit(paramCellEditEvent -> {
          ((DataType)paramCellEditEvent.getRowValue()).setRegExMatchers((String)paramCellEditEvent.getNewValue());
          b();
        });
    this.b.getColumns().addAll((Object[])new TableColumn[] { tableColumn1, tableColumn2, tableColumn3, tableColumn4, tableColumn5 });
    this.b.setEditable(true);
    this.b.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    this.b.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramDataType1, paramDataType2) -> this.c.b());
  }
  
  @Action
  public void addDataType() {
    String str = this.c.b(getScene(), "Data Type Name");
    if (str != null && !str.trim().isEmpty()) {
      for (DataType dataType : this.d.getDataTypes()) {
        if (str.equalsIgnoreCase(dataType.toString())) {
          this.c.c(getScene(), "Data Type '" + str + "' already defined.");
          return;
        } 
      } 
      this.d.createType(1111, str, Precision.NONE);
      this.c.b();
      b();
    } 
  }
  
  @Action(b = "flagSelectedDataType")
  public void editDataType() {}
  
  @Action(b = "flagSelectedDataType")
  public void dropDataType() {
    this.d.deleteDataType((DataType)this.b.getSelectionModel().getSelectedItem());
    b();
  }
  
  @Action
  public void learnDbms() {
    (new FxConnectorEditor((Workspace)this.a, this.d.dbId, DbmsLocation.a)).showDialog().ifPresent(paramConnector -> {
          try {
            paramConnector.learnDbmsTemplate();
          } catch (Exception exception) {
            Log.a("Error learning data types", exception);
            this.c.b(getScene(), exception.toString(), exception);
          } 
        });
  }
  
  private void b() {
    try {
      DataTypeStore.a(this.d);
    } catch (IOException iOException) {
      this.c.b(getScene(), iOException.toString(), iOException);
    } 
  }
  
  public void a(DbmsTypes paramDbmsTypes) {
    this.d = paramDbmsTypes;
    this.b.setItems(this.d.dataTypes);
  }
  
  @Action
  public void importDataTypes() {
    File file = FxFileChooser.a(getScene(), "Load Data Types from File", Sys.k.toString(), FileOperation.a, new FileType[] { FileType.j });
    if (file != null)
      try {
        new DataTypeLoader(this.d, file);
      } catch (Exception exception) {
        Log.a("Error in configuration by saving configuration to file", exception);
        this.c.b(getScene(), exception.getLocalizedMessage(), exception);
      }  
  }
  
  @Action
  public void exportDataTypes() {
    File file = FxFileChooser.a(getScene(), "Save Data Types to File", FileOperation.b, new FileType[] { FileType.j });
    if (file != null)
      try {
        DataTypeStore.a(this.d, file);
      } catch (Exception exception) {
        Log.a("Error loading data types from file", exception);
        this.c.a(getScene(), exception);
      }  
  }
}
