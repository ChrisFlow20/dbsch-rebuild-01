package com.wisecoders.dbs.sys.fx;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class HBox$ extends HBox {
  public HBox$ d() {
    setSpacing(20.0D);
    return this;
  }
  
  public HBox$ e() {
    setSpacing(10.0D);
    return this;
  }
  
  public HBox$ f() {
    setSpacing(5.0D);
    return this;
  }
  
  public HBox$ a(double paramDouble) {
    setSpacing(paramDouble);
    return this;
  }
  
  public HBox$ g() {
    setPadding(GridPane$.b);
    return this;
  }
  
  public HBox$ b(double paramDouble) {
    setPadding(new Insets(paramDouble, paramDouble, paramDouble, paramDouble));
    return this;
  }
  
  public HBox$ h() {
    setPadding(new Insets(0.0D, 0.0D, 0.0D, 20.0D));
    return this;
  }
  
  public HBox$ i() {
    return d().g();
  }
  
  public HBox$ a(List paramList) {
    getChildren().addAll(paramList);
    return this;
  }
  
  public HBox$ a(Node... paramVarArgs) {
    getChildren().addAll((Object[])paramVarArgs);
    return this;
  }
  
  public HBox$ a(Node paramNode) {
    getChildren().add(paramNode);
    return this;
  }
  
  public HBox$ a(Pos paramPos) {
    setAlignment(paramPos);
    return this;
  }
  
  public HBox$ j() {
    setAlignment(Pos.CENTER);
    return this;
  }
}
