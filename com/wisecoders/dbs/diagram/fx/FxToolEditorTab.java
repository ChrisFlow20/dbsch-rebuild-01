package com.wisecoders.dbs.diagram.fx;

import com.wisecoders.dbs.diagram.model.FxToolEditor;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.FxUtil;
import java.util.ArrayList;
import java.util.Collection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class FxToolEditorTab extends Tab {
  private final Rx a = new Rx(FxToolEditorTab.class, this);
  
  private final Label b = new Label();
  
  private final int c;
  
  private double d;
  
  public FxToolEditorTab(FxToolEditor paramFxToolEditor) {
    this(paramFxToolEditor, false);
  }
  
  public void a(String paramString) {
    this.b.setText(paramString);
  }
  
  public FxToolEditor a() {
    return (FxToolEditor)getContent();
  }
  
  @Action
  public void renameTab() {
    String str = this.a.b(getContent().getScene(), "renameTab", a().h());
    if (StringUtil.isFilledTrim(str) && a().j() != null)
      a().j().rename(str); 
  }
  
  @Action
  public void closeTab() {
    a().i();
    getTabPane().getTabs().remove(this);
  }
  
  @Action
  public void closeOtherTabs() {
    ArrayList arrayList = new ArrayList((Collection<?>)getTabPane().getTabs());
    for (Tab tab : arrayList) {
      if (tab != this && tab instanceof FxToolEditorTab) {
        ((FxToolEditorTab)tab).a().i();
        getTabPane().getTabs().remove(tab);
      } 
    } 
  }
  
  @Action
  public void moveTabFirst() {
    TabPane tabPane = getTabPane();
    tabPane.getTabs().remove(this);
    tabPane.getTabs().add(0, this);
    tabPane.getSelectionModel().select(this);
  }
  
  @Action
  public void moveTabLeft() {
    TabPane tabPane = getTabPane();
    int i = getTabPane().getTabs().indexOf(this);
    tabPane.getTabs().remove(this);
    tabPane.getTabs().add(i - 1, this);
    tabPane.getSelectionModel().select(this);
  }
  
  @Action
  public void moveTabLast() {
    TabPane tabPane = getTabPane();
    tabPane.getTabs().remove(this);
    tabPane.getTabs().add(tabPane.getTabs().size() - this.c, this);
    tabPane.getSelectionModel().select(this);
  }
  
  @Action
  public void moveTabRight() {
    TabPane tabPane = getTabPane();
    int i = tabPane.getTabs().indexOf(this);
    tabPane.getTabs().remove(this);
    tabPane.getTabs().add(i + 1, this);
    tabPane.getSelectionModel().select(this);
  }
  
  @Action
  public void floatTab() {
    TabPane tabPane1 = getTabPane();
    Scene scene1 = tabPane1.getScene();
    Stage stage = new Stage();
    if (a() instanceof Node)
      stage.initOwner(((Node)a()).getScene().getWindow()); 
    stage.initModality(Modality.NONE);
    TabPane tabPane2 = new TabPane();
    Timeline timeline = new Timeline(new KeyFrame[] { new KeyFrame(Duration.seconds(0.5D), paramActionEvent -> {
              for (Tab tab : paramTabPane.getTabs()) {
                if (tab.getContent() instanceof FxToolEditor)
                  ((FxToolEditor)tab.getContent()).a(true, 0); 
              } 
            }new javafx.animation.KeyValue[0]) });
    timeline.setCycleCount(-1);
    timeline.play();
    stage.setOnCloseRequest(paramWindowEvent -> {
          paramTabPane1.getTabs().remove(this);
          paramTabPane2.getTabs().add(0, this);
          paramTimeline.stop();
        });
    getTabPane().getTabs().remove(this);
    Node node = getContent();
    if (node instanceof FxLayoutPane) {
      FxLayoutPane fxLayoutPane = (FxLayoutPane)node;
      fxLayoutPane.getWorkspace().a((FxLayoutPane)getContent());
    } 
    FxUtil.a(getContent(), false);
    tabPane2.getTabs().add(this);
    tabPane2.getTabs().addListener(paramChange -> {
          if (paramTabPane.getTabs().isEmpty())
            paramStage.close(); 
        });
    Scene scene2 = new Scene((Parent)tabPane2, 900.0D, 600.0D);
    if (scene1 != null)
      scene2.getStylesheets().addAll((Collection)scene1.getStylesheets()); 
    stage.setTitle(a().h());
    stage.setScene(scene2);
    stage.sizeToScene();
    stage.show();
    stage.toFront();
    tabPane2.requestLayout();
    tabPane2.requestFocus();
  }
  
  public FxToolEditorTab(FxToolEditor paramFxToolEditor, boolean paramBoolean) {
    this.d = 0.5D;
    this.c = paramBoolean ? 1 : 0;
    setGraphic((Node)this.b);
    a(paramFxToolEditor.h());
    this.b.setOnMouseClicked(paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() == 2)
            maximizeTab(); 
        });
    try {
      this.b.setGraphic((paramFxToolEditor.n() != null) ? (Node)Rx.q(paramFxToolEditor.n()) : (Node)paramFxToolEditor.m().glyph(new String[0]));
    } catch (Throwable throwable) {
      Log.b(throwable);
    } 
    setOnSelectionChanged(paramEvent -> paramFxToolEditor.a(isSelected()));
    setContent((Node)paramFxToolEditor);
    FxUtil.a((Node)paramFxToolEditor, true);
    setOnCloseRequest(paramEvent -> {
          if (paramFxToolEditor.e()) {
            paramFxToolEditor.g();
          } else {
            getTabPane().setTabDragPolicy(TabPane.TabDragPolicy.FIXED);
            paramEvent.consume();
            Platform.runLater(());
          } 
        });
    ContextMenu contextMenu = new ContextMenu();
    contextMenu.getItems().add(new MenuItem("Blind"));
    contextMenu.setOnShowing(paramWindowEvent -> {
          paramContextMenu.getItems().clear();
          paramContextMenu.getItems().addAll(this.a.e(new String[] { "renameTab" }));
          if (a().o())
            paramContextMenu.getItems().addAll((Object[])new MenuItem[] { this.a.A("cloneEditor") }); 
          paramContextMenu.getItems().addAll(this.a.e(new String[] { "separator", "floatTab", "maximizeTab" }));
          if (getTabPane() != null && getTabPane().getTabs().size() > 1 + this.c) {
            paramContextMenu.getItems().add(new SeparatorMenuItem());
            int i = getTabPane().getTabs().indexOf(this);
            if (i > 1)
              paramContextMenu.getItems().addAll(this.a.e(new String[] { "moveTabLeft" })); 
            if (i < getTabPane().getTabs().size() - 2 - this.c)
              paramContextMenu.getItems().addAll(this.a.e(new String[] { "moveTabRight" })); 
            if (i > 0)
              paramContextMenu.getItems().addAll(this.a.e(new String[] { "moveTabFirst" })); 
            if (i < getTabPane().getTabs().size() - 1 - this.c)
              paramContextMenu.getItems().addAll(this.a.e(new String[] { "moveTabLast" })); 
          } 
          paramContextMenu.getItems().addAll(this.a.e(new String[] { "separator", "closeTab", "closeOtherTabs" }));
        });
    setContextMenu(contextMenu);
  }
  
  @Action
  public void maximizeTab() {
    Parent parent = getTabPane().getParent();
    while (parent != null && !(parent instanceof SplitPane))
      parent = parent.getParent(); 
    if (parent != null) {
      SplitPane splitPane = (SplitPane)parent;
      if ((splitPane.getDividerPositions()).length > 0) {
        double d = splitPane.getDividerPositions()[0];
        splitPane.setDividerPosition(0, (d > 0.01D) ? 0.0D : this.d);
        this.d = d;
      } 
    } 
  }
  
  @Action
  public void cloneEditor() {
    a().p();
  }
}
