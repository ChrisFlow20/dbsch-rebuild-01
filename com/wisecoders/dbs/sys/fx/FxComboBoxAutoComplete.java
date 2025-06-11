package com.wisecoders.dbs.sys.fx;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FxComboBoxAutoComplete {
  private ComboBox b;
  
  String a = "";
  
  public FxComboBoxAutoComplete(ComboBox paramComboBox) {
    this.b = paramComboBox;
    paramComboBox.setTooltip(new Tooltip());
    paramComboBox.setOnKeyPressed(this::a);
    paramComboBox.setOnHidden(this::a);
  }
  
  public void a(KeyEvent paramKeyEvent) {
    KeyCode keyCode = paramKeyEvent.getCode();
    if (keyCode.isLetterKey())
      this.a += this.a; 
    if (keyCode == KeyCode.BACK_SPACE && this.a.length() > 0)
      this.a = this.a.substring(0, this.a.length() - 1); 
    if (keyCode == KeyCode.ESCAPE)
      this.a = ""; 
    if (this.a.length() == 0) {
      this.b.getTooltip().hide();
    } else {
      String str = this.a.toLowerCase();
      Object object = null;
      int i = Integer.MAX_VALUE;
      for (Object object1 : this.b.getItems()) {
        int j = object1.toString().toLowerCase().indexOf(str);
        if (j > -1 && j < i) {
          object = object1;
          i = j;
        } 
      } 
      this.b.getTooltip().setText(str);
      this.b.getTooltip().show((Node)this.b, 0.0D, -20.0D);
      if (object != null) {
        this.b.setValue(object);
        this.b.hide();
        this.b.show();
      } 
    } 
  }
  
  public void a(Event paramEvent) {
    this.a = "";
    this.b.getTooltip().hide();
  }
}
