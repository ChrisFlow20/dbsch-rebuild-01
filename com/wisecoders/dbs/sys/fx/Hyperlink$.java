package com.wisecoders.dbs.sys.fx;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tooltip;

public class Hyperlink$ extends Hyperlink {
  public Hyperlink$(Node paramNode, EventHandler paramEventHandler) {
    super(null, paramNode);
    setOnAction(paramEventHandler);
  }
  
  public Hyperlink$(String paramString, EventHandler paramEventHandler) {
    super(paramString);
    setOnAction(paramEventHandler);
  }
  
  public Hyperlink$ a() {
    setUnderline(true);
    return this;
  }
  
  public Hyperlink$ a(String paramString) {
    setTooltip(new Tooltip(paramString));
    return this;
  }
}
