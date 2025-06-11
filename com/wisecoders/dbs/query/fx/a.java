package com.wisecoders.dbs.query.fx;

import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.dialogs.system.FxHierarchicalSelectionDialog;
import com.wisecoders.dbs.query.model.items.AbstractQueryColumn;
import com.wisecoders.dbs.query.model.items.QueryTable;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.ProgressBarWithText;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToolBar;

class a extends ToolBar {
  private final FxQueryEditor b;
  
  public final MenuButton a;
  
  a(FxQueryEditor paramFxQueryEditor) {
    this.b = paramFxQueryEditor;
    getStyleClass().add("white-tool-bar");
    MenuButton menuButton1 = paramFxQueryEditor.g.f("addTable", false);
    menuButton1.showingProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          paramMenuButton.getItems().clear();
          Entity entity = paramFxQueryEditor.h.c.d();
          if (paramFxQueryEditor.i.b.depicts.isEmpty()) {
            (new FxHierarchicalSelectionDialog(paramFxQueryEditor.getWorkspace())).showDialog().ifPresent(());
          } else if (entity == null) {
            paramMenuButton.getItems().add(paramFxQueryEditor.g.B("infoSelectTable.text"));
          } else {
            paramMenuButton.getItems().add(paramFxQueryEditor.g.B("Selected '" + String.valueOf(entity) + "'"));
            paramMenuButton.getItems().addAll(paramFxQueryEditor.h.a(entity, null, false, false));
          } 
        });
    MenuButton menuButton2 = paramFxQueryEditor.g.f("edit", false);
    menuButton2.getItems().addAll((Object[])new MenuItem[] { (MenuItem)paramFxQueryEditor.h.an
          .y("showSchemaName"), (MenuItem)paramFxQueryEditor.h.an
          .y("showDataType"), (MenuItem)paramFxQueryEditor.h.an
          .y("joinedRouting"), (MenuItem)new SeparatorMenuItem(), paramFxQueryEditor.g
          
          .A("addResultPane"), (MenuItem)new SeparatorMenuItem(), (MenuItem)paramFxQueryEditor.g
          
          .x("exportImage").a(paramFxQueryEditor.h.an, new String[] { "printPng", "printJpg", "printGif" }), paramFxQueryEditor.h.an
          .A("print") });
    this.a = paramFxQueryEditor.g.f("connectorMenu", false);
    this.a.getItems().addAll(paramFxQueryEditor.g.e(new String[] { "inheritConnector", "chooseConnector" }));
    MenuButton menuButton3 = paramFxQueryEditor.g.f("filterMenu", false);
    menuButton3.showingProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          paramMenuButton.getItems().clear();
          Unit unit = paramFxQueryEditor.h.c.c();
          if (unit instanceof com.wisecoders.dbs.query.model.items.QueryColumn || unit instanceof com.wisecoders.dbs.query.model.items.QueryAggregate) {
            paramFxQueryEditor.h.a((AbstractQueryColumn)unit);
          } else if (unit instanceof QueryTable) {
            paramMenuButton.getItems().add(paramFxQueryEditor.g.B("Selected '" + String.valueOf(unit) + "'"));
            for (AbstractQueryColumn abstractQueryColumn : ((QueryTable)unit).b()) {
              if (abstractQueryColumn instanceof com.wisecoders.dbs.query.model.items.QueryColumn || abstractQueryColumn instanceof com.wisecoders.dbs.query.model.items.QueryAggregate) {
                MenuItem menuItem = new MenuItem(abstractQueryColumn.toString(), (Node)Rx.q("filter.png"));
                menuItem.setOnAction(());
                paramMenuButton.getItems().add(menuItem);
              } 
            } 
          } else {
            paramMenuButton.getItems().add(paramFxQueryEditor.g.B("infoSelectTable.text"));
          } 
        });
    MenuButton menuButton4 = paramFxQueryEditor.g.f("help", false);
    menuButton4.getItems().addAll(paramFxQueryEditor.g.e(new String[] { "help", "tipsTricks", "separator", "shortcutsManager" }));
    getItems().addAll((Object[])new Node[] { (Node)menuButton1, (Node)menuButton2, (Node)this.a });
    getItems().addAll(paramFxQueryEditor.g.c(new String[] { "run", "runScript", "stop" }));
    getItems().addAll((Object[])new Node[] { (Node)menuButton3, (Node)paramFxQueryEditor.h.an.n("groupBy"), (Node)paramFxQueryEditor.g.n("changeSplitOrientation") });
    getItems().addAll(paramFxQueryEditor.g.c(new String[] { "explain" }));
    getItems().addAll((Object[])new Node[] { (Node)menuButton4 });
    ProgressBarWithText progressBarWithText = new ProgressBarWithText();
    paramFxQueryEditor.g.a(progressBarWithText);
    getItems().add(progressBarWithText);
  }
  
  public void a() {
    String str = (this.b.a() == null) ? "Inherit" : this.b.a().getName();
    if (!StringUtil.areEqual(this.a.getText(), str))
      this.a.setText(str); 
  }
}
