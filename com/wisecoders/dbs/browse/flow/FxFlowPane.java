package com.wisecoders.dbs.browse.flow;

import com.wisecoders.dbs.diagram.model.Point;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class FxFlowPane extends ScrollPane {
  private static final int b = 20;
  
  public final Pane a = new Pane();
  
  private final Point c = new Point();
  
  private static final Insets d = new Insets(10.0D, 10.0D, 10.0D, 10.0D);
  
  private final Label e = new Label();
  
  public FxFlowPane() {
    setContent((Node)this.a);
    this.a.setOnMousePressed(paramMouseEvent -> this.c.a(paramMouseEvent.getX(), paramMouseEvent.getY()));
    this.a.setOnMouseDragged(paramMouseEvent -> {
          if (paramMouseEvent.isSecondaryButtonDown()) {
            setHvalue(Math.min(1.0D, Math.max(0.0D, getHvalue() + (this.c.a() - paramMouseEvent.getX()) / this.a.getWidth())));
            setVvalue(Math.min(1.0D, Math.max(0.0D, getVvalue() + (this.c.b() - paramMouseEvent.getY()) / this.a.getHeight())));
          } 
        });
    widthProperty().addListener((paramObservableValue, paramNumber1, paramNumber2) -> Platform.runLater(this::b));
    heightProperty().addListener((paramObservableValue, paramNumber1, paramNumber2) -> Platform.runLater(this::b));
    getStyleClass().add("diagram");
    setStyle("-fx-background-insets: 0;");
    getStyleClass().add("wallpaper");
    this.a.setPadding(d);
    this.e.getStyleClass().add("text-gray");
  }
  
  public void a(String paramString) {
    this.e.setText(paramString);
    if (this.a.getChildren().isEmpty()) {
      this.e.setLayoutX(10.0D);
      this.e.setLayoutY(10.0D);
      this.a.getChildren().add(this.e);
    } 
  }
  
  private void b() {
    for (Node node : this.a.getChildren()) {
      if (node instanceof FxFlowFrame) {
        FxFlowFrame fxFlowFrame = (FxFlowFrame)node;
        if (fxFlowFrame.g.get())
          fxFlowFrame.d(); 
      } 
    } 
  }
  
  public void a(FxFlowFrame paramFxFlowFrame) {
    if (paramFxFlowFrame.e.getPrefWidth() == -2.147483648E9D || paramFxFlowFrame.e.getPrefHeight() == -2.147483648E9D)
      b(paramFxFlowFrame); 
    if (paramFxFlowFrame.getLayoutX() == -2.147483648E9D || paramFxFlowFrame.getLayoutY() == -2.147483648E9D)
      c(paramFxFlowFrame); 
    this.a.getChildren().remove(this.e);
    this.a.getChildren().add(paramFxFlowFrame);
  }
  
  public void a(FxFlowLine paramFxFlowLine) {
    this.a.getChildren().remove(this.e);
    this.a.getChildren().add(0, paramFxFlowLine.b);
    this.a.getChildren().add(0, paramFxFlowLine.a);
  }
  
  private void b(FxFlowFrame paramFxFlowFrame) {
    double d1 = Math.max(270.0D, getHeight() - 50.0D);
    double d2 = getHeight() - 60.0D;
    paramFxFlowFrame.e.setPrefSize(d1, d2);
    paramFxFlowFrame.b((int)d1, (int)d2);
  }
  
  private void c(FxFlowFrame paramFxFlowFrame) {
    double d = 20.0D;
    for (Node node : this.a.getChildren()) {
      if (node instanceof FxFlowFrame) {
        FxFlowFrame fxFlowFrame = (FxFlowFrame)node;
        if (node != paramFxFlowFrame)
          d = Math.max(d, fxFlowFrame.getLayoutX() + fxFlowFrame.e.getPrefWidth() + 20.0D); 
      } 
    } 
    paramFxFlowFrame.setLayoutX(d);
    paramFxFlowFrame.setLayoutY(20.0D);
    paramFxFlowFrame.a((int)d, 20);
  }
  
  public List a() {
    return (List)this.a.getChildren();
  }
}
