package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.diagram.model.AbstractTable;
import java.util.Iterator;

class b implements Iterator {
  private boolean b = false;
  
  private Iterator c;
  
  public boolean hasNext() {
    if (!this.b) {
      if (this.c == null)
        this.c = this.a.tables.iterator(); 
      if (!this.c.hasNext()) {
        this.b = true;
        this.c = this.a.views.iterator();
      } 
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
  
  b(Schema paramSchema) {}
}
