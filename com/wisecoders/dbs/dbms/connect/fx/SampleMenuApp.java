package com.wisecoders.dbs.dbms.connect.fx;

import java.util.AbstractMap;
import java.util.Map;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class SampleMenuApp extends Application {
  final i[] a = new i[] { new i(this, "Apple", "The apple is the pomaceous fruit of the apple tree, species Malus domestica in the rose family (Rosaceae). It is one of the most widely cultivated tree fruits, and the most widely known of the many members of genus Malus that are used by humans. The tree originated in Western Asia, where its wild ancestor, the Alma, is still found today.", "Malus domestica"), new i(this, "Hawthorn", "The hawthorn is a large genus of shrubs and trees in the rose family, Rosaceae, native to temperate regions of the Northern Hemisphere in Europe, Asia and North America. The name hawthorn was originally applied to the species native to northern Europe, especially the Common Hawthorn C. monogyna, and the unmodified name is often so used in Britain and Ireland.", "Crataegus monogyna"), new i(this, "Ivy", "The ivy is a flowering plant in the grape family (Vitaceae) native to eastern Asia in Japan, Korea, and northern and eastern China. It is a deciduous woody vine growing to 30 m tall or more given suitable support,  attaching itself by means of numerous small branched tendrils tipped with sticky disks.", "Parthenocissus tricuspidata"), new i(this, "Quince", "The quince is the sole member of the genus Cydonia and is native to warm-temperate southwest Asia in the Caucasus region. The immature fruit is green with dense grey-white pubescence, most of which rubs off before maturity in late autumn when the fruit changes color to yellow with hard, strongly perfumed flesh.", "Cydonia oblonga") };
  
  final String[] b = new String[] { "Title", "Binomial name", "Picture", "Description" };
  
  final Map.Entry[] c = new Map.Entry[] { new AbstractMap.SimpleEntry<>("Sepia Tone", new SepiaTone()), new AbstractMap.SimpleEntry<>("Glow", new Glow()), new AbstractMap.SimpleEntry<>("Shadow", new DropShadow()) };
  
  final ImageView d = new ImageView();
  
  final Label e = new Label();
  
  final Label f = new Label();
  
  final Label g = new Label();
  
  private int h = -1;
  
  public static void main(String[] paramArrayOfString) {
    launch(paramArrayOfString);
  }
  
  public void start(Stage paramStage) {
    paramStage.setTitle("Menu Sample");
    Scene scene = new Scene((Parent)new VBox(), 400.0D, 350.0D);
    scene.setFill((Paint)Color.OLDLACE);
    this.e.setFont(new Font("Verdana Bold", 22.0D));
    this.f.setFont(new Font("Arial Italic", 10.0D));
    this.d.setFitHeight(150.0D);
    this.d.setPreserveRatio(true);
    this.g.setWrapText(true);
    this.g.setTextAlignment(TextAlignment.JUSTIFY);
    a();
    MenuBar menuBar = new MenuBar();
    VBox vBox = new VBox();
    vBox.setAlignment(Pos.CENTER);
    vBox.setSpacing(10.0D);
    vBox.setPadding(new Insets(0.0D, 10.0D, 0.0D, 10.0D));
    vBox.getChildren().addAll((Object[])new Node[] { (Node)this.e, (Node)this.f, (Node)this.d, (Node)this.g });
    Menu menu1 = new Menu("File");
    MenuItem menuItem = new MenuItem("Shuffle", (Node)new ImageView(new Image("/icons/new.png")));
    menuItem.setOnAction(new SampleMenuApp$1(this, vBox));
    menu1.getItems().addAll((Object[])new MenuItem[] { menuItem });
    Menu menu2 = new Menu("Edit");
    Menu menu3 = new Menu("View");
    menuBar.getMenus().addAll((Object[])new Menu[] { menu1, menu2, menu3 });
    Platform.runLater(() -> paramMenuBar.setUseSystemMenuBar(true));
    ((VBox)scene.getRoot()).getChildren().addAll((Object[])new Node[] { (Node)menuBar, (Node)vBox });
    paramStage.setScene(scene);
    paramStage.show();
  }
  
  private void a() {
    int j = this.h;
    while (j == this.h)
      j = (int)(Math.random() * this.a.length); 
    this.d.setImage((this.a[j]).d);
    this.e.setText((this.a[j]).a);
    this.f.setText("(" + (this.a[j]).c + ")");
    this.g.setText((this.a[j]).b);
    this.h = j;
  }
}
