package com.wisecoders.dbs.dialogs.git.fx;

import java.util.HashMap;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revplot.AbstractPlotRenderer;
import org.eclipse.jgit.revplot.PlotCommit;
import org.eclipse.jgit.revplot.PlotLane;

public class PlotRenderer extends AbstractPlotRenderer {
  private static final double b = 1.5D;
  
  private static final double c = 1.5D;
  
  public static final Color[] a = new Color[] { Color.web("#1557bf"), 
      Color.web("#15bf70"), 
      Color.web("#bfb215"), 
      Color.web("#bf15a3"), 
      Color.web("#bf7015"), 
      Color.web("#15a8bf") };
  
  private final HashMap d = new HashMap<>();
  
  private GraphicsContext e;
  
  private PlotCommit f;
  
  private Canvas g;
  
  protected int drawLabel(int paramInt1, int paramInt2, Ref paramRef) {
    return 0;
  }
  
  protected void a(Color paramColor, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    paramInt1 = (int)(paramInt1 * 1.5D);
    paramInt3 = (int)(paramInt3 * 1.5D);
    this.e.setStroke((Paint)paramColor);
    this.e.setLineWidth(1.5D);
    if (paramInt1 == paramInt3 || paramInt2 == paramInt4) {
      this.e.strokeLine(paramInt1, paramInt2, paramInt3, paramInt4);
    } else {
      this.e.beginPath();
      this.e.moveTo(paramInt1, paramInt2);
      this.e.quadraticCurveTo(paramInt3, paramInt2, paramInt3, paramInt4);
      this.e.stroke();
      this.e.closePath();
    } 
  }
  
  protected void drawCommitDot(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    Color color = a(this.f.getLane());
    int i = paramInt1 + paramInt3 / 2;
    this.e.setFill((Paint)new LinearGradient(0.0D, 0.0D, 1.0D, 0.0D, true, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.TRANSPARENT), new Stop(1.0D, color) }));
    this.e.setGlobalAlpha(0.25D);
    this.e.fillRect(i, 0.0D, this.g.getWidth() - i, this.g.getHeight());
    this.e.setGlobalAlpha(1.0D);
    this.e.setFill((Paint)color);
    this.e.setStroke((Paint)color);
    if (this.f.getParentCount() > 1) {
      paramInt1 = (int)(paramInt1 * 1.5D);
      this.e.fillOval(paramInt1, paramInt2, (paramInt3 + 1), (paramInt4 + 1));
      this.e.strokeOval(paramInt1, paramInt2, (paramInt3 + 2), (paramInt4 + 2));
    } else {
      int j = i;
      int k = paramInt2 + paramInt4 / 2;
      byte b = 7;
      j = (int)(j * 1.5D);
      this.e.clearRect((j - b), (k - b), (2 * b + 1), (2 * b + 1));
      this.e.setGlobalBlendMode(BlendMode.SRC_OVER);
      this.e.strokeOval((j - b), (k - b), (2 * b + 1), (2 * b + 1));
    } 
  }
  
  protected void drawBoundaryDot(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    paramInt1 = (int)(paramInt1 * 1.5D);
    this.e.setFill((Paint)Color.LIGHTGRAY);
    this.e.fillOval(paramInt1, paramInt2, (paramInt3 + 1), (paramInt4 + 1));
    this.e.strokeOval(paramInt1, paramInt2, (paramInt3 + 2), (paramInt4 + 2));
  }
  
  protected Color a(PlotLane paramPlotLane) {
    if (this.d.get(paramPlotLane) == null)
      this.d.put(paramPlotLane, a[(1 + this.d.size()) % a.length]); 
    return (Color)this.d.get(paramPlotLane);
  }
  
  protected void drawText(String paramString, int paramInt1, int paramInt2) {}
  
  public void a(PlotCommit paramPlotCommit, Canvas paramCanvas) {
    this.e = paramCanvas.getGraphicsContext2D();
    this.f = paramPlotCommit;
    this.g = paramCanvas;
    this.e.clearRect(0.0D, 0.0D, paramCanvas.getWidth(), paramCanvas.getHeight());
    if (paramPlotCommit == null) {
      this.e.setStroke((Paint)Color.web("#676767"));
      this.e.setLineDashes(new double[] { 4.0D, 4.0D });
      this.e.strokeOval(5.0D, 5.0D, 14.0D, 14.0D);
      this.e.strokeLine(12.0D, 19.0D, 12.0D, 24.0D);
      this.e.setLineDashes(new double[] { 0.0D });
    } else {
      paintCommit(paramPlotCommit, (int)paramCanvas.getHeight());
    } 
  }
}
