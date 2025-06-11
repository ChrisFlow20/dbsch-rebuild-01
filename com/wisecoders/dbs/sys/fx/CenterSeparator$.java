package com.wisecoders.dbs.sys.fx;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class CenterSeparator$ extends HBox {
  private final Label a = new Label();
  
  public CenterSeparator$(String paramString) {
    this.a.setText(paramString + " ");
    this.a.setStyle("-fx-font-size:110%");
    Separator separator1 = new Separator();
    HBox.setHgrow((Node)separator1, Priority.ALWAYS);
    Separator separator2 = new Separator();
    HBox.setHgrow((Node)separator2, Priority.ALWAYS);
    getChildren().addAll((Object[])new Node[] { (Node)separator1, (Node)this.a, (Node)separator2 });
    setAlignment(Pos.CENTER);
  }
  
  public void a(String paramString) {
    this.a.setText(paramString);
  }
}
