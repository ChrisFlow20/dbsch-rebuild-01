package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.config.fx.FxSyntaxOptionComboBox;
import com.wisecoders.dbs.config.model.LetterCase;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.ForeignKeyAction;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.RelationCardinality;
import com.wisecoders.dbs.schema.RelationType;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.DelayedTimeline;
import com.wisecoders.dbs.sys.DelayedTimelineHandler;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.ToggleGroup$;
import com.wisecoders.dbs.sys.WebBrowserExternal;
import com.wisecoders.dbs.sys.fx.AutoCompleteComboBox;
import com.wisecoders.dbs.sys.fx.ComboBoxSearchDialog;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.NumericTextField$;
import com.wisecoders.dbs.sys.fx.RowPane$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import org.controlsfx.control.PopOver;

public class FxForeignKeyEditor extends FxDbDialog implements FxUnitEditor {
  private final AbstractTable a;
  
  private final ForeignKey c;
  
  private AbstractTable d;
  
  private final boolean e;
  
  private final Hyperlink f = this.rx.o("fkLogicalType");
  
  private final TextField i = new TextField();
  
  private final TableView j = new TableView();
  
  private final ComboBox k = new ComboBox();
  
  private final ComboBox l = new ComboBox();
  
  private final ComboBox m = new ComboBox();
  
  private final ComboBox n = new ComboBox();
  
  private final CheckBox o = this.rx.h("virtualLabel", false);
  
  private final CheckBox p = this.rx.h("enforcePkLabel", false);
  
  private final CheckBox q;
  
  private final FxSyntaxOptionComboBox r;
  
  private final FxCommentPanel s;
  
  private final Label t;
  
  private final Label u = this.rx.e("deleteLabel");
  
  private final Label v = this.rx.e("updateLabel");
  
  private final Label w;
  
  private final Label x;
  
  private final Label y;
  
  private final RadioButton z = this.rx.w("radioTypeIdentifying");
  
  private final RadioButton A = this.rx.w("radioTypeNonIdentifying");
  
  private final RadioButton B = this.rx.w("radioTypeManyToMany");
  
  private final RadioButton C = this.rx.w("radioMandatory");
  
  private final RadioButton D = this.rx.w("radioOptional");
  
  private final RadioButton E = this.rx.w("radioCardinalityZeroOrMore");
  
  private final RadioButton F = this.rx.w("radioCardinalityOneOrMore");
  
  private final RadioButton G = this.rx.w("radioCardinalityZeroOrOne");
  
  private final RadioButton H = this.rx.w("radioCardinalityOne");
  
  private final RadioButton I = this.rx.w("radioCardinalityRange");
  
  private final NumericTextField$ J = this.rx.u("rangeFromField");
  
  private final NumericTextField$ K = this.rx.u("rangeToField");
  
  private boolean L = true;
  
  public FxForeignKeyEditor(WorkspaceWindow paramWorkspaceWindow, AbstractTable paramAbstractTable1, AbstractTable paramAbstractTable2, boolean paramBoolean) {
    this(paramWorkspaceWindow, paramAbstractTable1, paramAbstractTable2, (ForeignKey)null, (Column)null, (Column)null, paramBoolean);
  }
  
  public FxForeignKeyEditor(WorkspaceWindow paramWorkspaceWindow, Column paramColumn1, Column paramColumn2, boolean paramBoolean) {
    this(paramWorkspaceWindow, paramColumn1.getEntity(), paramColumn2.getEntity(), (ForeignKey)null, paramColumn1, paramColumn2, paramBoolean);
  }
  
  FxForeignKeyEditor(WorkspaceWindow paramWorkspaceWindow, ForeignKey paramForeignKey, boolean paramBoolean) {
    this(paramWorkspaceWindow, paramForeignKey.getEntity(), paramForeignKey.getTargetEntity(), paramForeignKey, (Column)null, (Column)null, paramBoolean);
  }
  
  private FxForeignKeyEditor(WorkspaceWindow paramWorkspaceWindow, AbstractTable paramAbstractTable1, AbstractTable paramAbstractTable2, ForeignKey paramForeignKey, Column paramColumn1, Column paramColumn2, boolean paramBoolean) {
    super(paramWorkspaceWindow, paramBoolean);
    this.a = paramAbstractTable1;
    this.c = paramForeignKey;
    this.e = (this.c == null);
    this.s = new FxCommentPanel(paramWorkspaceWindow.getWorkspace(), this.c);
    this.y = this.rx.e("descriptionLabel");
    String str = (paramAbstractTable1.getSchema()).project.getDbId();
    this.rx.G(str);
    this.t = this.rx.e("nameLabel");
    this.w = this.rx.e("schemaLabel");
    this.x = this.rx.e("tableLabel");
    this.q = this.rx.h("optimizeDeletionLabel", false);
    Dbms dbms = Dbms.get(str);
    this.rx.a("flagCanAdd", () -> (this.d != null));
    this.rx.a("flagCanRemove", () -> (this.j.getSelectionModel().getSelectedIndex() > -1));
    this.rx.a("flagCanUp", () -> (this.j.getSelectionModel().getSelectedIndex() > 0));
    this.rx.a("flagCanDown", () -> (this.j.getSelectionModel().getSelectedIndex() > 0 && this.j.getSelectionModel().getSelectedIndex() < this.j.getItems().size() - 1));
    TableColumn tableColumn1 = new TableColumn(this.rx.H("fromColumn"));
    tableColumn1.setGraphic((Node)BootstrapIcons.arrow_up_right.glyph(new String[0]));
    tableColumn1.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(((g)paramCellDataFeatures.getValue()).a.getNameWithPath()));
    TableColumn tableColumn2 = new TableColumn(this.rx.H("toColumn"));
    tableColumn2.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper((((g)paramCellDataFeatures.getValue()).b != null) ? ((g)paramCellDataFeatures.getValue()).b.getNameWithPath() : null));
    tableColumn2.setGraphic((Node)BootstrapIcons.key_fill.glyph(new String[] { "glyph-orange" }));
    this.j.getColumns().addAll((Object[])new TableColumn[] { tableColumn1, tableColumn2 });
    this.j.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    this.p.setSelected((dbms.uniqueIndexCreate.j() || dbms.uniqueKeyCreate.j()));
    this.q.setSelected((dbms.indexCreate.j() && (Dbms.get(str)).newForeignKeyOptimizeDeletion.b()));
    this.k.getItems().addAll((paramAbstractTable1.getSchema()).project.schemas);
    this.m.getItems().add(ForeignKeyAction.noAction);
    if (dbms.alterForeignKey.g())
      this.m.getItems().add(ForeignKeyAction.cascade); 
    if (dbms.alterForeignKey.h())
      this.m.getItems().add(ForeignKeyAction.restrict); 
    if (dbms.alterForeignKey.j())
      this.m.getItems().add(ForeignKeyAction.setNull); 
    this.n.getItems().add(ForeignKeyAction.noAction);
    if (dbms.alterForeignKey.k())
      this.n.getItems().add(ForeignKeyAction.cascade); 
    if (dbms.alterForeignKey.l())
      this.n.getItems().add(ForeignKeyAction.restrict); 
    if (dbms.alterForeignKey.n())
      this.n.getItems().add(ForeignKeyAction.setNull); 
    if (this.n.getItems().isEmpty()) {
      this.v.setVisible(false);
      this.n.setVisible(false);
    } 
    if (dbms.autoLetterCases.b()) {
      LetterCase letterCase = dbms.getLetterCases();
      Rx.a(this.i, letterCase);
      this.t.setText("Name (" + String.valueOf(letterCase) + ")");
    } 
    boolean bool = this.b.getWorkspace().t();
    this.r = new FxSyntaxOptionComboBox(this, (Dbms.get(str)).fkOptions);
    setManagedVisible(new Node[] { (Node)this.r });
    this.J.setDisable(true);
    this.K.setDisable(true);
    this.I.selectedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          this.J.setDisable(!paramBoolean2.booleanValue());
          this.K.setDisable(!paramBoolean2.booleanValue());
        });
    this.z.selectedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          this.D.setDisable(paramBoolean2.booleanValue());
          if (paramBoolean2.booleanValue())
            this.C.setSelected(true); 
        });
    if (this.c == null) {
      boolean bool1 = false;
      if (paramColumn1 == null || paramColumn2 == null) {
        this.d = paramAbstractTable2;
      } else {
        this.j.getItems().add(new g(paramColumn1, paramColumn2));
        this.d = paramColumn2.getEntity();
        bool1 = (this.d instanceof View || paramColumn1.getEntity() instanceof View) ? true : false;
      } 
      this.i.setText((paramAbstractTable1.getSchema()).nameFinder.a(paramAbstractTable1, (paramColumn2 != null) ? paramColumn2.table : null, paramColumn1));
      this.i.selectAll();
      this.m.setValue(ForeignKeyAction.noAction);
      this.n.setValue(ForeignKeyAction.noAction);
      setInitFocusedNode((Node)this.i);
      this.z.setSelected(true);
      this.o.setSelected((bool1 || (Dbms.get(str)).createNewForeignKeysAsVirtual.b() || (!(Dbms.get(str)).fkCreate.j() && !(Dbms.get(str)).fkCreateInline.j() && !this.b.k())));
      this.o.setDisable(SyncUtil.c(paramAbstractTable1));
      this.C.setSelected(true);
      this.E.setSelected(true);
    } else {
      this.i.setDisable((bool && !dbms.alterForeignKey.d()));
      this.o.setSelected(this.c.isVirtual());
      this.o.setDisable((SyncUtil.c(paramAbstractTable1) || (!this.c.isVirtual() && this.b.getWorkspace().t())));
      this.d = this.c.getTargetEntity();
      for (Iterator<Column> iterator1 = this.c.getSourceAttributes().iterator(), iterator2 = this.c.getTargetAttributes().iterator(); iterator1.hasNext() && iterator2.hasNext();)
        this.j.getItems().add(new g(iterator1.next(), iterator2.next())); 
      this.L = (this.c.getName() == null || this.c.getName().isEmpty());
      this.i.setText(this.c.getName());
      this.m.setValue(this.c.getDeleteAction());
      this.n.setValue(this.c.getUpdateAction());
      this.r.a(paramForeignKey.getOptions());
      this.z.setSelected((paramForeignKey.getRelationType() == RelationType.b));
      this.A.setSelected((paramForeignKey.getRelationType() == RelationType.a));
      this.B.setSelected((paramForeignKey.getRelationType() == RelationType.c));
      this.C.setSelected(paramForeignKey.isLogicalMandatory());
      this.D.setSelected(!paramForeignKey.isLogicalMandatory());
      this.F.setSelected((paramForeignKey.getRelationCardinality() == RelationCardinality.c));
      this.H.setSelected((paramForeignKey.getRelationCardinality() == RelationCardinality.d));
      this.E.setSelected((paramForeignKey.getRelationCardinality() == RelationCardinality.a));
      this.G.setSelected((paramForeignKey.getRelationCardinality() == RelationCardinality.b));
      this.I.setSelected((paramForeignKey.getRelationCardinality() == RelationCardinality.e));
      this.J.setText("" + paramForeignKey.getLogicalRangeFrom());
      this.K.setText("" + paramForeignKey.getLogicalRangeTo());
    } 
    new ToggleGroup$(new ToggleButton[] { (ToggleButton)this.z, (ToggleButton)this.A, (ToggleButton)this.B });
    new ToggleGroup$(new ToggleButton[] { (ToggleButton)this.C, (ToggleButton)this.D });
    new ToggleGroup$(new ToggleButton[] { (ToggleButton)this.H, (ToggleButton)this.F, (ToggleButton)this.E, (ToggleButton)this.G, (ToggleButton)this.I });
    this.J.setPrefColumnCount(3);
    this.K.setPrefColumnCount(3);
    this.i.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> this.L = false);
    this.k.getSelectionModel().selectedItemProperty().addListener(paramObservable -> schemaChangeAction());
    if (this.d != null)
      this.k.setValue(this.d.getSchema()); 
    if (this.k.getItems().size() == 1)
      this.k.setValue(this.k.getItems().get(0)); 
    this.l.setOnAction(paramActionEvent -> {
          if (this.d != this.l.getValue()) {
            this.d = (AbstractTable)this.l.getValue();
            if (this.L) {
              String str = "fk_" + String.valueOf(paramAbstractTable) + "_" + String.valueOf(this.d);
              if (str.length() > 30)
                str = "fk_" + String.valueOf(paramAbstractTable); 
              this.i.setText(str.toLowerCase());
            } 
            this.j.getItems().clear();
            d();
          } 
          this.rx.b();
        });
    this.j.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramg1, paramg2) -> {
          this.rx.b();
          d();
        });
    this.rx.b();
    d();
    new AutoCompleteComboBox(this.k);
    new AutoCompleteComboBox(this.l);
    if (this.e)
      if (this.l.getValue() == null) {
        this.rx.a("balloonOnEmptyEntityCombo.text", (Node)this.l, PopOver.ArrowLocation.BOTTOM_CENTER, new String[0]);
      } else if (this.j.getItems().isEmpty()) {
        this.rx.a("balloonOnEmptyColumnList.text", (Node)this.j, PopOver.ArrowLocation.BOTTOM_CENTER, new String[0]);
      }  
    DelayedTimeline delayedTimeline = new DelayedTimeline(this);
    DelayedTimelineHandler delayedTimelineHandler = delayedTimeline.a(1000L, this::e);
    this.i.setOnKeyTyped(paramKeyEvent -> paramDelayedTimelineHandler.a());
    if (paramColumn1 != null && paramColumn2 != null)
      Platform.runLater(this::d); 
  }
  
  private void e() {
    String str = this.i.getText();
    Relation relation = (Relation)AbstractUnit.getByName(this.a.getRelations(), str);
    if (this.e)
      if (relation != null && relation != this.c) {
        showNotificationPane("duplicateNameWarning");
      } else if (Dbms.get(this.a.getSchema()).isReservedKeyword(str)) {
        showNotificationPane("reservedKeywordWarning");
      }  
  }
  
  @Action
  public void schemaChangeAction() {
    Schema schema = (Schema)this.k.getSelectionModel().getSelectedItem();
    this.l.getItems().clear();
    if (schema != null) {
      for (Table table : schema.tables)
        this.l.getItems().add(table); 
      if (this.o.isSelected())
        for (View view : schema.views)
          this.l.getItems().add(view);  
    } 
    this.l.setValue(this.d);
  }
  
  @Action(b = "flagCanDown")
  public void downColumn() {
    int i = this.j.getSelectionModel().getSelectedIndex();
    g g = (g)this.j.getItems().get(i);
    this.j.getItems().remove(g);
    this.j.getItems().add(i + 1, g);
    d();
  }
  
  @Action(b = "flagCanUp")
  public void upColumn() {
    int i = this.j.getSelectionModel().getSelectedIndex();
    if (i > 0) {
      g g = (g)this.j.getItems().get(i);
      this.j.getItems().remove(g);
      this.j.getItems().add(i - 1, g);
      this.j.getSelectionModel().select(i - 1);
    } 
    d();
  }
  
  @Action(b = "flagCanAdd")
  public void addColumn() {
    if (this.d == null) {
      showError("Please select the target table first");
      return;
    } 
    ArrayList<Column> arrayList1 = new ArrayList();
    ArrayList<Column> arrayList2 = new ArrayList();
    List list1 = b();
    for (Column column : this.a.getAttributes()) {
      if (!list1.contains(column))
        arrayList1.add(column); 
    } 
    List list2 = c();
    for (Column column : this.d.getAttributes()) {
      if (!list2.contains(column))
        arrayList2.add(column); 
    } 
    (new h(this, getDialogPane().getScene().getWindow(), arrayList1, arrayList2)).showDialog().ifPresent(paramPair -> {
          if (paramPair.getKey() != null && paramPair.getValue() != null)
            this.j.getItems().add(new g((Column)paramPair.getKey(), (Column)paramPair.getValue())); 
        });
    d();
  }
  
  @Action(b = "flagCanRemove")
  public void removeColumn() {
    if (this.rx.a(getDialogPane().getScene(), "Remove selected columns ?")) {
      int i = this.j.getSelectionModel().getSelectedIndex();
      if (i > -1) {
        g g = (g)this.j.getItems().get(i);
        this.j.getItems().remove(g);
      } 
    } 
  }
  
  @Action
  public void searchTable() {
    new ComboBoxSearchDialog(getWorkspace(), "Search Table Dialog", this.l);
  }
  
  public Node createContentPane() {
    this.i.setPrefColumnCount(25);
    this.j.setPrefHeight(120.0D);
    setDialogTitle((this.e ? "New " : "Edit ") + (this.e ? "New " : "Edit "));
    RowPane$ rowPane$ = (new RowPane$()).a().b();
    VBox.setVgrow((Node)this.j, Priority.ALWAYS);
    rowPane$.a((Node)new Label(this.rx.H("tableLabel") + " " + this.rx.H("tableLabel") + " " + this.a.getNameWithSchemaName()));
    rowPane$.a(new Node[] { (Node)this.w, (Node)this.k, (Node)this.x, (Node)this.l, (Node)this.rx.j("searchTable") });
    rowPane$.a((Node)this.j);
    rowPane$.a(this.rx.c(new String[] { "addColumn", "upColumn", "downColumn", "removeColumn" }));
    if (this.b.k()) {
      GridPane$ gridPane$ = (new GridPane$()).e().a(new int[] { -2, -1, -2, -1, -2, -2 });
      gridPane$.a((Node)this.z, "0,0,l,c");
      gridPane$.a((Node)this.A, "0,1,l,c");
      gridPane$.a((Node)this.B, "0,2,l,c");
      gridPane$.a((Node)this.C, "2,0,l,c");
      gridPane$.a((Node)this.D, "2,1,l,c");
      gridPane$.a((Node)this.E, "4,0,l,c");
      gridPane$.a((Node)this.G, "4,1,l,c");
      gridPane$.a((Node)this.I, "4,2,l,c");
      gridPane$.a((Node)this.F, "5,0,l,c");
      gridPane$.a((Node)this.H, "5,1,l,c");
      gridPane$.a((Node)(new HBox$()).d().a(new Node[] { (Node)this.J, (Node)this.K }, ), "5,2,l,c");
      rowPane$.a((Node)gridPane$);
    } 
    rowPane$.a(new Node[] { (Node)this.u, (Node)this.m, (Node)this.v, (Node)this.n, (Node)this.f });
    rowPane$.a((Node)this.r);
    this.r.b("Options");
    GridPane$ gridPane$1 = (new GridPane$()).e().g();
    gridPane$1.a(new int[] { -1 });
    gridPane$1.a((Node)this.p, "0,0,l,c");
    gridPane$1.a((Node)this.q, "0,1,l,c");
    TabPane tabPane = new TabPane();
    Rx.a(tabPane);
    tabPane.getTabs().add(new Tab(this.rx.H("definitionTab"), (Node)rowPane$));
    tabPane.getTabs().add(new Tab(this.rx.H("optimizationsTab"), (Node)gridPane$1));
    tabPane.getSelectionModel().select(0);
    GridPane$ gridPane$2 = (new GridPane$()).e();
    gridPane$2.a(new int[] { -2, -1 }).b(new int[] { -2, -2, -2, -1 });
    gridPane$2.a((Node)this.t, "0,0,r,c");
    gridPane$2.a((Node)this.i, "1,0,l,c");
    gridPane$2.a((Node)this.o, "1,1,l,c");
    gridPane$2.a((Node)this.y, "0,2,r,c");
    gridPane$2.a((Node)this.s, "1,2,2,2,f,f");
    gridPane$2.a((Node)tabPane, "0,3,2,3,f,f ");
    return (Node)gridPane$2;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
    createHelpButton("schema.html#fk");
  }
  
  @Action
  public void fkLogicalType() {
    WebBrowserExternal.a(getDialogScene(), "schema.html#fk-type");
  }
  
  public boolean validate() {
    String str = this.i.getText();
    if (str != null)
      str = str.trim(); 
    if (StringUtil.isEmptyTrim(str)) {
      showError("setForeignKeyNameError");
      this.i.requestFocus();
      return false;
    } 
    if (this.a.getRelations().isWrongName(str, this.c)) {
      showError("duplicateForeignKeyNameError");
      this.i.requestFocus();
      return false;
    } 
    if (this.l.getValue() == null) {
      showError("chooseReferredTableError");
      return false;
    } 
    if (this.j.getItems().isEmpty() && !this.a.is(UnitProperty.g).booleanValue()) {
      showError("addColumnToForeignKeyError");
      return false;
    } 
    return true;
  }
  
  public void applyChanges() {
    String str = this.i.getText().trim();
    AbstractTable abstractTable = (AbstractTable)this.l.getValue();
    boolean bool = (this.o.isSelected() && !this.o.isDisable()) ? true : false;
    if (!bool && this.p.isSelected() && abstractTable instanceof Table) {
      Table table = (Table)abstractTable;
      List<Column> list = c();
      Index index = table.getIndexFittingColumns(list);
      if (index == null) {
        String str1 = (table.getSchema()).nameFinder.a(table, list.isEmpty() ? null : list.get(0), IndexType.UNIQUE_KEY);
        index = this.doImplement ? new Index(table, str1) : table.createIndex(str1);
        for (Column column : list)
          index.addColumn(column); 
        index.setType(IndexType.UNIQUE_KEY);
        addSyncOperation(abstractTable, (AbstractUnit)null, index, true);
      } else if (!index.isUnique()) {
        if (this.doImplement) {
          Index index1 = new Index(table, index.getName());
          for (Column column : list)
            index1.addColumn(column); 
          index1.setType(IndexType.UNIQUE_KEY);
          addSyncOperation(abstractTable, index, index1, true);
        } else {
          index.setType(IndexType.UNIQUE_KEY);
        } 
      } 
    } 
    if (this.e && !bool) {
      AbstractTable abstractTable1 = this.a;
      if (abstractTable1 instanceof Table) {
        Table table = (Table)abstractTable1;
        if (this.q.isSelected() && !this.q.isDisabled()) {
          List<Column> list = b();
          Index index = table.getIndexFittingColumns(list);
          if (index == null) {
            String str1 = (this.a.getSchema()).nameFinder.a(this.a, list.get(0), IndexType.NORMAL);
            index = this.doImplement ? new Index(table, str1) : table.createIndex(str1);
            for (Column column : list)
              index.addColumn(column); 
            addSyncOperation(this.a, (AbstractUnit)null, index, true);
          } 
        } 
      } 
    } 
    ForeignKey foreignKey = (this.doImplement && !bool) ? new ForeignKey(this.a, this.i.getText()) : ((this.c != null) ? this.c : this.a.createRelation(this.i.getText()));
    foreignKey.rename(str);
    foreignKey.setTargetEntity((AbstractTable)this.l.getValue(), (!this.doImplement || bool));
    foreignKey.setVirtual(this.o.isSelected());
    foreignKey.setComment(this.s.b());
    foreignKey.setCommentTags(this.s.a());
    foreignKey.setOptions(this.r.a());
    for (g g : this.j.getItems())
      foreignKey.addColumns(g.a, g.b); 
    b(foreignKey);
    if (this.b.k()) {
      a(foreignKey);
      foreignKey.resolveLogical();
    } 
    if (!bool)
      addSyncOperation(this.a, this.c, foreignKey); 
  }
  
  private void a(ForeignKey paramForeignKey) {
    if (this.z.isSelected()) {
      paramForeignKey.setRelationType(RelationType.b);
    } else if (this.A.isSelected()) {
      paramForeignKey.setRelationType(RelationType.a);
    } else if (this.B.isSelected()) {
      paramForeignKey.setRelationType(RelationType.c);
    } 
    paramForeignKey.setLogicalMandatory(this.C.isSelected());
    if (this.H.isSelected()) {
      paramForeignKey.setRelationCardinality(RelationCardinality.d);
    } else if (this.F.isSelected()) {
      paramForeignKey.setRelationCardinality(RelationCardinality.c);
    } else if (this.G.isSelected()) {
      paramForeignKey.setRelationCardinality(RelationCardinality.b);
    } else if (this.E.isSelected()) {
      paramForeignKey.setRelationCardinality(RelationCardinality.a);
    } else if (this.I.isSelected()) {
      paramForeignKey.setRelationCardinality(RelationCardinality.e);
      paramForeignKey.setLogicalRangeFrom(this.J.a());
      paramForeignKey.setLogicalRangeTo(this.K.a());
    } 
  }
  
  public AbstractUnit a() {
    return this.c;
  }
  
  public List b() {
    ArrayList<Column> arrayList = new ArrayList();
    for (g g : this.j.getItems())
      arrayList.add(g.a); 
    return arrayList;
  }
  
  public List c() {
    ArrayList<Column> arrayList = new ArrayList();
    for (g g : this.j.getItems())
      arrayList.add(g.b); 
    return arrayList;
  }
  
  public void d() {
    this.rx.d();
    boolean bool1 = true;
    for (ForeignKey foreignKey : this.a.getRelations()) {
      if (foreignKey != this.c && c(foreignKey))
        showNotificationPane("Warning: A similar foreign key already exists."); 
    } 
    for (g g : this.j.getItems()) {
      Column column1 = g.a;
      Column column2 = g.b;
      if (column1 != null && column2 != null) {
        if (column1.isUnsigned() != column2.isUnsigned())
          showNotificationPane("Warning: The from and to column(s) have different unsigned options."); 
        if (column1.getDataType().getJavaType() != column2.getDataType().getJavaType())
          showNotificationPane("Warning: The from and to column(s) have different data type."); 
        if (!column1.isMandatory())
          bool1 = false; 
      } 
    } 
    Index index1 = (this.a instanceof Table) ? ((Table)this.a).getIndexFittingColumns(b()) : null;
    boolean bool2 = (index1 != null && index1.isUnique()) ? true : false;
    if (bool1) {
      this.f.setText(bool2 ? "Type 1:1" : "Type 1:[1 or many]");
    } else {
      this.f.setText(bool2 ? "Type 1:[O or 1]" : "Type 1:[0 or many]");
    } 
    AbstractTable abstractTable = (AbstractTable)this.l.getValue();
    Index index2 = null;
    if (abstractTable instanceof Table) {
      List list = c();
      for (Index index : ((Table)abstractTable).indexes) {
        if (Index.attributesAreEqual(index.getColumns(), list))
          index2 = index; 
      } 
    } 
    this.p.setDisable((!this.e || abstractTable == null || (index2 != null && index2.isUnique())));
    this.q.setDisable((!this.e || index1 != null));
  }
  
  public void saveSucceeded() {
    ForeignKey foreignKey = (ForeignKey)AbstractUnit.getByName(this.a.getRelations(), this.i.getText());
    if (foreignKey != null) {
      foreignKey.setOptions(this.r.a());
      foreignKey.setVirtual(this.o.isSelected());
      a(foreignKey);
      b(foreignKey);
    } 
    this.s.a(this.a.getRelations().getByName(this.i.getText()));
    this.b.x();
  }
  
  private void b(ForeignKey paramForeignKey) {
    ForeignKeyAction foreignKeyAction1 = (ForeignKeyAction)this.m.getValue();
    paramForeignKey.setDeleteAction((foreignKeyAction1 != null) ? foreignKeyAction1 : ForeignKeyAction.noAction);
    ForeignKeyAction foreignKeyAction2 = (ForeignKeyAction)this.n.getValue();
    paramForeignKey.setUpdateAction((foreignKeyAction2 != null) ? foreignKeyAction2 : ForeignKeyAction.noAction);
  }
  
  private boolean c(ForeignKey paramForeignKey) {
    if (paramForeignKey.getEntity() != this.a)
      return false; 
    if (paramForeignKey.getTargetEntity() != this.l.getValue())
      return false; 
    if (this.j.getItems().size() != paramForeignKey.getColumnCount())
      return false; 
    for (byte b = 0; b < paramForeignKey.getColumnCount(); b++) {
      if (((g)this.j.getItems().get(b)).a != paramForeignKey.getSourceAttributes().get(b))
        return false; 
      if (((g)this.j.getItems().get(b)).b != paramForeignKey.getTargetAttributes().get(b))
        return false; 
    } 
    return true;
  }
}
