package com.wisecoders.dbs.config.fx;

import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Window;

public class FxTextCellEditor extends ButtonDialog$ {
  private final TextField a = new TextField();
  
  public FxTextCellEditor(Window paramWindow, String paramString) {
    super(paramWindow);
    this.a.setText(paramString);
    this.a.setPrefColumnCount(40);
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
