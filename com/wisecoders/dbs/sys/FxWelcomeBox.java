package com.wisecoders.dbs.sys;

import com.wisecoders.dbs.sys.fx.VBox$;
import com.wisecoders.dbs.sys.fx.VGrowBox$;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FxWelcomeBox extends VBox$ {
  public final Label a;
  
  public FxWelcomeBox(String paramString, Rx paramRx) {
    l().i();
    getStyleClass().add("pane");
    setAlignment(Pos.CENTER);
    setFillWidth(false);
    this.a = paramRx.e(paramString);
    VBox.setVgrow((Node)this.a, Priority.NEVER);
    this.a.setContentDisplay(ContentDisplay.TOP);
    this.a.getStyleClass().add("h1");
    getChildren().addAll((Object[])new Node[] { (Node)new VGrowBox$(), (Node)this.a });
  }
  
  public VBox a(Node... paramVarArgs) {
    return a(10, paramVarArgs);
  }
  
  public VBox a(int paramInt, Node... paramVarArgs) {
    VBox$ vBox$ = (new VBox$()).b(paramInt);
    vBox$.setFillWidth(true);
    vBox$.getChildren().addAll((Object[])paramVarArgs);
    getChildren().add(vBox$);
    return vBox$;
  }
  
  public void b(Node... paramVarArgs) {
    for (Node node : paramVarArgs) {
      getChildren().addAll((Object[])new Node[] { node });
    } 
  }
}
