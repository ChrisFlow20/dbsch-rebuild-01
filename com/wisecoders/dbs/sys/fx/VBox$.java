package com.wisecoders.dbs.sys.fx;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class VBox$ extends VBox {
  public VBox$ l() {
    setSpacing(10.0D);
    return this;
  }
  
  public VBox$ m() {
    setAlignment(Pos.CENTER);
    return this;
  }
  
  public VBox$ b(Pos paramPos) {
    setAlignment(paramPos);
    return this;
  }
  
  public VBox$ b(int paramInt) {
    setSpacing(paramInt);
    return this;
  }
  
  public VBox$ k() {
    return l().i();
  }
  
  public VBox$ j() {
    setPadding(GridPane$.d);
    setSpacing(10.0D);
    return this;
  }
  
  public VBox$ n() {
    setSpacing(10.0D);
    return this;
  }
  
  public VBox$ i() {
    setPadding(GridPane$.b);
    return this;
  }
  
  public VBox$ c(int paramInt) {
    setPadding(new Insets(paramInt, paramInt, paramInt, paramInt));
    return this;
  }
  
  public VBox$ b(List paramList) {
    getChildren().addAll(paramList);
    return this;
  }
  
  public VBox$ c(Node... paramVarArgs) {
    getChildren().addAll((Object[])paramVarArgs);
    return this;
  }
  
  public VBox$ d(Node paramNode) {
    getChildren().add(paramNode);
    return this;
  }
}
