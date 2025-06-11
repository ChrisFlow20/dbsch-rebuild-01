package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.diagram.fx.FxLayoutPane;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.dialogs.layout.FxEditorFactory;
import com.wisecoders.dbs.project.fx.FxTreeTableCell;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.schema.Sql;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.RowPane$;
import java.util.Iterator;
import java.util.Map;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class FxFindDialog extends ButtonDialog$ {
  private final Workspace a;
  
  private final CheckBox b;
  
  private final CheckBox c;
  
  private final CheckBox d;
  
  private final CheckBox e;
  
  private final TextField f = new TextField();
  
  private final TreeTableView i = new TreeTableView();
  
  private final ContextMenu j = new ContextMenu();
  
  private final Button k;
  
  public FxFindDialog(Workspace paramWorkspace, TreeUnit paramTreeUnit) {
    super(paramWorkspace);
    this.a = paramWorkspace;
    this.rx.a("flagCanAddToLayout", () -> (paramWorkspace.B() && this.i.getSelectionModel().getSelectedItem() != null && ((TreeItem)this.i.getSelectionModel().getSelectedItem()).getValue() instanceof AbstractUnit && ((TreeUnit)((TreeItem)this.i.getSelectionModel().getSelectedItem()).getValue()).getEntity() != null));
    this.rx.a("flagCanEdit", () -> 
        (this.i.getSelectionModel().getSelectedItem() != null && ((TreeItem)this.i.getSelectionModel().getSelectedItem()).getValue() instanceof AbstractUnit));
    this.rx.a("flagEntity", () -> 
        (this.i.getSelectionModel().getSelectedItem() != null && ((TreeItem)this.i.getSelectionModel().getSelectedItem()).getValue() instanceof com.wisecoders.dbs.diagram.model.Entity));
    this.b = this.rx.h("checkCaseSensitive", false);
    this.c = this.rx.h("checkWholeWords", false);
    this.d = this.rx.h("checkSearchInComments", true);
    this.e = this.rx.h("checkSearchInCommentTags", true);
    e e = new e(this, paramTreeUnit);
    e.setExpanded(true);
    this.i.setRoot(e);
    this.i.setOnMouseClicked(paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() == 2)
            edit(); 
        });
    this.j.setAutoHide(true);
    this.i.setContextMenu(this.j);
    this.i.setOnContextMenuRequested(paramContextMenuEvent -> {
          a();
          this.j.show((Node)this.i, paramContextMenuEvent.getScreenX(), paramContextMenuEvent.getScreenY());
        });
    b();
    this.k = this.rx.j("showEntityInLayout");
    this.f.setPromptText("Text");
    this.f.textProperty().addListener(paramObservable -> parame.b());
    this.rx.b();
    setInitFocusedNode((Node)this.f);
    showDialog();
  }
  
  private void a() {
    this.j.getItems().clear();
    if (this.i.getSelectionModel().getSelectedItem() != null && ((TreeItem)this.i
      .getSelectionModel().getSelectedItem()).getValue() instanceof com.wisecoders.dbs.diagram.model.Entity)
      for (Iterator<E> iterator = (this.a.m()).layouts.iterator(); iterator.hasNext(); ) {
        Layout layout = (Layout)iterator.next();
        for (Depict depict : layout.getDepicts()) {
          if (depict.getEntity() == ((TreeItem)this.i.getSelectionModel().getSelectedItem()).getValue()) {
            MenuItem menuItem = new MenuItem("Show in Layout '" + layout.getName() + "'");
            menuItem.setOnAction(paramActionEvent -> {
                  FxLayoutPane fxLayoutPane = this.a.a(paramLayout);
                  fxLayoutPane.c.c(paramDepict.getEntity());
                });
            this.j.getItems().add(menuItem);
          } 
        } 
      }  
  }
  
  private void b() {
    TreeTableColumn treeTableColumn1 = new TreeTableColumn("Element");
    treeTableColumn1.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue().getValue()));
    treeTableColumn1.setCellFactory(paramTreeTableColumn -> new FxTreeTableCell(false));
    TreeTableColumn treeTableColumn2 = new TreeTableColumn("Matches");
    treeTableColumn2.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(((e)paramCellDataFeatures.getValue()).b));
    this.i.getColumns().addAll((Object[])new TreeTableColumn[] { treeTableColumn1, treeTableColumn2 });
    this.i.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
    setRegionPrefSize((Region)this.i, 600.0D, 600.0D);
    this.i.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramTreeItem1, paramTreeItem2) -> this.rx.b());
  }
  
  public Node createContentPane() {
    HBox.setHgrow((Node)this.f, Priority.ALWAYS);
    return (Node)(new RowPane$()).a()
      .a(new Node[] { (Node)this.rx.e("textLabel"), (Node)this.f }).a(new Node[] { (Node)this.b, (Node)this.c, (Node)this.d, (Node)this.e }).b((Node)this.i)
      .a(new Node[] { (Node)this.rx.j("edit"), (Node)this.rx.j("addToLayout"), (Node)this.k });
  }
  
  public void createButtons() {
    createCloseButton();
  }
  
  public boolean apply() {
    return true;
  }
  
  private boolean a(TreeUnit paramTreeUnit, String paramString) {
    if (StringUtil.isEmptyTrim(paramString) || StringUtil.isFilledTrim(b(paramTreeUnit, paramString)))
      return true; 
    for (byte b = 0; b < paramTreeUnit.getChildrenCount(); b++) {
      TreeUnit treeUnit = paramTreeUnit.getChildAt(b);
      if (a(treeUnit, paramString))
        return true; 
    } 
    return false;
  }
  
  private String b(TreeUnit paramTreeUnit, String paramString) {
    if (paramTreeUnit instanceof AbstractUnit && StringUtil.isFilledTrim(paramString)) {
      StringBuilder stringBuilder = new StringBuilder();
      AbstractUnit abstractUnit = (AbstractUnit)paramTreeUnit;
      stringBuilder.append(a(paramString, "Name", abstractUnit.getName()));
      if (abstractUnit instanceof View)
        stringBuilder.append(a(paramString, "Script", ((View)abstractUnit).getScript())); 
      if (abstractUnit instanceof Constraint)
        stringBuilder.append(a(paramString, "Check", ((Constraint)abstractUnit).getText())); 
      if (abstractUnit instanceof Sql)
        stringBuilder.append(a(paramString, "Script", ((Sql)abstractUnit).getText())); 
      if (abstractUnit instanceof Column) {
        stringBuilder.append(a(paramString, "Default", ((Column)abstractUnit).getDefaultValue()));
        stringBuilder.append(a(paramString, "Enum", ((Column)abstractUnit).getEnumeration()));
      } 
      if (this.d.isSelected())
        stringBuilder.append(a(paramString, "Comment", abstractUnit.getComment())); 
      if (this.e.isSelected())
        stringBuilder.append(a(paramString, abstractUnit.getCommentTags())); 
      String str = stringBuilder.toString();
      return str.startsWith("\n") ? str.substring(1) : str;
    } 
    return null;
  }
  
  private String a(String paramString, Map paramMap) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramMap != null)
      for (String str : paramMap.keySet()) {
        stringBuilder.append(a(paramString, "Tag Key: ", str));
        stringBuilder.append(a(paramString, "Tag Value: ", (String)paramMap.get(str)));
      }  
    return stringBuilder.toString();
  }
  
  private String a(String paramString1, String paramString2, String paramString3) {
    boolean bool1 = this.c.isSelected();
    boolean bool2 = this.b.isSelected();
    if (paramString3 != null) {
      int i = paramString3.length();
      if (bool1) {
        if ((!bool2 && paramString1.equalsIgnoreCase(paramString3.toLowerCase())) || (bool2 && paramString1
          .equals(paramString3)))
          return "\n" + paramString2 + ": " + paramString3; 
      } else {
        int j;
        if ((!bool2 && (
          j = paramString3.toLowerCase().indexOf(paramString1.toLowerCase())) > -1) || (bool2 && (
          j = paramString3.indexOf(paramString1)) > -1)) {
          String str = (j > 20) ? ("..." + paramString3.substring(j - 20, j)) : paramString3.substring(0, j);
          int k = str.length();
          int m = j + paramString1.length();
          str = str + str;
          str = str + str;
          return "\n" + paramString2 + ": " + str;
        } 
      } 
    } 
    return "";
  }
  
  @Action(b = "flagCanAddToLayout")
  public void addToLayout() {
    this.a.o().b(((TreeUnit)((TreeItem)this.i.getSelectionModel().getSelectedItem()).getValue()).getEntity());
  }
  
  @Action(b = "flagEntity")
  public void showEntityInLayout() {
    a();
    this.j.show((Node)this.k, Side.TOP, 10.0D, -10.0D);
  }
  
  @Action(b = "flagCanEdit")
  public void edit() {
    FxEditorFactory.a(this.a, (Unit)((TreeItem)this.i.getSelectionModel().getSelectedItem()).getValue());
  }
}
