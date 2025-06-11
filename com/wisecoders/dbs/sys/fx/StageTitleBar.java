package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.dialogs.system.FxAboutDialog;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class StageTitleBar extends HBox$ {
  private final Stage a;
  
  private double b;
  
  private double c;
  
  public StageTitleBar(Stage paramStage, Node... paramVarArgs) {
    this.a = paramStage;
    paramStage.initStyle(StageStyle.UNIFIED);
    getStyleClass().add("tool-bar");
    setOnMouseClicked(paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() == 2)
            restore(); 
        });
    Rx rx = new Rx(StageTitleBar.class, this);
    getChildren().add(rx.j("about"));
    getChildren().addAll((Object[])paramVarArgs);
    getChildren().add(new HGrowBox$());
    getChildren().addAll(rx.a(new String[] { "iconify", "restore", "close" }));
    setOnMousePressed(paramMouseEvent -> {
          this.b = paramMouseEvent.getSceneX();
          this.c = paramMouseEvent.getSceneY();
        });
    setOnMouseDragged(paramMouseEvent -> {
          if (paramMouseEvent.getScreenY() < Screen.getPrimary().getVisualBounds().getMaxY() - 20.0D)
            paramStage.setY(paramMouseEvent.getScreenY() - this.c); 
          paramStage.setX(paramMouseEvent.getScreenX() - this.b);
        });
    setOnMouseReleased(paramMouseEvent -> {
          if (paramStage.getY() < 0.0D)
            paramStage.setY(0.0D); 
        });
  }
  
  @Action
  public void iconify() {
    this.a.setIconified(true);
  }
  
  @Action
  public void restore() {
    this.a.setMaximized(!this.a.isMaximized());
  }
  
  @Action
  public void close() {
    this.a.close();
  }
  
  @Action
  public void about() {
    new FxAboutDialog((Window)this.a);
  }
}
