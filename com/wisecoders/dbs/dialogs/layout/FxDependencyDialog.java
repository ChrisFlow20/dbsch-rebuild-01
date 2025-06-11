package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.dialogs.DependencyCycles;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.Region;
import javafx.stage.Modality;

public class FxDependencyDialog extends ButtonDialog$ {
  private final Workspace a;
  
  private final TreeTableView b = new TreeTableView();
  
  private final CheckBox c;
  
  private final CheckBox d;
  
  private final TextField e = new TextField();
  
  private final DependencyCycles f = new DependencyCycles();
  
  private final List i = new ArrayList();
  
  private final boolean j;
  
  public FxDependencyDialog(Workspace paramWorkspace, DependencyNode paramDependencyNode, boolean paramBoolean) {
    super(paramWorkspace);
    this.a = paramWorkspace;
    initModality(Modality.NONE);
    this.j = paramBoolean;
    this.rx.a("flagHasSelection", () -> !this.i.isEmpty());
    this.c = this.rx.h("checkCurrentLayout", false);
    this.d = this.rx.h("checkCyclic", true);
    this.e.setPromptText("Filter");
    this.e.setPrefColumnCount(40);
    this.e.textProperty().addListener(paramObservable -> b());
    this.d.setOnAction(paramActionEvent -> b());
    this.c.setOnAction(paramActionEvent -> {
          this.b.setRoot(new e(this, a()));
          b();
        });
    a(a());
    this.rx.b();
  }
  
  private DependencyNode a() {
    DependencyNode dependencyNode = this.c.isSelected() ? new DependencyNode(this.a.p()) : new DependencyNode(this.a.m());
    this.f.a(dependencyNode.b);
    return dependencyNode;
  }
  
  private void b() {
    ((e)this.b.getRoot()).a();
  }
  
  private void a(DependencyNode paramDependencyNode) {
    this.b.setRoot(new e(this, paramDependencyNode));
    b();
    TreeTableColumn treeTableColumn1 = new TreeTableColumn("Schemas & Tables");
    treeTableColumn1.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue().getValue()));
    treeTableColumn1.setCellFactory(paramTreeTableColumn -> new f(this));
    TreeTableColumn treeTableColumn2 = new TreeTableColumn("Foreign Key");
    treeTableColumn2.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(((DependencyNode)paramCellDataFeatures.getValue().getValue()).b()));
    this.b.getColumns().addAll((Object[])new TreeTableColumn[] { treeTableColumn1, treeTableColumn2 });
    this.b.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramTreeItem1, paramTreeItem2) -> a((e)paramTreeItem2));
    this.b.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
  }
  
  private void a(e parame) {
    this.i.clear();
    if (parame != null) {
      e e1 = parame;
      TreeItem treeItem;
      do {
        if (!(e1.getValue() instanceof AbstractTable))
          continue; 
        this.i.add((AbstractTable)e1.getValue());
      } while ((treeItem = e1.getParent()) != null);
      if (parame.getValue() instanceof Entity && this.a.B())
        (this.a.o()).c.c((Entity)parame.getValue()); 
    } 
    this.rx.b();
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).l().a(new int[] { -1 }).b(new int[] { -2, -2, -2, -1 });
    gridPane$.a((Node)this.e, "0,0,l,c");
    gridPane$.a((Node)this.d, "0,1,l,c");
    gridPane$.a((Node)this.c, "0,2,l,c");
    gridPane$.a(this.rx.a((Node)this.b, "Dependencies"), "0,3,f,f");
    setRegionPrefSize((Region)gridPane$, 800.0D, 650.0D);
    setInitFocusedNode((Node)this.b);
    return (Node)gridPane$;
  }
  
  public void createButtons() {
    if (this.j)
      createOkButton(); 
    createCancelButton();
  }
  
  @Action(b = "flagHasSelection")
  public void select() {
    hide();
  }
  
  public boolean apply() {
    return true;
  }
}
