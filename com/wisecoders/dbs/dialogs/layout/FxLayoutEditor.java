package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.ArrangerMode;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Diagram;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.diagram.model.LineTextType;
import com.wisecoders.dbs.diagram.model.Point;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.FxSearchTextField;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.VBox$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.tips.fx.FxTipsDialog;
import com.wisecoders.dbs.tips.tips.LayoutTips;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

public class FxLayoutEditor extends ButtonDialog$ implements FxUnitEditor {
  private final Workspace a;
  
  private final Project b;
  
  private Layout c;
  
  private final TextField d = new TextField();
  
  private final ObservableList e = FXCollections.observableArrayList();
  
  private final ListView f = new ListView();
  
  private final ComboBox i = new ComboBox();
  
  private final ObservableList j = FXCollections.observableArrayList();
  
  private final ListView k = new ListView();
  
  private final CheckBox l;
  
  private final ComboBox m = new ComboBox();
  
  private final CheckBox n;
  
  private final CheckBox o;
  
  private final CheckBox p;
  
  private final FxCommentPanel q;
  
  public FxLayoutEditor(WorkspaceWindow paramWorkspaceWindow, Project paramProject, Layout paramLayout) {
    super(paramWorkspaceWindow.getWorkspace());
    this.a = paramWorkspaceWindow.getWorkspace();
    this.b = paramProject;
    this.c = paramLayout;
    this.rx.G(paramProject.getDbId());
    setHeaderText(this.rx.a("dialog.header", new String[0]));
    if (paramLayout == null)
      setTitle(this.rx.H("newLayout")); 
    this.q = new FxCommentPanel(paramWorkspaceWindow.getWorkspace(), paramLayout);
    this.l = this.rx.h("checkShowSchemaName", false);
    this.n = this.rx.h("checkShowDataType", false);
    this.o = this.rx.h("checkJoinedRouting", false);
    this.p = this.rx.h("checkShowPageBorders", false);
  }
  
  public Node createContentPane() {
    setGraphic(BootstrapIcons.diagram_2_fill);
    this.k.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    this.f.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    this.k.setCellFactory(paramListView -> new u());
    this.f.setCellFactory(paramListView -> new u());
    this.rx.a("flagSelectedUnused", () -> (this.k.getSelectionModel().getSelectedIndex() > -1));
    this.rx.a("flagSelectedUsed", () -> (this.f.getSelectionModel().getSelectedIndex() > -1));
    FxSearchTextField fxSearchTextField1 = new FxSearchTextField();
    FilteredList filteredList1 = new FilteredList(this.e, paramEntity -> true);
    fxSearchTextField1.a((paramObservableValue, paramString1, paramString2) -> paramFilteredList.setPredicate(()));
    SortedList sortedList1 = new SortedList((ObservableList)filteredList1);
    sortedList1.comparatorProperty().set(Comparator.comparing(Entity::getName));
    this.f.setItems((ObservableList)sortedList1);
    FxSearchTextField fxSearchTextField2 = new FxSearchTextField();
    FilteredList filteredList2 = new FilteredList(this.j, paramEntity -> true);
    fxSearchTextField2.a((paramObservableValue, paramString1, paramString2) -> paramFilteredList.setPredicate(()));
    SortedList sortedList2 = new SortedList((ObservableList)filteredList2);
    sortedList2.comparatorProperty().set(Comparator.comparing(Entity::getName));
    this.k.setItems((ObservableList)sortedList2);
    GridPane$ gridPane$1 = (new GridPane$()).e().a(new int[] { -2, -1 });
    gridPane$1.a((Node)this.rx.e("nameLabel"), "0,0,r,c");
    gridPane$1.a((Node)this.d, "1,0,f,c");
    gridPane$1.a((Node)this.rx.e("descriptionLabel"), "0,1,r,c");
    gridPane$1.a((Node)this.q, "1,1,f,f");
    VBox$ vBox$ = (new VBox$()).l();
    vBox$.getChildren().addAll(this.rx.c(new String[] { "addTable", "removeTable" }));
    vBox$.setAlignment(Pos.CENTER);
    GridPane$ gridPane$2 = (new GridPane$()).e().a(new int[] { -1, -2, -1 }).b(new int[] { -2, -2, -1, -2 });
    gridPane$2.a((Node)(new HBox$()).d().a(Pos.CENTER).a(new Node[] { (Node)new Label(this.rx
              .H("available") + " " + this.rx.H("available") + " in "), (Node)this.i }, ), "0,0,c,c");
    gridPane$2.a((Node)this.rx.e("currentLayout"), "2,0,c,c");
    gridPane$2.a((Node)fxSearchTextField2, "0,1,f,c");
    gridPane$2.a((Node)fxSearchTextField1, "2,1,f,c");
    gridPane$2.a((Node)this.k, "0,2,f,f");
    gridPane$2.a((Node)vBox$, "1,2,c,c");
    gridPane$2.a((Node)this.f, "2,2,f,f");
    SplitMenuButton splitMenuButton = this.rx.g("select", false);
    splitMenuButton.getItems().addAll(this.rx.e(new String[] { "selectAll", "selectUnlistedTables", "selectParentTables", "selectChildrenTables" }));
    gridPane$2.a((Node)splitMenuButton, "0,3,l,c");
    gridPane$2.a((Node)this.l, "0,4,l,c");
    gridPane$2.a((Node)this.n, "0,5,l,c");
    gridPane$2.a((Node)this.p, "2,3,l,c");
    gridPane$2.a((Node)this.o, "2,4,l,c");
    gridPane$2.a((Node)(new HBox$()).d().a(new Node[] { (Node)this.rx.e("fkLineLabel"), (Node)this.m }, ), "2,5,l,c");
    boolean bool = true;
    for (Schema schema : this.b.schemas) {
      this.i.getItems().add(schema);
      if (schema.toString().equals(Pref.a("LastSchema"))) {
        this.i.setValue(schema);
      } else if (bool) {
        this.i.setValue(schema);
      } 
      bool = false;
    } 
    this.m.getItems().addAll((Object[])LineTextType.values());
    this.m.setButtonCell(new t(this));
    this.m.setCellFactory(paramListView -> new t(this));
    if (this.c == null) {
      this.m.setValue(Diagram.getDefaultLineTextType());
      this.d.setText(this.b.layouts.proposeName("Layout"));
      this.d.selectAll();
      setInitFocusedNode((Node)this.d);
    } else {
      this.d.setText(this.c.getName());
      for (Depict depict : this.c.getDepicts()) {
        this.e.add(depict.getEntity());
        this.i.setValue(depict.getEntity().getSchema());
      } 
      this.n.setSelected(this.c.diagram.isShowDataType());
      this.o.setSelected(this.c.diagram.isJoinedRouting());
      this.p.setSelected(this.c.diagram.isShowPageBorders());
      this.l.setSelected(this.c.diagram.isShowSchemaName());
      this.m.setValue(this.c.diagram.getLineTextType());
    } 
    c();
    this.i.setOnAction(paramActionEvent -> c());
    for (Entity entity : this.a.E()) {
      int i;
      if ((i = this.j.indexOf(entity)) > -1)
        this.k.getSelectionModel().select(i); 
    } 
    this.k.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramEntity1, paramEntity2) -> this.rx.b());
    this.f.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramEntity1, paramEntity2) -> this.rx.b());
    this.k.setOnMouseClicked(paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() == 2)
            addTable(); 
        });
    this.f.setOnMouseClicked(paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() == 2)
            removeTable(); 
        });
    this.rx.b();
    GridPane$ gridPane$3 = (new GridPane$()).e().g();
    gridPane$3.a(new int[] { -1 });
    gridPane$3.b(new int[] { -2, -1 });
    gridPane$3.a((Node)gridPane$1, "0,0,f,f");
    gridPane$3.a((Node)gridPane$2, "0,1,f,f");
    setInitFocusedNode((Node)this.d);
    setRegionPrefSize((Region)gridPane$2, 900.0D, 450.0D);
    return (Node)gridPane$3;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
    createHelpButton("layouts.html");
  }
  
  private void c() {
    this.j.clear();
    if (this.i.getValue() != null) {
      Schema schema = (Schema)this.i.getValue();
      for (Entity entity : schema.getEntities()) {
        if (!this.e.contains(entity))
          this.j.add(entity); 
      } 
      Pref.a("LastSchema", String.valueOf(this.i.getValue()));
    } 
  }
  
  public AbstractUnit a() {
    return this.c;
  }
  
  public boolean b() {
    String str = this.d.getText();
    if (StringUtil.isEmptyTrim(str)) {
      showError("Please set the Layout name.");
      this.d.requestFocus();
      return false;
    } 
    if (this.b.layouts.isWrongName(str, this.c)) {
      showError("Please choose a different name.\n A layout with this name already exists.");
      this.d.requestFocus();
      return false;
    } 
    return true;
  }
  
  public boolean apply() {
    if (!b())
      return false; 
    String str = this.d.getText();
    boolean bool = (this.c == null || this.c.diagram.depicts.isEmpty()) ? true : false;
    if (this.c == null) {
      this.c = this.b.createLayout(str);
    } else {
      this.c.rename(str);
    } 
    for (Entity entity : this.j) {
      Depict depict = this.c.diagram.getDepictFor(entity);
      if (depict != null)
        this.c.diagram.detachUndoable(depict); 
    } 
    for (Depict depict : this.c.getDepicts()) {
      if (!this.e.contains(depict.entity))
        this.c.diagram.detachUndoable(depict); 
    } 
    for (Entity entity : this.e) {
      Point point = this.c.diagram.getFreePlacePoint();
      this.c.diagram.attachRecursiveAndCreateGroupForHierarchicalEntities(entity, point);
    } 
    this.c.diagram.setShowSchemaName(this.l.isSelected());
    this.c.diagram.setShowDataType(this.n.isSelected());
    this.c.diagram.setShowPageBorders(this.p.isSelected());
    this.c.setComment(this.q.b());
    this.c.setCommentTags(this.q.a());
    this.c.diagram.setLineTextType((LineTextType)this.m.getValue());
    this.c.diagram.setJoinedRouting(this.o.isSelected());
    if (bool)
      this.c.diagram.autoArrange(ArrangerMode.SIMPLE); 
    this.a.a(this.c);
    this.a.y();
    return true;
  }
  
  public void a(List paramList) {
    for (Entity entity : paramList) {
      this.j.remove(entity);
      this.e.add(entity);
    } 
  }
  
  @Action(b = "flagSelectedUnused")
  public void addTable() {
    ArrayList arrayList = new ArrayList((Collection<?>)this.k.getSelectionModel().getSelectedItems());
    this.j.removeAll(arrayList);
    this.e.addAll(arrayList);
  }
  
  @Action(b = "flagSelectedUsed")
  public void removeTable() {
    ArrayList arrayList = new ArrayList((Collection<?>)this.f.getSelectionModel().getSelectedItems());
    this.j.addAll(arrayList);
    this.e.removeAll(arrayList);
  }
  
  @Action
  public void selectAll() {
    this.k.getSelectionModel().selectAll();
    this.rx.b();
  }
  
  @Action(b = "flagSelectedUsed")
  public void selectChildrenTables() {
    boolean bool = false;
    this.k.getSelectionModel().clearSelection();
    for (Entity entity : this.f.getSelectionModel().getSelectedItems()) {
      for (Entity entity1 : this.k.getItems()) {
        for (Relation relation : entity1.getRelations()) {
          if (relation.getTargetEntity() == entity) {
            this.k.getSelectionModel().select(entity1);
            this.k.scrollTo(entity1);
            bool = true;
          } 
        } 
      } 
    } 
    this.rx.b();
    if (!bool)
      this.rx.d(this.a, "noChildrenTableFound"); 
  }
  
  @Action(b = "flagSelectedUsed")
  public void selectParentTables() {
    boolean bool = false;
    this.k.getSelectionModel().clearSelection();
    for (Entity entity : this.f.getSelectionModel().getSelectedItems()) {
      for (Relation relation : entity.getRelations()) {
        if (this.k.getItems().contains(relation.getTargetEntity())) {
          this.k.getSelectionModel().select(relation.getTargetEntity());
          this.k.scrollTo(relation.getEntity());
          bool = true;
        } 
      } 
    } 
    this.rx.b();
    if (!bool)
      this.rx.d(this.a, "noParentTableFound"); 
  }
  
  @Action
  public void tipsTricks() {
    new FxTipsDialog(this.a, new LayoutTips());
  }
  
  @Action
  public void selectUnlistedTables() {
    this.k.getSelectionModel().clearSelection();
    for (byte b = 0; b < this.j.size(); b++) {
      Entity entity = (Entity)this.j.get(b);
      boolean bool = false;
      for (Layout layout : this.b.layouts) {
        for (Depict depict : layout.diagram.depicts) {
          if (depict.getEntity() == entity)
            bool = true; 
        } 
      } 
      if (!bool)
        this.k.getSelectionModel().select(b); 
    } 
  }
  
  public Workspace getWorkspace() {
    return this.a;
  }
}
