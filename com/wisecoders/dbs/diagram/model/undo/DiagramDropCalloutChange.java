package com.wisecoders.dbs.diagram.model.undo;

import com.wisecoders.dbs.diagram.model.Callout;
import com.wisecoders.dbs.diagram.model.Depictable;
import com.wisecoders.dbs.diagram.model.Diagram;
import com.wisecoders.dbs.diagram.model.Point;

public class DiagramDropCalloutChange extends DiagramChange {
  private Callout b;
  
  public DiagramDropCalloutChange(Callout paramCallout) {
    this.b = paramCallout;
  }
  
  public Depictable a(Diagram paramDiagram) {
    if (this.b != null) {
      this.b.markForDeletion();
      paramDiagram.refresh();
    } 
    return this.b;
  }
  
  public Depictable b(Diagram paramDiagram) {
    this.b = paramDiagram.createCallout(this.b.getComment(), new Point(this.b.getPosition().a(), this.b.getPosition().b()));
    return this.b;
  }
  
  public boolean a() {
    return false;
  }
}
