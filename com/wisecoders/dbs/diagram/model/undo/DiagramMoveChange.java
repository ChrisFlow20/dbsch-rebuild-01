package com.wisecoders.dbs.diagram.model.undo;

import com.wisecoders.dbs.diagram.model.Depictable;
import com.wisecoders.dbs.diagram.model.Diagram;

public class DiagramMoveChange extends DiagramChange {
  private final Depictable b;
  
  private final double c;
  
  private final double d;
  
  private final double e;
  
  private final double f;
  
  public DiagramMoveChange(Depictable paramDepictable, double paramDouble1, double paramDouble2) {
    this.b = paramDepictable;
    this.c = paramDepictable.getPosition().a();
    this.d = paramDepictable.getPosition().b();
    this.e = paramDouble1;
    this.f = paramDouble2;
  }
  
  public Depictable a(Diagram paramDiagram) {
    this.b.moveTo(this.e, this.f);
    return this.b;
  }
  
  public Depictable b(Diagram paramDiagram) {
    this.b.moveTo(this.c, this.d);
    return this.b;
  }
  
  public boolean a() {
    return this.b.isMarkedForDeletion();
  }
}
