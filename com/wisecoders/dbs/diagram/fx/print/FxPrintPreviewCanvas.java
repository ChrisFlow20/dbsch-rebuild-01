package com.wisecoders.dbs.diagram.fx.print;

import com.wisecoders.dbs.diagram.fx.FxAbstractDiagramPane;
import com.wisecoders.dbs.diagram.fx.print.printable.GeneralPrintableNode;
import com.wisecoders.dbs.sys.fx.FxUtil;
import java.text.SimpleDateFormat;
import java.util.List;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.print.PageLayout;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;

class FxPrintPreviewCanvas extends Canvas {
  private static final double b = 9.0D;
  
  private final FxPrintPreviewCanvas$Mode c;
  
  private double d;
  
  private final boolean e;
  
  FxPrintPreviewCanvas(FxPrintPreviewCanvas$Mode paramFxPrintPreviewCanvas$Mode, double paramDouble, boolean paramBoolean) {
    this.a = f.createStyleableFontProperty((Styleable)this, "print-title-font", "print-title-font", paramFxPrintPreviewCanvas -> paramFxPrintPreviewCanvas.a, Font.font(FxUtil.a(new String[] { "Serif", "Trebuchet MS", "System" }, ), FxUtil.b * 0.75D));
    this.g = f.createStyleableColorProperty((Styleable)this, "print-title-foreground", "print-title-foreground", paramFxPrintPreviewCanvas -> paramFxPrintPreviewCanvas.g, Color.web("#222222"));
    this.h = new Text();
    this.c = paramFxPrintPreviewCanvas$Mode;
    this.d = paramDouble;
    this.e = paramBoolean;
    if (paramFxPrintPreviewCanvas$Mode == FxPrintPreviewCanvas$Mode.a)
      setStyle("-fx-background-insets: 0, 20 ;-fx-padding: 20 ; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"); 
  }
  
  public void a(PageLayout paramPageLayout, GeneralPrintableNode paramGeneralPrintableNode, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, double paramDouble, PrintPreviewPage paramPrintPreviewPage) {
    if (paramPageLayout == null) {
      setWidth(800.0D);
      setHeight(600.0D);
      GraphicsContext graphicsContext1 = getGraphicsContext2D();
      graphicsContext1.clearRect(0.0D, 0.0D, getWidth() - 1.0D, getHeight() - 1.0D);
      graphicsContext1.setFill((Paint)Color.WHITE);
      graphicsContext1.fillRect(0.0D, 0.0D, getWidth() - 1.0D, getHeight() - 1.0D);
      graphicsContext1.setFill((Paint)Color.BLACK);
      graphicsContext1.fillText("Please select a printer", 30.0D, 30.0D);
      return;
    } 
    switch (FxPrintPreviewCanvas$1.a[this.c.ordinal()]) {
      case 1:
        setWidth(this.d * paramPageLayout.getPrintableWidth());
        setHeight(this.d * paramPageLayout.getPrintableHeight());
        break;
      case 2:
        setWidth(this.d * (paramPageLayout.getPrintableWidth() + paramPageLayout.getLeftMargin() + paramPageLayout.getRightMargin()));
        setHeight(this.d * (paramPageLayout.getPrintableHeight() + paramPageLayout.getTopMargin() + paramPageLayout.getBottomMargin()));
        break;
    } 
    GraphicsContext graphicsContext = getGraphicsContext2D();
    graphicsContext.setFontSmoothingType(FontSmoothingType.LCD);
    graphicsContext.clearRect(0.0D, 0.0D, getWidth() - 1.0D, getHeight() - 1.0D);
    graphicsContext.setFill((Paint)Color.WHITE);
    graphicsContext.fillRect(0.0D, 0.0D, getWidth() - 1.0D, getHeight() - 1.0D);
    graphicsContext.scale(this.d, this.d);
    if (this.c == FxPrintPreviewCanvas$Mode.a)
      graphicsContext.translate(paramPageLayout.getLeftMargin(), paramPageLayout.getTopMargin()); 
    double d1 = paramBoolean3 ? ((Font)this.a.getValue()).getSize() : 0.0D;
    if (paramBoolean3) {
      graphicsContext.setFont((Font)this.a.getValue());
      graphicsContext.setFill((Paint)this.g.getValue());
      graphicsContext.fillText(paramGeneralPrintableNode.c(), 0.0D, ((Font)this.a.getValue()).getSize());
      String str1 = "Page " + paramPrintPreviewPage.a() + " of " + paramPrintPreviewPage.c;
      graphicsContext.fillText(str1, (paramPageLayout.getPrintableWidth() - a(str1)) / 2.0D, d1);
      String str2 = "DbSchema " + (new SimpleDateFormat("d-MMM-yyyy")).format(Long.valueOf(System.currentTimeMillis()));
      graphicsContext.fillText(str2, paramPageLayout.getPrintableWidth() - a(str2), d1);
    } 
    graphicsContext.save();
    graphicsContext.beginPath();
    graphicsContext.rect(0.0D, d1, paramPageLayout.getPrintableWidth(), paramPageLayout.getPrintableHeight() - d1);
    graphicsContext.closePath();
    graphicsContext.clip();
    paramGeneralPrintableNode.a(graphicsContext, paramDouble, paramPrintPreviewPage, paramPageLayout, d1);
    graphicsContext.restore();
    double d2 = 1.0D;
    graphicsContext.setStroke((Paint)this.g.getValue());
    graphicsContext.setLineWidth(1.0D);
    if (paramBoolean1) {
      graphicsContext.strokeRect(d2, d1 + d2, paramPageLayout.getPrintableWidth() - d2, paramPageLayout.getPrintableHeight() - d1 - d2);
    } else if (paramBoolean2) {
      graphicsContext.strokeLine(d2, d1 + d2, 9.0D, d1 + d2);
      graphicsContext.strokeLine(paramPageLayout.getPrintableWidth() - 9.0D, d1 + d2, paramPageLayout.getPrintableWidth() - d2, d1 + d2);
      graphicsContext.strokeLine(d2, d1 + d2, d2, d1 + 9.0D);
      graphicsContext.strokeLine(paramPageLayout.getPrintableWidth() - d2, d1 + d2, paramPageLayout.getPrintableWidth() - d2, d1 + 9.0D);
      graphicsContext.strokeLine(d2, paramPageLayout.getPrintableHeight() - d2, 9.0D, paramPageLayout.getPrintableHeight() - d2);
      graphicsContext.strokeLine(paramPageLayout.getPrintableWidth() - 9.0D, paramPageLayout.getPrintableHeight() - d2, paramPageLayout.getPrintableWidth() - d2, paramPageLayout.getPrintableHeight() - d2);
      graphicsContext.strokeLine(d2, paramPageLayout.getPrintableHeight() - 9.0D, d2, paramPageLayout.getPrintableHeight() - d2);
      graphicsContext.strokeLine(paramPageLayout.getPrintableWidth() - d2, paramPageLayout.getPrintableHeight() - 9.0D, paramPageLayout.getPrintableWidth() - d2, paramPageLayout.getPrintableHeight() - d2);
    } 
    if (this.c == FxPrintPreviewCanvas$Mode.a)
      graphicsContext.translate(-paramPageLayout.getLeftMargin(), -paramPageLayout.getTopMargin()); 
    graphicsContext.scale(1.0D / this.d, 1.0D / this.d);
    if (this.e) {
      graphicsContext.setFont((Font)this.a.getValue());
      graphicsContext.setFill((Paint)this.g.getValue());
      String str = "" + paramPrintPreviewPage.b + 1 + " - " + paramPrintPreviewPage.b + 1;
      graphicsContext.fillText(str, getWidth() - FxAbstractDiagramPane.a(str), getHeight() - 3.0D);
    } 
  }
  
  private static final StyleablePropertyFactory f = new StyleablePropertyFactory(Canvas.getClassCssMetaData());
  
  final StyleableProperty a;
  
  private final StyleableProperty g;
  
  private final Text h;
  
  public List getCssMetaData() {
    return f.getCssMetaData();
  }
  
  private double a(String paramString) {
    this.h.setFont((Font)this.a.getValue());
    this.h.setText(paramString);
    this.h.applyCss();
    return this.h.getBoundsInLocal().getWidth();
  }
  
  public void a(double paramDouble) {
    this.d = paramDouble;
  }
}
