package com.wisecoders.dbs.diagram.fx.notation;

import com.wisecoders.dbs.schema.RelationCardinality;
import java.io.PrintWriter;
import java.util.Locale;
import javafx.scene.canvas.GraphicsContext;

public class LineNotation extends Notation {
  public static final String a = "ShapeLine";
  
  public RelationCardinality a(RelationCardinality paramRelationCardinality, boolean paramBoolean1, boolean paramBoolean2) {
    return paramBoolean1 ? RelationCardinality.a : RelationCardinality.d;
  }
  
  public void a(GraphicsContext paramGraphicsContext, RelationCardinality paramRelationCardinality) {
    switch (LineNotation$1.a[paramRelationCardinality.ordinal()]) {
      case 1:
        paramGraphicsContext.setEffect(null);
        paramGraphicsContext.beginPath();
        paramGraphicsContext.moveTo(this.e, 0.0D);
        paramGraphicsContext.lineTo(-this.e, 0.0D);
        paramGraphicsContext.moveTo(0.0D, -this.e);
        paramGraphicsContext.lineTo(this.e, 0.0D);
        paramGraphicsContext.lineTo(0.0D, this.e);
        paramGraphicsContext.stroke();
        paramGraphicsContext.closePath();
        break;
      case 2:
      case 3:
      case 4:
      case 5:
        paramGraphicsContext.setEffect(null);
        paramGraphicsContext.beginPath();
        paramGraphicsContext.strokeLine(-this.e, 0.0D, this.e, 0.0D);
        paramGraphicsContext.stroke();
        paramGraphicsContext.closePath();
        break;
    } 
  }
  
  public void a_(GraphicsContext paramGraphicsContext) {
    paramGraphicsContext.beginPath();
    paramGraphicsContext.strokeLine(this.e, 0.0D, this.e, this.c);
    paramGraphicsContext.stroke();
    paramGraphicsContext.closePath();
  }
  
  public void b(GraphicsContext paramGraphicsContext) {
    paramGraphicsContext.beginPath();
    paramGraphicsContext.strokeLine(0.0D, this.e, this.c, this.e);
    paramGraphicsContext.stroke();
    paramGraphicsContext.closePath();
  }
  
  public void a(GraphicsContext paramGraphicsContext, double paramDouble) {
    paramGraphicsContext.setEffect(null);
    paramGraphicsContext.translate(this.e, this.e);
    paramGraphicsContext.rotate(paramDouble);
    paramGraphicsContext.beginPath();
    paramGraphicsContext.moveTo(0.0D, -this.e);
    paramGraphicsContext.quadraticCurveTo((0.07F * this.c), (0.07F * this.c), this.e, 0.0D);
    paramGraphicsContext.stroke();
    paramGraphicsContext.closePath();
    paramGraphicsContext.rotate(-paramDouble);
    paramGraphicsContext.translate(-this.e, -this.e);
  }
  
  public void a(PrintWriter paramPrintWriter, RelationCardinality paramRelationCardinality) {
    paramPrintWriter.printf(Locale.US, "  <marker id='%s' viewBox='0 0 %.2f %.2f' refX='%.2f' refY='%.2f' markerWidth='%.2f' markerHeight='%.2f' orient='auto-start-reverse'>\n", new Object[] { paramRelationCardinality, Float.valueOf(this.c), Float.valueOf(this.e), Float.valueOf(this.e), Float.valueOf(this.g), Float.valueOf(this.c), Float.valueOf(this.e) });
    if (paramRelationCardinality == RelationCardinality.f) {
      paramPrintWriter.printf(Locale.US, "    <path d='M %.3f,%.3f L %.3f,%.3f L %.3f,%.3f L %.3f,%.3f L %.3f,%.3f' style='stroke-width:1;'/>\n", new Object[] { Float.valueOf(this.e), Float.valueOf(this.g), Float.valueOf(this.c), Float.valueOf(this.g), Float.valueOf(this.e), Float.valueOf(0.0F), Float.valueOf(this.c), Float.valueOf(this.g), Float.valueOf(this.e), Float.valueOf(this.e) });
    } else {
      paramPrintWriter.printf(Locale.US, "    <path d='M %.2f,%.2f L %.2f,%.2f z' style='stroke-width:1;'/>\n", new Object[] { Float.valueOf(0.0F), Float.valueOf(this.g), Float.valueOf(this.c), Float.valueOf(this.g) });
    } 
    paramPrintWriter.printf("  </marker>\n", new Object[0]);
  }
  
  public boolean a() {
    return true;
  }
  
  public String toString() {
    return "ShapeLine";
  }
}
