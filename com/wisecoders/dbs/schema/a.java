package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.diagram.model.AbstractTable;
import java.util.Iterator;

class a implements Iterator {
  private final Iterator b = this.a.schemas.iterator();
  
  private Iterator c;
  
  public boolean hasNext() {
    if (this.c == null || !this.c.hasNext()) {
      boolean bool;
      do {
        bool = false;
        if (!this.b.hasNext())
          continue; 
        this.c = ((Schema)this.b.next()).getEntities().iterator();
        if (this.c.hasNext())
          continue; 
        bool = true;
      } while (bool);
    } 
    return (this.c != null && this.c.hasNext());
  }
  
  public AbstractTable a() {
    return hasNext() ? this.c.next() : null;
  }
  
  public void remove() {
    if (hasNext())
      this.c.remove(); 
  }
  
  public a(Project paramProject) {}
}
