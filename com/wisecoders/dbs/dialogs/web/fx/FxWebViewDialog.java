package com.wisecoders.dbs.dialogs.web.fx;

import com.wisecoders.dbs.sys.Action;
import java.util.Collection;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FxWebViewDialog extends Stage {
  private final FxWebView a;
  
  public FxWebViewDialog(Scene paramScene) {
    this(paramScene, "Help Dialog");
  }
  
  public FxWebViewDialog(Scene paramScene, String paramString) {
    setTitle(paramString);
    this.a = new FxWebView();
    Scene scene = new Scene((Parent)this.a, 1200.0D, 700.0D);
    initModality(Modality.NONE);
    initOwner(paramScene.getWindow());
    scene.getStylesheets().addAll((Collection)paramScene.getStylesheets());
    setScene(scene);
  }
  
  public void a(String paramString) {
    this.a.b(paramString);
    show();
  }
  
  @Action
  public void close() {
    hide();
  }
}
