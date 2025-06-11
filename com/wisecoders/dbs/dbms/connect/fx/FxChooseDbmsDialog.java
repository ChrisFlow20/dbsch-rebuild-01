package com.wisecoders.dbs.dbms.connect.fx;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.ConnectorManager;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.RowPane$;
import java.util.Arrays;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FxChooseDbmsDialog extends Dialog$ {
  private static final String a = "lastDbms";
  
  private final ListView b = new ListView();
  
  private final ListView c = new ListView();
  
  private final TextField d = new TextField();
  
  private final List e;
  
  private final WorkspaceWindow f;
  
  public FxChooseDbmsDialog(WorkspaceWindow paramWorkspaceWindow, String paramString) {
    super(paramWorkspaceWindow);
    this.f = paramWorkspaceWindow;
    this.rx.a("flagSelectedDbms", () -> (this.b.getSelectionModel().getSelectedItem() != null));
    this.e = Dbms.getKnownDbmsExclude(new String[] { "LogicalDesign" });
    this.b.getItems().addAll(this.e);
    VBox.setVgrow((Node)this.b, Priority.ALWAYS);
    this.b.setCellFactory(paramListView -> new a());
    this.b.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramString1, paramString2) -> {
          this.c.getItems().clear();
          this.c.getItems().addAll(ConnectorManager.getConnectors(paramString2));
          this.c.getItems().add(null);
          this.c.getSelectionModel().select(0);
          if (paramWorkspaceWindow.getWorkspace().l()) {
            String str = Pref.c(paramWorkspaceWindow.getWorkspace().m().getKey() + "-NonOperativeConnector", (String)null);
            if (str != null)
              this.c.getSelectionModel().select(ConnectorManager.getConnectors(paramString2, str)); 
          } 
          this.rx.b();
        });
    if (paramString == null && paramWorkspaceWindow.getWorkspace().l())
      paramString = paramWorkspaceWindow.getWorkspace().m().getDbId(); 
    if (paramString == null)
      paramString = Pref.c("lastDbms", "MySql"); 
    if (paramString != null) {
      this.b.getSelectionModel().select(paramString);
      this.b.scrollTo(paramString);
    } 
    this.b.setPrefSize(350.0D, 280.0D);
    this.b.addEventHandler(MouseEvent.MOUSE_CLICKED, paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() == 2)
            next(); 
        });
    this.c.setCellFactory(paramListView -> new FxChooseDbmsDialog$1(this));
    this.c.setPrefHeight(90.0D);
    this.c.getStyleClass().add("font-large");
    this.d.getStyleClass().add("search-field");
    this.d.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> {
          String str = this.d.getText();
          if (str != null)
            str = str.trim().toLowerCase(); 
          if (StringUtil.isEmpty(str)) {
            this.b.getItems().clear();
            this.b.getItems().addAll(this.e);
          } else {
            this.b.getItems().clear();
            for (String str1 : this.e) {
              if (str1.toLowerCase().contains(str))
                this.b.getItems().add(str1); 
            } 
          } 
        });
    this.rx.b();
    setResultConverter(paramButtonType -> {
          ButtonBar.ButtonData buttonData = (paramButtonType == null) ? null : paramButtonType.getButtonData();
          return (buttonData == ButtonBar.ButtonData.OK_DONE) ? new FxChooseDbmsDialog$DbmsChoice((String)this.b.getSelectionModel().getSelectedItem(), (Connector)this.c.getSelectionModel().getSelectedItem()) : null;
        });
  }
  
  public Node createContentPane() {
    return (Node)(new RowPane$()).f().c(new Node[] { (Node)this.d, (Node)this.b, (Node)this.c });
  }
  
  @Action
  public void otherLocations() {
    this.rx.a(getDialogScene(), "otherLocations", new String[0]);
  }
  
  public void createButtons() {
    createActionButton("next");
    createCancelButton();
  }
  
  public boolean apply() {
    return false;
  }
  
  @Action(b = "flagSelectedDbms")
  public void next() {
    Pref.a("lastDbms", (String)this.b.getSelectionModel().getSelectedItem());
    Connector connector = (Connector)this.c.getSelectionModel().getSelectedItem();
    if (this.f.getWorkspace().l() && connector != null)
      Pref.a(this.f.getWorkspace().m().getKey() + "-NonOperativeConnector", connector.getName()); 
    setResult(new FxChooseDbmsDialog$DbmsChoice((String)this.b.getSelectionModel().getSelectedItem(), connector));
    close();
  }
  
  private static final List i = Arrays.asList(new String[] { 
        "SqlServer", "PostgreSQL", "MySql", "MariaDb", "Redshift", "Oracle", "CsvFiles", "Cassandra", "MongoDb", "Redshift", 
        "Snowflake", "Sqlite" });
}
