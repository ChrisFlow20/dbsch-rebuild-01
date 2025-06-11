package com.wisecoders.dbs.dbms.connect.fx;

import com.wisecoders.dbs.sys.fx.VBox$;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebErrorEvent;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class SampleWebView extends Application {
  public void start(Stage paramStage) {
    WebView webView = new WebView();
    WebEngine webEngine = webView.getEngine();
    webView.getEngine().setOnError(paramWebErrorEvent -> System.out.println(paramWebErrorEvent));
    webView.getEngine().setOnAlert(paramWebEvent -> System.out.println(paramWebEvent));
    SimpleBooleanProperty simpleBooleanProperty = new SimpleBooleanProperty();
    SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
    webEngine.getLoadWorker().stateProperty().addListener(new SampleWebView$1(this, webEngine, (BooleanProperty)simpleBooleanProperty, (StringProperty)simpleStringProperty));
    webEngine.loadContent("<html><body style='font-family:sans-serif';><h2>Hello World</h2><div id='important-div' onmouseover='boolProperty.set(true)'onmouseout='boolProperty.set(false)' style='background: #ffd; padding:40px;' class='aha'>Move mouse here</div><h3 id='demo'>Thanks and good night</h3></body></html>");
    webEngine.load("https://dbschema.com");
    Label label1 = new Label();
    label1.textProperty().bind((ObservableValue)Bindings.when((ObservableBooleanValue)simpleBooleanProperty).then("Mouse in position").otherwise("Mouse out of area"));
    Label label2 = new Label();
    label2.textProperty().bind((ObservableValue)simpleStringProperty);
    VBox$ vBox$ = new VBox$();
    vBox$.getChildren().addAll((Object[])new Node[] { (Node)webView, (Node)label1, (Node)label2 });
    Scene scene = new Scene((Parent)vBox$, 400.0D, 250.0D);
    paramStage.setScene(scene);
    paramStage.show();
  }
  
  public static void main(String[] paramArrayOfString) {
    launch(paramArrayOfString);
  }
}
