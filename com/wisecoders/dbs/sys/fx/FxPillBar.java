package com.wisecoders.dbs.sys.fx;

import java.net.URL;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class FxPillBar extends HBox {
  private final ToggleGroup d = new ToggleGroup();
  
  public static final int a = 0;
  
  public static final int b = 1;
  
  public static final int c = 2;
  
  public FxPillBar() {
    URL uRL = FxPillBar.class.getResource("resources/" + FxPillBar.class.getSimpleName() + ".css");
    if (uRL != null)
      getStylesheets().add(uRL.toExternalForm()); 
  }
  
  public ToggleButton a(String paramString, int paramInt, boolean paramBoolean) {
    ToggleButton toggleButton = a(paramString, paramInt);
    toggleButton.setSelected(paramBoolean);
    return toggleButton;
  }
  
  public ToggleButton a(String paramString, int paramInt) {
    ToggleButton toggleButton = new ToggleButton(paramString);
    toggleButton.setToggleGroup(this.d);
    getChildren().add(toggleButton);
    return toggleButton;
  }
}
