package com.wisecoders.dbs.sys.fx;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class HGrowBox$ extends HBox {
  public HGrowBox$() {
    HBox.setHgrow((Node)this, Priority.ALWAYS);
  }
}
