package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.TreeSelection;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.VBox$;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public abstract class FxUnitSelectionDialog extends ButtonDialog$ {
  protected final WorkspaceWindow a;
  
  protected final Project b;
  
  public final TreeSelection c = new TreeSelection();
  
  protected final TreeView d = new TreeView();
  
  private boolean i = true;
  
  public final FxUnitSelectionDialog$FxCheckBoxTreeItem e;
  
  private final TextField j = new TextField();
  
  protected final VBox$ f = (new VBox$()).l();
  
  private boolean k;
  
  private boolean l;
  
  private String m;
  
  private Pattern n;
  
  public FxUnitSelectionDialog(WorkspaceWindow paramWorkspaceWindow, String paramString1, Project paramProject, String paramString2) {
    super(paramWorkspaceWindow);
    this.a = paramWorkspaceWindow;
    this.b = paramProject;
    if (paramString2 != null)
      setHeaderText(paramString2); 
    setDialogTitle(paramString1);
    this.e = new FxUnitSelectionDialog$FxCheckBoxTreeItem(this, paramProject.schemas);
    this.e.setExpanded(true);
    this.rx.a("flagFilterRegExp", () -> this.k);
    this.rx.a("flagFilterCaseSensitive", () -> this.l);
    this.d.setRoot((TreeItem)this.e);
    this.d.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    this.d.setCellFactory(CheckBoxTreeCell.forTreeView());
    this.d.addEventFilter(KeyEvent.KEY_PRESSED, paramKeyEvent -> {
          if (paramKeyEvent.getCode() == KeyCode.SPACE) {
            boolean bool1 = true;
            boolean bool2 = true;
            for (TreeItem treeItem : this.d.getSelectionModel().getSelectedItems()) {
              if (treeItem instanceof FxUnitSelectionDialog$FxCheckBoxTreeItem) {
                FxUnitSelectionDialog$FxCheckBoxTreeItem fxUnitSelectionDialog$FxCheckBoxTreeItem = (FxUnitSelectionDialog$FxCheckBoxTreeItem)treeItem;
                if (bool1)
                  bool2 = !fxUnitSelectionDialog$FxCheckBoxTreeItem.isSelected() ? true : false; 
                fxUnitSelectionDialog$FxCheckBoxTreeItem.setSelected(bool2);
                bool1 = false;
              } 
            } 
            paramKeyEvent.consume();
          } 
        });
  }
  
  protected void a() {
    this.e.d();
  }
  
  public boolean d(TreeUnit paramTreeUnit) {
    return true;
  }
  
  public void e(TreeUnit paramTreeUnit) {
    this.e.a(paramTreeUnit);
  }
  
  public void a(List paramList) {
    if (paramList != null)
      for (TreeUnit treeUnit : paramList)
        this.e.a(treeUnit);  
  }
  
  protected void f(TreeUnit paramTreeUnit) {
    a(this.e, paramTreeUnit);
  }
  
  private void a(FxUnitSelectionDialog$FxCheckBoxTreeItem paramFxUnitSelectionDialog$FxCheckBoxTreeItem, TreeUnit paramTreeUnit) {
    if (SyncUtil.a((TreeUnit)paramFxUnitSelectionDialog$FxCheckBoxTreeItem.getValue(), paramTreeUnit)) {
      paramFxUnitSelectionDialog$FxCheckBoxTreeItem.e();
      paramFxUnitSelectionDialog$FxCheckBoxTreeItem.d();
    } 
    for (TreeItem treeItem : paramFxUnitSelectionDialog$FxCheckBoxTreeItem.getChildren()) {
      if (paramTreeUnit == treeItem.getValue()) {
        ((FxUnitSelectionDialog$FxCheckBoxTreeItem)treeItem).setSelected(false);
        this.d.scrollTo(this.d.getRow(treeItem));
      } 
      a((FxUnitSelectionDialog$FxCheckBoxTreeItem)treeItem, paramTreeUnit);
    } 
  }
  
  public Node createContentPane() {
    this.j.getStyleClass().add("search-field");
    this.j.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> a(this.j.getText()));
    VBox.setVgrow((Node)this.d, Priority.ALWAYS);
    HBox$.setHgrow((Node)this.j, Priority.ALWAYS);
    MenuButton menuButton = this.rx.f("settings", false);
    menuButton.getItems().add(this.rx.y("filterRegExp"));
    menuButton.getItems().add(this.rx.y("filterCaseSensitive"));
    this.f.getChildren().addAll((Object[])new Node[] { (Node)(new HBox$()).a(3.0D).a(new Node[] { (Node)this.j, (Node)menuButton }), (Node)this.d });
    setRegionPrefSize((Region)this.d, 500.0D, 550.0D);
    return (Node)this.f;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean b() {
    return this.i;
  }
  
  public boolean apply() {
    if (this.c.hasNoSelectedUnits()) {
      showInformation("Please select at least one node", new String[0]);
      return false;
    } 
    this.i = false;
    return true;
  }
  
  public void a(TreeUnit paramTreeUnit) {}
  
  @Action(d = "flagFilterRegExp")
  public void filterRegExp() {
    this.k = !this.k;
    this.rx.b();
  }
  
  @Action(d = "flagFilterCaseSensitive")
  public void filterCaseSensitive() {
    this.l = !this.l;
    this.rx.b();
  }
  
  private void a(String paramString) {
    this.m = paramString;
    this.n = null;
    TreeItem treeItem = (TreeItem)this.d.getSelectionModel().getSelectedItem();
    this.j.getStyleClass().remove("text-red");
    if (this.k && StringUtil.isFilledTrim(paramString))
      try {
        this.n = Pattern.compile(paramString, 2);
      } catch (PatternSyntaxException patternSyntaxException) {
        this.j.getStyleClass().add("text-red");
      }  
    this.e.c();
    this.e.d();
    if (treeItem != null) {
      int i = this.d.getRow(treeItem);
      if (i > -1)
        this.d.scrollTo(i); 
    } 
  }
  
  private boolean g(TreeUnit paramTreeUnit) {
    if (StringUtil.isEmptyTrim(this.m))
      return true; 
    if (paramTreeUnit instanceof AbstractUnit) {
      AbstractUnit abstractUnit = (AbstractUnit)paramTreeUnit;
      if (this.k && this.n != null)
        return this.n.matcher(abstractUnit.toString()).find(); 
      if (this.l)
        return abstractUnit.toString().contains(this.m); 
      return abstractUnit.toString().toLowerCase().contains(this.m.toLowerCase());
    } 
    return false;
  }
  
  protected boolean b(TreeUnit paramTreeUnit) {
    return false;
  }
  
  public abstract boolean c(TreeUnit paramTreeUnit);
}
