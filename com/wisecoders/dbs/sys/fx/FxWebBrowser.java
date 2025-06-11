package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import java.util.Collection;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebErrorEvent;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class FxWebBrowser extends Stage {
  private final WebView a = new WebView();
  
  private final Rx b = new Rx(FxWebBrowser.class, this);
  
  FxWebBrowser(Scene paramScene, String paramString, boolean paramBoolean) {
    setTitle("Web Explorer of " + paramString);
    this.a.getEngine().setOnError(paramWebErrorEvent -> {
          FxUtil.a(this.a, paramWebErrorEvent);
          Log.a(paramWebErrorEvent.getMessage(), paramWebErrorEvent.getException());
        });
    this.a.getEngine().setOnAlert(paramWebEvent -> Log.c(paramWebEvent.toString()));
    this.a.getEngine().load(paramString);
    Scene scene = new Scene((Parent)a(), 1200.0D, 800.0D);
    scene.getStylesheets().addAll((Collection)paramScene.getStylesheets());
    setScene(scene);
    initOwner(paramScene.getWindow());
    if (paramBoolean) {
      showAndWait();
    } else {
      show();
    } 
  }
  
  public BorderPane a() {
    BorderPane borderPane = new BorderPane();
    borderPane.setCenter((Node)this.a);
    borderPane.setBottom((Node)(new FlowPane$()).h().d().a(new Node[] { (Node)this.b.j("close") }));
    return borderPane;
  }
  
  @Action
  public void close() {
    hide();
  }
}
