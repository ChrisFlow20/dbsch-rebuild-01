package com.wisecoders.dbs.sys.fx;

import javafx.scene.Node;
import javafx.scene.control.ButtonBar;

public class ButtonBar$ extends ButtonBar {
  public ButtonBar$ a(Node... paramVarArgs) {
    getButtons().addAll((Object[])paramVarArgs);
    return this;
  }
}
