package com.wisecoders.dbs.config.fx;

import com.wisecoders.dbs.config.model.RootProperty;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.VBox$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class FxConfigurationPanel extends VBox$ {
  final TreeTableView a = new TreeTableView();
  
  private final Rx c = new Rx(FxConfigurationPanel.class, this);
  
  private RootProperty d;
  
  private boolean e = false;
  
  private final boolean f;
  
  private final TreeTableColumn g;
  
  private final TreeTableColumn h;
  
  private String i;
  
  public static final String b = "edited-cell";
  
  public FxConfigurationPanel() {
    this(true, true);
  }
  
  @Action(b = "flagCanEdit")
  public void edit() {
    this.a.edit(this.a.getSelectionModel().getSelectedIndex(), this.h);
  }
  
  public void a(RootProperty paramRootProperty) {
    this.d = paramRootProperty;
    this.a.setRoot(new FxConfigurationPanel$PropertyTreeItem(this, paramRootProperty));
  }
  
  public FxConfigurationPanel(boolean paramBoolean1, boolean paramBoolean2) {
    this.g = new TreeTableColumn("Property");
    this.h = new TreeTableColumn("Value");
    this.f = paramBoolean2;
    this.c.a("flagCanEdit", () -> (this.a.getSelectionModel().getSelectedItem() != null));
    b();
    TextField textField = this.c.t("filterField");
    HBox$.setHgrow((Node)textField, Priority.ALWAYS);
    textField.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> a(paramTextField.getText()));
    HBox$.setHgrow((Node)this.a, Priority.ALWAYS);
    if (paramBoolean1) {
      getChildren().addAll((Object[])new Node[] { (Node)textField, (Node)this.a, (Node)(new FlowPane$()).a().a(this.c.c(new String[] { "edit", "importConfiguration", "exportConfiguration", "restoreDefaults" })) });
      j();
    } else {
      getChildren().addAll((Object[])new Node[] { (Node)this.a });
    } 
    this.a.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramTreeItem1, paramTreeItem2) -> this.c.b());
  }
  
  private void b() {
    this.g.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue().getValue()));
    this.g.setCellFactory(paramTreeTableColumn -> new a(this.f));
    this.g.setSortable(false);
    this.h.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue().getValue()));
    this.h.setCellFactory(paramTreeTableColumn -> new b(this));
    this.h.setGraphic((Node)BootstrapIcons.pencil.glyph(new String[] { "glyph-orange" }));
    this.h.setSortable(false);
    this.a.setEditable(true);
    this.a.setShowRoot(false);
    this.h.setEditable(true);
    this.a.getColumns().addAll((Object[])new TreeTableColumn[] { this.g, this.h });
    this.a.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
    Rx.b(this.a, new double[] { 0.7D, 0.3D });
    Dialog$.setRegionPrefSize((Region)this.a, 550.0D, 550.0D);
    VBox$.setVgrow((Node)this.a, Priority.ALWAYS);
  }
  
  public void a(String paramString) {
    this.i = (paramString != null) ? paramString.toLowerCase().trim() : null;
    if (this.a.getRoot() instanceof FxConfigurationPanel$PropertyTreeItem)
      ((FxConfigurationPanel$PropertyTreeItem)this.a.getRoot()).a(); 
  }
  
  void a(Object paramObject) {
    a(paramObject, this.a.getRoot());
  }
  
  private boolean a(Object paramObject, TreeItem paramTreeItem) {
    if (paramTreeItem.getValue() == paramObject) {
      Platform.runLater(() -> {
            this.a.requestFocus();
            this.a.getSelectionModel().select(paramTreeItem);
            this.a.scrollTo(this.a.getSelectionModel().getSelectedIndex());
          });
      paramTreeItem.setExpanded(true);
      return true;
    } 
    for (TreeItem treeItem : paramTreeItem.getChildren()) {
      if (a(paramObject, treeItem)) {
        paramTreeItem.setExpanded(true);
        return true;
      } 
    } 
    return false;
  }
  
  @Action
  public void restoreDefaults() {
    if (this.c.a(getScene(), "The current settings will be lost.\nContinue and restore default values ?"))
      try {
        this.d.q();
        this.a.refresh();
      } catch (Exception exception) {
        Log.b(exception);
        this.c.b(getScene(), exception.toString(), exception);
      }  
  }
  
  @Action
  public void importConfiguration() {
    File file = FxFileChooser.a(getScene(), "Load configuration file", FileOperation.a, new FileType[] { FileType.e });
    if (file != null)
      try {
        FileReader fileReader = new FileReader(file, StandardCharsets.UTF_8);
        try {
          this.d.a(fileReader);
          this.d.j();
          fileReader.close();
        } catch (Throwable throwable) {
          try {
            fileReader.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          } 
          throw throwable;
        } 
      } catch (Exception exception) {
        Log.a("Error in configuration by loading configuration from file", exception);
        this.c.b(getScene(), exception.getLocalizedMessage(), exception);
      }  
  }
  
  @Action
  public void exportConfiguration() {
    File file = FxFileChooser.a(getScene(), "Save configuration file", Sys.k
        .toString(), FileOperation.b, new FileType[] { FileType.e });
    if (file != null)
      try {
        this.d.a(file, true);
      } catch (Exception exception) {
        Log.a("Error in configuration by saving configuration to file", exception);
        this.c.b(getScene(), exception.getLocalizedMessage(), exception);
      }  
  }
  
  public void a() {
    this.e = true;
  }
}
