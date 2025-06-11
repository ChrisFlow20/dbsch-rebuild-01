package com.wisecoders.dbs.diagram.fx;

import com.wisecoders.dbs.diagram.model.Rect;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.sys.fx.LabeledPane$;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class FxDiagramPerspectivePanel extends LabeledPane$ {
  public static final String a = "Perspective";
  
  private static final Color c = Color.web("#4a68ae");
  
  private final Workspace d;
  
  private double e;
  
  private double f;
  
  private final Rect g = new Rect();
  
  private double h = 0.0D;
  
  private final Canvas i = new Canvas();
  
  private final Hyperlink j = new Hyperlink();
  
  private final Rect k;
  
  public void a(FxLayoutPane paramFxLayoutPane) {
    String str = (paramFxLayoutPane != null) ? ("" + (int)(paramFxLayoutPane.c.n() * 100.0D) + "%") : "";
    if (!str.equals(this.j.getText()))
      this.j.setText(str); 
  }
  
  public FxDiagramPerspectivePanel(Workspace paramWorkspace) {
    this.k = new Rect();
    this.d = paramWorkspace;
    a("Perspective");
    setPrefSize(150.0D, 150.0D);
    Pane pane = new Pane();
    pane.getChildren().add(this.i);
    setCenter((Node)pane);
    this.j.setOnAction(paramActionEvent -> {
          if (paramWorkspace.o() != null)
            (paramWorkspace.o()).c.zoomChoose(); 
        });
    this.b.getItems().add(this.j);
    this.i.widthProperty().bind((ObservableValue)widthProperty());
    this.i.heightProperty().bind((ObservableValue)heightProperty());
    this.i.setOnMousePressed(paramMouseEvent -> {
          FxLayoutPane fxLayoutPane = paramWorkspace.o();
          if (fxLayoutPane != null && this.h > 0.001D) {
            this.g.a(fxLayoutPane.c.h());
            this.e = paramMouseEvent.getX();
            this.f = paramMouseEvent.getY();
          } 
        });
    this.i.setOnMouseDragged(paramMouseEvent -> {
          FxLayoutPane fxLayoutPane = paramWorkspace.o();
          if (fxLayoutPane != null && this.h > 0.001D) {
            Rect rect = new Rect(this.g);
            rect.c((int)((paramMouseEvent.getX() - this.e) / this.h), (int)((paramMouseEvent.getY() - this.f) / this.h));
            fxLayoutPane.c.a(rect);
            a();
          } 
        });
  }
  
  public void a() {
    if (isVisible() && getScene() != null && getScene().getWindow().isFocused()) {
      GraphicsContext graphicsContext = this.i.getGraphicsContext2D();
      graphicsContext.clearRect(0.0D, 0.0D, getWidth(), getHeight());
      FxLayoutPane fxLayoutPane = this.d.o();
      if (fxLayoutPane != null) {
        double d1 = fxLayoutPane.c.n(), d2 = 1.0D / d1;
        int i = fxLayoutPane.c.t(), j = fxLayoutPane.c.u();
        this.h = Math.min((getWidth() - 5.0D) / i, (getHeight() - 5.0D) / j);
        double d3 = 1.0D / this.h;
        if (this.h > 0.001D) {
          double d4 = (getWidth() - i * this.h) / 2.0D;
          double d5 = (getHeight() - j * this.h) / 2.0D;
          graphicsContext.translate(d4, d5);
          int k = (int)(i * this.h);
          int m = (int)(j * this.h);
          graphicsContext.setStroke((Paint)fxLayoutPane.c.D.getValue());
          graphicsContext.strokeRect(0.0D, 0.0D, (k - 1), (m - 1));
          graphicsContext.scale(this.h, this.h);
          graphicsContext.scale(d2, d2);
          fxLayoutPane.c.a(graphicsContext);
          graphicsContext.scale(d1, d1);
          graphicsContext.scale(d3, d3);
          graphicsContext.setLineWidth(1.0D);
          this.k.a(fxLayoutPane.c.h());
          this.k.b(Math.min(this.k.c(), (i - 1)), Math.min(this.k.d(), (j - 1)));
          double d6 = this.h / fxLayoutPane.c.n();
          graphicsContext.setStroke((Paint)Color.LIGHTGRAY);
          graphicsContext.strokeRect(((int)(this.k.a() * d6) + 1), ((int)(this.k.b() * d6) + 1), (int)(this.k.c() * d6), (int)(this.k.d() * d6));
          graphicsContext.setStroke((Paint)c);
          graphicsContext.strokeRect((int)(this.k.a() * d6), (int)(this.k.b() * d6), (int)(this.k.c() * d6), (int)(this.k.d() * d6));
          graphicsContext.translate(-d4, -d5);
        } 
      } 
    } 
  }
}
