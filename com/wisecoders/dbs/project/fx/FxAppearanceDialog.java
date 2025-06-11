package com.wisecoders.dbs.project.fx;

import com.wisecoders.dbs.config.fx.FxSettingsDialog;
import com.wisecoders.dbs.config.fx.FxSettingsDialog$SelectTab;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.HGrowBox$;
import com.wisecoders.dbs.sys.fx.RowPane$;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;

public class FxAppearanceDialog extends ButtonDialog$ {
  private static final int a = 2;
  
  private final Scene b;
  
  private final ComboBox c = new ComboBox();
  
  private final CheckBox d;
  
  FxAppearanceDialog(Scene paramScene) {
    super(paramScene.getWindow());
    this.b = paramScene;
    this.d = this.rx.v("doNotSendDataCheck");
    this.c.getItems().addAll((Object[])new String[] { "90%", "100%", "125%", "150%", "175%", "200%", "300%", "400%" });
    this.c.setValue(Pref.c("UIScaling", "100%"));
    this.c.setOnAction(paramActionEvent -> {
          if (a())
            this.rx.a(getDialogScene(), "scalingInfo", new String[0]); 
        });
    showDialog();
  }
  
  private boolean a() {
    String str1 = (String)this.c.getValue();
    String str2 = Pref.c("UIScaling", "100%");
    if (!StringUtil.areEqual(str1, str2)) {
      if ("100%".equals(str1)) {
        Pref.b("UIScaling");
      } else {
        Pref.a("UIScaling", str1);
      } 
      return true;
    } 
    return false;
  }
  
  public Node createContentPane() {
    RowPane$ rowPane$ = (new RowPane$()).g();
    rowPane$.a(new Node[] { (Node)this.rx.e("scalingLabel"), (Node)this.c, (Node)new HGrowBox$(), (Node)this.rx.j("languageSettings") });
    byte b = 0;
    for (Theme theme : Theme.values()) {
      ImageView imageView = new ImageView(theme.i);
      Button button = new Button(theme.toString(), (Node)imageView);
      button.setContentDisplay(ContentDisplay.BOTTOM);
      button.setOnAction(paramActionEvent -> {
            this.b.getStylesheets().setAll(paramTheme.f);
            paramTheme.b();
          });
      if (b % 2 == 0) {
        rowPane$.a((Node)(new HBox$()).a(3.0D).a(Pos.CENTER_LEFT).a((Node)button));
      } else {
        rowPane$.c((Node)button);
      } 
      b++;
    } 
    return (Node)rowPane$;
  }
  
  @Action
  public void languageSettings() {
    (new FxSettingsDialog(getDialogScene(), FxSettingsDialog$SelectTab.a, Sys.B.locale)).showDialog();
  }
  
  public void createButtons() {
    createCloseButton();
  }
  
  public boolean apply() {
    return true;
  }
}
