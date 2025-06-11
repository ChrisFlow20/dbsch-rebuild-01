package com.wisecoders.dbs.editors.csv.fx;

import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CsvEditorFind extends GridPane$ {
  protected final CsvEditorSkin a;
  
  private final TextField e = new TextField();
  
  private final CheckBox f = new CheckBox("Match Case");
  
  private final CheckBox g = new CheckBox("RegExp");
  
  CsvEditorFind(CsvEditorSkin paramCsvEditorSkin) {
    this.a = paramCsvEditorSkin;
    this.e.setPrefColumnCount(30);
    this.e.setPromptText("Text to find");
    this.e.setOnAction(paramActionEvent -> a(true));
    this.e.setOnKeyPressed(paramKeyEvent -> {
          if (paramKeyEvent.getCode() == KeyCode.F3)
            a(true); 
        });
    Button button1 = new Button("Find Next (F3)", (Node)BootstrapIcons.binoculars_fill.glyph(new String[] { "glyph-blue" }));
    button1.setOnAction(paramActionEvent -> {
          a(true);
          paramCsvEditorSkin.a.requestFocus();
        });
    Button button2 = new Button(null, (Node)BootstrapIcons.x_lg.glyph(new String[0]));
    button2.setOnAction(paramActionEvent -> paramCsvEditorSkin.f());
    setHgap(10.0D);
    setVgap(5.0D);
    setPadding(new Insets(5.0D, 5.0D, 5.0D, 5.0D));
    a(new int[] { -2, -2, -2, -2, -2, -1 });
    a((Node)this.f, "1,0,l,c");
    a((Node)this.g, "2,0,l,c");
    a((Node)button1, "3,0,l,c");
    a((Node)button2, "5,0,r,t");
    a((Node)this.e, "0,0,f,c");
  }
  
  public void a() {
    Platform.runLater(() -> {
          this.e.requestFocus();
          this.e.selectAll();
        });
  }
  
  boolean a(boolean paramBoolean) {
    String str = this.e.getText();
    if (!this.g.isSelected())
      str = Pattern.quote(str); 
    this.a.a(Pattern.compile(str, this.f.isSelected() ? 0 : 2));
    return this.a.a(paramBoolean);
  }
  
  public boolean b() {
    if (this.e.isFocused()) {
      this.e.copy();
      return true;
    } 
    return false;
  }
  
  public boolean c() {
    if (this.e.isFocused()) {
      this.e.paste();
      return true;
    } 
    return false;
  }
  
  public boolean d() {
    if (this.e.isFocused()) {
      this.e.cut();
      return true;
    } 
    return false;
  }
}
