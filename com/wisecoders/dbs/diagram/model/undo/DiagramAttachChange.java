package com.wisecoders.dbs.diagram.model.undo;

import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Depictable;
import com.wisecoders.dbs.diagram.model.Diagram;
import com.wisecoders.dbs.diagram.model.Entity;

public class DiagramAttachChange extends DiagramChange {
  private final Entity b;
  
  private final double c;
  
  private final double d;
  
  public DiagramAttachChange(Entity paramEntity, double paramDouble1, double paramDouble2) {
    this.b = paramEntity;
    this.c = paramDouble1;
    this.d = paramDouble2;
  }
  
  public Depictable a(Diagram paramDiagram) {
    return paramDiagram.attach(this.b, this.c, this.d);
  }
  
  public Depictable b(Diagram paramDiagram) {
    Depict depict = paramDiagram.getDepictFor(this.b);
    if (depict != null)
      paramDiagram.detach(depict); 
    return depict;
  }
  
  public boolean a() {
    return (this.b != null && this.b.isMarkedForDeletion());
  }
}
