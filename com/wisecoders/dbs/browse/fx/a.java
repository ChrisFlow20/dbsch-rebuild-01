package com.wisecoders.dbs.browse.fx;

import com.wisecoders.dbs.sys.StringUtil;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;

class a extends ToolBar {
  private final FxBrowseEditor a;
  
  private final MenuButton b;
  
  a(FxBrowseEditor paramFxBrowseEditor) {
    this.a = paramFxBrowseEditor;
    getStyleClass().add("white-tool-bar");
    MenuButton menuButton1 = paramFxBrowseEditor.b.f("addTable", false);
    menuButton1.showingProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          paramMenuButton.getItems().clear();
          FxBrowseFrame fxBrowseFrame = paramFxBrowseEditor.a();
          if (paramFxBrowseEditor.c.a().isEmpty()) {
            paramFxBrowseEditor.addTable();
          } else if (fxBrowseFrame == null) {
            paramMenuButton.getItems().add(paramFxBrowseEditor.b.B("infoSelectTable.text"));
          } else {
            paramMenuButton.getItems().addAll(fxBrowseFrame.i());
          } 
        });
    MenuButton menuButton2 = paramFxBrowseEditor.b.f("editMenu", false);
    menuButton2.getItems().add(new MenuItem("Blind"));
    menuButton2.setOnShowing(paramEvent -> {
          paramMenuButton.getItems().clear();
          FxBrowseFrame fxBrowseFrame = paramFxBrowseEditor.a();
          if (paramFxBrowseEditor.c.a().isEmpty()) {
            paramMenuButton.getItems().addAll((Object[])new MenuItem[] { paramFxBrowseEditor.b.B("infoAddTable.text") });
          } else if (fxBrowseFrame == null) {
            paramMenuButton.getItems().add(paramFxBrowseEditor.b.B("infoSelectTable.text"));
          } else {
            paramMenuButton.getItems().addAll(paramFxBrowseEditor.b.e(new String[] { "insertRecord", "deleteRecord", "separator", "visibleColumns" }));
            paramMenuButton.getItems().addAll((Object[])new MenuItem[] { (MenuItem)paramFxBrowseEditor.b.y("showRowNumber") });
            paramMenuButton.getItems().addAll(paramFxBrowseEditor.b.e(new String[] { "separator", "toQueryBuilder" }));
          } 
        });
    this.b = paramFxBrowseEditor.b.f("connectorMenu", false);
    this.b.getItems().addAll(paramFxBrowseEditor.b.e(new String[] { "inheritConnector", "chooseConnector" }));
    MenuButton menuButton3 = paramFxBrowseEditor.b.f("detailsMenu", false);
    menuButton3.getItems().add(new MenuItem("Blind"));
    menuButton3.setOnShowing(paramEvent -> {
          paramMenuButton.getItems().clear();
          FxBrowseFrame fxBrowseFrame = paramFxBrowseEditor.a();
          if (paramFxBrowseEditor.c.a().isEmpty()) {
            paramMenuButton.getItems().addAll((Object[])new MenuItem[] { paramFxBrowseEditor.b.B("infoAddTable.text") });
          } else if (fxBrowseFrame == null) {
            paramMenuButton.getItems().add(paramFxBrowseEditor.b.B("infoSelectTable.text"));
          } else {
            paramMenuButton.getItems().add(paramFxBrowseEditor.b.A("addDetailsFrame"));
          } 
        });
    MenuButton menuButton4 = paramFxBrowseEditor.b.f("filterMenu", false);
    menuButton4.getItems().add(new MenuItem("Blind"));
    menuButton4.setOnShowing(paramEvent -> {
          paramMenuButton.getItems().clear();
          FxBrowseFrame fxBrowseFrame = paramFxBrowseEditor.a();
          if (paramFxBrowseEditor.c.a().isEmpty()) {
            paramMenuButton.getItems().addAll((Object[])new MenuItem[] { paramFxBrowseEditor.b.B("infoAddTable.text") });
          } else if (fxBrowseFrame == null) {
            paramMenuButton.getItems().addAll((Object[])new MenuItem[] { paramFxBrowseEditor.b.B("infoSelectTable.text") });
          } else {
            paramMenuButton.getItems().addAll(paramFxBrowseEditor.b.e(new String[] { "addFilter", "clearFilters" }));
          } 
        });
    MenuButton menuButton5 = paramFxBrowseEditor.b.f("help", false);
    menuButton5.getItems().addAll(paramFxBrowseEditor.b.e(new String[] { "help", "tipsTricks", "separator", "shortcutsManager" }));
    getItems().addAll((Object[])new Node[] { (Node)menuButton1, (Node)menuButton2, (Node)this.b, (Node)paramFxBrowseEditor.b

          
          .j("reload"), (Node)paramFxBrowseEditor.b
          .j("addFilter"), (Node)paramFxBrowseEditor.b
          .j("addDetailsFrame"), (Node)menuButton5 });
  }
  
  public void a() {
    String str = (this.a.c() == null) ? "Inherit" : this.a.c().getName();
    if (!StringUtil.areEqual(this.b.getText(), str))
      this.b.setText(str); 
  }
}
