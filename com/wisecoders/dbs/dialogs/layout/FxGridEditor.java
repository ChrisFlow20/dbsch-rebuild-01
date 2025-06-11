package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.FxDnd;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.RelationCardinality;
import com.wisecoders.dbs.schema.RelationType;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.ToggleGroup$;
import com.wisecoders.dbs.sys.UniqueArrayList;
import com.wisecoders.dbs.sys.fx.ChoiceDialogWithFilterableCombo;
import com.wisecoders.dbs.sys.fx.ContextMenu$;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.RowPane$;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTablePosition;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.ComboBoxTreeTableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Priority;
import org.controlsfx.control.SegmentedButton;

public class FxGridEditor extends Dialog$ implements WorkspaceWindow {
  private final TreeTableView a = new TreeTableView();
  
  private final TreeTableColumn b = new TreeTableColumn("Name");
  
  private final TreeTableColumn c = new TreeTableColumn("Schema");
  
  private final TreeTableColumn d = new TreeTableColumn();
  
  private final TreeTableColumn e = new TreeTableColumn();
  
  private final TreeTableColumn f = new TreeTableColumn();
  
  private final TreeTableColumn i = new TreeTableColumn("DataType");
  
  private final TreeTableColumn j = new TreeTableColumn("Precision");
  
  private final TreeTableColumn k = new TreeTableColumn("Decimal");
  
  private final TreeTableColumn l = new TreeTableColumn("Default Value");
  
  private final TreeTableColumn m = new TreeTableColumn("Definition");
  
  private final TreeTableColumn n = new TreeTableColumn("Definition");
  
  private final TreeTableColumn o = new TreeTableColumn("Parent");
  
  private final TreeTableColumn p = new TreeTableColumn("Type");
  
  private final TreeTableColumn q = new TreeTableColumn("Cardinality");
  
  private final TreeTableColumn r = new TreeTableColumn("Comment");
  
  private final TreeTableColumn s = new TreeTableColumn("Table");
  
  private final TreeTableColumn t = new TreeTableColumn("Virtual");
  
  private final TreeTableColumn u = new TreeTableColumn();
  
  private final ToggleButton v;
  
  private final ToggleButton w;
  
  private final ToggleButton x;
  
  private final ToggleButton y;
  
  private final ToggleButton z;
  
  private final ToggleButton A;
  
  private final i B = new i(this, null);
  
  private final List C = new UniqueArrayList();
  
  private final WorkspaceWindow D;
  
  private final boolean E;
  
  private String F;
  
  private boolean G = false;
  
  private static final String H = "GridColWidth";
  
  public FxGridEditor(WorkspaceWindow paramWorkspaceWindow, List paramList) {
    super(paramWorkspaceWindow);
    this.D = paramWorkspaceWindow;
    String str = paramWorkspaceWindow.getWorkspace().m().getDbId();
    Dbms dbms = Dbms.get(str);
    this.rx.G(str);
    this.E = "LogicalDesign".equals(str);
    this.v = this.rx.b("showTree", true);
    this.w = this.rx.b("showTable", false);
    this.x = this.rx.b("tables", false);
    this.y = this.rx.b("columns", true);
    this.z = this.rx.b("relations", false);
    this.A = this.rx.b("indexes", false);
    this.e.setGraphic((Node)this.rx.e("pkHeaderLabel"));
    this.f.setGraphic((Node)this.rx.e("visibleHeaderLabel"));
    this.d.setGraphic((Node)this.rx.e("mandatoryHeaderLabel"));
    this.u.setGraphic((Node)this.rx.e("physicalNameHeaderLabel"));
    this.c.setText(dbms.schemaAlias.c_());
    this.s.setText(dbms.tableAlias.c_());
    this.x.setText(StringUtil.getPluralOf(dbms.tableAlias.c_()));
    this.y.setText(StringUtil.getPluralOf(dbms.columnAlias.c_()));
    this.z.setText(StringUtil.getPluralOf(dbms.fkAlias.c_()));
    this.rx.a("flagSelectedItem", () -> (this.a.getSelectionModel().getSelectedItem() != null));
    this.rx.a("flagIsUseRegExp", () -> this.G);
    new ToggleGroup$(new ToggleButton[] { this.v, this.w });
    new ToggleGroup$(new ToggleButton[] { this.x, this.y, this.z, this.A });
    c();
    this.a.getColumns().addAll((Object[])new TreeTableColumn[] { this.b });
    if (paramList.isEmpty()) {
      if (paramWorkspaceWindow.getWorkspace().o() != null)
        for (Depict depict : (paramWorkspaceWindow.getWorkspace().o()).c.a.depicts) {
          if (depict.getEntity() instanceof Table)
            this.C.add((Table)depict.getEntity()); 
        }  
    } else {
      for (Unit unit : paramList) {
        if (unit.getEntity() instanceof Table)
          this.C.add((Table)unit.getEntity()); 
      } 
      columns();
    } 
    this.B.d();
    this.a.setShowRoot(false);
    this.B.c();
    this.B.setExpanded(false);
    this.a.setRoot(this.B);
    this.a.setEditable(true);
    this.a.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    this.a.getSelectionModel().setCellSelectionEnabled(true);
    this.a.setColumnResizePolicy(TreeTableView.UNCONSTRAINED_RESIZE_POLICY);
    this.a.setContextMenu((new ContextMenu$()).a(this.rx, new String[] { 
            "editCell", "separator", "addColumn", "cloneUnit", "separator", "includeTable", "separator", "clipboardCopy", "clipboardPaste", "deleteUnit", 
            "excludeTable" }));
    this.a.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramTreeItem1, paramTreeItem2) -> this.rx.b());
  }
  
  public Node createContentPane() {
    RowPane$ rowPane$ = (new RowPane$()).a();
    RowPane$.setVgrow((Node)this.a, Priority.ALWAYS);
    rowPane$.a(new Node[] { (Node)new SegmentedButton(new ToggleButton[] { this.v, this.w }), (Node)new SegmentedButton(new ToggleButton[] { this.x, this.y, this.z, this.A }) });
    MenuButton menuButton = this.rx.e("settings", false);
    menuButton.getStyleClass().add("transparent-split-menu-button");
    menuButton.getItems().addAll(this.rx.d(new String[] { "searchUsingRegExp" }));
    TextField textField = new TextField();
    textField.setMinWidth(10.0D);
    textField.setPrefWidth(10.0D);
    HBox$.setHgrow((Node)textField, Priority.ALWAYS);
    textField.getStyleClass().add("search-field");
    textField.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> a(paramTextField.getText()));
    rowPane$.a(new Node[] { (Node)textField, (Node)menuButton });
    rowPane$.a((Node)this.a);
    Dbms dbms = Dbms.get(this.D.getWorkspace().m().getDbId());
    SplitMenuButton splitMenuButton = this.rx.g("includeTable", true);
    splitMenuButton.setText("Include " + dbms.tableAlias.c_());
    splitMenuButton.getItems().addAll(this.rx.e(new String[] { "includeTable", "includeLayoutTables" }));
    Button button = this.rx.j("addColumn");
    button.setText("Add " + dbms.columnAlias.c_());
    rowPane$.a(new Node[] { (Node)splitMenuButton, (Node)button, (Node)this.rx.j("editCell"), (Node)this.rx.j("editUnit") });
    rowPane$.setPrefSize(950.0D, 650.0D);
    return (Node)rowPane$;
  }
  
  public void createButtons() {
    createCloseButton();
    createHelpButton("grid-editor.html");
  }
  
  public boolean apply() {
    this.D.getWorkspace().x();
    return true;
  }
  
  @Action(d = "flagIsUseRegExp")
  public void searchUsingRegExp() {
    this.G = !this.G;
  }
  
  private void a() {
    this.a.getColumns().clear();
    if (this.B.a() == Table.class) {
      if (this.B.b()) {
        this.a.getColumns().addAll((Object[])new TreeTableColumn[] { this.b });
      } else {
        this.a.getColumns().addAll((Object[])new TreeTableColumn[] { this.c, this.b });
      } 
      if (this.E)
        this.a.getColumns().addAll((Object[])new TreeTableColumn[] { this.t, this.u }); 
    } else if (this.B.a() == Column.class) {
      if (this.B.b()) {
        this.a.getColumns().add(this.b);
      } else {
        this.a.getColumns().addAll((Object[])new TreeTableColumn[] { this.s, this.b });
      } 
      this.a.getColumns().addAll((Object[])new TreeTableColumn[] { this.e, this.d, this.i, this.j, this.k, this.l });
      if (this.E)
        this.a.getColumns().addAll((Object[])new TreeTableColumn[] { this.u }); 
    } else if (this.B.a() == ForeignKey.class) {
      if (this.B.b()) {
        this.a.getColumns().add(this.b);
      } else {
        this.a.getColumns().addAll((Object[])new TreeTableColumn[] { this.s, this.b });
      } 
      this.a.getColumns().addAll((Object[])new TreeTableColumn[] { this.o, this.n, this.t });
      if (this.E)
        this.a.getColumns().addAll((Object[])new TreeTableColumn[] { this.q, this.p, this.u }); 
    } else if (this.B.a() == Index.class) {
      if (this.B.b()) {
        this.a.getColumns().add(this.b);
      } else {
        this.a.getColumns().addAll((Object[])new TreeTableColumn[] { this.s, this.b });
      } 
      this.a.getColumns().addAll((Object[])new TreeTableColumn[] { this.m, this.t });
      if (this.E)
        this.a.getColumns().addAll((Object[])new TreeTableColumn[] { this.u }); 
    } 
    this.a.getColumns().addAll((Object[])new TreeTableColumn[] { this.r });
  }
  
  @Action
  public void showTree() {
    this.B.a(true);
    a();
  }
  
  @Action
  public void showTable() {
    this.B.a(false);
    a();
  }
  
  @Action
  public void tables() {
    this.B.a(Table.class);
    a();
  }
  
  @Action
  public void columns() {
    this.B.a(Column.class);
    a();
  }
  
  @Action
  public void relations() {
    this.B.a(ForeignKey.class);
    a();
  }
  
  @Action
  public void indexes() {
    this.B.a(Index.class);
    a();
  }
  
  @Action
  public void includeTable() {
    Project project = this.D.getWorkspace().m();
    FxGridTableSelectionDialog fxGridTableSelectionDialog = new FxGridTableSelectionDialog(this.D.getWorkspace(), "Select Tables", project, null);
    if (fxGridTableSelectionDialog.showDialogIsResultOkDone()) {
      for (Schema schema : project.schemas) {
        for (Table table : schema.tables) {
          if (fxGridTableSelectionDialog.c.isSelected(table))
            this.C.add(table); 
        } 
      } 
      this.B.d();
    } 
  }
  
  @Action
  public void excludeTable() {
    for (TreeItem treeItem : this.a.getSelectionModel().getSelectedItems()) {
      AbstractUnit abstractUnit = (AbstractUnit)treeItem.getValue();
      if (abstractUnit != null)
        this.C.remove(abstractUnit.getEntity()); 
    } 
    this.B.d();
  }
  
  @Action
  public void includeLayoutTables() {
    ChoiceDialogWithFilterableCombo choiceDialogWithFilterableCombo = new ChoiceDialogWithFilterableCombo(getDialogScene(), this.D.getWorkspace().p(), (this.D.getWorkspace().m()).layouts);
    this.rx.a("chooseLayoutDialog", choiceDialogWithFilterableCombo);
    Optional optional = choiceDialogWithFilterableCombo.showAndWait();
    if (optional.isPresent()) {
      for (Depict depict : ((Layout)optional.get()).diagram.depicts) {
        if (depict.getEntity() instanceof Table)
          this.C.add((Table)depict.getEntity()); 
      } 
      this.B.d();
    } 
  }
  
  @Action(b = "flagSelectedItem")
  public void addColumn() {
    UniqueArrayList uniqueArrayList = new UniqueArrayList();
    for (TreeItem treeItem : this.a.getSelectionModel().getSelectedItems()) {
      if (treeItem.getValue() != null && ((AbstractUnit)treeItem.getValue()).getEntity() instanceof Table)
        uniqueArrayList.add((Table)((AbstractUnit)treeItem.getValue()).getEntity()); 
    } 
    if (!uniqueArrayList.isEmpty()) {
      String str = this.rx.b(getDialogScene(), "inputColumnName");
      if (StringUtil.isFilledTrim(str)) {
        DataType dataType = null;
        for (Table table : uniqueArrayList) {
          if (dataType == null)
            dataType = table.columns.isEmpty() ? DbmsTypes.get(table.getDbId()).getDataType(12) : ((Column)table.columns.get(table.columns.size() - 1)).getDataType(); 
          table.createColumn(table.columns.proposeName(str), dataType);
        } 
      } 
      this.B.c();
      this.a.getSelectionModel().clearSelection();
      for (j j : this.B.a(str, Column.class, new ArrayList()))
        this.a.getSelectionModel().select(j); 
    } 
  }
  
  @Action(b = "flagSelectedItem")
  public void clipboardCopy() {
    FxDnd.a();
    for (TreeItem treeItem : this.a.getSelectionModel().getSelectedItems())
      FxDnd.a((Unit)treeItem.getValue()); 
    ClipboardContent clipboardContent = new ClipboardContent();
    clipboardContent.put(FxDnd.a, Integer.valueOf(1));
    Clipboard.getSystemClipboard().setContent((Map)clipboardContent);
  }
  
  @Action(b = "flagSelectedItem")
  public void clipboardPaste() {
    for (Table table : b()) {
      for (Unit unit : FxDnd.b)
        a(unit, table); 
    } 
    this.B.c();
  }
  
  @Action(b = "flagSelectedItem")
  public void cloneUnit() {
    AbstractUnit abstractUnit = (AbstractUnit)((TreeItem)this.a.getSelectionModel().getSelectedItem()).getValue();
    for (Table table : b())
      a(abstractUnit, table); 
    this.B.c();
  }
  
  @Action(b = "flagSelectedItem")
  public void deleteUnit() {
    AbstractUnit abstractUnit = (AbstractUnit)((TreeItem)this.a.getSelectionModel().getSelectedItem()).getValue();
    if (abstractUnit != null && abstractUnit.getEntity() != null) {
      abstractUnit.markForDeletion();
      abstractUnit.getEntity().getSchema().refresh();
    } 
    this.B.c();
  }
  
  @Action(b = "flagSelectedItem")
  public void editCell() {
    TreeTableColumn treeTableColumn = null;
    for (TreeTablePosition treeTablePosition : this.a.getSelectionModel().getSelectedCells())
      treeTableColumn = (TreeTableColumn)this.a.getColumns().get(treeTablePosition.getColumn()); 
    if (treeTableColumn != null)
      this.a.edit(this.a.getSelectionModel().getSelectedIndex(), treeTableColumn); 
  }
  
  @Action(b = "flagSelectedItem")
  public void editUnit() {
    AbstractUnit abstractUnit = (AbstractUnit)((TreeItem)this.a.getSelectionModel().getSelectedItem()).getValue();
    if (abstractUnit != null)
      FxEditorFactory.a(this, abstractUnit); 
    this.B.c();
  }
  
  private void a(Unit paramUnit, Table paramTable) {
    if (paramUnit instanceof Table) {
      SyncUtil.a(this.D.getWorkspace(), (Table)paramUnit);
    } else if (paramUnit instanceof Column) {
      Column column = (Column)paramUnit;
      if (paramTable != null) {
        Column column1 = paramTable.createColumn(paramTable.getAttributes().proposeName(column.getName()), column.getDataType());
        column1.cloneFrom(column);
      } 
    } 
  }
  
  private List b() {
    UniqueArrayList uniqueArrayList = new UniqueArrayList();
    for (TreeItem treeItem : this.a.getSelectionModel().getSelectedItems()) {
      AbstractUnit abstractUnit = (AbstractUnit)treeItem.getValue();
      if (abstractUnit instanceof Table)
        uniqueArrayList.add((Table)abstractUnit); 
      if (abstractUnit instanceof Column && ((Column)abstractUnit).getEntity() instanceof Table)
        uniqueArrayList.add((Table)((Column)abstractUnit).getEntity()); 
    } 
    return uniqueArrayList;
  }
  
  private void c() {
    this.b.setCellValueFactory(paramCellDataFeatures -> new SimpleStringProperty((paramCellDataFeatures.getValue() != null && paramCellDataFeatures.getValue().getValue() != null) ? ((AbstractUnit)paramCellDataFeatures.getValue().getValue()).getName() : null));
    this.b.setCellFactory(paramTreeTableColumn -> new FxGridEditor$1(this));
    this.b.setEditable(true);
    this.b.setOnEditCommit(paramCellEditEvent -> {
          if (paramCellEditEvent.getRowValue() != null && paramCellEditEvent.getRowValue().getValue() != null)
            ((AbstractUnit)paramCellEditEvent.getRowValue().getValue()).rename((String)paramCellEditEvent.getNewValue()); 
          Objects.requireNonNull(this.a);
          Platform.runLater(this.a::refresh);
        });
    this.d.setStyle("-fx-alignment: center;");
    this.d.setCellValueFactory(paramCellDataFeatures -> new SimpleObjectProperty(paramCellDataFeatures.getValue().getValue()));
    this.d.setMinWidth(40.0D);
    this.d.setMaxWidth(40.0D);
    this.d.setCellFactory(paramTreeTableColumn -> new k());
    this.d.setEditable(true);
    this.d.setOnEditCommit(paramCellEditEvent -> {
          if (paramCellEditEvent.getNewValue() instanceof Column) {
            boolean bool = ((Column)paramCellEditEvent.getNewValue()).isMandatory();
            for (TreeItem treeItem : this.a.getSelectionModel().getSelectedItems()) {
              if (treeItem.getValue() instanceof Column)
                ((Column)treeItem.getValue()).setMandatory(bool); 
            } 
          } 
          Objects.requireNonNull(this.a);
          Platform.runLater(this.a::refresh);
        });
    this.e.setStyle("-fx-alignment: center;");
    this.e.setCellValueFactory(paramCellDataFeatures -> new SimpleObjectProperty(paramCellDataFeatures.getValue().getValue()));
    this.e.setCellFactory(paramTreeTableColumn -> new m());
    this.e.setEditable(true);
    this.e.setMinWidth(40.0D);
    this.e.setMaxWidth(40.0D);
    this.f.setStyle("-fx-alignment: center;");
    this.f.setCellValueFactory(paramCellDataFeatures -> new SimpleObjectProperty(paramCellDataFeatures.getValue().getValue()));
    this.f.setCellFactory(paramTreeTableColumn -> new o(this));
    this.f.setEditable(true);
    this.f.setMinWidth(40.0D);
    this.f.setMaxWidth(40.0D);
    this.i.setStyle("-fx-alignment: center-right;");
    this.i.setCellValueFactory(paramCellDataFeatures -> new SimpleObjectProperty((paramCellDataFeatures.getValue().getValue() instanceof Column) ? ((Column)paramCellDataFeatures.getValue().getValue()).getDataType() : null));
    this.i.setCellFactory(paramTreeTableColumn -> new ComboBoxTreeTableCell((DbmsTypes.get(this.D.getWorkspace().m().getDbId())).dataTypes));
    this.i.setEditable(true);
    this.i.setMinWidth(120.0D);
    this.i.setMaxWidth(120.0D);
    this.i.setOnEditCommit(paramCellEditEvent -> {
          if (paramCellEditEvent.getRowValue() != null && paramCellEditEvent.getRowValue().getValue() instanceof Column)
            ((Column)paramCellEditEvent.getRowValue().getValue()).setDataType((DataType)paramCellEditEvent.getNewValue()); 
          for (TreeItem treeItem : this.a.getSelectionModel().getSelectedItems()) {
            if (treeItem.getValue() instanceof Column)
              ((Column)treeItem.getValue()).setDataType((DataType)paramCellEditEvent.getNewValue()); 
          } 
          Objects.requireNonNull(this.a);
          Platform.runLater(this.a::refresh);
        });
    this.j.setStyle("-fx-alignment: center-right;");
    this.j.setCellValueFactory(paramCellDataFeatures -> {
          Object object = paramCellDataFeatures.getValue().getValue();
          if (object instanceof Column) {
            Column column = (Column)object;
            return (column.getDataType().getPrecision().usesLength() && column.getLength() != DataType.UNSET) ? (ObservableValue)new SimpleStringProperty("" + column.getLength()) : (ObservableValue)new ReadOnlyStringWrapper("");
          } 
          return (ObservableValue)new ReadOnlyStringWrapper("");
        });
    this.j.setCellFactory(paramTreeTableColumn -> new FxGridEditor$2(this));
    this.j.setEditable(true);
    this.j.setMinWidth(80.0D);
    this.j.setMaxWidth(80.0D);
    this.j.setOnEditCommit(paramCellEditEvent -> {
          try {
            int j = Integer.parseInt((String)paramCellEditEvent.getNewValue());
            if (paramCellEditEvent.getRowValue() != null) {
              Object object = paramCellEditEvent.getRowValue().getValue();
              if (object instanceof Column) {
                Column column = (Column)object;
                column.setLength(j);
              } 
            } 
          } catch (NumberFormatException numberFormatException) {}
        });
    this.k.setStyle("-fx-alignment: center-right;");
    this.k.setCellValueFactory(paramCellDataFeatures -> {
          Object object = paramCellDataFeatures.getValue().getValue();
          if (object instanceof Column) {
            Column column = (Column)object;
            return (column.getDataType().getPrecision().usesDecimal() && column.getDecimal() != DataType.UNSET) ? (ObservableValue)new SimpleStringProperty("" + column.getDecimal()) : (ObservableValue)new ReadOnlyStringWrapper("");
          } 
          return (ObservableValue)new ReadOnlyStringWrapper("");
        });
    this.k.setCellFactory(paramTreeTableColumn -> new FxGridEditor$3(this));
    this.k.setEditable(true);
    this.k.setMinWidth(80.0D);
    this.k.setMaxWidth(80.0D);
    this.k.setOnEditCommit(paramCellEditEvent -> {
          try {
            int j = Integer.parseInt((String)paramCellEditEvent.getNewValue());
            if (paramCellEditEvent.getRowValue() != null && paramCellEditEvent.getRowValue().getValue() instanceof Column)
              ((Column)paramCellEditEvent.getRowValue().getValue()).setDecimal(j); 
          } catch (NumberFormatException numberFormatException) {}
        });
    this.l.setCellValueFactory(paramCellDataFeatures -> {
          Object object = paramCellDataFeatures.getValue().getValue();
          if (object instanceof Column) {
            Column column = (Column)object;
            return (ObservableValue)new SimpleStringProperty(column.getDefaultValue());
          } 
          return (ObservableValue)new ReadOnlyStringWrapper("");
        });
    this.l.setCellFactory(paramTreeTableColumn -> new FxGridEditor$4(this));
    this.l.setEditable(true);
    this.l.setMinWidth(80.0D);
    this.l.setMaxWidth(80.0D);
    this.l.setOnEditCommit(paramCellEditEvent -> {
          if (paramCellEditEvent.getRowValue() != null) {
            Object object = paramCellEditEvent.getRowValue().getValue();
            if (object instanceof Column) {
              Column column = (Column)object;
              column.setDefaultValue((String)paramCellEditEvent.getNewValue());
            } 
          } 
        });
    this.m.setCellValueFactory(paramCellDataFeatures -> {
          Object object = paramCellDataFeatures.getValue().getValue();
          Index index = (Index)object;
          return (ObservableValue)new SimpleObjectProperty((object instanceof Index) ? index : null);
        });
    this.m.setCellFactory(paramTreeTableColumn -> new FxGridEditor$5(this));
    this.m.setOnEditStart(paramCellEditEvent -> new FxIndexEditor(this, (Index)paramCellEditEvent.getRowValue().getValue(), false));
    this.m.setEditable(true);
    this.n.setCellValueFactory(paramCellDataFeatures -> {
          Object object = paramCellDataFeatures.getValue().getValue();
          ForeignKey foreignKey = (ForeignKey)object;
          return (ObservableValue)new SimpleObjectProperty((object instanceof ForeignKey) ? foreignKey : null);
        });
    this.n.setCellFactory(paramTreeTableColumn -> new FxGridEditor$6(this));
    this.n.setOnEditStart(paramCellEditEvent -> new FxForeignKeyEditor(this, (ForeignKey)paramCellEditEvent.getRowValue().getValue(), false));
    this.n.setEditable(true);
    this.o.setCellValueFactory(paramCellDataFeatures -> new SimpleObjectProperty((paramCellDataFeatures.getValue().getValue() instanceof ForeignKey) ? ((ForeignKey)paramCellDataFeatures.getValue().getValue()).getTargetEntity() : null));
    this.p.setCellValueFactory(paramCellDataFeatures -> new SimpleObjectProperty((paramCellDataFeatures.getValue().getValue() instanceof ForeignKey) ? ((ForeignKey)paramCellDataFeatures.getValue().getValue()).getRelationType() : null));
    this.p.setCellFactory(paramTreeTableColumn -> new ComboBoxTreeTableCell((Object[])RelationType.values()));
    this.p.setEditable(true);
    this.q.setCellValueFactory(paramCellDataFeatures -> new SimpleObjectProperty((paramCellDataFeatures.getValue().getValue() instanceof ForeignKey) ? ((ForeignKey)paramCellDataFeatures.getValue().getValue()).getRelationCardinality() : null));
    this.q.setCellFactory(paramTreeTableColumn -> new ComboBoxTreeTableCell((Object[])RelationCardinality.values()));
    this.q.setEditable(true);
    this.t.setCellValueFactory(paramCellDataFeatures -> new SimpleObjectProperty(paramCellDataFeatures.getValue().getValue()));
    this.t.setCellFactory(paramTreeTableColumn -> new n());
    this.t.setStyle("-fx-alignment: center;");
    this.t.setEditable(true);
    this.t.setMinWidth(40.0D);
    this.t.setMaxWidth(40.0D);
    this.r.setCellValueFactory(paramCellDataFeatures -> new SimpleStringProperty((paramCellDataFeatures.getValue() != null && paramCellDataFeatures.getValue().getValue() != null) ? ((AbstractUnit)paramCellDataFeatures.getValue().getValue()).getComment() : null));
    this.r.setCellFactory(paramTreeTableColumn -> new FxGridEditor$7(this, true));
    this.r.setEditable(true);
    this.r.setOnEditCommit(paramCellEditEvent -> {
          if (paramCellEditEvent.getRowValue() != null && paramCellEditEvent.getRowValue().getValue() != null)
            ((AbstractUnit)paramCellEditEvent.getRowValue().getValue()).setComment((String)paramCellEditEvent.getNewValue()); 
        });
    this.u.setCellValueFactory(paramCellDataFeatures -> new SimpleObjectProperty((paramCellDataFeatures.getValue() != null) ? paramCellDataFeatures.getValue().getValue() : null));
    this.u.setCellFactory(paramTreeTableColumn -> new l());
    this.u.setEditable(true);
    this.s.setCellValueFactory(paramCellDataFeatures -> new SimpleStringProperty((paramCellDataFeatures.getValue().getValue() != null && ((AbstractUnit)paramCellDataFeatures.getValue().getValue()).getEntity() != null) ? ((AbstractUnit)paramCellDataFeatures.getValue().getValue()).getEntity().getName() : null));
    this.s.setEditable(false);
    this.c.setCellValueFactory(paramCellDataFeatures -> new SimpleStringProperty((paramCellDataFeatures.getValue().getValue() != null && ((AbstractUnit)paramCellDataFeatures.getValue().getValue()).getEntity() != null && ((AbstractUnit)paramCellDataFeatures.getValue().getValue()).getEntity().getSchema() != null) ? ((AbstractUnit)paramCellDataFeatures.getValue().getValue()).getEntity().getSchema().toString() : null));
    a(new TreeTableColumn[] { 
          this.b, this.c, this.f, this.e, this.d, this.i, this.j, this.k, this.l, this.r, 
          this.m, this.o, this.n, this.p, this.q, this.u, this.t });
  }
  
  public Workspace getWorkspace() {
    return this.D.getWorkspace();
  }
  
  public void a(String paramString) {
    this.F = (paramString != null) ? paramString.toLowerCase().trim() : null;
    if (this.a.getRoot() instanceof i)
      ((i)this.a.getRoot()).e(); 
  }
  
  public void a(TreeTableColumn... paramVarArgs) {
    for (TreeTableColumn treeTableColumn : paramVarArgs) {
      double d = Pref.b("GridColWidth" + treeTableColumn.getText(), -1.0D);
      if (d > 10.0D)
        treeTableColumn.setPrefWidth(d); 
      treeTableColumn.widthProperty().addListener((paramObservableValue, paramNumber1, paramNumber2) -> Pref.a("GridColWidth" + paramTreeTableColumn.getText(), paramNumber2.doubleValue()));
    } 
  }
}
