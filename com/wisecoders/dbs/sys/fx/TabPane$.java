package com.wisecoders.dbs.sys.fx;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class TabPane$ extends TabPane {
  public TabPane$ a(String paramString, Node paramNode) {
    return a(null, paramString, paramNode);
  }
  
  public TabPane$ a(Node paramNode1, String paramString, Node paramNode2) {
    Tab tab = new Tab(paramString, paramNode2);
    tab.setGraphic(paramNode1);
    getTabs().add(tab);
    return this;
  }
  
  public TabPane$ a(Tab paramTab) {
    getTabs().add(paramTab);
    return this;
  }
}
