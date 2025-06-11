package com.wisecoders.dbs.diagram.fx.print.printable;

import com.wisecoders.dbs.diagram.fx.FxAbstractDiagramPane;
import com.wisecoders.dbs.diagram.fx.PaintMode;
import com.wisecoders.dbs.diagram.fx.print.PrintPreviewPage;
import javafx.print.PageLayout;
import javafx.scene.canvas.GraphicsContext;

public class PrintableDiagram implements GeneralPrintableNode {
  private final FxAbstractDiagramPane a;
  
  private final String b;
  
  public PrintableDiagram(String paramString, FxAbstractDiagramPane paramFxAbstractDiagramPane) {
    this.a = paramFxAbstractDiagramPane;
    this.b = paramString;
  }
  
  public double a() {
    return this.a.a.getWidth();
  }
  
  public double b() {
    return this.a.a.getHeight();
  }
  
  public void a(GraphicsContext paramGraphicsContext, double paramDouble1, PrintPreviewPage paramPrintPreviewPage, PageLayout paramPageLayout, double paramDouble2) {
    this.a.k.a(paramGraphicsContext, false, PaintMode.a, paramDouble1, paramPrintPreviewPage.b * paramPageLayout
        .getPrintableWidth(), paramPrintPreviewPage.a * (paramPageLayout
        .getPrintableHeight() - paramDouble2), paramPageLayout
        .getPrintableWidth(), paramPageLayout.getPrintableHeight());
  }
  
  public String c() {
    return this.b;
  }
}
