package com.wisecoders.dbs.sys.fx;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;

public class MenuItem$ extends MenuItem {
  public MenuItem$(String paramString, ResourceCallback paramResourceCallback) {
    this(paramString, null, paramResourceCallback);
  }
  
  public MenuItem$(String paramString, Node paramNode, ResourceCallback paramResourceCallback) {
    super(paramString, paramNode);
    setOnAction(paramActionEvent -> paramResourceCallback.evaluate());
  }
}
