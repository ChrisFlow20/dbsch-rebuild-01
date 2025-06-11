package com.wisecoders.dbs.sys.fx;

import javafx.scene.Node;
import javafx.scene.control.Tab;

public class UnclosableTab extends Tab {
  public UnclosableTab(String paramString, Node paramNode) {
    super(paramString, paramNode);
    setClosable(false);
  }
}
