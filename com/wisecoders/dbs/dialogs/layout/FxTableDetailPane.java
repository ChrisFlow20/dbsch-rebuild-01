package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.config.fx.FxSettingsDialog;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.schema.DataTypeFormat;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.ReversedList;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.FxUtil;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.VBox$;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;

public class FxTableDetailPane extends Tab implements GenericColumnDetailPane {
  public static final String a = "TableDialogShowRowNum";
  
  private final boolean c;
  
  private final Folder d;
  
  private final FxTableEditor e;
  
  protected final TableView b = new TableView();
  
  private final Rx f = new Rx(FxTableDetailPane.class, this);
  
  private final FlowPane$ g = (new FlowPane$()).a();
  
  private final Button h;
  
  private final Depict i;
  
  private final TextField j = new TextField();
  
  private final ObservableList k = FXCollections.observableArrayList();
  
  private final TableColumn l;
  
  FxTableDetailPane(FxTableEditor paramFxTableEditor, Folder paramFolder, String paramString, boolean paramBoolean1, boolean paramBoolean2) {
    this(paramFxTableEditor, paramFolder, null, paramString, paramBoolean1, paramBoolean2);
  }
  
  FxTableDetailPane(FxTableEditor paramFxTableEditor, Folder paramFolder, Depict paramDepict, String paramString, boolean paramBoolean1, boolean paramBoolean2) {
    this.e = paramFxTableEditor;
    this.d = paramFolder;
    this.c = paramBoolean2;
    this.i = paramDepict;
    this.b.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    this.b.setItems(this.k);
    a();
    this.b.setOnMouseClicked(paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() == 2)
            editItem(); 
        });
    this.f.G(paramFxTableEditor.a);
    this.l = new TableColumn("#");
    this.l.getStyleClass().addAll((Object[])new String[] { "column-header", "text-gray" });
    this.l.setStyle("-fx-alignment: center; -fx-padding:0;");
    this.l.setCellFactory(paramTableColumn -> new FxTableDetailPane$1(this));
    this.l.setPrefWidth(40.0D);
    TableColumn tableColumn1 = new TableColumn("Name");
    tableColumn1.setGraphic(Rx.a());
    tableColumn1.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue()));
    tableColumn1.setCellFactory(paramTableColumn -> new FxTableDetailPane$2(this));
    TableColumn tableColumn2 = new TableColumn(paramString);
    tableColumn2.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(a((AbstractUnit)paramCellDataFeatures.getValue())));
    TableColumn tableColumn3 = new TableColumn("Description");
    tableColumn3.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(((AbstractUnit)paramCellDataFeatures.getValue()).getComment()));
    this.b.getColumns().addAll((Object[])new TableColumn[] { tableColumn1, tableColumn2, tableColumn3 });
    if (paramDepict == null) {
      Rx.a(this.b, new double[] { 0.4D, 0.3D, 0.3D });
    } else {
      c();
      Rx.a(this.b, new double[] { 0.07D, 0.33D, 0.3D, 0.3D });
    } 
    this.f.a("flagCanEdit", () -> (this.b.getSelectionModel().getSelectedIndex() > -1));
    this.f.a("flagCanUp", () -> (d() > 0));
    this.f.a("flagCanDown", () -> (e() > -1 && e() < paramFolder.size() - 1));
    this.f.a("flagFilterFieldVisible", () -> !this.j.isVisible());
    this.f.a("flagRowNumVisible", () -> this.b.getColumns().contains(this.l));
    this.g.getChildren().addAll((Object[])new Node[] { (Node)this.f.j("editItem"), (Node)(this.h = this.f.j("addItem")) });
    if (paramBoolean1)
      this.g.getChildren().addAll(this.f.c(new String[] { "upItem", "downItem" })); 
    this.g.getChildren().addAll((Object[])new Node[] { (Node)this.f.j("itemStatement"), (Node)this.f.n("showFilterField") });
    if (Pref.b("TableDialogShowRowNum", false))
      showRowNum(); 
    if (paramDepict == null) {
      CheckBox checkBox = this.f.v("showRowNum");
      checkBox.selectedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> showRowNum());
      this.g.getChildren().add(checkBox);
    } else {
      SplitMenuButton splitMenuButton = this.f.g("visibleColumns", false);
      splitMenuButton.getItems().addAll(this.f.e(new String[] { "showAll", "hideAll" }));
      splitMenuButton.getItems().add(this.f.y("showRowNum"));
      splitMenuButton.getItems().addAll(this.f.e(new String[] { "separator", "settings" }));
      this.g.getChildren().add(splitMenuButton);
    } 
    this.g.getChildren().addAll((Object[])new Node[] { (Node)this.f.j("dropItem") });
    this.b.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramAbstractUnit1, paramAbstractUnit2) -> this.f.b());
    this.b.getStyleClass().add("font-larger");
    VBox$ vBox$ = (new VBox$()).b(3).c(3);
    Dialog$.setManagedVisible(new Node[] { (Node)this.j });
    this.j.setVisible(false);
    this.j.setMinWidth(10.0D);
    this.j.setPrefWidth(10.0D);
    HBox$.setHgrow((Node)this.j, Priority.ALWAYS);
    this.j.getStyleClass().add("search-field");
    this.j.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> a());
    this.b.addEventFilter(KeyEvent.KEY_PRESSED, paramKeyEvent -> {
          if (!paramKeyEvent.isShiftDown() && paramKeyEvent.getCode() == KeyCode.ENTER) {
            editItem();
            paramKeyEvent.consume();
          } 
        });
    VBox$.setVgrow((Node)this.b, Priority.ALWAYS);
    vBox$.getChildren().addAll((Object[])new Node[] { (Node)this.j, (Node)this.b, (Node)this.g });
    setContent((Node)vBox$);
  }
  
  public void a(MenuItem paramMenuItem) {
    SplitMenuButton splitMenuButton = this.f.g("addItem", true);
    splitMenuButton.getItems().add(this.f.A("addItem"));
    splitMenuButton.getItems().addAll((Object[])new MenuItem[] { paramMenuItem });
    int i = this.g.getChildren().indexOf(this.h);
    this.g.getChildren().remove(this.h);
    this.g.getChildren().add(i, splitMenuButton);
  }
  
  private void c() {
    TableColumn tableColumn = new TableColumn("Visible");
    tableColumn.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue()));
    tableColumn.setCellFactory(paramTableColumn -> new FxTableDetailPane$3(this));
    this.b.getColumns().add(0, tableColumn);
  }
  
  @Action(d = "flagRowNumVisible")
  public void showRowNum() {
    if (this.b.getColumns().contains(this.l)) {
      this.b.getColumns().remove(this.l);
    } else {
      this.b.getColumns().add(0, this.l);
    } 
    Pref.a("TableDialogShowRowNum", this.b.getColumns().contains(this.l));
  }
  
  private boolean a(Attribute paramAttribute) {
    return !paramAttribute.hasMarker(1);
  }
  
  public void a() {
    ArrayList arrayList = new ArrayList(this.d);
    String str = (this.j.getText() != null) ? this.j.getText().toLowerCase() : null;
    if (StringUtil.isFilled(str))
      arrayList.removeIf(paramAbstractUnit -> !a(paramAbstractUnit, paramString)); 
    arrayList.removeIf(paramAbstractUnit -> {
          if (paramAbstractUnit instanceof Column) {
            Column column = (Column)paramAbstractUnit;
            if (column.getSpec() == AttributeSpec.functional);
          } 
          return false;
        });
    this.k.retainAll(arrayList);
    for (AbstractUnit abstractUnit : arrayList) {
      if (!this.k.contains(abstractUnit))
        this.k.add(abstractUnit); 
    } 
  }
  
  private boolean a(AbstractUnit paramAbstractUnit, String paramString) {
    if (StringUtil.isFilled(paramString))
      return (paramAbstractUnit.getName().toLowerCase().contains(paramString) || (paramAbstractUnit
        .getComment() != null && paramAbstractUnit.getComment().contains(paramString))); 
    return true;
  }
  
  @Action(d = "flagFilterFieldVisible")
  public void showFilterField() {
    this.j.setVisible(!this.j.isVisible());
  }
  
  @Action
  public void addItem() {
    FxUnitEditor fxUnitEditor = FxEditorFactory.a(this.e, this.d, this.c);
    a();
    AbstractUnit abstractUnit = (AbstractUnit)fxUnitEditor.getUnit();
    if (abstractUnit != null && this.b.getItems().contains(abstractUnit)) {
      this.b.getSelectionModel().clearSelection();
      this.b.getSelectionModel().select(abstractUnit);
      this.b.scrollTo(abstractUnit);
    } 
  }
  
  @Action(b = "flagCanEdit")
  public void dropItem() {
    if (this.f.a(this.e.getDialogPane().getScene(), "Delete selected item(s) ?")) {
      FxTableDetailPane$4 fxTableDetailPane$4 = new FxTableDetailPane$4(this);
      this.f.a(fxTableDetailPane$4);
    } 
  }
  
  @Action(b = "flagCanEdit")
  public void editItem() {
    try {
      AbstractUnit abstractUnit = (AbstractUnit)this.b.getSelectionModel().getSelectedItem();
      int i = this.b.getSelectionModel().getSelectedIndex();
      if (abstractUnit != null) {
        FxEditorFactory.a(this.e, abstractUnit, (this.c && !this.e.a()));
        this.b.refresh();
        if (i > -1 && i < this.b.getItems().size() - 1)
          this.b.getSelectionModel().clearAndSelect(i + 1); 
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
      Log.b(exception);
    } 
    a();
  }
  
  @Action(b = "flagCanUp")
  public void upItem() {
    ArrayList arrayList = new ArrayList((Collection<?>)this.b.getSelectionModel().getSelectedItems());
    for (AbstractUnit abstractUnit : arrayList) {
      FxUtil.a((List)this.b.getItems(), this.b.getItems().indexOf(abstractUnit));
      this.d.moveUp(this.d.indexOf(abstractUnit));
      int i = this.d.indexOf(abstractUnit);
      a((i > 0) ? (AbstractUnit)this.d.get(i - 1) : null, abstractUnit);
    } 
    this.b.getSelectionModel().clearSelection();
    for (AbstractUnit abstractUnit : arrayList)
      this.b.getSelectionModel().select(abstractUnit); 
    if (arrayList.size() > 0) {
      int i = this.b.getItems().indexOf(arrayList.get(0));
      this.b.scrollTo(Math.max(0, i - 2));
    } 
    this.f.b();
  }
  
  public void a(AbstractUnit paramAbstractUnit1, AbstractUnit paramAbstractUnit2) {}
  
  @Action(b = "flagCanDown")
  public void downItem() {
    ArrayList<AbstractUnit> arrayList = new ArrayList((Collection<?>)this.b.getSelectionModel().getSelectedItems());
    for (AbstractUnit abstractUnit : ReversedList.a(arrayList)) {
      FxUtil.b((List)this.b.getItems(), this.b.getItems().indexOf(abstractUnit));
      this.d.moveDown(this.d.indexOf(abstractUnit));
      int i = this.d.indexOf(abstractUnit);
      a((i > 0) ? (AbstractUnit)this.d.get(i - 1) : null, abstractUnit);
    } 
    this.b.getSelectionModel().clearSelection();
    for (AbstractUnit abstractUnit : arrayList)
      this.b.getSelectionModel().select(abstractUnit); 
    if (arrayList.size() > 0)
      this.b.scrollTo(arrayList.get(0)); 
    this.f.b();
  }
  
  @Action(b = "flagCanEdit")
  public void itemStatement() {
    SyncPair syncPair = new SyncPair(null, (AbstractUnit)this.b.getSelectionModel().getSelectedItem());
    String str = syncPair.generateCommitScript(this.e.a, null, SyncSide.right).toString();
    (new FxStatementDialog(this.e, str)).showDialog();
  }
  
  @Action
  public void showAll() {
    this.i.getHiddenAttributes().clear();
    this.b.refresh();
    this.e.setRefreshDiagram();
  }
  
  @Action
  public void hideAll() {
    for (AbstractUnit abstractUnit : this.d) {
      Attribute attribute = (Attribute)abstractUnit;
      if (a(attribute))
        this.i.setAttributeVisible(attribute, false); 
    } 
    this.b.refresh();
    this.e.setRefreshDiagram();
  }
  
  @Action
  public void settings() {
    (new FxSettingsDialog(this.e.b)).showDialog();
  }
  
  private int d() {
    int i = Integer.MAX_VALUE;
    for (Iterator<Integer> iterator = this.b.getSelectionModel().getSelectedIndices().iterator(); iterator.hasNext(); ) {
      int j = ((Integer)iterator.next()).intValue();
      i = Math.min(i, j);
    } 
    return i;
  }
  
  private int e() {
    int i = -1;
    for (Iterator<Integer> iterator = this.b.getSelectionModel().getSelectedIndices().iterator(); iterator.hasNext(); ) {
      int j = ((Integer)iterator.next()).intValue();
      i = Math.max(i, j);
    } 
    return i;
  }
  
  public static String a(AbstractUnit paramAbstractUnit) {
    if (paramAbstractUnit instanceof Column) {
      Column column = (Column)paramAbstractUnit;
      StringBuilder stringBuilder = new StringBuilder();
      if (column.getDataType() != null)
        stringBuilder.append(column.getTypeString(DataTypeFormat.b)).append(' '); 
      if (column.isMandatory())
        stringBuilder.append("not null "); 
      if (column.isIdentity())
        stringBuilder.append(column.getIdentity()).append(" "); 
      if (column.isUnsigned())
        stringBuilder.append("unsigned "); 
      if (column.getEnumeration() != null)
        stringBuilder.append("enumeration ").append(column.getEnumeration()); 
      if (column.getDefaultValue() != null)
        stringBuilder.append("default ").append(column.getDefaultValue()).append(" "); 
      if (column.getOptions() != null)
        stringBuilder.append(column.getOptions()); 
      return stringBuilder.toString();
    } 
    if (paramAbstractUnit instanceof Index) {
      Index index = (Index)paramAbstractUnit;
      StringBuilder stringBuilder = new StringBuilder();
      boolean bool = true;
      for (Column column : index.getColumns()) {
        if (!bool)
          stringBuilder.append(", "); 
        stringBuilder.append(column);
        bool = false;
      } 
      return stringBuilder.toString();
    } 
    if (paramAbstractUnit instanceof ForeignKey) {
      ForeignKey foreignKey = (ForeignKey)paramAbstractUnit;
      return foreignKey.getTargetEntity().getNameWithSchemaName();
    } 
    if (paramAbstractUnit instanceof Constraint) {
      Constraint constraint = (Constraint)paramAbstractUnit;
      return constraint.getText();
    } 
    return null;
  }
  
  public Node b() {
    return (Node)this.b;
  }
}
