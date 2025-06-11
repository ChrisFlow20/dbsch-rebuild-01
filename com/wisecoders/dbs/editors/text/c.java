package com.wisecoders.dbs.editors.text;

import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

class c extends Pane {
  static final int a = 4;
  
  static final double b = 0.2D;
  
  private final StyledEditorSkin c;
  
  private final Canvas d = new Canvas();
  
  public c(StyledEditorSkin paramStyledEditorSkin) {
    this.c = paramStyledEditorSkin;
    getStyleClass().add("code-editor");
    setPrefWidth(40.0D);
    setBorder(Border.EMPTY);
    getChildren().add(this.d);
    this.d.widthProperty().bind((ObservableValue)widthProperty());
    this.d.heightProperty().bind((ObservableValue)heightProperty());
    a();
  }
  
  void a() {
    if (isVisible() && getScene() != null) {
      StyledEditor styledEditor = (StyledEditor)this.c.getSkinnable();
      double d1 = ((Font)styledEditor.d.getValue()).getSize();
      int i = this.c.c();
      int j = ((Number)styledEditor.e.getValue()).intValue();
      double d2 = ((Font)styledEditor.d.getValue()).getSize() + j;
      int k = styledEditor.K.k().getLine();
      int m = styledEditor.K.l().getLine();
      GraphicsContext graphicsContext = this.d.getGraphicsContext2D();
      graphicsContext.setFill((Paint)styledEditor.t.getValue());
      graphicsContext.fillRect(0.0D, 0.0D, this.d.getWidth(), this.d.getHeight());
      graphicsContext.setFont((Font)styledEditor.d.getValue());
      graphicsContext.setFill((Paint)styledEditor.s.getValue());
      graphicsContext.setStroke((Paint)styledEditor.s.getValue());
      graphicsContext.setLineWidth(0.2D);
      graphicsContext.strokeLine(getWidth() - 0.2D, 0.0D, getWidth() - 0.2D, getHeight() - 1.0D);
      graphicsContext.setLineWidth(1.0D);
      for (int n = i; n < i + this.c.d() && n < styledEditor.K.a.size(); n++) {
        double d = (n + 1) * d2 - this.c.a.U();
        if (n >= k && n <= m && k < m) {
          String str = "" + n + 1 - styledEditor.K.k().getLine();
          double d3 = str.length() * this.c.b(49);
          graphicsContext.setFill((Paint)styledEditor.v.getValue());
          graphicsContext.fillRect(4.0D, d - d1, d3, d2);
          graphicsContext.setFill((Paint)styledEditor.u.getValue());
          graphicsContext.fillText(str, 4.0D, d - 0.65D);
        } else {
          graphicsContext.setFill((Paint)styledEditor.s.getValue());
          graphicsContext.fillText("" + n + 1, 4.0D, d - 0.65D);
        } 
      } 
    } 
  }
}
