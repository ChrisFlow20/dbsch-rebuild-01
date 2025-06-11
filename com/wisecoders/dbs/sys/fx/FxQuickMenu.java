package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.Rx;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.PopupControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class FxQuickMenu extends PopupControl {
  public static final int b = 3;
  
  public static final int c = -55;
  
  public static final String d = "-fx-background-color:-fx-base;";
  
  public final HBox e = new HBox();
  
  public FxQuickMenu() {
    setAutoFix(true);
    setAutoHide(true);
    setHideOnEscape(true);
    setConsumeAutoHidingEvents(false);
    this.e.setStyle("-fx-background-color:-fx-base;");
    getScene().setRoot((Parent)new DraggablePane((Node)this.e));
    this.e.getStyleClass().add("quick-menu");
  }
  
  public void b() {
    this.e.getChildren().clear();
  }
  
  public FxQuickMenu c() {
    this.e.setPadding(new Insets(2.0D, 2.0D, 2.0D, 2.0D));
    return this;
  }
  
  public FxQuickMenu d() {
    this.e.addEventHandler(MouseEvent.MOUSE_CLICKED, paramMouseEvent -> hide());
    return this;
  }
  
  public FxQuickMenu e() {
    return c().d();
  }
  
  public FxQuickMenu a(Node paramNode) {
    this.e.getChildren().add(paramNode);
    return this;
  }
  
  public FxQuickMenu a(List paramList) {
    this.e.getChildren().addAll(paramList);
    return this;
  }
  
  public FxQuickMenu b(List paramList) {
    this.e.getChildren().setAll(paramList);
    return this;
  }
  
  public void f() {
    Objects.requireNonNull(this.e.getChildren());
    Iterator<Node> iterator = this.e.getChildren().iterator();
    if (License.a().f())
      while (iterator.hasNext()) {
        Node node = iterator.next();
        if (node.getStyleClass().contains("e4se"))
          Rx.a(node); 
      }  
  }
}
