package com.wisecoders.dbs.sys.fx;

import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class VGrowBox$ extends VBox {
  public VGrowBox$() {
    VBox.setVgrow((Node)this, Priority.ALWAYS);
  }
}
