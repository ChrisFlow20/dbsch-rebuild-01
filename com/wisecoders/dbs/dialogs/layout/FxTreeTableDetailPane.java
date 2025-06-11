package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.config.fx.FxSettingsDialog;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.schema.DataTypeFormat;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.VBox$;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;

public class FxTreeTableDetailPane extends Tab implements GenericColumnDetailPane {
  private final boolean b;
  
  private final Folder c;
  
  private final FxTableEditor d;
  
  protected final TreeTableView a = new TreeTableView();
  
  private final Rx e = new Rx(FxTreeTableDetailPane.class, this);
  
  private final FlowPane$ f = (new FlowPane$()).a();
  
  private final Button g;
  
  private final Depict h;
  
  private final FxTreeTableDetailPane$FxTreeItem i;
  
  FxTreeTableDetailPane(FxTableEditor paramFxTableEditor, Folder paramFolder, String paramString, boolean paramBoolean1, boolean paramBoolean2) {
    this(paramFxTableEditor, paramFolder, null, paramString, paramBoolean1, paramBoolean2);
  }
  
  FxTreeTableDetailPane(FxTableEditor paramFxTableEditor, Folder paramFolder, Depict paramDepict, String paramString, boolean paramBoolean1, boolean paramBoolean2) {
    this.d = paramFxTableEditor;
    this.c = paramFolder;
    this.b = paramBoolean2;
    this.h = paramDepict;
    this.a.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    this.a.setShowRoot(false);
    this.a.setRoot(this.i = new FxTreeTableDetailPane$FxTreeItem(paramFolder));
    a();
    this.a.setOnMouseClicked(paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() == 2)
            editItem(); 
        });
    this.e.G(paramFxTableEditor.a);
    TreeTableColumn treeTableColumn1 = new TreeTableColumn("Name");
    treeTableColumn1.setGraphic(Rx.a());
    treeTableColumn1.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue().getValue()));
    treeTableColumn1.setCellFactory(paramTreeTableColumn -> new FxTreeTableDetailPane$1(this));
    TreeTableColumn treeTableColumn2 = new TreeTableColumn(paramString);
    treeTableColumn2.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(a((TreeUnit)paramCellDataFeatures.getValue().getValue())));
    TreeTableColumn treeTableColumn3 = new TreeTableColumn("Description");
    treeTableColumn3.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(((TreeUnit)paramCellDataFeatures.getValue().getValue()).getComment()));
    if (paramDepict == null) {
      this.a.getColumns().addAll((Object[])new TreeTableColumn[] { treeTableColumn1, treeTableColumn2, treeTableColumn3 });
      Rx.b(this.a, new double[] { 0.4D, 0.3D, 0.3D });
    } else {
      this.a.getColumns().addAll((Object[])new TreeTableColumn[] { treeTableColumn1, c(), treeTableColumn2, treeTableColumn3 });
      Rx.b(this.a, new double[] { 0.33D, 0.07D, 0.3D, 0.3D });
    } 
    this.e.a("flagCanEdit", () -> (this.a.getSelectionModel().getSelectedIndex() > -1));
    this.e.a("flagCanUp", () -> (d() > 0));
    this.e.a("flagCanDown", () -> (e() > -1 && e() < paramFolder.size() - 1));
    this.e.a("flagCanAddChildren", () -> {
          TreeItem treeItem = (TreeItem)this.a.getSelectionModel().getSelectedItem();
          return (treeItem != null && treeItem.getValue() instanceof Column && ((Column)treeItem.getValue()).hasChildEntity());
        });
    this.f.getChildren().addAll((Object[])new Node[] { (Node)this.e.j("editItem") });
    this.f.getChildren().addAll((Object[])new Node[] { (Node)(this.g = this.e.j("addItem")) });
    this.f.getChildren().addAll((Object[])new Node[] { (Node)this.e.j("addChildrenItem") });
    this.f.getChildren().addAll((Object[])new Node[] { (Node)this.e.j("dropItem") });
    if (paramBoolean1) {
      this.f.getChildren().addAll((Object[])new Node[] { (Node)this.e.j("upItem") });
      this.f.getChildren().addAll((Object[])new Node[] { (Node)this.e.j("downItem") });
    } 
    if (paramDepict != null) {
      SplitMenuButton splitMenuButton = this.e.g("visibleColumns", false);
      splitMenuButton.getItems().addAll(this.e.e(new String[] { "showAll", "hideAll", "separator", "settings" }));
      this.f.getChildren().add(splitMenuButton);
    } 
    this.a.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramTreeItem1, paramTreeItem2) -> this.e.b());
    VBox$ vBox$ = (new VBox$()).l().i();
    VBox$.setVgrow((Node)this.a, Priority.ALWAYS);
    vBox$.getChildren().addAll((Object[])new Node[] { (Node)this.a, (Node)this.f });
    setContent((Node)vBox$);
  }
  
  public Node b() {
    return (Node)this.a;
  }
  
  public void a(MenuItem paramMenuItem) {
    SplitMenuButton splitMenuButton = this.e.g("addItem", true);
    splitMenuButton.getItems().add(paramMenuItem);
    int i = this.f.getChildren().indexOf(this.g);
    this.f.getChildren().remove(this.g);
    this.f.getChildren().add(i, splitMenuButton);
  }
  
  private TreeTableColumn c() {
    TreeTableColumn treeTableColumn = new TreeTableColumn("Visible");
    treeTableColumn.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue().getValue()));
    treeTableColumn.setCellFactory(paramTreeTableColumn -> new FxTreeTableDetailPane$2(this));
    return treeTableColumn;
  }
  
  private boolean a(Attribute paramAttribute) {
    return (!paramAttribute.hasMarker(1) && !paramAttribute.hasMarker(32) && !paramAttribute.hasMarker(64));
  }
  
  public void a() {
    this.i.b();
    this.a.refresh();
  }
  
  @Action
  public void addItem() {
    Folder folder = this.c;
    FxTreeTableDetailPane$FxTreeItem fxTreeTableDetailPane$FxTreeItem = (FxTreeTableDetailPane$FxTreeItem)this.a.getSelectionModel().getSelectedItem();
    if (fxTreeTableDetailPane$FxTreeItem != null) {
      Object object = fxTreeTableDetailPane$FxTreeItem.getValue();
      if (object instanceof Column) {
        Column column = (Column)object;
        folder = (Folder)column.getParentEntity().getAttributes();
      } 
    } 
    FxUnitEditor fxUnitEditor = FxEditorFactory.a(this.d, folder, this.b);
    a();
    if (fxUnitEditor.getUnit() != null)
      ((FxTreeTableDetailPane$FxTreeItem)this.a.getRoot()).a(this.a, (AbstractUnit)fxUnitEditor.getUnit()); 
  }
  
  @Action(b = "flagCanAddChildren")
  public void addChildrenItem() {
    Folder folder = this.c;
    FxTreeTableDetailPane$FxTreeItem fxTreeTableDetailPane$FxTreeItem = (FxTreeTableDetailPane$FxTreeItem)this.a.getSelectionModel().getSelectedItem();
    if (fxTreeTableDetailPane$FxTreeItem != null) {
      Object object = fxTreeTableDetailPane$FxTreeItem.getValue();
      if (object instanceof Column) {
        Column column = (Column)object;
        folder = (Folder)column.getChildEntity().getAttributes();
      } 
    } 
    FxEditorFactory.a(this.d, folder, this.b);
    a();
  }
  
  @Action(b = "flagCanEdit")
  public void dropItem() {
    if (this.e.a(this.d.getDialogPane().getScene(), "Delete selected item(s) ?")) {
      FxTreeTableDetailPane$3 fxTreeTableDetailPane$3 = new FxTreeTableDetailPane$3(this);
      this.e.a(fxTreeTableDetailPane$3);
    } 
  }
  
  @Action(b = "flagCanEdit")
  public void editItem() {
    try {
      FxTreeTableDetailPane$FxTreeItem fxTreeTableDetailPane$FxTreeItem = (FxTreeTableDetailPane$FxTreeItem)this.a.getSelectionModel().getSelectedItem();
      if (fxTreeTableDetailPane$FxTreeItem != null)
        FxEditorFactory.a(this.d, (Unit)fxTreeTableDetailPane$FxTreeItem.getValue(), (this.b && !this.d.a())); 
    } catch (Exception exception) {
      exception.printStackTrace();
      Log.b(exception);
    } 
    this.a.refresh();
    this.e.b();
    a();
  }
  
  @Action(b = "flagCanUp")
  public void upItem() {
    ArrayList<TreeUnit> arrayList = new ArrayList();
    for (TreeItem treeItem : new ArrayList((Collection<?>)this.a.getSelectionModel().getSelectedItems())) {
      TreeUnit treeUnit = (TreeUnit)treeItem.getValue();
      if (treeUnit instanceof Column) {
        if (treeUnit.getParent() instanceof Column) {
          (((Column)treeUnit.getParent()).getChildEntity()).columns.moveUp((Column)treeUnit);
        } else if (treeUnit.getParent() instanceof Folder) {
          ((Folder)treeUnit.getParent()).moveUp((Column)treeUnit);
        } 
        arrayList.add(treeUnit);
      } 
    } 
    ((FxTreeTableDetailPane$FxTreeItem)this.a.getRoot()).b();
    this.a.getSelectionModel().clearSelection();
    int i = Integer.MAX_VALUE;
    for (TreeItem treeItem : a(arrayList)) {
      this.a.getSelectionModel().select(treeItem);
      i = Math.min(i, this.a.getRow(treeItem));
    } 
    this.e.b();
  }
  
  @Action(b = "flagCanDown")
  public void downItem() {
    ArrayList<TreeUnit> arrayList = new ArrayList();
    for (TreeItem treeItem : new ArrayList((Collection<?>)this.a.getSelectionModel().getSelectedItems())) {
      TreeUnit treeUnit = (TreeUnit)treeItem.getValue();
      if (treeUnit instanceof Column) {
        if (treeUnit.getParent() instanceof Column) {
          (((Column)treeUnit.getParent()).getChildEntity()).columns.moveDown((Column)treeUnit);
        } else if (treeUnit.getParent() instanceof Folder) {
          ((Folder)treeUnit.getParent()).moveDown((Column)treeUnit);
        } 
        arrayList.add(treeUnit);
      } 
    } 
    ((FxTreeTableDetailPane$FxTreeItem)this.a.getRoot()).b();
    this.a.getSelectionModel().clearSelection();
    int i = Integer.MAX_VALUE;
    for (TreeItem treeItem : a(arrayList)) {
      this.a.getSelectionModel().select(treeItem);
      i = Math.min(i, this.a.getRow(treeItem));
    } 
    this.e.b();
  }
  
  public List a(List paramList) {
    ArrayList arrayList = new ArrayList();
    a(paramList, this.a.getRoot(), arrayList);
    return arrayList;
  }
  
  public void a(List paramList1, TreeItem paramTreeItem, List<TreeItem> paramList2) {
    if (paramList1.contains(paramTreeItem.getValue()))
      paramList2.add(paramTreeItem); 
    for (TreeItem treeItem : paramTreeItem.getChildren())
      a(paramList1, treeItem, paramList2); 
  }
  
  @Action
  public void showAll() {
    this.h.getHiddenAttributes().clear();
    this.a.refresh();
  }
  
  @Action
  public void hideAll() {
    for (E e : this.c) {
      Attribute attribute = (Attribute)e;
      if (a(attribute))
        this.h.setAttributeVisible(attribute, false); 
    } 
    this.a.refresh();
  }
  
  @Action
  public void settings() {
    (new FxSettingsDialog(this.d.b)).showDialog();
  }
  
  private int d() {
    int i = Integer.MAX_VALUE;
    for (Iterator<Integer> iterator = this.a.getSelectionModel().getSelectedIndices().iterator(); iterator.hasNext(); ) {
      int j = ((Integer)iterator.next()).intValue();
      i = Math.min(i, j);
    } 
    return i;
  }
  
  private int e() {
    int i = -1;
    for (Iterator<Integer> iterator = this.a.getSelectionModel().getSelectedIndices().iterator(); iterator.hasNext(); ) {
      int j = ((Integer)iterator.next()).intValue();
      i = Math.max(i, j);
    } 
    return i;
  }
  
  private String a(TreeUnit paramTreeUnit) {
    if (paramTreeUnit instanceof Column) {
      Column column = (Column)paramTreeUnit;
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
    if (paramTreeUnit instanceof Index) {
      Index index = (Index)paramTreeUnit;
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
    if (paramTreeUnit instanceof ForeignKey)
      return ((ForeignKey)paramTreeUnit).getTargetEntity().getNameWithSchemaName(); 
    if (paramTreeUnit instanceof Constraint)
      return ((Constraint)paramTreeUnit).getText(); 
    return null;
  }
}
