package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.FxUtil;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

public class FxShortcutsDialog extends ButtonDialog$ {
  private final TableView a = new TableView();
  
  private final Rx b;
  
  public FxShortcutsDialog(WorkspaceWindow paramWorkspaceWindow, Rx paramRx) {
    super(paramWorkspaceWindow);
    this.b = paramRx;
    a();
    setRegionPrefSize((Region)getDialogPane(), 700.0D, 500.0D);
    showDialog();
  }
  
  private void a() {
    Properties properties = this.b.i();
    for (Object object : properties.keySet()) {
      String str = object.toString();
      if (str.endsWith(".accelerator")) {
        String str1 = str.substring(0, str.length() - ".accelerator".length());
        String str2 = properties.containsKey(str1 + ".text") ? properties.getProperty(str1 + ".text") : str1;
        this.a.getItems().add(new f(this, str1, str2));
      } 
    } 
    Collections.sort((List<?>)this.a.getItems(), Comparator.comparing(f::a));
    TableColumn tableColumn1 = new TableColumn(this.rx.H("actionColumn"));
    tableColumn1.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(((f)paramCellDataFeatures.getValue()).a()));
    TableColumn tableColumn2 = new TableColumn(this.rx.H("shortcutColumn"));
    tableColumn2.setGraphic(Rx.a());
    tableColumn2.setCellValueFactory(paramCellDataFeatures -> new SimpleStringProperty(this.b.E(((f)paramCellDataFeatures.getValue()).b)));
    tableColumn2.setCellFactory(paramTableColumn -> new g(this));
    tableColumn2.setEditable(true);
    tableColumn2.setOnEditCommit(paramCellEditEvent -> ((f)paramCellEditEvent.getRowValue()).a((String)paramCellEditEvent.getNewValue()));
    FxUtil.a((Control)this.a, this.rx);
    this.a.setEditable(true);
    this.a.getColumns().addAll((Object[])new TableColumn[] { tableColumn1, tableColumn2 });
    this.a.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
  }
  
  public Node createContentPane() {
    BorderPane borderPane = new BorderPane();
    borderPane.setCenter((Node)this.a);
    return (Node)borderPane;
  }
  
  public void createButtons() {
    createActionButton("restore", ButtonBar.ButtonData.LEFT);
    createCloseButton();
  }
  
  public boolean apply() {
    return true;
  }
  
  @Action
  public void restore() {
    for (f f : this.a.getItems())
      this.b.C(f.b); 
    this.a.refresh();
  }
}
