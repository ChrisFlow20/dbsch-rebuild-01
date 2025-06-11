package com.wisecoders.dbs.editors.text;

import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.NumericTextField$;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class StyledEditorGoToLine extends ToolBar {
  private final StyledEditor b;
  
  private final NumericTextField$ c = new NumericTextField$();
  
  protected final GridPane$ a = (new GridPane$()).e();
  
  public StyledEditorGoToLine(StyledEditor paramStyledEditor) {
    this.b = paramStyledEditor;
    this.c.setEditable(true);
    this.c.setPromptText("Line Number");
    this.c.setOnAction(paramActionEvent -> e());
    Button button = new Button("Go");
    button.setOnAction(paramActionEvent -> {
          e();
          paramStyledEditor.requestFocus();
        });
    this.a.a((Node)this.c, "0,0,f,c");
    this.a.a((Node)button, "1,0,c,c");
    getItems().add(this.a);
  }
  
  public void a() {
    Platform.runLater(() -> {
          this.c.requestFocus();
          this.c.selectAll();
        });
  }
  
  private void e() {
    int i = this.b.a(this.c.a());
    this.c.setText("" + i);
    this.c.selectAll();
    this.b.requestFocus();
  }
  
  public boolean b() {
    if (this.c.isFocused()) {
      this.c.copy();
      return true;
    } 
    return false;
  }
  
  public boolean c() {
    if (this.c.isFocused()) {
      this.c.paste();
      return true;
    } 
    return false;
  }
  
  public boolean d() {
    if (this.c.isFocused()) {
      this.c.cut();
      return true;
    } 
    return false;
  }
}
