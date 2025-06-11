package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.diagram.model.Group;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.FxSearchTextField;
import com.wisecoders.dbs.sys.fx.RowPane$;
import com.wisecoders.dbs.tips.fx.FxTipsDialog;
import com.wisecoders.dbs.tips.tips.LayoutTips;
import java.util.Comparator;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

public class FxGroupsEditor extends ButtonDialog$ implements FxUnitEditor {
  private final Workspace a;
  
  private final Project b;
  
  private final Layout c;
  
  private final ObservableList d = FXCollections.observableArrayList();
  
  private final ListView e = new ListView();
  
  private final ComboBox f = new ComboBox();
  
  public FxGroupsEditor(WorkspaceWindow paramWorkspaceWindow, Project paramProject, Layout paramLayout) {
    super(paramWorkspaceWindow.getWorkspace());
    this.a = paramWorkspaceWindow.getWorkspace();
    this.b = paramProject;
    this.c = paramLayout;
    this.rx.G(paramProject.getDbId());
  }
  
  public Node createContentPane() {
    this.e.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    this.e.setCellFactory(paramListView -> new q());
    this.rx.a("flagSelectedGroup", () -> (this.e.getSelectionModel().getSelectedIndex() > -1));
    FxSearchTextField fxSearchTextField = new FxSearchTextField();
    FilteredList filteredList = new FilteredList(this.d, paramGroup -> true);
    fxSearchTextField.a((paramObservableValue, paramString1, paramString2) -> paramFilteredList.setPredicate(()));
    SortedList sortedList = new SortedList((ObservableList)filteredList);
    sortedList.comparatorProperty().set(Comparator.comparing(AbstractUnit::getName));
    this.e.setItems((ObservableList)sortedList);
    this.f.getItems().addAll(this.b.layouts);
    this.f.getSelectionModel().select(this.c);
    c();
    this.f.setOnAction(paramActionEvent -> c());
    this.e.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramGroup1, paramGroup2) -> this.rx.b());
    this.e.setOnMouseClicked(paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() == 2) {
            Group group = (Group)this.e.getSelectionModel().getSelectedItem();
            if (group != null)
              (new FxGroupEditor(this.a, group)).showDialog(); 
          } 
        });
    this.rx.b();
    setInitFocusedNode((Node)this.e);
    RowPane$ rowPane$ = (new RowPane$()).g().a(new Node[] { (Node)this.rx.e("layoutLabel"), (Node)this.f }).b((Node)fxSearchTextField).b((Node)this.e).a(this.rx.c(new String[] { "addGroup", "editGroup", "showGroup", "deleteGroup" }));
    setRegionPrefSize((Region)rowPane$, 600.0D, 450.0D);
    return (Node)rowPane$;
  }
  
  private void c() {
    this.d.clear();
    this.d.addAll(((Layout)this.f.getSelectionModel().getSelectedItem()).diagram.groups);
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
    createHelpButton("schema.html#index");
  }
  
  public AbstractUnit a() {
    return this.c;
  }
  
  public boolean b() {
    return true;
  }
  
  public boolean apply() {
    if (!b())
      return false; 
    this.a.y();
    return true;
  }
  
  @Action
  public void addGroup() {
    (new FxGroupEditor(this.a, (this.a.p()).diagram, null)).showDialog();
    c();
  }
  
  @Action(b = "flagSelectedGroup")
  public void editGroup() {
    (new FxGroupEditor(this.a, (Group)this.e.getSelectionModel().getSelectedItem())).showDialog();
  }
  
  @Action(b = "flagSelectedGroup")
  public void deleteGroup() {
    if (this.rx.a(getDialogScene(), "confirmGroupDeletion")) {
      for (Group group : this.e.getSelectionModel().getSelectedItems())
        group.markForDeletion(); 
      this.a.y();
    } 
  }
  
  @Action(b = "flagSelectedGroup")
  public void showGroup() {
    this.a.a((Layout)this.f.getValue());
    (this.a.o()).c.c((Unit)this.e.getSelectionModel().getSelectedItem());
  }
  
  @Action
  public void tipsTricks() {
    new FxTipsDialog(this.a, new LayoutTips());
  }
  
  public Workspace getWorkspace() {
    return this.a;
  }
}
