package com.wisecoders.dbs.diagram.model.undo;

import com.wisecoders.dbs.diagram.model.Depictable;
import com.wisecoders.dbs.diagram.model.Diagram;

public abstract class DiagramChange {
  public final long a = System.currentTimeMillis();
  
  public abstract Depictable a(Diagram paramDiagram);
  
  public abstract Depictable b(Diagram paramDiagram);
  
  public abstract boolean a();
}
