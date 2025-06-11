package com.wisecoders.dbs.dbms.connect.fx;

import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class SamplePane extends BorderPane {
  private final Pane b = new Pane();
  
  private final Canvas c = new Canvas();
  
  private void b() {
    this.b.getChildren().add(this.c);
    this.c.widthProperty().bind((ObservableValue)this.b.widthProperty());
    this.c.heightProperty().bind((ObservableValue)this.b.heightProperty());
    widthProperty().addListener((paramObservableValue, paramNumber1, paramNumber2) -> a());
  }
  
  public void a() {
    GraphicsContext graphicsContext = this.c.getGraphicsContext2D();
    graphicsContext.clearRect(0.0D, 0.0D, this.c.getWidth(), this.c.getHeight());
    graphicsContext.setFill((Paint)this.a.getValue());
    graphicsContext.fillRect(0.0D, 0.0D, getWidth(), getHeight());
    graphicsContext.setStroke((Paint)Color.RED);
    graphicsContext.setLineWidth(1.0D);
    graphicsContext.strokeLine(0.0D, 0.0D, this.c.getWidth(), this.c.getHeight());
    graphicsContext.setFill((Paint)Color.BLACK);
    graphicsContext.fillText("This is a text", 100.0D, 100.0D);
    graphicsContext.setFont(Font.font("Monospaced"));
    graphicsContext.setFill((Paint)Color.BLACK);
    graphicsContext.fillText("This is a text", 100.0D, 100.0D);
  }
  
  protected void layoutChildren() {
    super.layoutChildren();
    a();
  }
  
  private static final StyleablePropertyFactory d = new StyleablePropertyFactory(getClassCssMetaData());
  
  public StyleableProperty a;
  
  public List getCssMetaData() {
    return d.getCssMetaData();
  }
  
  public SamplePane() {
    this.a = d.createStyleableColorProperty((Styleable)this, "custom-background", "custom-background", paramSamplePane -> paramSamplePane.a, Color.web("0xffffff"));
    b();
    Button button1 = new Button("Repaint");
    button1.setOnAction(paramActionEvent -> a());
    Button button2 = new Button("Test Alert");
    button2.setOnAction(paramActionEvent -> {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setAlertType(Alert.AlertType.INFORMATION);
          alert.setHeaderText("Sample dialog");
          alert.initOwner(getScene().getWindow());
          alert.showAndWait();
        });
    FlowPane flowPane = new FlowPane();
    flowPane.getChildren().addAll((Object[])new Node[] { (Node)button1, (Node)button2 });
    setTop((Node)flowPane);
    setCenter((Node)this.b);
    getStyleClass().add("custom-pane");
  }
}
