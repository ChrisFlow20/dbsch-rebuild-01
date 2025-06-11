package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.SchemaMapping;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import java.util.Comparator;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FxSchemaMappingDialog extends ButtonDialog$ {
  private final Workspace b;
  
  public final SchemaMapping a;
  
  private final TableView c = new TableView();
  
  private final TableColumn d = new TableColumn("Design Model Schema");
  
  private final TableColumn e = new TableColumn("Database Schema");
  
  public FxSchemaMappingDialog(WorkspaceWindow paramWorkspaceWindow, Connector paramConnector) {
    super(paramWorkspaceWindow);
    this.b = paramWorkspaceWindow.getWorkspace();
    this.a = paramConnector.mapping;
    setTitle("Schema Mapping for Connection '" + paramConnector.getName() + "'");
    a();
    initOwner(paramWorkspaceWindow);
  }
  
  public FxSchemaMappingDialog(Workspace paramWorkspace, SchemaMapping paramSchemaMapping) {
    super(paramWorkspace);
    this.b = paramWorkspace;
    this.a = paramSchemaMapping;
    a();
  }
  
  private void a() {
    ObservableList observableList1 = FXCollections.observableArrayList((this.b.m()).schemas);
    observableList1.sort(Comparator.comparing(Schema::getNameWithCatalog));
    this.c.setItems(observableList1);
    this.d.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue()));
    this.d.setCellFactory(paramTableColumn -> new FxSchemaMappingDialog$1(this));
    this.e.setCellValueFactory(paramCellDataFeatures -> new SimpleStringProperty(this.a.getRemoteSchemaName((Schema)paramCellDataFeatures.getValue())));
    ObservableList observableList2 = FXCollections.observableList(this.a.getRemoteSchemaNames());
    this.e.setCellFactory(paramTableColumn -> new FxSchemaMappingDialog$2(this, null, paramObservableList));
    this.e.setEditable(true);
    this.e.setGraphic(Rx.a());
    this.e.setOnEditCommit(paramCellEditEvent -> this.a.setMapping((Schema)paramCellEditEvent.getRowValue(), (String)paramCellEditEvent.getNewValue()));
    this.c.setEditable(true);
    this.c.getColumns().addAll((Object[])new TableColumn[] { this.d, this.e });
    this.c.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
  }
  
  public boolean apply() {
    return true;
  }
  
  public Node createContentPane() {
    return (Node)this.c;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
}
