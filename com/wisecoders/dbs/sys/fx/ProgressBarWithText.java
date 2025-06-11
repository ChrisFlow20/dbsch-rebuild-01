package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;

public class ProgressBarWithText extends HBox$ {
  public final Button a = new Button();
  
  public final ProgressBar b = new ProgressBar();
  
  public final Label c = new Label();
  
  private static final int d = 5;
  
  public ProgressBarWithText() {
    this(true);
  }
  
  public ProgressBarWithText(boolean paramBoolean) {
    this.b.setPrefSize(340.0D, 22.0D);
    this.b.setMaxWidth(340.0D);
    this.c.setMaxWidth(340.0D);
    setAlignment(Pos.CENTER);
    this.b.setMinHeight(this.c.getBoundsInLocal().getHeight() + 10.0D);
    this.c.setAlignment(Pos.CENTER);
    StackPane stackPane = new StackPane();
    stackPane.getChildren().setAll((Object[])new Node[] { (Node)this.b, (Node)this.c });
    this.a.setGraphic((Node)BootstrapIcons.x_circle_fill.glyph(new String[] { "glyph-red" }));
    getChildren().addAll((Object[])new Node[] { (Node)stackPane });
    if (paramBoolean)
      getChildren().addAll((Object[])new Node[] { (Node)this.a }); 
  }
}
