package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class PasswordFieldWithEye extends BorderPane {
  private static final String a = "Password";
  
  private final PasswordField b = new PasswordField();
  
  private String c;
  
  public PasswordFieldWithEye() {
    setCenter((Node)this.b);
    this.b.setPromptText("Password");
    Button button = new Button(null, (Node)BootstrapIcons.eye.glyph(new String[0]));
    button.addEventFilter(MouseEvent.MOUSE_PRESSED, paramMouseEvent -> {
          this.c = this.b.getText();
          this.b.clear();
          this.b.setPromptText(this.c);
        });
    button.addEventFilter(MouseEvent.MOUSE_RELEASED, paramMouseEvent -> {
          this.b.setText(this.c);
          this.b.setPromptText("Password");
        });
    setRight((Node)button);
  }
  
  public void a(String paramString) {
    this.b.setText(paramString);
  }
  
  public String a() {
    return this.b.getText();
  }
}
