package com.wisecoders.dbs.config.fx;

import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.Window;

public class FxStringCellEditor extends ButtonDialog$ {
  private final TextArea a = new TextArea();
  
  public FxStringCellEditor(Window paramWindow, String paramString) {
    super(paramWindow);
    this.a.setText(paramString);
    this.a.setPrefColumnCount(40);
    this.a.setPrefRowCount(12);
  }
  
  public Node createContentPane() {
    return (Node)this.a;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean apply() {
    return true;
  }
  
  public String a() {
    return this.a.getText();
  }
}
