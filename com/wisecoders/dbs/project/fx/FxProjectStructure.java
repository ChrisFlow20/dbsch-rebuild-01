package com.wisecoders.dbs.project.fx;

import com.wisecoders.dbs.diagram.fx.PopupFactory;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.ContextMenu$;
import com.wisecoders.dbs.sys.fx.HBox$;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;

public class FxProjectStructure extends BorderPane {
  private final Workspace b;
  
  private final Rx c = new Rx(FxProjectStructure.class, this);
  
  private final ContextMenu$ d = new ContextMenu$();
  
  public final ToolBar a = new ToolBar();
  
  private final TreeView e = new TreeView();
  
  private String f;
  
  private final TextField g = new TextField();
  
  private boolean h = false;
  
  private boolean i = false;
  
  private boolean j = false;
  
  FxProjectStructure(Workspace paramWorkspace) {
    this.b = paramWorkspace;
    this.c.a("flagIsSearchInComments", () -> this.h);
    this.c.a("flagIsUseRegExp", () -> this.i);
    this.c.a("flagExactMatch", () -> this.j);
    this.e.getStyleClass().add("project-structure");
    this.e.setCellFactory(paramTreeView -> new b(this));
    this.e.setOnMouseClicked(paramMouseEvent -> {
          // Byte code:
          //   0: aload_0
          //   1: getfield e : Ljavafx/scene/control/TreeView;
          //   4: invokevirtual requestFocus : ()V
          //   7: aload_0
          //   8: getfield e : Ljavafx/scene/control/TreeView;
          //   11: invokevirtual getSelectionModel : ()Ljavafx/scene/control/MultipleSelectionModel;
          //   14: invokevirtual getSelectedItem : ()Ljava/lang/Object;
          //   17: checkcast javafx/scene/control/TreeItem
          //   20: astore_3
          //   21: aload_3
          //   22: ifnull -> 248
          //   25: aload_2
          //   26: invokevirtual getClickCount : ()I
          //   29: iconst_1
          //   30: if_icmpne -> 61
          //   33: aload_1
          //   34: invokevirtual o : ()Lcom/wisecoders/dbs/diagram/fx/FxLayoutPane;
          //   37: ifnull -> 244
          //   40: aload_1
          //   41: invokevirtual o : ()Lcom/wisecoders/dbs/diagram/fx/FxLayoutPane;
          //   44: getfield c : Lcom/wisecoders/dbs/diagram/fx/FxDiagramPane;
          //   47: aload_3
          //   48: invokevirtual getValue : ()Ljava/lang/Object;
          //   51: checkcast com/wisecoders/dbs/diagram/model/Unit
          //   54: invokevirtual c : (Lcom/wisecoders/dbs/diagram/model/Unit;)Z
          //   57: pop
          //   58: goto -> 244
          //   61: aload_2
          //   62: invokevirtual getClickCount : ()I
          //   65: iconst_2
          //   66: if_icmpne -> 244
          //   69: aload_3
          //   70: invokevirtual getValue : ()Ljava/lang/Object;
          //   73: astore #5
          //   75: aload #5
          //   77: instanceof com/wisecoders/dbs/schema/Table
          //   80: ifeq -> 200
          //   83: aload #5
          //   85: checkcast com/wisecoders/dbs/schema/Table
          //   88: astore #4
          //   90: aload_1
          //   91: invokevirtual j : ()Z
          //   94: ifeq -> 200
          //   97: aload_1
          //   98: invokevirtual p : ()Lcom/wisecoders/dbs/schema/Layout;
          //   101: ifnull -> 116
          //   104: aload_1
          //   105: invokevirtual p : ()Lcom/wisecoders/dbs/schema/Layout;
          //   108: aload #4
          //   110: invokevirtual containsDepictForEntity : (Lcom/wisecoders/dbs/diagram/model/Entity;)Z
          //   113: ifne -> 177
          //   116: aload_1
          //   117: aload_1
          //   118: invokevirtual m : ()Lcom/wisecoders/dbs/schema/Project;
          //   121: aload_1
          //   122: invokevirtual m : ()Lcom/wisecoders/dbs/schema/Project;
          //   125: getfield layouts : Lcom/wisecoders/dbs/diagram/model/Folder;
          //   128: aload #4
          //   130: invokevirtual getName : ()Ljava/lang/String;
          //   133: invokevirtual proposeName : (Ljava/lang/String;)Ljava/lang/String;
          //   136: invokevirtual createLayout : (Ljava/lang/String;)Lcom/wisecoders/dbs/schema/Layout;
          //   139: invokevirtual a : (Lcom/wisecoders/dbs/schema/Layout;)Lcom/wisecoders/dbs/diagram/fx/FxLayoutPane;
          //   142: astore #5
          //   144: aload #5
          //   146: getfield c : Lcom/wisecoders/dbs/diagram/fx/FxDiagramPane;
          //   149: getfield a : Lcom/wisecoders/dbs/diagram/model/Diagram;
          //   152: aload #4
          //   154: new com/wisecoders/dbs/diagram/model/Point
          //   157: dup
          //   158: dconst_0
          //   159: dconst_0
          //   160: invokespecial <init> : (DD)V
          //   163: invokevirtual attachRecursiveAndCreateGroupForHierarchicalEntities : (Lcom/wisecoders/dbs/diagram/model/Entity;Lcom/wisecoders/dbs/diagram/model/Point;)V
          //   166: aload #5
          //   168: getfield c : Lcom/wisecoders/dbs/diagram/fx/FxDiagramPane;
          //   171: getstatic com/wisecoders/dbs/diagram/model/ArrangerMode.SIMPLE : Lcom/wisecoders/dbs/diagram/model/ArrangerMode;
          //   174: invokevirtual a : (Lcom/wisecoders/dbs/diagram/model/ArrangerMode;)V
          //   177: aload_1
          //   178: invokevirtual o : ()Lcom/wisecoders/dbs/diagram/fx/FxLayoutPane;
          //   181: getfield c : Lcom/wisecoders/dbs/diagram/fx/FxDiagramPane;
          //   184: aload #4
          //   186: invokevirtual c : (Lcom/wisecoders/dbs/diagram/model/Unit;)Z
          //   189: pop
          //   190: aload_1
          //   191: checkcast com/wisecoders/dbs/project/fx/FxFrame
          //   194: invokevirtual generateSelectSql : ()V
          //   197: goto -> 244
          //   200: aload_3
          //   201: invokevirtual getValue : ()Ljava/lang/Object;
          //   204: ifnull -> 244
          //   207: aload_3
          //   208: invokevirtual getValue : ()Ljava/lang/Object;
          //   211: instanceof com/wisecoders/dbs/schema/Layout
          //   214: ifeq -> 232
          //   217: aload_1
          //   218: aload_3
          //   219: invokevirtual getValue : ()Ljava/lang/Object;
          //   222: checkcast com/wisecoders/dbs/schema/Layout
          //   225: invokevirtual a : (Lcom/wisecoders/dbs/schema/Layout;)Lcom/wisecoders/dbs/diagram/fx/FxLayoutPane;
          //   228: pop
          //   229: goto -> 244
          //   232: aload_1
          //   233: aload_3
          //   234: invokevirtual getValue : ()Ljava/lang/Object;
          //   237: checkcast com/wisecoders/dbs/diagram/model/Unit
          //   240: invokestatic a : (Lcom/wisecoders/dbs/dialogs/layout/WorkspaceWindow;Lcom/wisecoders/dbs/diagram/model/Unit;)Lcom/wisecoders/dbs/diagram/model/FxUnitEditor;
          //   243: pop
          //   244: aload_2
          //   245: invokevirtual consume : ()V
          //   248: return
          // Line number table:
          //   Java source line number -> byte code offset
          //   #51	-> 0
          //   #52	-> 7
          //   #53	-> 21
          //   #54	-> 25
          //   #55	-> 33
          //   #56	-> 40
          //   #58	-> 61
          //   #59	-> 69
          //   #60	-> 97
          //   #61	-> 116
          //   #62	-> 144
          //   #63	-> 166
          //   #65	-> 177
          //   #66	-> 190
          //   #67	-> 200
          //   #68	-> 207
          //   #69	-> 217
          //   #71	-> 232
          //   #75	-> 244
          //   #77	-> 248
        });
    this.e.setContextMenu(this.d);
    this.e.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    this.e.setOnContextMenuRequested(paramContextMenuEvent -> {
          PopupFactory.a(paramWorkspace, paramWorkspace.o(), this.d, c());
          this.d.show((Node)this.e, paramContextMenuEvent.getScreenX(), paramContextMenuEvent.getScreenY());
        });
    this.e.addEventFilter(KeyEvent.KEY_PRESSED, paramKeyEvent -> {
          if (paramKeyEvent.getCode() == KeyCode.C && paramKeyEvent.isControlDown()) {
            h();
            paramKeyEvent.consume();
          } 
        });
    this.a.getStyleClass().add("title-tool-bar");
    this.g.setMinWidth(10.0D);
    this.g.setPrefWidth(10.0D);
    HBox$.setHgrow((Node)this.g, Priority.ALWAYS);
    this.g.getStyleClass().add("search-field");
    this.g.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> f());
    MenuButton menuButton = this.c.e("settings", false);
    menuButton.getStyleClass().add("transparent-split-menu-button");
    menuButton.getItems().addAll(this.c.d(new String[] { "exactMatch", "searchUsingRegExp", "searchInComments" }));
    this.a.getItems().addAll((Object[])new Node[] { (Node)this.g, (Node)menuButton });
    setTop((Node)this.a);
    setCenter((Node)this.e);
  }
  
  public void a() {
    this.e.refresh();
  }
  
  public boolean b() {
    return this.e.isFocused();
  }
  
  @Action(d = "flagIsSearchInComments")
  public void searchInComments() {
    this.h = !this.h;
  }
  
  @Action(d = "flagIsUseRegExp")
  public void searchUsingRegExp() {
    this.i = !this.i;
  }
  
  @Action(d = "flagExactMatch")
  public void exactMatch() {
    this.j = !this.j;
    f();
  }
  
  private void h() {
    StringBuilder stringBuilder = new StringBuilder();
    for (Unit unit : c())
      stringBuilder.append(unit.ref()).append(" "); 
    Clipboard clipboard = Clipboard.getSystemClipboard();
    ClipboardContent clipboardContent = new ClipboardContent();
    clipboardContent.putString(stringBuilder.toString());
    clipboard.setContent((Map)clipboardContent);
  }
  
  List c() {
    ArrayList<Unit> arrayList = new ArrayList();
    for (TreeItem treeItem : this.e.getSelectionModel().getSelectedItems()) {
      if (treeItem != null && treeItem.getValue() != null)
        arrayList.add((Unit)treeItem.getValue()); 
    } 
    return arrayList;
  }
  
  List d() {
    ArrayList<Entity> arrayList = new ArrayList();
    for (TreeItem treeItem : this.e.getSelectionModel().getSelectedItems()) {
      TreeUnit treeUnit = (TreeUnit)treeItem.getValue();
      if (treeUnit instanceof Entity)
        arrayList.add((Entity)treeUnit); 
    } 
    return arrayList;
  }
  
  void a(Project paramProject) {
    FxProjectStructure$FxTreeItem fxProjectStructure$FxTreeItem = new FxProjectStructure$FxTreeItem(this, paramProject);
    fxProjectStructure$FxTreeItem.setExpanded(true);
    boolean bool = true;
    for (TreeItem treeItem : fxProjectStructure$FxTreeItem.getChildren()) {
      if (!(treeItem.getValue() instanceof com.wisecoders.dbs.diagram.model.PropertyAddOnFolder))
        treeItem.setExpanded(true); 
      if (!treeItem.getChildren().isEmpty() && bool) {
        ((TreeItem)treeItem.getChildren().get(0)).setExpanded(true);
        bool = false;
      } 
    } 
    this.e.setRoot(fxProjectStructure$FxTreeItem);
  }
  
  boolean a(int paramInt) {
    if (this.e.getRoot() != null)
      return a((FxProjectStructure$FxTreeItem)this.e.getRoot(), paramInt); 
    return false;
  }
  
  private boolean a(FxProjectStructure$FxTreeItem paramFxProjectStructure$FxTreeItem, int paramInt) {
    boolean bool = false;
    if (paramFxProjectStructure$FxTreeItem.getValue() != null && ((TreeUnit)paramFxProjectStructure$FxTreeItem.getValue()).getTouchId() >= paramInt) {
      bool = true;
      paramFxProjectStructure$FxTreeItem.c();
      for (TreeItem treeItem : paramFxProjectStructure$FxTreeItem.getChildren())
        a((FxProjectStructure$FxTreeItem)treeItem, paramInt); 
      this.b.u();
    } 
    return bool;
  }
  
  public void e() {
    for (TreeItem treeItem : this.e.getSelectionModel().getSelectedItems()) {
      treeItem.setExpanded(true);
      for (TreeItem treeItem1 : treeItem.getChildren())
        treeItem1.setExpanded(true); 
    } 
  }
  
  public void f() {
    String str = this.g.getText();
    this.f = (str != null) ? str.toLowerCase().trim() : null;
    TreeItem treeItem = this.e.getRoot();
    if (treeItem instanceof FxProjectStructure$FxTreeItem) {
      FxProjectStructure$FxTreeItem fxProjectStructure$FxTreeItem = (FxProjectStructure$FxTreeItem)treeItem;
      fxProjectStructure$FxTreeItem.a();
    } 
  }
  
  public void a(TreeUnit paramTreeUnit) {
    if (paramTreeUnit != null) {
      boolean bool;
      ArrayList<TreeUnit> arrayList = new ArrayList();
      TreeUnit treeUnit = paramTreeUnit;
      do {
        arrayList.add(treeUnit);
      } while ((treeUnit = treeUnit.getParent()) != null);
      TreeItem treeItem = this.e.getRoot();
      do {
        bool = false;
        for (TreeItem treeItem1 : treeItem.getChildren()) {
          if (arrayList.contains(treeItem1.getValue())) {
            treeItem1.setExpanded(true);
            treeItem = treeItem1;
            bool = true;
            if (treeItem.getValue() == paramTreeUnit) {
              this.e.getSelectionModel().clearSelection();
              this.e.getSelectionModel().select(treeItem);
              this.e.scrollTo(this.e.getSelectionModel().getSelectedIndex());
            } 
            break;
          } 
        } 
      } while (bool);
    } 
  }
  
  public boolean g() {
    return this.e.isFocused();
  }
}
