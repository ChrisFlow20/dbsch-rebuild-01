package com.wisecoders.dbs.dbms.connect.fx;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CanvasIssue extends Application {
  private final StackPane a = new StackPane();
  
  private final Scene b = new Scene((Parent)this.a, 300.0D, 250.0D);
  
  public void start(Stage paramStage) {
    paramStage.setTitle("Sample Canvas");
    TabPane tabPane = new TabPane();
    for (byte b = 0; b < 30; b++)
      tabPane.getTabs().add(new Tab("Tab " + tabPane.getTabs().size(), (Node)a())); 
    this.a.getChildren().add(tabPane);
    paramStage.setScene(this.b);
    paramStage.sizeToScene();
    a();
    paramStage.show();
  }
  
  private Pane a() {
    BorderPane borderPane = new BorderPane();
    CanvasIssue$1 canvasIssue$1 = new CanvasIssue$1(this);
    borderPane.setCenter((Node)canvasIssue$1);
    canvasIssue$1.widthProperty().bind((ObservableValue)borderPane.widthProperty());
    canvasIssue$1.heightProperty().bind((ObservableValue)borderPane.heightProperty());
    borderPane.widthProperty().addListener((paramObservableValue, paramNumber1, paramNumber2) -> a(paramCanvas));
    a(canvasIssue$1);
    return (Pane)borderPane;
  }
  
  public void a(Canvas paramCanvas) {
    GraphicsContext graphicsContext = paramCanvas.getGraphicsContext2D();
    graphicsContext.clearRect(0.0D, 0.0D, paramCanvas.getWidth(), paramCanvas.getHeight());
    graphicsContext.setFill((Paint)Color.web("#ffffff"));
    graphicsContext.fillRect(0.0D, 0.0D, paramCanvas.getWidth(), paramCanvas.getHeight());
    graphicsContext.setFont(Font.font("Monospaced"));
    graphicsContext.setFill((Paint)Color.BLACK);
    graphicsContext.fillText("This is a", 50.0D, 70.0D);
    graphicsContext.setFill((Paint)Color.RED);
    graphicsContext.fillText("demo", 50.0D, 88.0D);
  }
  
  public static void main(String[] paramArrayOfString) {
    launch(paramArrayOfString);
  }
}
