package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.DbSchema;
import com.wisecoders.dbs.project.fx.Theme;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Keys;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.HtmlLabel;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class FxAboutDialog extends Dialog$ {
  public FxAboutDialog(Window paramWindow) {
    super(paramWindow);
    showDialog();
  }
  
  public Node createContentPane() {
    String str1 = this.rx.H("info");
    License license = License.a();
    str1 = str1.replace("{licenseOwner}", (Keys.g.a() != null) ? (this.rx.H("owner") + " " + this.rx.H("owner")) : "");
    str1 = str1.replace("{license}", license.a(true));
    String str2 = "DbSchema " + DbSchema.class.getPackage().getSpecificationVersion() + " build " + Log.c;
    str1 = str1.replace("{build}", str2);
    str1 = str1.replace("{java.version}", System.getProperty("java.version"));
    str1 = str1.replace("{javafx.version}", System.getProperty("javafx.version"));
    str1 = str1.replace("{java.vendor}", System.getProperty("java.vendor"));
    str1 = str1.replace("{java.home}", System.getProperty("java.home"));
    str1 = str1.replace("{profileId}", Keys.f.a());
    HtmlLabel htmlLabel = new HtmlLabel(str1);
    ScrollPane scrollPane = new ScrollPane((Node)htmlLabel);
    scrollPane.setPrefSize(400.0D, 550.0D);
    return (Node)new BorderPane((Node)scrollPane, null, null, null, (Node)(new FxAnimatedLogo(false)).a());
  }
  
  public void createButtons() {
    createCloseButton();
    createActionButton("fullScreenLogo", ButtonBar.ButtonData.LEFT);
  }
  
  @Action
  public void fullScreenLogo() {
    BorderPane borderPane = new BorderPane();
    borderPane.getStyleClass().add("animated-logo");
    borderPane.setCenter((Node)(new FxAnimatedLogo(true)).a());
    Scene scene = new Scene((Parent)borderPane, 600.0D, 600.0D, true, SceneAntialiasing.BALANCED);
    scene.getStylesheets().addAll((Theme.a()).f);
    Stage stage = new Stage();
    stage.setFullScreenExitHint("");
    stage.setFullScreen(true);
    stage.setTitle("DbSchema Logo");
    stage.setScene(scene);
    stage.show();
  }
  
  public boolean apply() {
    return true;
  }
}
