package com.wisecoders.dbs.sys.fx;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

public class FlowPane$ extends FlowPane {
  private static final Insets a = new Insets(10.0D, 10.0D, 10.0D, 10.0D);
  
  public FlowPane$ a() {
    setHgap(10.0D);
    setVgap(10.0D);
    return this;
  }
  
  public FlowPane$ a(double paramDouble) {
    setHgap(paramDouble);
    setVgap(paramDouble);
    return this;
  }
  
  public FlowPane$ b() {
    setHgap(10.0D);
    getStyleClass().add("button-bar");
    return this;
  }
  
  public FlowPane$ c() {
    setPrefWidth(10.0D);
    return this;
  }
  
  public FlowPane$ d() {
    setAlignment(Pos.TOP_RIGHT);
    return this;
  }
  
  public FlowPane$ e() {
    setPadding(a);
    return this;
  }
  
  public FlowPane$ f() {
    setPadding(GridPane$.d);
    setHgap(8.0D);
    setHgap(5.0D);
    return this;
  }
  
  public FlowPane$ g() {
    setHgap(8.0D);
    setHgap(5.0D);
    return this;
  }
  
  public FlowPane$ h() {
    return a().e();
  }
  
  public FlowPane$ a(Node... paramVarArgs) {
    getChildren().addAll((Object[])paramVarArgs);
    return this;
  }
  
  public FlowPane$ b(Node... paramVarArgs) {
    for (Node node : paramVarArgs) {
      if (node.isVisible())
        getChildren().add(node); 
    } 
    return this;
  }
  
  public FlowPane$ a(List paramList) {
    getChildren().addAll(paramList);
    return this;
  }
  
  public FlowPane$ i() {
    setAlignment(Pos.CENTER);
    return this;
  }
  
  public FlowPane$ a(String paramString) {
    getStyleClass().add(paramString);
    return this;
  }
  
  public FlowPane$ a(Pos paramPos) {
    setAlignment(paramPos);
    return this;
  }
  
  public FlowPane$ j() {
    GridPane$.setFillWidth((Node)this, Boolean.valueOf(true));
    return this;
  }
}
