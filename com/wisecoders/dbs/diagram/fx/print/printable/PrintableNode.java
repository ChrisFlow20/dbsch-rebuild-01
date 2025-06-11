package com.wisecoders.dbs.diagram.fx.print.printable;

import com.wisecoders.dbs.diagram.fx.print.PrintPreviewPage;
import javafx.geometry.Rectangle2D;
import javafx.print.PageLayout;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class PrintableNode implements GeneralPrintableNode {
  private final Node a;
  
  private final String b;
  
  public PrintableNode(String paramString, Node paramNode) {
    this.a = paramNode;
    this.b = paramString;
  }
  
  public double a() {
    return this.a.getBoundsInParent().getWidth();
  }
  
  public double b() {
    return this.a.getBoundsInParent().getHeight();
  }
  
  public void a(GraphicsContext paramGraphicsContext, double paramDouble1, PrintPreviewPage paramPrintPreviewPage, PageLayout paramPageLayout, double paramDouble2) {
    WritableImage writableImage = new WritableImage((int)paramPageLayout.getPrintableWidth(), (int)paramPageLayout.getPrintableHeight());
    SnapshotParameters snapshotParameters = new SnapshotParameters();
    snapshotParameters.setViewport(new Rectangle2D(paramPrintPreviewPage.b * paramPageLayout
          .getPrintableWidth(), paramPrintPreviewPage.a * (paramPageLayout
          .getPrintableHeight() - paramDouble2), paramPageLayout
          .getPrintableWidth(), paramPageLayout
          .getPrintableHeight()));
    this.a.snapshot(snapshotParameters, writableImage);
    paramGraphicsContext.drawImage((Image)writableImage, 0.0D, 0.0D);
  }
  
  public String c() {
    return this.b;
  }
}
