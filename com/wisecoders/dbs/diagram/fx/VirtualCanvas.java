package com.wisecoders.dbs.diagram.fx;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.diagram.model.Rect;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;

public abstract class VirtualCanvas extends BorderPane {
  public final ScrollBar ap = new ScrollBar();
  
  public final ScrollBar aq = new ScrollBar();
  
  private boolean a = true;
  
  private boolean b = true;
  
  public final VirtualCanvas$CanvasPane ar = new VirtualCanvas$CanvasPane(this);
  
  final Rect as = new Rect();
  
  private boolean c = false;
  
  public final synchronized void Q() {
    if (isVisible() && getScene() != null)
      if (Platform.isFxApplicationThread()) {
        this.c = false;
        z();
      } else {
        if (!this.c)
          Platform.runLater(() -> {
                this.c = false;
                z();
              }); 
        this.c = true;
      }  
  }
  
  public VirtualCanvas() {
    this.aq.setOrientation(Orientation.VERTICAL);
    this.ap.setOrientation(Orientation.HORIZONTAL);
    this.aq.setUnitIncrement(Sys.i() ? 7.0D : 6.0D);
    setCenter((Node)this.ar);
    this.ar.widthProperty().addListener(paramObservable -> {
          R();
          b();
          Q();
        });
    this.ar.heightProperty().addListener(paramObservable -> {
          a();
          c();
          Q();
        });
    this.ap.valueProperty().addListener((paramObservableValue, paramNumber1, paramNumber2) -> {
          if (this.a) {
            b();
            Q();
          } 
        });
    this.aq.valueProperty().addListener((paramObservableValue, paramNumber1, paramNumber2) -> {
          if (this.b) {
            c();
            Q();
          } 
        });
    this.ar.setOnScroll(paramScrollEvent -> {
          if (!paramScrollEvent.isControlDown()) {
            c(-paramScrollEvent.getDeltaY());
            d(-paramScrollEvent.getDeltaX());
          } 
        });
    this.ar.setFocusTraversable(false);
    visibleProperty().addListener(paramObservable -> {
          if (isVisible())
            Q(); 
        });
  }
  
  public void R() {
    double d1 = f();
    double d2 = this.ap.getValue() / (this.ap.getMax() + 1.0E-4D);
    this.ap.setMax(d1 - this.ar.getWidth());
    boolean bool = getChildren().contains(this.ap);
    if (d1 > this.ar.getWidth()) {
      if (bool) {
        this.a = false;
        this.ap.setValue(d2 * this.ap.getMax());
        this.a = true;
      } else {
        setBottom((Node)this.ap);
      } 
    } else if (bool) {
      this.ap.setValue(0.0D);
      setBottom(null);
    } 
    this.ap.setVisibleAmount((d1 - this.ar.getWidth()) * this.ar.getWidth() / d1);
  }
  
  public void b(double paramDouble) {
    if (paramDouble - this.ar.getWidth() > this.ap.getMax()) {
      this.ap.setMax(paramDouble - this.ar.getWidth());
      boolean bool = (getBottom() == this.ap) ? true : false;
      if (paramDouble > this.ar.getWidth()) {
        if (!bool)
          setBottom((Node)this.ap); 
      } else if (bool) {
        this.ap.setValue(0.0D);
        setBottom(null);
      } 
      this.ap.setVisibleAmount((paramDouble - this.ar.getWidth()) * this.ar.getWidth() / paramDouble);
    } 
  }
  
  private void a() {
    double d1 = g();
    double d2 = this.aq.getValue() / (this.aq.getMax() + 1.0E-5D);
    this.aq.setMax(d1 - this.ar.getHeight());
    boolean bool = (getRight() == this.aq) ? true : false;
    if (d1 > this.ar.getHeight()) {
      if (bool) {
        this.b = false;
        this.aq.setValue(d2 * this.aq.getMax());
        this.b = true;
      } else {
        setRight((Node)this.aq);
      } 
    } else {
      this.aq.setValue(0.0D);
      if (bool)
        setRight(null); 
    } 
    this.aq.setVisibleAmount((d1 - this.ar.getHeight()) * this.ar.getHeight() / d1);
  }
  
  private void b() {
    double d = f();
    if (d < this.ar.getWidth()) {
      this.as.a(0.0D);
    } else {
      this.as.a(this.ap.getValue());
    } 
    this.as.c(this.ar.getWidth());
  }
  
  private void c() {
    double d = g();
    if (d < this.ar.getHeight()) {
      this.as.b(0.0D);
    } else {
      this.as.b(this.aq.getValue());
    } 
    this.as.d(this.ar.getHeight());
  }
  
  void b(double paramDouble1, double paramDouble2) {
    this.ap.setValue(Math.max(this.ap.getMin(), Math.min(this.ap.getMax(), this.ap.getValue() + paramDouble1)));
    this.aq.setValue(Math.max(this.aq.getMin(), Math.min(this.aq.getMax(), this.aq.getValue() + paramDouble2)));
  }
  
  public void S() {
    R();
    a();
    b();
    c();
  }
  
  public double T() {
    return this.ap.getValue();
  }
  
  public double U() {
    return this.aq.getValue();
  }
  
  public void c(double paramDouble) {
    this.aq.setValue(Math.max(this.aq.getMin(), Math.min(this.aq.getMax(), this.aq.getValue() + paramDouble)));
  }
  
  public void d(double paramDouble) {
    this.ap.setValue(Math.max(this.ap.getMin(), Math.min(this.ap.getMax(), this.ap.getValue() + paramDouble)));
  }
  
  public void e(double paramDouble) {
    this.aq.setValue(Math.max(this.aq.getMin(), Math.min(this.aq.getMax(), paramDouble)));
  }
  
  public void f(double paramDouble) {
    this.ap.setValue(Math.max(this.ap.getMin(), Math.min(this.ap.getMax(), paramDouble)));
  }
  
  public abstract double f();
  
  public abstract double g();
  
  protected abstract void z();
}
