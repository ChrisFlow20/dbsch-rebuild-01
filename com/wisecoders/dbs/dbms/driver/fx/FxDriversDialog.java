package com.wisecoders.dbs.dbms.driver.fx;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.driver.model.DriverManager;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.FileUtils;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.WebBrowserExternal;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.FxEditableStringTreeTableCell;
import com.wisecoders.dbs.sys.fx.VBox$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.io.IOException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class FxDriversDialog extends ButtonDialog$ {
  private final TreeTableView a = new TreeTableView();
  
  private final TreeTableColumn b = new TreeTableColumn();
  
  private final ComboBox c = new ComboBox();
  
  private boolean d = false;
  
  public FxDriversDialog(WorkspaceWindow paramWorkspaceWindow) {
    this(paramWorkspaceWindow, paramWorkspaceWindow.getWorkspace().l() ? paramWorkspaceWindow.getWorkspace().m().getDbId() : "MySql");
  }
  
  public FxDriversDialog(WorkspaceWindow paramWorkspaceWindow, String paramString) {
    super(paramWorkspaceWindow);
    setGraphic(BootstrapIcons.plug_fill);
    a(paramString);
    this.rx.a("flagSelectedJarEntry", () -> (this.a.getSelectionModel().getSelectedItem() != null));
    this.rx.a("flagCanDownload", () -> (a() != null && (Dbms.get(a())).hasJDBCDriverOnWeb.b()));
    TreeTableColumn treeTableColumn1 = new TreeTableColumn("JDBC Driver & Class");
    treeTableColumn1.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue().getValue()));
    treeTableColumn1.setCellFactory(paramTreeTableColumn -> new FxDriversDialog$1(this));
    this.b.setGraphic((Node)this.rx.e("patternTitleLabel"));
    this.b.setCellValueFactory(paramCellDataFeatures -> {
          b b = (b)paramCellDataFeatures.getValue().getValue();
          return (ObservableValue)new ReadOnlyStringWrapper((b.b != null) ? b.b.c() : null);
        });
    this.b.setEditable(true);
    this.b.setCellFactory(paramTreeTableColumn -> new FxEditableStringTreeTableCell());
    this.b.setOnEditCommit(paramCellEditEvent -> {
          if (paramCellEditEvent.getRowValue() != null) {
            b b = (b)paramCellEditEvent.getRowValue().getValue();
            b.a((String)paramCellEditEvent.getNewValue());
            this.d = true;
          } 
        });
    TreeTableColumn treeTableColumn2 = new TreeTableColumn("Alias");
    treeTableColumn2.setStyle("-fx-alignment: center;");
    treeTableColumn2.setCellValueFactory(paramCellDataFeatures -> {
          b b = (b)paramCellDataFeatures.getValue().getValue();
          return (ObservableValue)new ReadOnlyStringWrapper((b.b != null) ? b.b.h() : null);
        });
    TreeTableColumn treeTableColumn3 = new TreeTableColumn("JDBC URL");
    treeTableColumn3.getColumns().addAll(new Object[] { treeTableColumn2, this.b });
    setRegionPrefSize((Region)this.a, 1050.0D, 500.0D);
    this.b.setPrefWidth(600.0D);
    this.a.setEditable(true);
    this.a.getColumns().addAll((Object[])new TreeTableColumn[] { treeTableColumn1, treeTableColumn3 });
    Rx.a(this.a, new TreeTableColumn[] { treeTableColumn1, treeTableColumn2, this.b }, new double[] { 0.3D, 0.2D, 0.5D });
    this.a.setShowRoot(false);
    this.a.selectionModelProperty().addListener((paramObservableValue, paramTreeTableViewSelectionModel1, paramTreeTableViewSelectionModel2) -> this.rx.b());
    this.c.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramString1, paramString2) -> refreshDrivers());
    this.a.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramTreeItem1, paramTreeItem2) -> this.rx.b());
    this.rx.b();
    setResizable(true);
    showDialog(paramWorkspaceWindow);
  }
  
  public Node createContentPane() {
    refreshDrivers();
    VBox.setVgrow((Node)this.a, Priority.ALWAYS);
    VBox$ vBox$ = (new VBox$()).l().i();
    vBox$.getChildren().add((new FlowPane$()).a().a(new Node[] { (Node)this.rx.e("dbms"), (Node)this.c, (Node)this.rx.j("addDbms") }));
    vBox$.getChildren().add(this.a);
    vBox$.getChildren().add((new FlowPane$()).a(10.0D).a(new Node[] { (Node)this.rx
            .j("addDriver"), (Node)this.rx
            .j("downloadDriverFromWeb"), (Node)this.rx
            .j("removeDriver"), (Node)this.rx
            .j("editDriver"), (Node)this.rx
            .j("restoreDefaults"), (Node)this.rx
            .j("helpJdbcWebsite") }));
    return (Node)vBox$;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
    createHelpButton("connect.html");
  }
  
  private void a(String paramString) {
    this.c.getItems().clear();
    Dbms.reloadImporters();
    this.c.getItems().addAll(Dbms.getKnownDbmsExclude(new String[0]));
    if (paramString != null)
      this.c.setValue(paramString); 
  }
  
  @Action
  public void refreshDrivers() {
    if (a() != null) {
      FxDriversDialog$DriverTreeItem fxDriversDialog$DriverTreeItem = new FxDriversDialog$DriverTreeItem(a());
      this.a.setRoot(fxDriversDialog$DriverTreeItem);
      fxDriversDialog$DriverTreeItem.setExpanded(true);
      setHeaderText((Dbms.get(a())).jdbcTipFx.c_());
    } 
    this.rx.b();
  }
  
  @Action(b = "flagSelectedJarEntry")
  public void helpJdbcWebsite() {
    b b = (b)((TreeItem)this.a.getSelectionModel().getSelectedItem()).getValue();
    if (b.b != null)
      WebBrowserExternal.a(getDialogScene(), b.b.i()); 
  }
  
  @Action(b = "flagSelectedJarEntry")
  public void editDriver() {
    this.a.edit(this.a.getSelectionModel().getSelectedIndex(), this.b);
  }
  
  @Action(b = "flagSelectedJarEntry")
  public void removeDriver() {
    String str = this.rx.H("removeDriverMessage");
    if (str != null)
      str = str.replace("USER_DRIVERS_FOLDER", Sys.e.toString()).replace("INSTALL_DRIVERS_FOLDER", Sys.v.toString()); 
    showInformation(str, new String[0]);
  }
  
  @Action
  public void restoreDefaults() {
    try {
      FileUtils.b(DriverManager.a);
      DriverManager.d();
      refreshDrivers();
      showInformation("Done", new String[0]);
    } catch (Exception exception) {
      this.rx.b(getDialogPane().getScene(), "Could not remove file <home_dir>/.DbSchema/dbms/drivers.xml.\nPlease stop application and remove the file.\n" + String.valueOf(exception), exception);
    } 
    this.rx.b();
  }
  
  @Action(b = "flagCanDownload")
  public Task downloadDriverFromWeb() {
    return new a(this);
  }
  
  private String a() {
    return (String)this.c.getSelectionModel().getSelectedItem();
  }
  
  @Action
  public Task addDriver() {
    return new FxDriversDialog$2(this, this, a());
  }
  
  @Action
  public Task addDbms() {
    String str = showInputString("newDbmsName");
    if (StringUtil.isFilledTrim(str)) {
      str = str.replaceAll(" ", "_");
      String str1 = str;
      if (this.rx.a(getDialogPane().getScene(), "Next step is to upload the " + str1 + " JDBC .jar driver file.\nThis is required to connect to the database.\nLook for the JDBC driver in " + str1 + " installation kit, website or in internet.", "Upload"))
        return new FxDriversDialog$3(this, this, str1, str1); 
    } 
    return null;
  }
  
  public boolean apply() {
    if (this.d)
      try {
        DriverManager.b();
      } catch (IOException iOException) {
        this.rx.b(getDialogScene(), "saveError", iOException);
      }  
    return true;
  }
}
