package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.VBox$;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

public class FxHierarchicalSelectionDialog extends Dialog$ {
  public final TreeItem a = new TreeItem();
  
  private final Workspace b;
  
  private final TextField c;
  
  private final ObservableList d = FXCollections.observableArrayList();
  
  private final ListView e = new ListView();
  
  private final TreeView f = new TreeView();
  
  public FxHierarchicalSelectionDialog(Workspace paramWorkspace) {
    super(paramWorkspace);
    this.b = paramWorkspace;
    this.c = this.rx.t("filterTextField");
    this.rx.a("flagCanAdd", () -> (this.e.getSelectionModel().getSelectedItem() != null));
    this.rx.a("flagCanRemove", () -> (this.f.getSelectionModel().getSelectedItem() != null));
    this.e.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramFxHierarchyNode1, paramFxHierarchyNode2) -> this.rx.b());
    this.f.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramTreeItem1, paramTreeItem2) -> {
          a();
          this.rx.b();
        });
    this.f.setRoot(this.a);
    this.f.setShowRoot(false);
    this.rx.b();
    a();
    this.e.setOnMouseClicked(paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() == 2)
            addToSelection(); 
        });
    this.f.setOnMouseClicked(paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() == 2)
            removeFromSelection(); 
        });
    setInitFocusedNode((Node)this.e);
    setResultConverter(paramButtonType -> 
        (paramButtonType != null && paramButtonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) ? (List)getResult() : null);
  }
  
  private void a() {
    this.d.clear();
    this.c.setText("");
    TreeItem treeItem = (TreeItem)this.f.getSelectionModel().getSelectedItem();
    if (treeItem == null || treeItem.getValue() == null) {
      for (Schema schema : (this.b.m()).schemas) {
        for (Table table : schema.tables)
          this.d.add(new FxHierarchyNode(table, null)); 
        for (View view : schema.views)
          this.d.add(new FxHierarchyNode(view, null)); 
      } 
    } else {
      Entity entity = ((FxHierarchyNode)treeItem.getValue()).a;
      for (Relation relation : entity.getRelations())
        this.d.add(new FxHierarchyNode(relation.getTargetEntity(), relation)); 
      for (Relation relation : entity.getImportedRelations())
        this.d.add(new FxHierarchyNode(relation.getEntity(), relation)); 
    } 
  }
  
  public Node createContentPane() {
    FilteredList filteredList = new FilteredList(this.d, paramFxHierarchyNode -> true);
    this.c.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> paramFilteredList.setPredicate(()));
    SortedList sortedList = new SortedList((ObservableList)filteredList);
    sortedList.comparatorProperty().set(Comparator.comparing(FxHierarchyNode::toString));
    this.e.setItems((ObservableList)sortedList);
    VBox$ vBox$ = (new VBox$()).l();
    vBox$.setAlignment(Pos.CENTER);
    vBox$.getChildren().addAll(this.rx.c(new String[] { "addToSelection", "removeFromSelection" }));
    GridPane$ gridPane$ = (new GridPane$()).e().a(new int[] { -1, -2, -1 }).b(new int[] { -2, -2, -1 });
    gridPane$.a((Node)this.rx.h("availablePaneBorder"), "0,0,f,f");
    gridPane$.a((Node)this.c, "0,1,f,f");
    gridPane$.a((Node)this.e, "0,2,f,f");
    gridPane$.a((Node)vBox$, "1,2,c,c");
    setRegionPrefSize((Region)this.e, 400.0D, 500.0D);
    setRegionPrefSize((Region)this.f, 400.0D, 500.0D);
    gridPane$.a((Node)this.rx.h("selectionPaneBorder"), "2,0,f,f");
    gridPane$.a((Node)this.f, "2,1,2,2,f,f");
    b();
    return (Node)gridPane$;
  }
  
  private void b() {
    if (!this.b.E().isEmpty())
      for (FxHierarchyNode fxHierarchyNode : this.d) {
        if (this.b.E().contains(fxHierarchyNode.a())) {
          this.e.getSelectionModel().select(fxHierarchyNode);
          this.e.scrollTo(fxHierarchyNode);
        } 
      }  
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean apply() {
    ArrayList<Entity> arrayList = new ArrayList();
    byte b = 0;
    while (this.f.getTreeItem(b) != null) {
      FxHierarchyNode fxHierarchyNode = (FxHierarchyNode)this.f.getTreeItem(b).getValue();
      if (fxHierarchyNode != null)
        arrayList.add(fxHierarchyNode.a); 
      b++;
    } 
    setResult(arrayList);
    return true;
  }
  
  @Action(b = "flagCanAdd")
  public void addToSelection() {
    byte b = 0;
    TreeItem treeItem = null;
    while (this.f.getTreeItem(b) != null) {
      treeItem = this.f.getTreeItem(b);
      b++;
    } 
    if (this.f.getSelectionModel().getSelectedItem() == null)
      treeItem = (TreeItem)this.f.getSelectionModel().getSelectedItem(); 
    if (treeItem == null)
      treeItem = this.a; 
    ArrayList arrayList = new ArrayList((Collection<?>)this.e.getSelectionModel().getSelectedItems());
    for (FxHierarchyNode fxHierarchyNode : arrayList) {
      TreeItem treeItem1 = new TreeItem(fxHierarchyNode);
      treeItem1.setExpanded(true);
      treeItem.getChildren().add(treeItem1);
      this.f.getSelectionModel().select(treeItem1);
    } 
    this.rx.b();
  }
  
  @Action(b = "flagCanRemove")
  public void removeFromSelection() {
    TreeItem treeItem = (TreeItem)this.f.getSelectionModel().getSelectedItem();
    if (treeItem != null && treeItem.getParent() != null) {
      treeItem.getParent().getChildren().remove(treeItem);
      this.rx.b();
    } 
  }
}
