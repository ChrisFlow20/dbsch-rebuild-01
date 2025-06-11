package com.wisecoders.dbs.editors.text;

import javafx.event.Event;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.layout.Region;
import javafx.stage.WindowEvent;

public class StyledEditorCompletionMenu extends ContextMenu {
  protected final StyledEditorSkin a;
  
  public StyledEditorCompletionMenu(StyledEditorSkin paramStyledEditorSkin) {
    this.a = paramStyledEditorSkin;
    setAutoHide(true);
    setOnHiding(paramWindowEvent -> getItems().clear());
    addEventHandler(Menu.ON_SHOWING, paramEvent -> {
          Node node = getSkin().getNode();
          if (node instanceof Region)
            ((Region)node).setMaxHeight(400.0D); 
        });
  }
  
  public void a() {
    if (!getItems().isEmpty() && !isShowing()) {
      Bounds bounds = ((StyledEditor)this.a.getSkinnable()).getBoundsInLocal();
      Point2D point2D = ((StyledEditor)this.a.getSkinnable()).localToScreen(bounds.getMinX(), bounds.getMinY());
      show((Node)this.a.getSkinnable(), point2D.getX() + this.a.k(), point2D.getY() + this.a.l());
    } 
  }
}
