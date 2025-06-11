package com.wisecoders.dbs.diagram.model.undo;

import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Depictable;
import com.wisecoders.dbs.diagram.model.Diagram;
import com.wisecoders.dbs.diagram.model.Group;

public class DiagramDetachChange extends DiagramChange {
  private final Depict b;
  
  private final double c;
  
  private final double d;
  
  private Group e;
  
  public DiagramDetachChange(Depict paramDepict) {
    this.b = paramDepict;
    this.c = paramDepict.getPosition().a();
    this.d = paramDepict.getPosition().b();
  }
  
  public Depict c(Diagram paramDiagram) {
    this.e = null;
    for (Group group : paramDiagram.groups) {
      if (group.getDepicts().contains(this.b))
        this.e = group; 
    } 
    paramDiagram.detach(this.b);
    return this.b;
  }
  
  public Depict d(Diagram paramDiagram) {
    Depict depict = paramDiagram.attach(this.b.entity, this.c, this.d);
    if (this.e != null)
      this.e.attachDepict(depict); 
    return depict;
  }
  
  public boolean a() {
    return this.b.entity.isMarkedForDeletion();
  }
}
