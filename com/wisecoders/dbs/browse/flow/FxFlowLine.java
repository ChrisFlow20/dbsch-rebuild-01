package com.wisecoders.dbs.browse.flow;

import javafx.application.Platform;
import javafx.beans.value.ObservableNumberValue;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.geometry.Side;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Polygon;

public class FxFlowLine {
  public final Polygon a = new Polygon();
  
  public final CubicCurve b = new CubicCurve();
  
  private final FxFlowFrame c;
  
  private final FxFlowFrame d;
  
  private Side e;
  
  private Side f;
  
  private static final double g = 60.0D;
  
  public FxFlowLine(FxFlowFrame paramFxFlowFrame1, FxFlowFrame paramFxFlowFrame2) {
    this.c = paramFxFlowFrame1;
    this.d = paramFxFlowFrame2;
    this.b.getStyleClass().add("browse-line");
    this.a.getStyleClass().add("browse-line-arrow");
    paramFxFlowFrame1.layoutXProperty().addListener((paramObservableValue, paramNumber1, paramNumber2) -> a());
    paramFxFlowFrame1.layoutYProperty().addListener((paramObservableValue, paramNumber1, paramNumber2) -> a());
    paramFxFlowFrame1.widthProperty().addListener((paramObservableValue, paramNumber1, paramNumber2) -> a());
    paramFxFlowFrame2.layoutXProperty().addListener((paramObservableValue, paramNumber1, paramNumber2) -> a());
    paramFxFlowFrame2.layoutYProperty().addListener((paramObservableValue, paramNumber1, paramNumber2) -> a());
    paramFxFlowFrame2.widthProperty().addListener((paramObservableValue, paramNumber1, paramNumber2) -> a());
    Platform.runLater(this::a);
  }
  
  private void a() {
    double d = Double.MAX_VALUE;
    Side side1 = Side.RIGHT, side2 = Side.LEFT;
    for (Side side : Side.values()) {
      for (Side side3 : Side.values()) {
        double d1 = a(this.c, side).distance(a(this.d, side3));
        if (d1 < d) {
          d = d1;
          side1 = side;
          side2 = side3;
        } 
      } 
    } 
    if (this.e != side1 || this.f != side2) {
      this.e = side1;
      this.f = side2;
      b();
    } 
  }
  
  private Point2D a(FxFlowFrame paramFxFlowFrame, Side paramSide) {
    switch (FxFlowLine$1.a[paramSide.ordinal()]) {
      case 1:
        return new Point2D(paramFxFlowFrame.getLayoutX() + paramFxFlowFrame.getWidth() / 2.0D, paramFxFlowFrame.getLayoutY());
      case 2:
        return new Point2D(paramFxFlowFrame.getLayoutX() + paramFxFlowFrame.getWidth(), paramFxFlowFrame.getLayoutY() + paramFxFlowFrame.getHeight() / 2.0D);
      case 3:
        return new Point2D(paramFxFlowFrame.getLayoutX() + paramFxFlowFrame.getWidth() / 2.0D, paramFxFlowFrame.getLayoutY() + paramFxFlowFrame.getHeight());
    } 
    return new Point2D(paramFxFlowFrame.getLayoutX(), paramFxFlowFrame.getLayoutY() + paramFxFlowFrame.getHeight() / 2.0D);
  }
  
  private void b() {
    switch (FxFlowLine$1.a[this.e.ordinal()]) {
      case 1:
        this.b.startXProperty().bind((ObservableValue)this.c.layoutXProperty().add((ObservableNumberValue)this.c.widthProperty().divide(2)));
        this.b.startYProperty().bind((ObservableValue)this.c.layoutYProperty());
        this.b.controlX1Property().bind((ObservableValue)this.c.layoutXProperty().add((ObservableNumberValue)this.c.widthProperty().divide(2)));
        this.b.controlY1Property().bind((ObservableValue)this.c.layoutYProperty().subtract(60.0D));
        break;
      case 2:
        this.b.startXProperty().bind((ObservableValue)this.c.layoutXProperty().add((ObservableNumberValue)this.c.widthProperty()));
        this.b.startYProperty().bind((ObservableValue)this.c.layoutYProperty().add((ObservableNumberValue)this.c.heightProperty().divide(2)));
        this.b.controlX1Property().bind((ObservableValue)this.c.layoutXProperty().add((ObservableNumberValue)this.c.widthProperty()).add(60.0D));
        this.b.controlY1Property().bind((ObservableValue)this.c.layoutYProperty().add((ObservableNumberValue)this.c.heightProperty().divide(2)));
        break;
      case 3:
        this.b.startXProperty().bind((ObservableValue)this.c.layoutXProperty().add((ObservableNumberValue)this.c.widthProperty().divide(2)));
        this.b.startYProperty().bind((ObservableValue)this.c.layoutYProperty().add((ObservableNumberValue)this.c.heightProperty()));
        this.b.controlX1Property().bind((ObservableValue)this.c.layoutXProperty().add((ObservableNumberValue)this.c.widthProperty().divide(2)));
        this.b.controlY1Property().bind((ObservableValue)this.c.layoutYProperty().add((ObservableNumberValue)this.c.heightProperty()).add(60.0D));
        break;
      case 4:
        this.b.startXProperty().bind((ObservableValue)this.c.layoutXProperty());
        this.b.startYProperty().bind((ObservableValue)this.c.layoutYProperty().add((ObservableNumberValue)this.c.heightProperty().divide(2)));
        this.b.controlX1Property().bind((ObservableValue)this.c.layoutXProperty().subtract(60.0D));
        this.b.controlY1Property().bind((ObservableValue)this.c.layoutYProperty().add((ObservableNumberValue)this.c.heightProperty().divide(2)));
        break;
    } 
    switch (FxFlowLine$1.a[this.f.ordinal()]) {
      case 1:
        this.b.endXProperty().bind((ObservableValue)this.d.layoutXProperty().add((ObservableNumberValue)this.d.widthProperty().divide(2)));
        this.b.endYProperty().bind((ObservableValue)this.d.layoutYProperty());
        this.b.controlX2Property().bind((ObservableValue)this.d.layoutXProperty().add((ObservableNumberValue)this.d.widthProperty().divide(2)));
        this.b.controlY2Property().bind((ObservableValue)this.d.layoutYProperty().subtract(60.0D));
        this.a.getPoints().setAll((Object[])new Double[] { Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(-4.0D), Double.valueOf(-7.0D), Double.valueOf(4.0D), Double.valueOf(-7.0D) });
        this.a.layoutXProperty().bind((ObservableValue)this.d.layoutXProperty().add((ObservableNumberValue)this.d.widthProperty().divide(2)));
        this.a.layoutYProperty().bind((ObservableValue)this.d.layoutYProperty());
        break;
      case 2:
        this.b.endXProperty().bind((ObservableValue)this.d.layoutXProperty().add((ObservableNumberValue)this.d.widthProperty()));
        this.b.endYProperty().bind((ObservableValue)this.d.layoutYProperty().add((ObservableNumberValue)this.d.heightProperty().divide(2)));
        this.b.controlX2Property().bind((ObservableValue)this.d.layoutXProperty().add((ObservableNumberValue)this.d.widthProperty()).add(60.0D));
        this.b.controlY2Property().bind((ObservableValue)this.d.layoutYProperty().add((ObservableNumberValue)this.d.heightProperty().divide(2)));
        this.a.getPoints().setAll((Object[])new Double[] { Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(7.0D), Double.valueOf(-4.0D), Double.valueOf(7.0D), Double.valueOf(4.0D) });
        this.a.layoutXProperty().bind((ObservableValue)this.d.layoutXProperty().add((ObservableNumberValue)this.d.widthProperty()));
        this.a.layoutYProperty().bind((ObservableValue)this.d.layoutYProperty().add((ObservableNumberValue)this.d.heightProperty().divide(2)));
        break;
      case 3:
        this.b.endXProperty().bind((ObservableValue)this.d.layoutXProperty().add((ObservableNumberValue)this.d.widthProperty().divide(2)));
        this.b.endYProperty().bind((ObservableValue)this.d.layoutYProperty().add((ObservableNumberValue)this.d.heightProperty()));
        this.b.controlX2Property().bind((ObservableValue)this.d.layoutXProperty().add((ObservableNumberValue)this.d.widthProperty().divide(2)));
        this.b.controlY2Property().bind((ObservableValue)this.d.layoutYProperty().add((ObservableNumberValue)this.d.heightProperty()).add(60.0D));
        this.a.getPoints().setAll((Object[])new Double[] { Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(-4.0D), Double.valueOf(7.0D), Double.valueOf(4.0D), Double.valueOf(7.0D) });
        this.a.layoutXProperty().bind((ObservableValue)this.d.layoutXProperty().add((ObservableNumberValue)this.d.widthProperty().divide(2)));
        this.a.layoutYProperty().bind((ObservableValue)this.d.layoutYProperty().add((ObservableNumberValue)this.d.heightProperty()));
        break;
      case 4:
        this.b.endXProperty().bind((ObservableValue)this.d.layoutXProperty());
        this.b.endYProperty().bind((ObservableValue)this.d.layoutYProperty().add((ObservableNumberValue)this.d.heightProperty().divide(2)));
        this.b.controlX2Property().bind((ObservableValue)this.d.layoutXProperty().subtract(60.0D));
        this.b.controlY2Property().bind((ObservableValue)this.d.layoutYProperty().add((ObservableNumberValue)this.d.heightProperty().divide(2)));
        this.a.getPoints().setAll((Object[])new Double[] { Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(-7.0D), Double.valueOf(-4.0D), Double.valueOf(-7.0D), Double.valueOf(4.0D) });
        this.a.layoutXProperty().bind((ObservableValue)this.d.layoutXProperty());
        this.a.layoutYProperty().bind((ObservableValue)this.d.layoutYProperty().add((ObservableNumberValue)this.d.heightProperty().divide(2)));
        break;
    } 
  }
}
