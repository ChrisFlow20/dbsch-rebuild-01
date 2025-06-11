package com.wisecoders.dbs.project.fx;

import com.wisecoders.dbs.DbSchema;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import javafx.application.Preloader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@DoNotObfuscate
public class SplashScreen extends Preloader {
  private Stage a;
  
  public void start(Stage paramStage) {
    this.a = paramStage;
    this.a.initStyle(StageStyle.UNDECORATED);
    this.a.setScene(createScene());
    this.a.show();
  }
  
  public Scene createScene() {
    Reflection reflection = new Reflection();
    reflection.setFraction(0.699999988079071D);
    Text text1 = new Text("DbSchema");
    text1.setEffect((Effect)reflection);
    text1.setCache(true);
    text1.setFill((Paint)Color.GRAY);
    text1.setFont(Font.font(null, FontWeight.BOLD, 32.0D));
    Text text2 = new Text(DbSchema.class.getPackage().getSpecificationVersion() + " #" + DbSchema.class.getPackage().getSpecificationVersion());
    text1.setCache(true);
    text1.setFill((Paint)Color.GRAY);
    text1.setFont(Font.font(null, FontWeight.BOLD, 18.0D));
    AnchorPane anchorPane = new AnchorPane();
    anchorPane.getStyleClass().add("wallpaper");
    anchorPane.getChildren().addAll((Object[])new Node[] { (Node)text1, (Node)text2 });
    AnchorPane.setLeftAnchor((Node)text1, Double.valueOf(20.0D));
    AnchorPane.setTopAnchor((Node)text1, Double.valueOf(20.0D));
    AnchorPane.setLeftAnchor((Node)text2, Double.valueOf(30.0D));
    AnchorPane.setTopAnchor((Node)text2, Double.valueOf(60.0D));
    Scene scene = new Scene((Parent)anchorPane, 300.0D, 200.0D);
    scene.getStylesheets().add(Sys.x.resolve("base-light.css").toString());
    return scene;
  }
  
  public void handleStateChangeNotification(Preloader.StateChangeNotification paramStateChangeNotification) {
    if (paramStateChangeNotification.getType() == Preloader.StateChangeNotification.Type.BEFORE_START)
      this.a.hide(); 
  }
}
