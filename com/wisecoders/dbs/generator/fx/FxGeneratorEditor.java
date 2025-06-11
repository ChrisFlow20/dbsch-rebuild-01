package com.wisecoders.dbs.generator.fx;

import com.wisecoders.dbs.browse.fx.FxBrowsePreviewPopup;
import com.wisecoders.dbs.config.fx.FxSettingsDialog;
import com.wisecoders.dbs.config.fx.FxSettingsDialog$SelectTab;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.dialogs.layout.FxEditorFactory;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.generator.engine.plan.CyclicFkWarn;
import com.wisecoders.dbs.generator.engine.plan.HasData;
import com.wisecoders.dbs.generator.engine.plan.Warn;
import com.wisecoders.dbs.project.fx.FxFrame;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.GeneratorPlan;
import com.wisecoders.dbs.schema.GeneratorTable;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.Alert$;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.FxEditableStringTableCell;
import com.wisecoders.dbs.sys.fx.FxEditableStringTableCell$Type;
import com.wisecoders.dbs.sys.fx.VBox$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.tips.fx.FxTipsDialog;
import com.wisecoders.dbs.tips.tips.DataGeneratorTips;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class FxGeneratorEditor extends ButtonDialog$ implements WorkspaceWindow {
  private final Workspace a;
  
  private final TableView b = new TableView();
  
  private final TableView c = new TableView();
  
  private final GeneratorPlan d = new GeneratorPlan();
  
  private final TableColumn e;
  
  private final TableColumn f;
  
  private final TableColumn i;
  
  private final TableColumn j;
  
  private final TableColumn k;
  
  private final TableColumn l;
  
  private final TableColumn m;
  
  public FxGeneratorEditor(Workspace paramWorkspace, List paramList) {
    super(paramWorkspace);
    this.e = new TableColumn("Tables");
    this.f = new TableColumn("Generate Row Count");
    this.i = new TableColumn("Status");
    this.j = new TableColumn("Generator Message");
    this.k = new TableColumn("Column");
    this.l = new TableColumn("Occurrences");
    this.m = new TableColumn("Message");
    this.a = paramWorkspace;
    setGraphic(BootstrapIcons.dice_5_fill);
    this.rx.G(paramWorkspace.m().getDbId());
    this.rx.a("flagCanAdd", () -> {
          boolean bool = false;
          for (Schema schema : (paramWorkspace.m()).schemas) {
            for (Table table : schema.tables) {
              if (!this.d.containsTable(table))
                bool = true; 
            } 
          } 
          return bool;
        });
    this.rx.a("flagSelectedTable", () -> (this.b.getSelectionModel().getSelectedItem() != null));
    this.rx.a("flagSelectedWarn", () -> (this.c.getSelectionModel().getSelectedItem() != null));
    Objects.requireNonNull(this.d);
    this.rx.a("flagHasTables", this.d::hasGenerators);
    Objects.requireNonNull(this.d);
    this.rx.a("flagCanStart", this.d::hasGenerators);
    this.rx.a("flagCanUp", () -> (this.b.getSelectionModel().getSelectedIndex() > 0));
    this.rx.a("flagCanDown", () -> (this.b.getSelectionModel().getSelectedIndex() < this.b.getItems().size() - 1));
    for (Entity entity : paramList) {
      if (entity instanceof Table)
        this.d.addTable((Table)entity); 
    } 
    if (this.d.getTableGenerators().isEmpty() && paramWorkspace.p() != null)
      for (Depict depict : (paramWorkspace.p()).diagram.depicts) {
        Entity entity = depict.getEntity();
        if (entity instanceof Table)
          this.d.addTable((Table)entity); 
      }  
    a();
    b();
    this.rx.b();
    this.rx.a(checkDependencies());
    if (paramWorkspace.t()) {
      Task task = checkDataPresence();
      if (task != null)
        Platform.runLater(() -> this.rx.a(paramTask)); 
    } 
    showDialog();
  }
  
  private void a() {
    this.e.setText(this.rx.H("nameColumn"));
    this.e.setGraphic(Rx.a());
    this.e.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(((GeneratorTable)paramCellDataFeatures.getValue()).table.getNameWithSchemaName()));
    this.e.setCellFactory(paramTableColumn -> new b(this));
    this.f.setGraphic(Rx.a());
    this.f.setCellValueFactory(paramCellDataFeatures -> new SimpleStringProperty("" + ((GeneratorTable)paramCellDataFeatures.getValue()).table.getGeneratorRowsCount()));
    this.f.setCellFactory(paramTableColumn -> new FxEditableStringTableCell(FxEditableStringTableCell$Type.c));
    this.f.setOnEditCommit(paramCellEditEvent -> {
          try {
            ((GeneratorTable)paramCellEditEvent.getRowValue()).table.setGeneratorRowsCount(Integer.parseInt((String)paramCellEditEvent.getNewValue()));
          } catch (NumberFormatException numberFormatException) {}
          this.a.u();
        });
    this.f.setStyle("-fx-alignment: center-right;");
    this.i.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(((GeneratorTable)paramCellDataFeatures.getValue()).hasData()));
    this.i.setCellFactory(paramTableColumn -> new FxGeneratorEditor$1(this));
    this.j.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue()));
    this.j.setCellFactory(paramTableColumn -> new a());
    this.b.setEditable(true);
    this.b.getColumns().addAll((Object[])new TableColumn[] { this.e, this.i, this.f, this.j });
    this.b.setItems(this.d.getTableGenerators());
    this.b.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramGeneratorTable1, paramGeneratorTable2) -> {
          GeneratorTable generatorTable = (GeneratorTable)this.b.getSelectionModel().getSelectedItem();
          this.c.setItems((generatorTable != null) ? generatorTable.warns : null);
          this.rx.b();
        });
    this.b.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    Rx.a(this.b, new double[] { 0.3D, 0.15D, 0.15D, 0.4D });
  }
  
  private void b() {
    this.k.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(((Warn)paramCellDataFeatures.getValue()).d));
    this.l.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(Integer.valueOf(((Warn)paramCellDataFeatures.getValue()).b())));
    this.m.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue()));
    this.m.setCellFactory(paramTableColumn -> new c());
    this.c.getColumns().addAll((Object[])new TableColumn[] { this.k, this.l, this.m });
    this.c.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    this.c.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    setRegionPrefSize((Region)this.c, 900.0D, 180.0D);
    Rx.a(this.c, new double[] { 0.3D, 0.2D, 0.5D });
  }
  
  private void c() {
    this.d.checkPatterns();
    this.b.refresh();
  }
  
  public Node createContentPane() {
    VBox.setVgrow((Node)this.b, Priority.ALWAYS);
    VBox$ vBox$ = (new VBox$()).l().c(new Node[] { (Node)this.rx
          .h("includedTables"), (Node)this.b, (Node)(new FlowPane$())
          
          .a().a(this.rx.c(new String[] { "edit", "removeTables", "upItem", "downItem", "checkDataPresence", "showSampleData", "reorder", "repository" })), (Node)this.rx
          .h("warnTitle"), (Node)this.c });
    setRegionPrefSize((Region)vBox$, 900.0D, 600.0D);
    return (Node)vBox$;
  }
  
  public void createButtons() {
    createActionButton("generate");
    createActionButton("generateToFile");
    createCloseButton();
    createHelpButton("random-data-generator.html");
  }
  
  @Action(b = "flagSelectedTable")
  public Task removeTables() {
    this.d.getTableGenerators().removeAll((Collection)this.b.getSelectionModel().getSelectedItems());
    this.b.getSelectionModel().clearSelection();
    return checkDependencies();
  }
  
  @Action
  public void settings() {
    (new FxSettingsDialog(this.a, FxSettingsDialog$SelectTab.a)).showDialog();
  }
  
  @Action(b = "flagSelectedTable")
  public void edit() {
    new FxGeneratorTableDialog(this, ((GeneratorTable)this.b.getSelectionModel().getSelectedItem()).table, false);
    c();
    this.rx.b();
  }
  
  @Action(b = "flagSelectedTable")
  public void editTable() {
    FxEditorFactory.a(this, ((GeneratorTable)this.b.getSelectionModel().getSelectedItem()).table);
  }
  
  @Action(b = "flagSelectedWarn")
  public void warnDetails() {
    Warn warn = (Warn)this.c.getSelectionModel().getSelectedItem();
    if (warn != null && 
      !(warn instanceof com.wisecoders.dbs.generator.engine.plan.MissingTableWarn))
      if (warn instanceof CyclicFkWarn) {
        CyclicFkWarn cyclicFkWarn = (CyclicFkWarn)warn;
        ((FxFrame)this.a).a(cyclicFkWarn.a);
      } else {
        new FxGeneratorTableDialog(this, warn.c, false);
      }  
    c();
  }
  
  @Action
  public Task checkDependencies() {
    return new FxGeneratorEditor$2(this);
  }
  
  @Action(b = "flagHasTables")
  public Task checkDataPresence() {
    Connector connector = this.a.a(this);
    if (connector != null)
      return new FxGeneratorEditor$3(this, this, connector, this.d); 
    return null;
  }
  
  @Action(b = "flagCanStart")
  public Task generate() {
    Connector connector = this.a.a(this);
    if (connector != null) {
      if (this.d.hasData() == HasData.a) {
        showInformation("Please press 'Refresh Status' first.", new String[0]);
        return null;
      } 
      boolean bool = false;
      if (this.d.hasData() == HasData.b) {
        Alert$ alert$ = this.rx.b(getDialogPane().getScene(), "confirmDropData", Alert.AlertType.INFORMATION, new String[0]);
        ButtonType buttonType1 = new ButtonType("Leave Current Data", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonType2 = new ButtonType("Drop Current Data", ButtonBar.ButtonData.OK_DONE);
        alert$.getButtonTypes().setAll((Object[])new ButtonType[] { buttonType1, buttonType2, ButtonType.CANCEL });
        Optional<ButtonType> optional = alert$.showAndWait();
        if (!optional.isPresent() || optional.get() == ButtonType.CANCEL)
          return null; 
        bool = (optional.get() == buttonType2) ? true : false;
      } 
      this.d.clearWarns();
      this.rx.b();
      return new FxGeneratorEditor$4(this, this, connector, this.d, bool);
    } 
    return null;
  }
  
  @Action(b = "flagCanStart")
  public Task generateToFile() {
    switch (FxGeneratorEditor$7.b[this.rx.b(getDialogScene(), "chooseFileType", new String[0]).c().ordinal()]) {
      case 1:
        return a(this.a.m().is(UnitProperty.f).booleanValue() ? FileType.p : FileType.r);
      case 2:
        return a(FileType.g);
    } 
    return null;
  }
  
  public Task a(FileType paramFileType) {
    File file = FxFileChooser.a(getDialogScene(), "Choose Output File", FileOperation.b, new FileType[] { paramFileType });
    if (file != null) {
      this.d.clearWarns();
      this.rx.b();
      return new FxGeneratorEditor$5(this, this, this.a.s(), this.d, file, (paramFileType == FileType.g));
    } 
    return null;
  }
  
  @Action
  public void tipsTricks() {
    new FxTipsDialog(this.a, new DataGeneratorTips());
  }
  
  @Action(b = "flagSelectedTable")
  public void showActualData() {
    Table table = ((GeneratorTable)this.b.getSelectionModel().getSelectedItem()).table;
    if (table != null)
      (new FxBrowsePreviewPopup(this.a, table)).a(getDialogScene(), 0.0D, 0.0D); 
  }
  
  @Action
  public void repository() {
    (new FxGeneratorRepositoryDialog(this)).showDialog();
  }
  
  @Action
  public Task reorder() {
    return new FxGeneratorEditor$6(this);
  }
  
  @Action(b = "flagCanUp")
  public void upItem() {
    int i = this.b.getSelectionModel().getSelectedIndex();
    if (this.d.moveUp(i))
      this.b.getSelectionModel().clearAndSelect(i - 1); 
    this.d.saveGeneratorOrder(false);
  }
  
  @Action(b = "flagCanDown")
  public void downItem() {
    int i = this.b.getSelectionModel().getSelectedIndex();
    if (this.d.moveDown(i))
      this.b.getSelectionModel().clearAndSelect(i + 1); 
    this.d.saveGeneratorOrder(false);
  }
  
  @Action(b = "flagSelectedTable")
  public void showSampleData() {
    new FxGeneratorSamplesDialog(this.a, ((GeneratorTable)this.b.getSelectionModel().getSelectedItem()).table);
  }
  
  public boolean apply() {
    return true;
  }
  
  public Workspace getWorkspace() {
    return this.a;
  }
}
