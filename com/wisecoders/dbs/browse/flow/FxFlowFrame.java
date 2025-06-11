package com.wisecoders.dbs.browse.flow;

import com.wisecoders.dbs.sys.fx.FxUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public abstract class FxFlowFrame extends Region {
  public final FxFlowPane a;
  
  protected FxFlowLine b;
  
  public final HBox c = new HBox();
  
  public final HBox d = new HBox();
  
  public final BorderPane e = new BorderPane();
  
  protected final Label f = c();
  
  private double h;
  
  private double i;
  
  private double j;
  
  private double k;
  
  public final SimpleBooleanProperty g = new SimpleBooleanProperty();
  
  private static final int l = 3;
  
  private double m;
  
  private double n;
  
  private double o;
  
  private double p;
  
  public FxFlowFrame(FxFlowPane paramFxFlowPane) {
    this.a = paramFxFlowPane;
    getStyleClass().addAll((Object[])new String[] { "browse-frame", "data-pane" });
    this.c.setAlignment(Pos.CENTER);
    this.c.setSpacing(5.0D);
    this.c.getStyleClass().addAll((Object[])new String[] { "browse-frame-bar-top", "browse-frame-bar" });
    this.d.setAlignment(Pos.CENTER);
    this.d.setSpacing(5.0D);
    this.d.getStyleClass().addAll((Object[])new String[] { "browse-frame-bar-bottom", "browse-frame-bar" });
    this.e.getStyleClass().add("browse-frame-content");
    this.e.setTop((Node)this.c);
    this.e.setBottom((Node)this.d);
    getChildren().add(this.e);
    c((Node)this.c);
    b((Node)this.c);
    setOnMouseClicked(paramMouseEvent -> toFront());
    this.g.addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          if (paramBoolean2.booleanValue()) {
            this.h = getLayoutX();
            this.i = getLayoutY();
            this.j = this.e.getPrefWidth();
            this.k = this.e.getPrefHeight();
            d();
          } else {
            setLayoutX(this.h);
            setLayoutY(this.i);
            this.e.setPrefSize(this.j, this.k);
          } 
        });
    this.d.setOnMouseClicked(paramMouseEvent -> h());
  }
  
  protected Button a() {
    Button button = new Button(null, (Node)BootstrapIcons.x_lg.glyph(new String[] { "glyph-black" }));
    button.getStyleClass().add("browse-frame-button");
    button.setOnAction(paramActionEvent -> f());
    return button;
  }
  
  protected ToggleButton b() {
    ToggleButton toggleButton = new ToggleButton(null, (Node)BootstrapIcons.arrows_fullscreen.glyph(new String[] { "glyph-black" }));
    toggleButton.getStyleClass().add("browse-frame-button");
    toggleButton.selectedProperty().bindBidirectional((Property)this.g);
    return toggleButton;
  }
  
  public Label c() {
    Label label = new Label(null, (Node)BootstrapIcons.grip_horizontal.glyph(new String[] { "glyph-black" }));
    label.getStyleClass().add("browse-frame-button");
    d((Node)label);
    label.setCursor(Cursor.NW_RESIZE);
    return label;
  }
  
  private void h() {
    if (this.e.getCenter() != null && !this.e.getCenter().isFocused())
      this.e.getCenter().requestFocus(); 
  }
  
  public void a(Node paramNode) {
    this.e.setCenter(paramNode);
  }
  
  private void c(Node paramNode) {
    paramNode.setOnMouseClicked(paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() == 2)
            this.g.set(!this.g.getValue().booleanValue()); 
          h();
        });
  }
  
  protected void d() {
    ScrollPane scrollPane = FxUtil.b((Node)this);
    if (scrollPane != null) {
      Bounds bounds = scrollPane.getViewportBounds();
      setLayoutX(3.0D - bounds.getMinX());
      setLayoutY(3.0D - bounds.getMinY());
      this.e.setPrefSize(bounds.getWidth() - 15.0D, bounds.getHeight() - 15.0D);
    } 
  }
  
  public void b(Node paramNode) {
    paramNode.setOnMousePressed(paramMouseEvent -> {
          this.m = Double.MIN_VALUE;
          this.n = Double.MIN_VALUE;
          toFront();
        });
    paramNode.setOnMouseDragged(paramMouseEvent -> {
          if (this.m == Double.MIN_VALUE) {
            this.m = paramMouseEvent.getScreenX();
            this.n = paramMouseEvent.getScreenY();
          } else {
            setLayoutX(Math.max(0.0D, getLayoutX() + paramMouseEvent.getScreenX() - this.m));
            setLayoutY(Math.max(0.0D, getLayoutY() + paramMouseEvent.getScreenY() - this.n));
            this.m = paramMouseEvent.getScreenX();
            this.n = paramMouseEvent.getScreenY();
            e();
          } 
        });
  }
  
  private void d(Node paramNode) {
    paramNode.addEventFilter(MouseEvent.MOUSE_PRESSED, paramMouseEvent -> {
          this.o = paramMouseEvent.getSceneX() - this.e.getWidth();
          this.p = paramMouseEvent.getSceneY() - this.e.getHeight();
        });
    paramNode.addEventFilter(MouseEvent.MOUSE_DRAGGED, paramMouseEvent -> {
          this.e.setPrefSize(paramMouseEvent.getSceneX() - this.o, paramMouseEvent.getSceneY() - this.p);
          e();
        });
  }
  
  public void f() {
    this.a.a.getChildren().remove(this);
    if (this.b != null) {
      this.a.a.getChildren().remove(this.b.b);
      this.a.a.getChildren().remove(this.b.a);
    } 
  }
  
  public void a(FxFlowFrame paramFxFlowFrame) {
    if (paramFxFlowFrame != null) {
      this.b = new FxFlowLine(paramFxFlowFrame, this);
      this.a.a(this.b);
    } 
  }
  
  public FxFlowLine g() {
    return this.b;
  }
  
  protected abstract void e();
  
  public abstract void a(int paramInt1, int paramInt2);
  
  public abstract void b(int paramInt1, int paramInt2);
}
