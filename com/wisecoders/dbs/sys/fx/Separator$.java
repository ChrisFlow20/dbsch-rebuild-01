package com.wisecoders.dbs.sys.fx;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class Separator$ extends HBox {
  private final Label a = new Label();
  
  public Separator$(String paramString) {
    this.a.setText(paramString + " ");
    this.a.setStyle("-fx-font-size:110%");
    Separator separator = new Separator();
    HBox.setHgrow((Node)separator, Priority.ALWAYS);
    getChildren().addAll((Object[])new Node[] { (Node)this.a, (Node)separator });
    setAlignment(Pos.CENTER);
  }
  
  public void a(String paramString) {
    this.a.setText(paramString);
  }
}
