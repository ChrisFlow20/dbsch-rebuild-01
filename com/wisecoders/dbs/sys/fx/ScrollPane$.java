package com.wisecoders.dbs.sys.fx;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

public class ScrollPane$ extends ScrollPane {
  public ScrollPane$(Node paramNode) {
    super(paramNode);
  }
  
  public ScrollPane$ a() {
    setFitToHeight(true);
    setFitToWidth(true);
    return this;
  }
}
