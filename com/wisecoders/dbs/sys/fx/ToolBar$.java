package com.wisecoders.dbs.sys.fx;

import javafx.scene.Node;
import javafx.scene.control.ToolBar;

public class ToolBar$ extends ToolBar {
  public ToolBar$ a(Node... paramVarArgs) {
    getItems().addAll((Object[])paramVarArgs);
    return this;
  }
  
  public ToolBar$ a(String paramString) {
    getStyleClass().add(paramString);
    return this;
  }
}
