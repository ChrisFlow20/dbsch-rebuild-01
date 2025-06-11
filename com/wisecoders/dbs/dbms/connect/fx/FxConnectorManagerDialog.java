package com.wisecoders.dbs.dbms.connect.fx;

import com.wisecoders.dbs.dbms.DbmsLocation;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.ConnectorManager;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import java.util.Objects;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

public class FxConnectorManagerDialog extends Dialog$ {
  private final Workspace a;
  
  private final TableView b = new TableView();
  
  public FxConnectorManagerDialog(WorkspaceWindow paramWorkspaceWindow, boolean paramBoolean) {
    super(paramWorkspaceWindow);
    this.a = paramWorkspaceWindow.getWorkspace();
    if (paramBoolean)
      setHeaderText(this.rx.H("offlineHeader")); 
    this.rx.a("flagSelected", () -> (this.b.getSelectionModel().getSelectedIndex() > -1));
    if (this.a.l())
      this.b.getItems().addAll(ConnectorManager.getConnectors(this.a.m().getDbId())); 
    this.b.setEditable(true);
    TableColumn tableColumn1 = new TableColumn("Visible");
    tableColumn1.setEditable(true);
    tableColumn1.setCellValueFactory(paramCellDataFeatures -> {
          Connector connector = (Connector)paramCellDataFeatures.getValue();
          SimpleBooleanProperty simpleBooleanProperty = new SimpleBooleanProperty(connector.isVisibleInMenu());
          simpleBooleanProperty.addListener(());
          return (ObservableValue)simpleBooleanProperty;
        });
    tableColumn1.setOnEditCommit(paramCellEditEvent -> ((Connector)paramCellEditEvent.getRowValue()).setVisibleInMenu(((Boolean)paramCellEditEvent.getNewValue()).booleanValue()));
    tableColumn1.setCellFactory(CheckBoxTableCell.forTableColumn(tableColumn1));
    TableColumn tableColumn2 = new TableColumn("Name");
    tableColumn2.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(((Connector)paramCellDataFeatures.getValue()).getName()));
    TableColumn tableColumn3 = new TableColumn("URL");
    tableColumn3.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(((Connector)paramCellDataFeatures.getValue()).getURL()));
    TableColumn tableColumn4 = new TableColumn("Status");
    tableColumn4.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(((Connector)paramCellDataFeatures.getValue()).getStatusString() + " (" + ((Connector)paramCellDataFeatures.getValue()).getStatusString() + ")"));
    this.b.getColumns().addAll((Object[])new TableColumn[] { tableColumn1, tableColumn2, tableColumn3, tableColumn4 });
    this.b.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramConnector1, paramConnector2) -> this.rx.b());
    setRegionPrefSize((Region)this.b, 650.0D, 350.0D);
    this.b.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    Rx.a(this.b, new double[] { 0.1D, 0.2D, 0.6D, 0.2D });
    this.b.setOnMouseClicked(paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() == 2)
            editConnection(); 
        });
    Connector connector = this.a.s();
    if (connector == null && paramWorkspaceWindow.getWorkspace().l()) {
      String str = Pref.c(paramWorkspaceWindow.getWorkspace().m().getKey() + "-NonOperativeConnector", (String)null);
      if (str != null)
        connector = ConnectorManager.getConnectors(paramWorkspaceWindow.getWorkspace().m().getDbId(), str); 
    } 
    if (connector != null) {
      this.b.getSelectionModel().select(connector);
    } else if (!this.b.getItems().isEmpty()) {
      this.b.getSelectionModel().select(0);
    } 
    this.rx.b();
    setInitFocusedNode((Node)this.b);
    setResultConverter(paramButtonType -> (paramButtonType != null && paramButtonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) ? (Connector)getResult() : null);
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean apply() {
    Connector connector = (Connector)this.b.getSelectionModel().getSelectedItem();
    if (connector != null) {
      if (connector.needsEdit()) {
        (new FxConnectorEditor(this.a, connector)).showDialog().ifPresent(this::setResult);
      } else {
        setResult(connector);
      } 
      if (this.a.l())
        Pref.a(this.a.m().getKey() + "-NonOperativeConnector", connector.getName()); 
    } 
    close();
    return true;
  }
  
  public Node createContentPane() {
    FlowPane$ flowPane$ = (new FlowPane$()).a().e();
    flowPane$.getChildren().addAll(this.rx.c(new String[] { "editConnection", "addConnection", "cloneConnection", "disconnect", "deleteConnection" }));
    BorderPane borderPane = new BorderPane();
    borderPane.setCenter((Node)this.b);
    borderPane.setBottom((Node)flowPane$);
    return (Node)borderPane;
  }
  
  @Action
  public void addConnection() {
    (new FxConnectorEditor(this.a, (this.a.m() != null) ? this.a.m().getDbId() : null, DbmsLocation.a))
      .d()
      .showDialog()
      .ifPresentOrElse(paramConnector -> {
          this.b.getItems().add(paramConnector);
          this.rx.b();
          this.b.getSelectionModel().select(paramConnector);
          this.a.b(paramConnector);
        }() -> {
          if (!this.b.getItems().isEmpty())
            this.b.getSelectionModel().select(0); 
        });
  }
  
  @Action(b = "flagSelected")
  public void deleteConnection() {
    Connector connector = (Connector)this.b.getSelectionModel().getSelectedItem();
    if (connector != null) {
      connector.markForDeletion();
      ConnectorManager.saveConnectors();
      this.a.m().refresh();
      this.b.getItems().remove(connector);
      this.rx.b();
    } 
  }
  
  @Action(b = "flagSelected")
  public void editConnection() {
    Connector connector = (Connector)this.b.getSelectionModel().getSelectedItem();
    if (connector != null) {
      Objects.requireNonNull(this.a);
      (new FxConnectorEditor(this.a, connector)).showDialog().ifPresent(this.a::b);
    } 
    this.b.refresh();
  }
  
  @Action(b = "flagSelected")
  public void cloneConnection() {
    Connector connector = (Connector)this.b.getSelectionModel().getSelectedItem();
    if (connector != null) {
      Connector connector1 = ConnectorManager.createConnector(ConnectorManager.proposeName(connector.getName(), connector.dbId), connector.dbId, connector.getDriverJarClassName(), connector.getDriverJarFileName(), connector.getUrlTemplateName(), connector
          .getHost(), connector.getPort(), connector.getInstance(), connector.getUserName(), connector.isReadOnly());
      connector1.setParam(connector.getParameter());
      connector1.setParam2(connector.getParameter2());
      connector1.setParam3(connector.getParameter3());
      connector1.setParam4(connector.getParameter4());
      this.b.getItems().add(connector1);
      (new FxConnectorEditor(this.a, connector1)).showDialog();
    } 
    this.b.refresh();
  }
  
  @Action(b = "flagSelected")
  public void disconnect() {
    Connector connector = (Connector)this.b.getSelectionModel().getSelectedItem();
    if (connector != null) {
      connector.closeAllEnvoysAndSsh();
      connector.refresh();
      this.b.refresh();
    } 
  }
  
  @Action
  public void help() {}
}
