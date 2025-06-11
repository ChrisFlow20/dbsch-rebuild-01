package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Diagram;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.diagram.model.Group;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
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
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class FxGroupEditor extends ButtonDialog$ implements FxUnitEditor {
  private static final String a = "groupEditorShowSchema";
  
  private final Workspace b;
  
  private final Diagram c;
  
  private Group d;
  
  private final boolean e;
  
  private final TextField f = new TextField();
  
  private final ColorPicker i = new ColorPicker();
  
  private final CheckBox j;
  
  private final CheckBox k;
  
  private final ObservableList l = FXCollections.observableArrayList();
  
  private final ObservableList m = FXCollections.observableArrayList();
  
  private final ListView n = new ListView();
  
  private final ListView o = new ListView();
  
  private final FxCommentPanel p;
  
  private final List q = new ArrayList();
  
  public FxGroupEditor(WorkspaceWindow paramWorkspaceWindow, Diagram paramDiagram, List paramList) {
    this(paramWorkspaceWindow.getWorkspace(), paramDiagram, (Group)null);
    if (paramList != null)
      this.q.addAll(paramList); 
  }
  
  public FxGroupEditor(WorkspaceWindow paramWorkspaceWindow, Group paramGroup) {
    this(paramWorkspaceWindow, paramGroup.getLayout(), paramGroup);
    setDialogTitle(this.rx.H("editGroup"));
  }
  
  private FxGroupEditor(WorkspaceWindow paramWorkspaceWindow, Diagram paramDiagram, Group paramGroup) {
    super(paramWorkspaceWindow.getWorkspace());
    this.b = paramWorkspaceWindow.getWorkspace();
    setDialogTitle(this.rx.H("editGroup"));
    this.rx.G(this.b.m().getDbId());
    this.c = paramDiagram;
    this.d = paramGroup;
    this.e = (paramGroup == null);
    this.p = new FxCommentPanel(paramWorkspaceWindow.getWorkspace(), paramGroup);
    this.j = this.rx.h("overrideColor", false);
    this.k = this.rx.v("showSchemaName");
    this.k.setSelected(Pref.b("groupEditorShowSchema", false));
    this.k.setOnAction(paramActionEvent -> {
          this.o.refresh();
          this.n.refresh();
        });
    this.o.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    this.n.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    this.rx.a("flagCanAdd", () -> (this.o.getSelectionModel().getSelectedIndex() > -1));
    this.rx.a("flagCanRemove", () -> (this.n.getSelectionModel().getSelectedIndex() > -1));
    this.o.setCellFactory(paramListView -> new p(this));
    this.n.setCellFactory(paramListView -> new p(this));
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$1 = (new GridPane$()).e().g();
    gridPane$1.a(new int[] { -2, -2, -1 });
    gridPane$1.b(new int[] { -2, -2, -1 });
    gridPane$1.a((Node)this.rx.a("nameLabel", (Node)this.f), "0,0,r,c");
    gridPane$1.a((Node)this.f, "1,0,2,0,f,c");
    gridPane$1.a((Node)this.rx.a("colorLabel", (Node)this.i), "0,1,r,c");
    gridPane$1.a((Node)this.i, "1,1,l,c");
    gridPane$1.a((Node)this.j, "2,1,l,c");
    gridPane$1.a((Node)this.rx.a("descriptionLabel", (Node)this.p.a), "0,2,r,c");
    gridPane$1.a((Node)this.p, "1,2,2,2,f,f");
    this.o.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramEntity1, paramEntity2) -> this.rx.b());
    this.n.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramEntity1, paramEntity2) -> this.rx.b());
    TextField textField1 = this.rx.t("usedTextField");
    FilteredList filteredList1 = new FilteredList(this.l, paramEntity -> true);
    textField1.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> paramFilteredList.setPredicate(()));
    SortedList sortedList1 = new SortedList((ObservableList)filteredList1);
    sortedList1.comparatorProperty().set(Comparator.comparing(Entity::getName));
    this.n.setItems((ObservableList)sortedList1);
    this.n.setCellFactory(paramListView -> new FxGroupEditor$UnitListCell(this));
    this.o.setCellFactory(paramListView -> new FxGroupEditor$UnitListCell(this));
    TextField textField2 = this.rx.t("unusedTextField");
    FilteredList filteredList2 = new FilteredList(this.m, paramEntity -> true);
    textField2.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> paramFilteredList.setPredicate(()));
    SortedList sortedList2 = new SortedList((ObservableList)filteredList2);
    sortedList2.comparatorProperty().set(Comparator.comparing(Entity::getName));
    this.o.setItems((ObservableList)sortedList2);
    GridPane$ gridPane$2 = (new GridPane$()).e();
    gridPane$2.b(new int[] { -1, -2, -2, -1 });
    gridPane$2.a((Node)this.rx.j("addTable"), "0,1,c,c");
    gridPane$2.a((Node)this.rx.j("removeTable"), "0,2,c,c");
    GridPane$ gridPane$3 = (new GridPane$()).e().g();
    gridPane$3.a(new int[] { -1, -2, -1 });
    gridPane$3.b(new int[] { -2, -2, -1, -2, -2 });
    setRegionPrefSize((Region)gridPane$3, 800.0D, 500.0D);
    gridPane$3.a((Node)this.rx.h("titleLabel"), "0,0,2,0,f,c");
    gridPane$3.a((Node)this.rx.e("unusedLabel"), "0,1,c,c");
    gridPane$3.a((Node)this.rx.e("usedLabel"), "2,1,c,c");
    gridPane$3.a((Node)textField2, "0,2,f,c");
    gridPane$3.a((Node)textField1, "2,2,f,c");
    gridPane$3.a((Node)this.o, "0,3,f,f");
    gridPane$3.a((Node)gridPane$2, "1,3,c,c");
    gridPane$3.a((Node)this.n, "2,3,f,f");
    gridPane$3.a((Node)this.rx.j("selectAll"), "0,4,l,c");
    gridPane$3.a((Node)this.rx.j("selectAllInGroup"), "2,4,r,c");
    gridPane$3.a((Node)this.k, "0,5,l,c");
    if (this.d == null) {
      this.f.setText("Group_");
      this.f.selectAll();
      setInitFocusedNode((Node)this.f);
      this.i.setValue(Group.DEFAULT_COLOR);
    } else {
      this.f.setText(this.d.getName());
      this.i.setValue(this.d.getColor());
      for (Depict depict : this.d.getDepicts())
        this.l.add(depict.getEntity()); 
    } 
    for (Depict depict : this.c.depicts) {
      if (!this.l.contains(depict.getEntity()))
        this.m.add(depict.getEntity()); 
    } 
    for (Group group : this.c.groups) {
      for (Depict depict : group.getDepicts())
        this.m.remove(depict.getEntity()); 
    } 
    for (Entity entity : this.q) {
      if (this.m.contains(entity)) {
        this.m.remove(entity);
        this.l.add(entity);
        this.n.getSelectionModel().select(entity);
      } 
    } 
    this.o.setOnMouseClicked(paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() == 2)
            addTable(); 
        });
    this.n.setOnMouseClicked(paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() == 2)
            removeTable(); 
        });
    BorderPane borderPane = new BorderPane();
    borderPane.setTop((Node)gridPane$1);
    borderPane.setCenter((Node)gridPane$3);
    return (Node)borderPane;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
    createHelpButton("layouts.html");
  }
  
  public Group a() {
    return this.d;
  }
  
  public boolean b() {
    String str = this.f.getText();
    if (StringUtil.isEmptyTrim(str)) {
      showError("emptyGroupNameError");
      this.f.requestFocus();
      return false;
    } 
    if (this.l.isEmpty()) {
      showError("addEntityToGroupError");
      return false;
    } 
    return true;
  }
  
  public boolean apply() {
    if (!b())
      return false; 
    String str = this.f.getText();
    if (this.d == null) {
      this.d = this.c.createGroup(str);
    } else {
      this.d.rename(str);
    } 
    this.d.setColor((Color)this.i.getValue());
    this.d.setComment(this.p.b());
    this.d.setCommentTags(this.p.a());
    this.p.a(this.d);
    for (Entity entity : this.m) {
      Depict depict = this.c.getDepictFor(entity);
      if (depict != null)
        this.d.dettach(depict); 
    } 
    for (Entity entity : this.l) {
      Depict depict = this.c.getDepictFor(entity);
      if (depict != null)
        this.d.attachDepict(depict); 
    } 
    if (this.j.isSelected())
      for (Depict depict : this.d.getDepicts())
        depict.setColor((Color)this.i.getValue());  
    this.b.u();
    this.b.y();
    return true;
  }
  
  @Action
  public void showSchemaName() {
    this.o.refresh();
    this.n.refresh();
    Pref.a("groupEditorShowSchema", this.k.isSelected());
  }
  
  @Action(b = "flagCanAdd")
  public void addTable() {
    this.l.addAll((Collection)this.o.getSelectionModel().getSelectedItems());
    this.m.removeAll((Collection)this.o.getSelectionModel().getSelectedItems());
  }
  
  @Action(b = "flagCanRemove")
  public void removeTable() {
    this.m.addAll((Collection)this.n.getSelectionModel().getSelectedItems());
    this.l.removeAll((Collection)this.n.getSelectionModel().getSelectedItems());
  }
  
  @Action
  public void selectAll() {
    this.o.getSelectionModel().selectAll();
    this.rx.b();
  }
  
  @Action
  public void selectAllInGroup() {
    this.n.getSelectionModel().selectAll();
    this.rx.b();
  }
  
  public Workspace getWorkspace() {
    return this.b;
  }
}
