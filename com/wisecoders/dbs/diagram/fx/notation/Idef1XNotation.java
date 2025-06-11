package com.wisecoders.dbs.diagram.fx.notation;

import com.wisecoders.dbs.schema.RelationCardinality;
import java.io.PrintWriter;
import java.util.Locale;
import javafx.scene.canvas.GraphicsContext;

public class Idef1XNotation extends Notation {
  public static final String a = "Idef1x";
  
  public RelationCardinality a(RelationCardinality paramRelationCardinality, boolean paramBoolean1, boolean paramBoolean2) {
    return paramBoolean1 ? RelationCardinality.a : (paramBoolean2 ? RelationCardinality.e : RelationCardinality.d);
  }
  
  public boolean a() {
    return false;
  }
  
  public void a(GraphicsContext paramGraphicsContext, RelationCardinality paramRelationCardinality) {
    switch (Idef1XNotation$1.a[paramRelationCardinality.ordinal()]) {
      case 1:
        a(paramGraphicsContext, true);
        break;
      case 2:
        c(paramGraphicsContext);
        b(paramGraphicsContext, true);
        e(paramGraphicsContext, false);
        break;
      case 3:
        c(paramGraphicsContext);
        b(paramGraphicsContext, true);
        a(paramGraphicsContext);
        break;
      case 4:
        c(paramGraphicsContext);
        b(paramGraphicsContext, true);
        break;
      case 5:
        c(paramGraphicsContext);
        b(paramGraphicsContext, true);
        d(paramGraphicsContext, false);
        break;
      case 6:
        c(paramGraphicsContext);
        break;
    } 
  }
  
  public void a(PrintWriter paramPrintWriter, RelationCardinality paramRelationCardinality) {
    paramPrintWriter.printf(Locale.US, "  <marker id='%s' viewBox='0 0 %.2f %.2f' refX='%.2f' refY='%.2f' markerWidth='%.2f' markerHeight='%.2f' orient='auto-start-reverse'>\n", new Object[] { paramRelationCardinality, Float.valueOf(this.e), Float.valueOf(this.e), Float.valueOf(this.g), Float.valueOf(this.g), Float.valueOf(this.e), Float.valueOf(this.e) });
    paramPrintWriter.printf(Locale.US, "    <path d='M %.2f,%.2f L %.2f,%.2f z' style='stroke-width:1;'/>\n", new Object[] { Float.valueOf(0.0F), Float.valueOf(this.g), Float.valueOf(this.e), Float.valueOf(this.g) });
    if (paramRelationCardinality != RelationCardinality.f && paramRelationCardinality != RelationCardinality.e)
      paramPrintWriter.printf(Locale.US, "    <circle cx='%.2f' cy='%.2f' r='%.2f' style='stroke-width:1;' class='filled' />\n", new Object[] { Float.valueOf(this.g + this.k), Float.valueOf(this.g), Float.valueOf(this.o) }); 
    if (paramRelationCardinality == RelationCardinality.b)
      paramPrintWriter.printf(Locale.US, "<text x='%.2f' y='%.2f' style='font-size:6px;' class='filled'>Z</text>", new Object[] { Float.valueOf(this.o), Float.valueOf(this.e) }); 
    if (paramRelationCardinality == RelationCardinality.d)
      paramPrintWriter.printf(Locale.US, "<text x='%.2f' y='%.2f' style='font-size:6px;' class='filled'>1</text>", new Object[] { Float.valueOf(this.o), Float.valueOf(this.e) }); 
    if (paramRelationCardinality == RelationCardinality.c)
      paramPrintWriter.printf(Locale.US, "<text x='%.2f' y='%.2f' style='font-size:6px;' class='filled'>P</text>", new Object[] { Float.valueOf(this.o), Float.valueOf(this.e) }); 
    paramPrintWriter.printf("  </marker>\n", new Object[0]);
  }
  
  public String toString() {
    return "Idef1x";
  }
  
  private void d(GraphicsContext paramGraphicsContext, boolean paramBoolean) {
    float f1 = this.c * 0.6F, f2 = this.c * 0.8F;
    float f3 = -this.c * 0.1F, f4 = -this.c * 0.3F, f5 = this.c * 0.2F;
    boolean bool = paramBoolean ? true : true;
    paramGraphicsContext.beginPath();
    paramGraphicsContext.moveTo((bool * (f1 - this.e)), (bool * (f3 - this.e)));
    paramGraphicsContext.lineTo((bool * (f2 - this.e)), (bool * (f4 - this.e)));
    paramGraphicsContext.lineTo((bool * (f2 - this.e)), (bool * (f5 - this.e)));
    paramGraphicsContext.stroke();
    paramGraphicsContext.closePath();
  }
  
  private void e(GraphicsContext paramGraphicsContext, boolean paramBoolean) {
    boolean bool = paramBoolean ? true : true;
    float f1 = this.c * 0.6F, f2 = this.c * 0.95F;
    float f3 = -this.c * 0.3F, f4 = this.c * 0.2F;
    paramGraphicsContext.beginPath();
    paramGraphicsContext.moveTo((bool * (f1 - this.e)), (bool * (f3 - this.e)));
    paramGraphicsContext.lineTo((bool * (f2 - this.e)), (bool * (f3 - this.e)));
    paramGraphicsContext.lineTo((bool * (f1 - this.e)), (bool * (f4 - this.e)));
    paramGraphicsContext.lineTo((bool * (f2 - this.e)), (bool * (f4 - this.e)));
    paramGraphicsContext.stroke();
    paramGraphicsContext.closePath();
  }
  
  public void a(GraphicsContext paramGraphicsContext) {
    float f1 = this.c * 0.7F, f2 = this.c * 1.0F;
    float f3 = -this.c * 0.3F, f4 = -this.c * 0.05F, f5 = this.c * 0.0F, f6 = this.c * 0.3F;
    paramGraphicsContext.beginPath();
    paramGraphicsContext.moveTo((f1 - this.e), (f6 - this.e));
    paramGraphicsContext.lineTo((f1 - this.e), (f3 - this.e));
    paramGraphicsContext.quadraticCurveTo((f2 - this.e), (f3 - this.e), (f2 - this.e), (f4 - this.e));
    paramGraphicsContext.quadraticCurveTo((f2 - this.e), (f5 - this.e), (f1 - this.e), (f5 - this.e));
    paramGraphicsContext.stroke();
    paramGraphicsContext.closePath();
  }
}
