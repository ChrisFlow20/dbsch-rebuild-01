package com.wisecoders.dbs.sys.fx;

import java.util.HashMap;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;

public class ControlBehaviour extends HashMap {
  public void a(KeyEvent paramKeyEvent) {
    for (KeyCodeCombination keyCodeCombination : keySet()) {
      if (keyCodeCombination.match(paramKeyEvent)) {
        ((EventHandler)get(keyCodeCombination)).handle((Event)paramKeyEvent);
        paramKeyEvent.consume();
      } 
    } 
  }
}
