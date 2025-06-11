package com.wisecoders.dbs.diagram.fx.print;

import java.util.ArrayList;
import java.util.Collection;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Window;

public class NodePrinter {
  private static final double a = 0.75D;
  
  private double b = 1.0D;
  
  private Rectangle c;
  
  public boolean a(PrinterJob paramPrinterJob, boolean paramBoolean, Node paramNode) {
    Window window = (paramNode.getScene() != null) ? paramNode.getScene().getWindow() : null;
    if (!paramBoolean || paramPrinterJob.showPrintDialog(window)) {
      PageLayout pageLayout = paramPrinterJob.getJobSettings().getPageLayout();
      double d1 = pageLayout.getPrintableWidth();
      double d2 = pageLayout.getPrintableHeight();
      NodePrinter$PrintInfo nodePrinter$PrintInfo = a(pageLayout);
      double d3 = this.c.getX();
      double d4 = this.c.getY();
      double d5 = this.c.getWidth();
      double d6 = this.c.getHeight();
      Node node = paramNode.getClip();
      ArrayList arrayList = new ArrayList((Collection<?>)paramNode.getTransforms());
      paramNode.setClip((Node)new Rectangle(d3, d4, d5, d6));
      int i = nodePrinter$PrintInfo.c();
      int j = nodePrinter$PrintInfo.b();
      double d7 = nodePrinter$PrintInfo.a();
      paramNode.getTransforms().add(new Scale(d7, d7));
      paramNode.getTransforms().add(new Translate(-d3, -d4));
      Translate translate = new Translate();
      paramNode.getTransforms().add(translate);
      boolean bool = true;
      for (byte b = 0; b < j; b++) {
        for (byte b1 = 0; b1 < i; b1++) {
          translate.setX(-b1 * d1 / d7);
          translate.setY(-b * d2 / d7);
          bool &= paramPrinterJob.printPage(pageLayout, paramNode);
        } 
      } 
      paramNode.getTransforms().clear();
      paramNode.getTransforms().addAll(arrayList);
      paramNode.setClip(node);
      return bool;
    } 
    return false;
  }
  
  public double a() {
    return this.b;
  }
  
  public void a(double paramDouble) {
    this.b = paramDouble;
  }
  
  public Rectangle b() {
    return this.c;
  }
  
  public void a(Rectangle paramRectangle) {
    this.c = paramRectangle;
  }
  
  public NodePrinter$PrintInfo a(PageLayout paramPageLayout) {
    double d1 = paramPageLayout.getPrintableWidth();
    double d2 = paramPageLayout.getPrintableHeight();
    double d3 = a() * 0.75D;
    Rectangle rectangle = b();
    double d4 = rectangle.getWidth() * d3;
    double d5 = rectangle.getHeight() * d3;
    int i = (int)Math.ceil(d4 / d1);
    int j = (int)Math.ceil(d5 / d2);
    return new NodePrinter$PrintInfo(d3, j, i);
  }
}
