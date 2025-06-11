package com.wisecoders.dbs.diagram.fx.notation;

import com.wisecoders.dbs.schema.RelationCardinality;
import java.io.PrintWriter;
import java.util.Locale;
import javafx.scene.canvas.GraphicsContext;

public class BarkerNotation extends Notation {
  public static final String a = "Barker";
  
  public RelationCardinality a(RelationCardinality paramRelationCardinality, boolean paramBoolean1, boolean paramBoolean2) {
    return paramBoolean1 ? RelationCardinality.a : RelationCardinality.d;
  }
  
  public void a(GraphicsContext paramGraphicsContext, RelationCardinality paramRelationCardinality) {
    switch (BarkerNotation$1.a[paramRelationCardinality.ordinal()]) {
      case 1:
        a(paramGraphicsContext, true);
        break;
      case 2:
        c(paramGraphicsContext);
        break;
      case 3:
      case 4:
        c(paramGraphicsContext);
        f(paramGraphicsContext);
        break;
      case 5:
        e(paramGraphicsContext);
        break;
      case 6:
        e(paramGraphicsContext);
        f(paramGraphicsContext);
        break;
    } 
  }
  
  public void a(PrintWriter paramPrintWriter, RelationCardinality paramRelationCardinality) {
    paramPrintWriter.printf(Locale.US, "  <marker id='%s' viewBox='0 0 %.2f %.2f' refX='%.2f' refY='%.2f' markerWidth='%.2f' markerHeight='%.2f' orient='auto-start-reverse'>\n", new Object[] { paramRelationCardinality, Float.valueOf(this.e), Float.valueOf(this.e), Float.valueOf(this.g), Float.valueOf(this.g), Float.valueOf(this.e), Float.valueOf(this.e) });
    if (paramRelationCardinality == RelationCardinality.f)
      paramPrintWriter.printf(Locale.US, "    <path d='M %.3f,%.3f L %.3f,%.3f L %.3f,%.3f L %.3f,%.3f L %.3f,%.3f' style='stroke-width:1;'/>\n", new Object[] { Float.valueOf(this.g), Float.valueOf(this.g), Float.valueOf(this.e), Float.valueOf(this.g), Float.valueOf(this.g), Float.valueOf(0.0F), Float.valueOf(this.e), Float.valueOf(this.g), Float.valueOf(this.g), Float.valueOf(this.e) }); 
    if (paramRelationCardinality == RelationCardinality.d || paramRelationCardinality == RelationCardinality.c || paramRelationCardinality == RelationCardinality.e)
      paramPrintWriter.printf(Locale.US, "    <path d='M %.2f,%.2f L %.2f,%.2f z' style='stroke-width:1;'/>\n", new Object[] { Float.valueOf(this.g), Float.valueOf(this.g), Float.valueOf(this.e), Float.valueOf(this.g) }); 
    if (paramRelationCardinality == RelationCardinality.b || paramRelationCardinality == RelationCardinality.a)
      paramPrintWriter.printf(Locale.US, "    <path d='M %.2f,%.2f L %.2f,%.2f M %.2f,%.2f L %.2f,%.2f z' style='stroke-width:1;'/>\n", new Object[] { Float.valueOf(this.k), Float.valueOf(this.g), Float.valueOf(2.0F * this.k), Float.valueOf(this.g), Float.valueOf(3.0F * this.k), Float.valueOf(this.g), Float.valueOf(4.0F * this.k), Float.valueOf(this.g) }); 
    if (paramRelationCardinality == RelationCardinality.c || paramRelationCardinality == RelationCardinality.a) {
      paramPrintWriter.printf(Locale.US, "    <path d='M %.2f,%.2f L %.2f,%.2f L %.2f,%.2f' style='stroke-width:1;'/>\n", new Object[] { Float.valueOf(this.e), Float.valueOf(0.0F), Float.valueOf(this.g), Float.valueOf(this.g), Float.valueOf(this.e), Float.valueOf(this.e) });
      paramPrintWriter.printf(Locale.US, "    <path d='M %.2f,%.2f L %.2f,%.2f z' style='stroke-width:1;'/>\n", new Object[] { Float.valueOf(0.0F), Float.valueOf(this.g), Float.valueOf(this.e), Float.valueOf(this.g) });
    } 
    paramPrintWriter.printf("  </marker>\n", new Object[0]);
  }
  
  public boolean a() {
    return true;
  }
  
  public String toString() {
    return "Barker";
  }
}
