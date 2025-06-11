package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.config.fx.FxSyntaxOptionComboBox;
import com.wisecoders.dbs.config.model.LetterCase;
import com.wisecoders.dbs.config.model.OptionsProperty;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.DelayedTimeline;
import com.wisecoders.dbs.sys.DelayedTimelineHandler;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.ToggleGroup$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

public class FxIndexEditor extends FxDbDialog implements FxUnitEditor {
  private final boolean a;
  
  private final Table c;
  
  private final Index d;
  
  private final String e;
  
  private final Map f = new HashMap<>();
  
  private OptionsProperty i;
  
  private final TableView j = new TableView();
  
  private final TableColumn k = new TableColumn(this.rx.H("columnsColumn"));
  
  private final TableColumn l = new TableColumn(this.rx.H("optionsColumn"));
  
  private final FxCommentPanel m;
  
  private final FxSyntaxOptionComboBox n;
  
  private final FxSyntaxOptionComboBox o;
  
  private final TextField p = new TextField();
  
  private boolean q = false;
  
  private final Label r = this.rx.e("nameLabel");
  
  private final Label s = this.rx.e("descriptionLabel");
  
  private final RadioButton t;
  
  private final RadioButton u = this.rx.w("uniqueKeyRadio");
  
  private final RadioButton v = this.rx.w("uniqueIndexRadio");
  
  private final RadioButton w = this.rx.w("normalRadio");
  
  private final RadioButton x = this.rx.w("partitionRadio");
  
  private final RadioButton y = this.rx.w("sortRadio");
  
  private final RadioButton z = this.rx.w("clusterRadio");
  
  private final RadioButton A = this.rx.w("index1Radio");
  
  private final RadioButton B = this.rx.w("index2Radio");
  
  private final CheckBox C = this.rx.h("virtualLabel", false);
  
  public FxIndexEditor(WorkspaceWindow paramWorkspaceWindow, Table paramTable, boolean paramBoolean) {
    super(paramWorkspaceWindow, paramBoolean);
    setDialogTitle("Create Index on Table " + paramTable.getName());
    this.d = null;
    this.c = paramTable;
    this.e = paramTable.schema.project.getDbId();
    this.a = true;
    this.rx.G(this.e);
    this.t = this.rx.w("primaryKeyRadio");
    this.n = new FxSyntaxOptionComboBox(this, this.e);
    this.o = new FxSyntaxOptionComboBox(this, this.e);
    this.m = new FxCommentPanel(getWorkspace(), null);
    this.C.setSelected(SyncUtil.c(paramTable));
    this.C.setDisable(SyncUtil.c(paramTable));
    if ((Dbms.get(this.e)).autoLetterCases.b())
      Rx.a(this.p, Dbms.get(this.e).getLetterCases()); 
    this.p.setText(paramTable.schema.nameFinder.a(paramTable, (Column)null, IndexType.NORMAL));
    this.p.selectAll();
    this.q = true;
    b();
    c();
    this.n.d();
    setInitFocusedNode((Node)this.p);
  }
  
  public FxIndexEditor(WorkspaceWindow paramWorkspaceWindow, Table paramTable, List<Column> paramList, boolean paramBoolean) {
    super(paramWorkspaceWindow, paramBoolean);
    setDialogTitle("Create Index on Table " + paramTable.getName());
    this.d = null;
    this.c = paramTable;
    this.e = paramTable.schema.project.getDbId();
    this.a = true;
    this.t = this.rx.w("primaryKeyRadio");
    this.n = new FxSyntaxOptionComboBox(this, this.e);
    this.o = new FxSyntaxOptionComboBox(this, this.e);
    this.m = new FxCommentPanel(getWorkspace(), null);
    this.C.setSelected(SyncUtil.c(paramTable));
    this.C.setDisable(SyncUtil.c(paramTable));
    this.rx.G(this.e);
    this.j.getItems().addAll(paramList);
    if ((Dbms.get(this.e)).autoLetterCases.b())
      Rx.a(this.p, Dbms.get(this.e).getLetterCases()); 
    this.p.setText(paramTable.schema.nameFinder.a(paramTable, paramList.get(0), IndexType.NORMAL));
    this.p.selectAll();
    this.q = true;
    b();
    c();
    this.n.d();
    setInitFocusedNode((Node)this.p);
  }
  
  FxIndexEditor(WorkspaceWindow paramWorkspaceWindow, Index paramIndex, boolean paramBoolean) {
    super(paramWorkspaceWindow, paramBoolean);
    this.d = paramIndex;
    this.c = paramIndex.getEntity();
    this.e = this.c.schema.project.getDbId();
    this.a = false;
    this.rx.G(this.e);
    this.t = this.rx.w("primaryKeyRadio");
    this.n = new FxSyntaxOptionComboBox(this, this.e);
    this.o = new FxSyntaxOptionComboBox(this, this.e);
    this.m = new FxCommentPanel(getWorkspace(), paramIndex);
    this.C.setSelected(SyncUtil.c(paramIndex));
    this.C.setDisable((SyncUtil.c(this.c) || (!paramIndex.isVirtual() && this.b.getWorkspace().t())));
    setDialogTitle(this.rx.H("titleString") + " " + this.rx.H("titleString"));
    Dbms dbms = Dbms.get(this.e);
    boolean bool = dbms.getAlterIndex(paramIndex.getType()).c();
    if (paramIndex.getType() != IndexType.PRIMARY_KEY)
      this.t.setDisable(true); 
    this.p.setDisable((paramWorkspaceWindow.getWorkspace().t() && !bool));
    this.p.setText(paramIndex.getName());
    if ((Dbms.get(this.e)).autoLetterCases.b()) {
      LetterCase letterCase = Dbms.get(this.e).getLetterCases();
      Rx.a(this.p, letterCase);
      this.r.setText("Name (" + String.valueOf(letterCase) + ")");
    } 
    this.j.getItems().addAll(paramIndex.getColumns());
    if (paramIndex.getColumnOptions() != null)
      this.f.putAll(paramIndex.getColumnOptions()); 
    this.n.a(paramIndex.getOptions());
    this.o.a(paramIndex.getSpecificationOptions());
    b();
    c();
  }
  
  public Node createContentPane() {
    this.p.setPrefColumnCount(20);
    this.rx.a("flagCanRemove", () -> (this.j.getSelectionModel().getSelectedIndex() > -1));
    this.rx.a("flagCanUp", () -> (this.j.getSelectionModel().getSelectedIndex() > 0));
    this.rx.a("flagCanDown", () -> (this.j.getSelectionModel().getSelectedIndex() > -1 && this.j.getSelectionModel().getSelectedIndex() < this.j.getItems().size() - 1));
    ToggleGroup$ toggleGroup$ = new ToggleGroup$(new ToggleButton[] { (ToggleButton)this.x, (ToggleButton)this.y, (ToggleButton)this.z, (ToggleButton)this.A, (ToggleButton)this.B, (ToggleButton)this.t, (ToggleButton)this.u, (ToggleButton)this.v, (ToggleButton)this.w });
    toggleGroup$.selectedToggleProperty().addListener((paramObservableValue, paramToggle1, paramToggle2) -> {
          if (this.q)
            this.p.setText(this.c.schema.nameFinder.a(this.c, this.j.getItems().isEmpty() ? null : (Column)this.j.getItems().get(0), e())); 
          Dbms dbms = Dbms.get(this.e);
          if (this.d == null && !dbms.pkCreate.j() && !dbms.pkCreateInline.j() && !dbms.pkCreateEnding.j())
            this.C.setSelected(true); 
        });
    this.p.addEventFilter(KeyEvent.KEY_TYPED, paramKeyEvent -> this.q = false);
    this.x.setOnAction(paramActionEvent -> c());
    this.y.setOnAction(paramActionEvent -> c());
    this.z.setOnAction(paramActionEvent -> c());
    this.A.setOnAction(paramActionEvent -> c());
    this.B.setOnAction(paramActionEvent -> c());
    this.t.setOnAction(paramActionEvent -> c());
    this.u.setOnAction(paramActionEvent -> c());
    this.v.setOnAction(paramActionEvent -> c());
    this.w.setOnAction(paramActionEvent -> c());
    this.k.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(((Column)paramCellDataFeatures.getValue()).toString()));
    this.k.setEditable(false);
    this.l.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue()));
    this.l.setCellFactory(paramTableColumn -> new s(this));
    this.l.setEditable(true);
    this.l.setGraphic(Rx.a());
    this.j.getColumns().addAll((Object[])new TableColumn[] { this.k, this.l });
    this.j.setEditable(true);
    this.j.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramColumn1, paramColumn2) -> this.rx.b());
    this.j.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    SplitMenuButton splitMenuButton = this.rx.g("addColumn", true);
    splitMenuButton.getItems().addAll(this.rx.e(new String[] { "addColumn", "addFunctionalColumn" }));
    this.o.b(this.rx.H("specificationOptions.promptText.text"));
    this.n.b(this.rx.H("options.promptText.text"));
    setManagedVisible(new Node[] { (Node)this.n });
    GridPane$ gridPane$ = (new GridPane$()).e().g().a(new int[] { -2, -1, -1 }).b(new int[] { -2, -2, -1, -2, -1, -2 });
    gridPane$.a((Node)this.r, "0,0,r,c");
    gridPane$.a((Node)this.p, "1,0,f,c");
    gridPane$.a((Node)this.o, "2,0,f,c");
    gridPane$.a((Node)(new FlowPane$()).g().b(new Node[] { (Node)this.t, (Node)this.u, (Node)this.v, (Node)this.w, (Node)this.x, (Node)this.y, (Node)this.z, (Node)this.A, (Node)this.B }, ), "1,1,3,1,f,c");
    gridPane$.a((Node)this.C, "1,2,l,c");
    gridPane$.a((Node)this.s, "0,3,r,c");
    gridPane$.a((Node)this.m, "1,3,3,3,f,f");
    gridPane$.a((Node)this.j, "0,4,3,4,f,f");
    gridPane$.a((Node)(new FlowPane$()).a().a(new Node[] { (Node)splitMenuButton }, ).a(this.rx.c(new String[] { "removeColumn", "upColumn", "downColumn" }, )), "0,5,3,5,l,c");
    gridPane$.a((Node)this.n, "0,6,3,6,f,c");
    setRegionPrefSize((Region)this.j, 270.0D, 170.0D);
    DelayedTimeline delayedTimeline = new DelayedTimeline(this);
    DelayedTimelineHandler delayedTimelineHandler = delayedTimeline.a(1000L, this::d);
    this.p.setOnKeyTyped(paramKeyEvent -> paramDelayedTimelineHandler.a());
    setInitFocusedNode((Node)this.p);
    return (Node)gridPane$;
  }
  
  private void b() {
    boolean bool = "LogicalDesign".equals(this.e);
    Dbms dbms = Dbms.get(this.e);
    if (!dbms.partitionName.j())
      this.x.setVisible(false); 
    if (!dbms.sortName.j())
      this.y.setVisible(false); 
    if (!dbms.clusterName.j())
      this.z.setVisible(false); 
    if (!dbms.index1Name.j())
      this.A.setVisible(false); 
    if (!dbms.index2Name.j())
      this.B.setVisible(false); 
    if (!bool && !dbms.pkCreate.j() && !dbms.pkCreateInline.j() && !dbms.pkCreateEnding.j() && !dbms.pkIndexAllowVirtual.b())
      this.t.setVisible(false); 
    if (!dbms.uniqueIndexCreate.j() && !dbms.uniqueIndexCreateInline.j())
      this.v.setVisible(false); 
    if (!bool && !dbms.uniqueKeyCreate.j() && !dbms.uniqueKeyCreateInline.j())
      this.u.setVisible(false); 
    if (!bool && !dbms.indexCreate.j())
      this.w.setVisible(false); 
    this.x.setText(dbms.partitionName.c_());
    this.y.setText(dbms.sortName.c_());
    this.z.setText(dbms.clusterName.c_());
    this.A.setText(dbms.index1Name.c_());
    this.B.setText(dbms.index2Name.c_());
    Rx.a((Control)this.x, dbms.partitionTooltip.c_());
    Rx.a((Control)this.y, dbms.sortTooltip.c_());
    Rx.a((Control)this.z, dbms.clusterTooltip.c_());
    Rx.a((Control)this.A, dbms.index1Tooltip.c_());
    Rx.a((Control)this.B, dbms.index2Tooltip.c_());
    if (this.d == null) {
      this.w.setVisible(true);
      this.w.setSelected(true);
    } else {
      switch (FxIndexEditor$1.a[this.d.getType().ordinal()]) {
        case 1:
          this.x.setVisible(true);
          this.x.setSelected(true);
          break;
        case 2:
          this.y.setVisible(true);
          this.y.setSelected(true);
          break;
        case 3:
          this.z.setVisible(true);
          this.z.setSelected(true);
          break;
        case 4:
          this.A.setVisible(true);
          this.A.setSelected(true);
          break;
        case 5:
          this.B.setVisible(true);
          this.B.setSelected(true);
          break;
        case 6:
          this.t.setVisible(true);
          this.t.setSelected(true);
          break;
        case 7:
          this.u.setVisible(true);
          this.u.setSelected(true);
          break;
        case 8:
          this.v.setVisible(true);
          this.v.setSelected(true);
          break;
        case 9:
          this.w.setVisible(true);
          this.w.setSelected(true);
          break;
      } 
    } 
  }
  
  private void c() {
    Dbms dbms = Dbms.get(this.e);
    if (this.t.isSelected()) {
      this.o.a(dbms.pkSpecificationOptions);
      this.i = dbms.pkColumnOptions;
      this.n.a(dbms.pkOptions);
    } else if (this.u.isSelected()) {
      this.o.a(dbms.uniqueKeySpecificationOptions);
      this.i = dbms.uniqueKeyColumnOptions;
      this.n.a(dbms.uniqueKeyOptions);
    } else if (this.v.isSelected()) {
      this.o.a(dbms.uniqueIndexSpecificationOptions);
      this.i = dbms.uniqueIndexColumnOptions;
      this.n.a(dbms.uniqueIndexOptions);
    } else if (this.w.isSelected()) {
      this.o.a(dbms.indexSpecificationOptions);
      this.i = dbms.indexColumnOptions;
      this.n.a(dbms.indexOptions);
    } else if (this.x.isSelected()) {
      this.o.a(dbms.partitionSpecificationOptions);
      this.i = dbms.partitionColumnOptions;
      this.n.a(dbms.partitionOptions);
    } else if (this.y.isSelected()) {
      this.o.a(dbms.sortSpecificationOptions);
      this.i = dbms.sortColumnOptions;
      this.n.a(dbms.sortOptions);
    } else if (this.z.isSelected()) {
      this.o.a(dbms.clusterSpecificationOptions);
      this.i = dbms.clusterColumnOptions;
      this.n.a(dbms.clusterOptions);
    } else if (this.A.isSelected()) {
      this.o.a(dbms.index1SpecificationOptions);
      this.i = dbms.index1ColumnOptions;
      this.n.a(dbms.index1Options);
    } else if (this.B.isSelected()) {
      this.o.a(dbms.index2SpecificationOptions);
      this.i = dbms.index2ColumnOptions;
      this.n.a(dbms.index2Options);
    } 
    this.l.setVisible(((this.i != null && this.i.f()) || !this.f.isEmpty()));
    this.j.refresh();
  }
  
  private void d() {
    String str = this.p.getText();
    Index index = (Index)AbstractUnit.getByName(this.c.getIndexes(), str);
    if (this.a)
      if (index != null && index != this.d) {
        showNotificationPane("duplicateNameWarning");
      } else if (Dbms.get(this.c.schema.project.getDbId()).isReservedKeyword(str)) {
        showNotificationPane("reservedKeywordWarning");
      }  
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
    createHelpButton("schema.html#index");
  }
  
  public boolean validate() {
    if (this.j.getItems().isEmpty()) {
      showError("addColumnToIndexError");
      return false;
    } 
    Index index1 = this.c.getIndexFittingColumns((List)this.j.getItems());
    if (index1 != null && index1 != this.d && 

      
      a(this.n.a()) && 
      a(index1.getOptions()) && (((this.w
      .isSelected() || this.u.isSelected()) && (index1
      .getType() == IndexType.NORMAL || index1.getType() == IndexType.UNIQUE_KEY || index1
      .getType() == IndexType.UNIQUE_INDEX)) || (this.x
      .isSelected() && index1.getType() == IndexType.PARTITION) || (this.y
      .isSelected() && index1.getType() == IndexType.SORT) || (this.z
      .isSelected() && index1.getType() == IndexType.CLUSTER) || (this.A
      .isSelected() && index1.getType() == IndexType.INDEX1) || (this.B
      .isSelected() && index1.getType() == IndexType.INDEX2))) {
      showError(this.rx.H("columnAlreadyInIndexError") + "\n" + this.rx.H("columnAlreadyInIndexError"));
      return false;
    } 
    Index index2 = this.c.getPrimaryKeyOrUniqueIndex();
    if (this.t.isSelected() && index2 != null && index2 != this.d && index2.getType() == IndexType.PRIMARY_KEY) {
      showError("tableHasPrimaryKeyError");
      return false;
    } 
    String str = this.p.getText();
    if (str != null)
      str = str.trim(); 
    if (StringUtil.isEmptyTrim(str)) {
      showError("setNameError");
      this.p.requestFocus();
      return false;
    } 
    for (Table table : (this.c.getSchema()).tables) {
      Index index = (Index)table.getIndexes().getByName(str);
      if (index != null && index != this.d) {
        showError("duplicateNameError");
        this.p.requestFocus();
        return false;
      } 
    } 
    return true;
  }
  
  public boolean a(String paramString) {
    String str = (Dbms.get(this.e)).duplicateIndexesOptions.c_();
    if (paramString != null && str != null)
      for (String str1 : str.split(",")) {
        if (paramString.toLowerCase().contains(str1.trim().toLowerCase()))
          return false; 
      }  
    return true;
  }
  
  public void applyChanges() {
    String str1 = this.p.getText().trim();
    if (this.t.isSelected())
      for (Column column : this.j.getItems()) {
        if (!column.isMandatory() && !column.hasDefaultValue() && 
          this.rx.a(getDialogPane().getScene(), String.format(this.rx.H("primaryKeyMandatoryColumns"), new Object[] { column }))) {
          if (this.doImplement) {
            Column column1 = new Column(this.c, column.getName());
            column1.cloneFrom(column);
            column1.setMandatory(true);
            addSyncOperation(this.c, column, column1);
            continue;
          } 
          column.setMandatory(true);
        } 
      }  
    Index index = (this.doImplement && !this.C.isSelected()) ? new Index(this.c, str1) : ((this.d != null) ? this.d : this.c.createIndex(str1));
    index.rename(str1);
    index.clearAttributes();
    String str2 = this.m.b();
    if (str2 != null && !str2.isEmpty())
      index.setComment(str2); 
    index.setCommentTags(this.m.a());
    for (Column column : this.j.getItems()) {
      index.addColumn(column);
      index.setColumnOptions(column, (String)this.f.get(column));
    } 
    index.setType(e());
    index.setOptions(this.n.a());
    index.setSpecificationOptions(this.o.a());
    index.setVirtual((this.C.isSelected() && !this.C.isDisable()));
    addSyncOperation(this.c, this.d, index);
  }
  
  private IndexType e() {
    if (this.x.isSelected())
      return IndexType.PARTITION; 
    if (this.y.isSelected())
      return IndexType.SORT; 
    if (this.z.isSelected())
      return IndexType.CLUSTER; 
    if (this.A.isSelected())
      return IndexType.INDEX1; 
    if (this.B.isSelected())
      return IndexType.INDEX2; 
    if (this.t.isSelected())
      return IndexType.PRIMARY_KEY; 
    if (this.u.isSelected())
      return IndexType.UNIQUE_KEY; 
    if (this.v.isSelected())
      return IndexType.UNIQUE_INDEX; 
    return IndexType.NORMAL;
  }
  
  public void saveSucceeded() {
    Index index = (Index)this.c.indexes.getByName(this.p.getText());
    if (index != null) {
      index.setSpecificationOptions(this.o.a());
      index.setOptions(this.n.a());
      index.setVirtual((this.C.isSelected() && !this.C.isDisable()));
      for (Column column1 : this.f.keySet()) {
        Column column2 = (Column)index.columns.getByName(column1.getName());
        index.setColumnOptions(column2, (String)this.f.get(column1));
      } 
      this.m.a(index);
    } 
    this.b.y();
  }
  
  public AbstractUnit a() {
    return this.d;
  }
  
  @Action
  public void addColumn() {
    ArrayList arrayList = new ArrayList();
    a(this.c, arrayList);
    if (arrayList.isEmpty()) {
      this.rx.a(getDialogPane().getScene(), "noColumnsFound", new String[0]);
    } else {
      (new r(this, getDialogScene().getWindow(), arrayList)).showDialog().ifPresent(paramColumn -> this.j.getItems().add(paramColumn));
    } 
  }
  
  @Action
  public void addFunctionalColumn() {
    String str = this.rx.b(getDialogScene(), "addFunctionalColumn");
    if (StringUtil.isFilledTrim(str)) {
      Column column = (Column)this.c.columns.getByName(str);
      if (column == null) {
        column = this.c.createColumn(str, DbmsTypes.get(this.c.schema.project.getDbId()).getDataType(12));
        column.setSpec(AttributeSpec.functional);
      } 
      this.j.getItems().add(column);
    } 
  }
  
  private void a(Entity paramEntity, ArrayList<Column> paramArrayList) {
    for (Attribute attribute : paramEntity.getAttributes()) {
      if (!this.j.getItems().contains(attribute))
        paramArrayList.add((Column)attribute); 
      if (attribute instanceof Column && ((Column)attribute).hasChildEntity())
        a(((Column)attribute).getChildEntity(), paramArrayList); 
    } 
  }
  
  @Action(b = "flagCanRemove")
  public void removeColumn() {
    if (this.rx.a(getDialogPane().getScene(), "Remove selected columns ?"))
      this.j.getItems().removeAll((Collection)this.j.getSelectionModel().getSelectedItems()); 
  }
  
  @Action(b = "flagCanUp")
  public void upColumn() {
    int i = this.j.getSelectionModel().getSelectedIndex();
    if (i > 0) {
      Column column = (Column)this.j.getItems().remove(i);
      this.j.getItems().add(i - 1, column);
      this.j.getSelectionModel().select(i - 1);
    } 
  }
  
  @Action(b = "flagCanDown")
  public void downColumn() {
    int i = this.j.getSelectionModel().getSelectedIndex();
    if (i > -1 && i < this.j.getItems().size() - 1) {
      Column column = (Column)this.j.getItems().remove(i);
      this.j.getItems().add(i + 1, column);
      this.j.getSelectionModel().select(i + 1);
    } 
  }
  
  private boolean f() {
    return ((Dbms.get(this.e)).uniqueIndexCreate.j() && (Dbms.get(this.e)).uniqueKeyCreate.j());
  }
}
