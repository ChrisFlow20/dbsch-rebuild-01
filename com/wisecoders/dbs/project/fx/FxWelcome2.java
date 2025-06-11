package com.wisecoders.dbs.project.fx;

import com.wisecoders.dbs.dialogs.system.FxAnimatedLogo;
import com.wisecoders.dbs.sys.FxWelcomeBox;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.GridPane$;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class FxWelcome2 extends StackPane {
  private final Rx a = new Rx(FxWelcome2.class, this);
  
  private final GridPane$ b = new GridPane$();
  
  private final TreeView c = new TreeView();
  
  public FxWelcome2(FxFrame paramFxFrame) {
    SubScene subScene = (new FxAnimatedLogo(true)).a();
    StackPane.setAlignment((Node)subScene, Pos.CENTER);
    getChildren().add(subScene);
    TreeItem treeItem = new TreeItem("Menu");
    treeItem.getChildren().add(new TreeItem("New Project"));
    treeItem.getChildren().add(new TreeItem("Reopen Project"));
    this.c.setRoot(treeItem);
    FxWelcomeBox fxWelcomeBox = new FxWelcomeBox("newConnectedToDb", this.a);
    this.b.b(new int[] { -1, -2 }).e();
    this.b.a((Node)fxWelcomeBox, "0,1,l,c");
    BorderPane borderPane = new BorderPane();
    borderPane.setCenter((Node)this.b);
    borderPane.setLeft((Node)this.c);
    StackPane.setAlignment((Node)borderPane, Pos.CENTER);
    getChildren().add(borderPane);
  }
}
