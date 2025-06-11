package com.wisecoders.dbs.diagram.fx.print.printable;

import com.wisecoders.dbs.diagram.fx.print.PrintPreviewPage;
import javafx.print.PageLayout;
import javafx.scene.canvas.GraphicsContext;

public interface GeneralPrintableNode {
  double a();
  
  double b();
  
  void a(GraphicsContext paramGraphicsContext, double paramDouble1, PrintPreviewPage paramPrintPreviewPage, PageLayout paramPageLayout, double paramDouble2);
  
  String c();
}
