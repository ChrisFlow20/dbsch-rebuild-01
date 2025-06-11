package com.wisecoders.dbs.diagram.fx;

import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.FxUtil;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class FxSplitWithTabPane extends SplitPane {
  protected final TabPane f = new TabPane();
  
  public FxSplitWithTabPane() {
    setOrientation(Orientation.VERTICAL);
    Rx.b(this.f);
    this.f.getTabs().addListener(paramChange -> {
          if (this.f.getTabs().isEmpty())
            getItems().remove(this.f); 
        });
    this.f.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramTab1, paramTab2) -> {
          if (paramTab1 != null)
            FxUtil.a(paramTab1.getContent(), true); 
          if (paramTab2 != null)
            FxUtil.a(paramTab2.getContent(), false); 
        });
    this.f.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
    FxSplitWithTabPane$1 fxSplitWithTabPane$1 = new FxSplitWithTabPane$1(this);
    widthProperty().addListener(fxSplitWithTabPane$1);
    heightProperty().addListener(fxSplitWithTabPane$1);
  }
  
  protected void b(Tab paramTab) {
    a(2147483647, paramTab);
  }
  
  protected void a(int paramInt, Tab paramTab) {
    int i = Math.max(0, Math.min(this.f.getTabs().size(), paramInt));
    this.f.getTabs().add(i, paramTab);
    this.f.getSelectionModel().select(paramTab);
    if (!getItems().contains(this.f))
      getItems().add(this.f); 
    double[] arrayOfDouble = getDividerPositions();
    if (arrayOfDouble == null || arrayOfDouble.length == 0 || arrayOfDouble[0] > 0.9D)
      FxUtil.a(1.0D, paramActionEvent -> setDividerPosition(0, 0.5D)); 
  }
}
