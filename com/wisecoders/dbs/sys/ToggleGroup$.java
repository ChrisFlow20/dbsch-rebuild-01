package com.wisecoders.dbs.sys;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class ToggleGroup$ extends ToggleGroup {
  public ToggleGroup$(ToggleButton... paramVarArgs) {
    for (ToggleButton toggleButton : paramVarArgs)
      toggleButton.setToggleGroup(this); 
  }
}
