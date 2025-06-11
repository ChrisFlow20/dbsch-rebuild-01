package com.wisecoders.dbs.dialogs.web.fx;

import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FxUtil;
import com.wisecoders.dbs.sys.fx.VBox$;
import java.io.IOException;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.Node;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class FxWebViewVideoDialog extends Dialog$ {
  private final WebView a = new WebView();
  
  private final ListView b = new ListView();
  
  public FxWebViewVideoDialog(Window paramWindow, String paramString) {
    super(paramWindow, Modality.NONE);
    System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
    FxUtil.a(this.a);
    this.b.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramc1, paramc2) -> Platform.runLater(()));
    c c = null;
    String str = this.rx.a("video.list", new String[0]);
    if (str != null)
      for (String str1 : str.split("\\r?\\n")) {
        String[] arrayOfString = str1.split(",");
        if (arrayOfString.length == 4) {
          c c1 = new c(arrayOfString[0], arrayOfString[1], arrayOfString[2], arrayOfString[3]);
          this.b.getItems().add(c1);
          if (arrayOfString[0].equals(paramString))
            c = c1; 
        } 
      }  
    this.b.setCellFactory(paramListView -> new b());
    if (c != null)
      this.b.getSelectionModel().select(c); 
    setOnCloseRequest(paramDialogEvent -> close());
    this.a.getEngine().getLoadWorker().stateProperty().addListener((paramObservableValue, paramState1, paramState2) -> {
          if (paramState2 == Worker.State.FAILED) {
            Throwable throwable = this.a.getEngine().getLoadWorker().getException();
            Log.a("Video Dialog cannot connect to internet.", throwable);
            this.rx.b(getDialogScene(), "Cannot connect to internet", throwable);
          } 
        });
    getDialogScene().getWindow().setOnCloseRequest(paramWindowEvent -> {
          this.a.getEngine().load(null);
          getDialogScene().getWindow().hide();
        });
  }
  
  private void a(c paramc) {
    if (paramc != null)
      try {
        String str = StringUtil.readStringFromInputStream(FxWebViewVideoDialog.class.getResourceAsStream("resources/YouTube.html"));
        str = str.replaceAll("VIDEOCODE", paramc.c);
        this.a.getEngine().loadContent(str);
      } catch (IOException iOException) {
        this.rx.a(getDialogScene(), iOException);
      }  
  }
  
  public Node createContentPane() {
    this.b.setMaxHeight(Double.MAX_VALUE);
    VBox$.setVgrow((Node)this.b, Priority.ALWAYS);
    VBox$ vBox$ = (new VBox$()).l().d((Node)this.b);
    BorderPane borderPane = new BorderPane();
    borderPane.setMinSize(300.0D, 200.0D);
    borderPane.setCenter((Node)this.a);
    SplitPane splitPane = new SplitPane(new Node[] { (Node)vBox$, (Node)borderPane });
    SplitPane.setResizableWithParent((Node)vBox$, Boolean.FALSE);
    setRegionPrefSize((Region)splitPane, 1400.0D, 720.0D);
    Platform.runLater(() -> paramSplitPane.setDividerPositions(new double[] { 0.2D }));
    return (Node)splitPane;
  }
  
  public void createButtons() {}
  
  public boolean apply() {
    return true;
  }
}
