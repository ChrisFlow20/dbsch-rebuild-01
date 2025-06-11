package com.wisecoders.dbs.editors.text;

import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

class a extends StyledEditorFind {
  private final TextField e = new TextField();
  
  private final CheckBox f = new CheckBox("Complete Document");
  
  private static final int g = 300;
  
  a(StyledEditor paramStyledEditor) {
    super(paramStyledEditor);
    this.e.setPrefColumnCount(30);
    this.e.setPromptText("Replace with");
    Button button1 = new Button("Replace Next", (Node)BootstrapIcons.binoculars_fill.glyph(new String[] { "glyph-blue" }));
    button1.setOnAction(paramActionEvent -> {
          m();
          paramStyledEditor.requestFocus();
        });
    Button button2 = new Button("Replace All", (Node)BootstrapIcons.binoculars_fill.glyph(new String[] { "glyph-blue" }));
    button2.setOnAction(paramActionEvent -> {
          n();
          paramStyledEditor.requestFocus();
        });
    this.f.setSelected(true);
    a((Node)this.e, "0,1,f,c");
    a((Node)this.f, "1,1,l,c");
    a((Node)button1, "3,1,l,c");
    a((Node)button2, "4,1,l,c");
  }
  
  private void m() {
    this.a.K.c(this.e.getText());
    a(true, true, false, true);
  }
  
  private void n() {
    this.a.setVisible(false);
    if (this.f.isSelected())
      this.a.K.b(0, 0); 
    byte b = 0;
    while (a(true, true, false, false)) {
      this.a.K.c(this.e.getText());
      b++;
      if (b > 'Ĭ')
        this.a.K.b(true); 
    } 
    this.a.K.b(false);
    this.a.setVisible(true);
    this.a.J.q();
  }
  
  public boolean b() {
    if (super.b())
      return true; 
    if (this.e.isFocused()) {
      this.e.copy();
      return true;
    } 
    return false;
  }
  
  public boolean c() {
    if (super.c())
      return true; 
    if (this.e.isFocused()) {
      this.e.paste();
      return true;
    } 
    return false;
  }
  
  public boolean d() {
    if (super.d())
      return true; 
    if (this.e.isFocused()) {
      this.e.cut();
      return true;
    } 
    return false;
  }
}
