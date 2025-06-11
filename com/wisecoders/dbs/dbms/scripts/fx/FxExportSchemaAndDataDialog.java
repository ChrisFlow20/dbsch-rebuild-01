package com.wisecoders.dbs.dbms.scripts.fx;

import com.wisecoders.dbs.config.fx.FxSettingsDialog;
import com.wisecoders.dbs.config.fx.FxSettingsDialog$SelectTab;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.fx.FxSyncSettingsDialog;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.TreeSelection;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.dialogs.system.FxSchemaSelectionDialog;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.project.history.History;
import com.wisecoders.dbs.project.history.HistoryFile;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.ToggleGroup$;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.FxUtil$FileCell;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.io.File;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.ToggleButton;

public class FxExportSchemaAndDataDialog extends ButtonDialog$ {
  private static final String a = "ExportFolder";
  
  private static final String b = "File:";
  
  private final Project c;
  
  private final Workspace d;
  
  private final CheckBox e;
  
  private final CheckBox f;
  
  private final CheckBox i;
  
  private final CheckBox j;
  
  private final CheckBox k;
  
  private final RadioButton l;
  
  private final RadioButton m = new RadioButton("File:");
  
  private final Button n;
  
  private final boolean o;
  
  private final ComboBox p = new ComboBox();
  
  private final History q = new History("ExportSchemaAndData");
  
  private final ComboBox r = new ComboBox();
  
  public FxExportSchemaAndDataDialog(Workspace paramWorkspace, Project paramProject) {
    super(paramWorkspace);
    this.d = paramWorkspace;
    this.c = paramProject;
    this.rx.G(paramProject.getDbId());
    this.rx.a("flagHasFile", () -> StringUtil.isFilledTrim(this.p.getValue()));
    this.e = this.rx.h("checkSchemaCreate", true);
    this.f = this.rx.h("checkData", false);
    this.i = this.rx.h("checkExcludeAutoIncrementColumns", false);
    this.i.setDisable(true);
    this.f.selectedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> this.i.setDisable(!paramBoolean2.booleanValue()));
    this.l = this.rx.i("radioSQLPane", true);
    this.k = this.rx.v("includeSchemaName");
    this.k.setSelected(this.o = (Dbms.get(paramProject.getDbId())).prefixTableWithSchema.b());
    this.j = this.rx.h("includeForeignKeys", (Dbms.get(paramProject.getDbId())).foreignKeysInline.b());
    this.n = this.rx.j("chooseFile");
    this.p.setEditable(true);
    this.p.setOnAction(paramActionEvent -> this.rx.b());
    this.p.setMaxWidth(Double.MAX_VALUE);
    this.p.setCellFactory(paramListView -> new FxUtil$FileCell());
    for (HistoryFile historyFile : this.q.c())
      this.p.getItems().add(historyFile.b.getAbsolutePath()); 
    this.r.getItems().addAll((Object[])a.values());
    this.r.setValue(a.a);
    this.r.setCellFactory(paramListView -> new b(this));
    this.r.setButtonCell(new b(this));
    this.rx.b();
    showDialog();
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).a(12.0D, 15.0D).g().a(new int[] { -2, -1 });
    gridPane$.a((Node)(new FlowPane$()).a().a(new Node[] { (Node)this.rx.e("selectionLabel"), (Node)this.r }, ), "0,0,1,0,l,c");
    gridPane$.a((Node)this.rx.h("targetSeparator"), "0,1,1,1,f,c");
    gridPane$.a((Node)this.e, "0,2,l,c");
    gridPane$.a((Node)this.f, "0,3,l,c");
    gridPane$.a((Node)this.i, "0,4,l,c");
    gridPane$.a((Node)this.rx.h("optionsSeparator"), "0,5,1,5,f,c");
    gridPane$.a((Node)this.k, "0,6,3,6,l,c");
    gridPane$.a((Node)this.j, "0,7,1,7,l,c");
    SplitMenuButton splitMenuButton = this.rx.g("syncSettings", true);
    splitMenuButton.getItems().addAll(this.rx.e(new String[] { "syncSettings", "escapeSettings", "dateFormatSettings" }));
    gridPane$.a((Node)splitMenuButton, "0,8,l,c");
    new ToggleGroup$(new ToggleButton[] { (ToggleButton)this.l, (ToggleButton)this.m });
    this.l.setSelected(true);
    gridPane$.a((Node)this.rx.h("destinationSeparator"), "0,9,1,9,f,c");
    gridPane$.a((Node)this.l, "0,10,1,10,l,c");
    gridPane$.a((Node)(new FlowPane$()).a().a(new Node[] { (Node)this.m, (Node)this.p, (Node)this.n }, ), "0,11,1,11,f,c");
    setInitFocusedNode((Node)this.e);
    this.n.setDisable(true);
    this.m.setOnAction(paramActionEvent -> this.n.setDisable(!this.m.isSelected()));
    if (this.c.is(UnitProperty.f).booleanValue()) {
      this.k.setDisable(true);
      this.j.setDisable(true);
      this.f.setDisable(true);
    } 
    return (Node)gridPane$;
  }
  
  public void createButtons() {
    createActionButton("ok");
    createCancelButton();
    createHelpButton("schema-synchronization.html");
  }
  
  @Action
  public void chooseFile() {
    String str = Pref.c("ExportFolder", (this.d.m().getFile() != null) ? this.d.m().getFile().getParent() : null);
    File file = FxFileChooser.a(getDialogScene(), "Choose file", str, FileOperation.b, new FileType[] { FileType.r });
    if (file != null) {
      Pref.a("ExportFolder", file.getParent());
      this.p.setValue(file.getAbsolutePath());
    } 
  }
  
  @Action
  public Task ok() {
    TreeSelection treeSelection;
    if (this.e.isSelected() && !this.c.hasEntities())
      showError("schemasAreEmpty"); 
    if (this.f.isSelected() && !this.c.hasOperativeConnector()) {
      showError("missingConnectionError");
      return null;
    } 
    if (this.m.isSelected() && a() == null) {
      showError("missingFileError");
      return null;
    } 
    if (!this.e.isSelected() && !this.f.isSelected()) {
      showError("noSelectedCheckboxError");
      return null;
    } 
    Dbms dbms = Dbms.get(this.c.getDbId());
    dbms.prefixTableWithSchema.a(this.k.isSelected());
    FxSchemaSelectionDialog fxSchemaSelectionDialog = new FxSchemaSelectionDialog(this.d, this.rx.H("selectSchemasAndTablesTitle"), this.c, null);
    if (this.r.getValue() == a.a || this.d.p() == null) {
      List list = this.d.E();
      if (!list.isEmpty())
        for (Entity entity : list)
          fxSchemaSelectionDialog.e(entity);  
      fxSchemaSelectionDialog.showDialog();
      if (fxSchemaSelectionDialog.b()) {
        dbms.prefixTableWithSchema.a(this.o);
        return null;
      } 
      treeSelection = fxSchemaSelectionDialog.c;
    } else {
      treeSelection = new TreeSelection();
      for (Depict depict : (this.d.p()).diagram.depicts)
        treeSelection.select(depict.entity); 
    } 
    close();
    this.q.a(null, a());
    this.d.getRx().a(new FxExportSchemaAndDataDialog$1(this, this.d, this.c, treeSelection, this.e
          .isSelected(), this.f.isSelected(), this.i.isSelected(), this.j.isSelected(), 
          this.m.isSelected() ? a() : null, dbms));
    return null;
  }
  
  private File a() {
    String str = (String)this.p.getValue();
    if (StringUtil.isFilledTrim(str))
      return new File(str); 
    return null;
  }
  
  @Action
  public void syncSettings() {
    (new FxSyncSettingsDialog(this.d)).showDialog();
  }
  
  @Action
  public void escapeSettings() {
    (new FxSettingsDialog(this.d, FxSettingsDialog$SelectTab.a)).showDialog();
  }
  
  @Action
  public void dateFormatSettings() {
    (new FxSettingsDialog(this.d, FxSettingsDialog$SelectTab.b)).showDialog();
  }
  
  public boolean apply() {
    return true;
  }
}
