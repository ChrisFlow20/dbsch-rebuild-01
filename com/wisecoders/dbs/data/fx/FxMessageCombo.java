package com.wisecoders.dbs.data.fx;

import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.Alert$;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public abstract class FxMessageCombo extends HBox {
  private final Rx a;
  
  private final Hyperlink b;
  
  private String c;
  
  private Throwable d;
  
  public FxMessageCombo() {
    this.a = new Rx(FxMessageCombo.class, this);
    this.b = new Hyperlink();
    HBox.setHgrow((Node)this.b, Priority.SOMETIMES);
    this.b.setUnderline(false);
    this.b.setOnAction(paramActionEvent -> showDetailsDialog());
    getChildren().addAll((Object[])new Node[] { (Node)this.b, (Node)this.a.j("showDetailsDialog") });
    setAlignment(Pos.CENTER_LEFT);
  }
  
  public void a(String paramString) {
    this.c = paramString;
    this.d = null;
    this.b.setText(b(paramString));
    this.b.getStyleClass().removeAll((Object[])new String[] { "text-blue", "text-red" });
    this.b.getStyleClass().add("text-blue");
  }
  
  public void a(String paramString, Throwable paramThrowable) {
    this.c = paramString;
    this.d = paramThrowable;
    this.b.setText(b(paramString));
    this.b.getStyleClass().removeAll((Object[])new String[] { "text-blue", "text-red" });
    this.b.getStyleClass().add("text-red");
  }
  
  @Action
  public void showDetailsDialog() {
    (new Alert$((this.d == null) ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR))
      .a(getScene())
      .a("Result Information")
      .b(StringUtil.cutOfWithDots(a(), 200))
      .c(StringUtil.cutOfNoDots(this.c, 600)).a(this.d);
    this.a.a(getScene(), StringUtil.cutOfNoDots(this.c, 600), new String[0]);
  }
  
  static String b(String paramString) {
    if (paramString != null) {
      StringBuilder stringBuilder = new StringBuilder();
      byte b = 0;
      boolean bool = false;
      while (b < paramString.length() && stringBuilder.length() < 200) {
        char c = paramString.charAt(b);
        if (c == '\n' || c == '\r' || c == '\t' || c == ' ') {
          if (!bool)
            stringBuilder.append(' '); 
          bool = true;
        } else {
          stringBuilder.append(c);
          bool = false;
        } 
        b++;
      } 
      return stringBuilder.toString();
    } 
    return null;
  }
  
  public abstract String a();
}
