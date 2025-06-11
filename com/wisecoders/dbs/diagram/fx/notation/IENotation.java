package com.wisecoders.dbs.diagram.fx.notation;

import com.wisecoders.dbs.schema.RelationCardinality;
import java.io.PrintWriter;
import java.util.Locale;
import javafx.scene.canvas.GraphicsContext;

public class IENotation extends Notation {
  public static final String a = "Information Engineering";
  
  public boolean a() {
    return true;
  }
  
  public String toString() {
    return "Information Engineering";
  }
  
  public RelationCardinality a(RelationCardinality paramRelationCardinality, boolean paramBoolean1, boolean paramBoolean2) {
    return paramBoolean1 ? RelationCardinality.a : (paramBoolean2 ? RelationCardinality.d : RelationCardinality.b);
  }
  
  public void a(GraphicsContext paramGraphicsContext, RelationCardinality paramRelationCardinality) {
    switch (IENotation$1.a[paramRelationCardinality.ordinal()]) {
      case 1:
        a(paramGraphicsContext, true);
        break;
      case 2:
        a(paramGraphicsContext, this.e);
        c(paramGraphicsContext);
        break;
      case 3:
        c(paramGraphicsContext);
        a(paramGraphicsContext, this.e);
        f(paramGraphicsContext);
        break;
      case 4:
        c(paramGraphicsContext);
        break;
      case 5:
        g(paramGraphicsContext);
        f(paramGraphicsContext);
        break;
      case 6:
        g(paramGraphicsContext);
        a(paramGraphicsContext, this.e);
        d(paramGraphicsContext);
        break;
    } 
  }
  
  public void a(PrintWriter paramPrintWriter, RelationCardinality paramRelationCardinality) {
    paramPrintWriter.printf(Locale.US, "  <marker id='%s' viewBox='0 0 %.2f %.2f' refX='%.2f' refY='%.2f' markerWidth='%.2f' markerHeight='%.2f' orient='auto-start-reverse'>\n", new Object[] { paramRelationCardinality, Float.valueOf(this.e), Float.valueOf(this.e), Float.valueOf(this.g), Float.valueOf(this.g), Float.valueOf(this.e), Float.valueOf(this.e) });
    if (paramRelationCardinality == RelationCardinality.f)
      paramPrintWriter.printf(Locale.US, "    <path d='M %.3f,%.3f L %.3f,%.3f L %.3f,%.3f z' style='stroke-width:1;' class='filled'/>\n", new Object[] { Float.valueOf(this.g), Float.valueOf(this.k), Float.valueOf(this.e), Float.valueOf(this.g), Float.valueOf(this.g), Float.valueOf(this.e - this.k) }); 
    if (paramRelationCardinality == RelationCardinality.d || paramRelationCardinality == RelationCardinality.c || paramRelationCardinality == RelationCardinality.e || paramRelationCardinality == RelationCardinality.b)
      paramPrintWriter.printf(Locale.US, "    <path d='M %.2f,%.2f L %.2f,%.2f z' style='stroke-width:1;'/>\n", new Object[] { Float.valueOf(0.0F), Float.valueOf(this.g), Float.valueOf(this.e), Float.valueOf(this.g) }); 
    if (paramRelationCardinality == RelationCardinality.a || paramRelationCardinality == RelationCardinality.b)
      paramPrintWriter.printf(Locale.US, "    <circle cx='%.2f' cy='%.2f' r='%.2f' style='stroke-width:1;' />\n", new Object[] { Float.valueOf(this.k), Float.valueOf(this.g), Float.valueOf(this.k) }); 
    if (paramRelationCardinality == RelationCardinality.d || paramRelationCardinality == RelationCardinality.c || paramRelationCardinality == RelationCardinality.b)
      paramPrintWriter.printf(Locale.US, "    <path d='M %.2f,%.2f L %.2f, %.2f z' style='stroke-width:1;'/>\n", new Object[] { Float.valueOf(this.g), Float.valueOf(0.0F), Float.valueOf(this.g), Float.valueOf(this.e) }); 
    if (paramRelationCardinality == RelationCardinality.b);
    if (paramRelationCardinality == RelationCardinality.c || paramRelationCardinality == RelationCardinality.a) {
      paramPrintWriter.printf(Locale.US, "    <path d='M %.2f,%.2f L %.2f,%.2f L %.2f,%.2f' style='stroke-width:1;'/>\n", new Object[] { Float.valueOf(this.e), Float.valueOf(0.0F), Float.valueOf(this.g), Float.valueOf(this.g), Float.valueOf(this.e), Float.valueOf(this.e) });
      paramPrintWriter.printf(Locale.US, "    <path d='M %.2f,%.2f L %.2f,%.2f z' style='stroke-width:1;'/>\n", new Object[] { Float.valueOf(0.0F), Float.valueOf(this.g), Float.valueOf(this.e), Float.valueOf(this.g) });
    } 
    paramPrintWriter.printf("  </marker>\n", new Object[0]);
  }
}
