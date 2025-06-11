package com.wisecoders.dbs.editors.csv.fx;

import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class CsvEditorFindReplace extends CsvEditorFind {
  private final TextField e = new TextField();
  
  private final CheckBox f = new CheckBox("Complete Document");
  
  CsvEditorFindReplace(CsvEditorSkin paramCsvEditorSkin) {
    super(paramCsvEditorSkin);
    this.e.setPrefColumnCount(30);
    this.e.setPromptText("Replace with");
    Button button1 = new Button("Replace Next", (Node)BootstrapIcons.binoculars_fill.glyph(new String[] { "glyph-blue" }));
    button1.setOnAction(paramActionEvent -> {
          m();
          paramCsvEditorSkin.a.requestFocus();
        });
    Button button2 = new Button("Replace All", (Node)BootstrapIcons.binoculars_fill.glyph(new String[] { "glyph-blue" }));
    button2.setOnAction(paramActionEvent -> {
          n();
          paramCsvEditorSkin.a.requestFocus();
        });
    this.f.setSelected(true);
    a((Node)this.e, "0,1,f,c");
    a((Node)this.f, "1,1,l,c");
    a((Node)button1, "3,1,l,c");
    a((Node)button2, "4,1,l,c");
  }
  
  private void m() {
    if (a(false))
      this.a.a(this.e.getText()); 
  }
  
  private void n() {
    if (this.f.isSelected())
      this.a.a(); 
    while (a(false))
      this.a.a(this.e.getText()); 
    this.a.a.refresh();
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
