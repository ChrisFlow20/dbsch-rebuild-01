package com.wisecoders.dbs.diagram.fx;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.sys.fx.FxUtil;
import java.util.Iterator;
import java.util.TreeMap;

class b {
  private static final int b = 5;
  
  private final TreeMap c = new TreeMap<>();
  
  private final boolean d;
  
  public b(FxLayoutPane paramFxLayoutPane, boolean paramBoolean) {
    this.d = paramBoolean;
  }
  
  public b a(Folder paramFolder) {
    for (AbstractUnit abstractUnit : paramFolder) {
      int i = FxUtil.a(abstractUnit);
      if (i > -1 || (this.d && this.c.size() < 5)) {
        while (this.c.containsKey(Integer.valueOf(i)))
          i++; 
        this.c.put(Integer.valueOf(i), abstractUnit);
      } 
    } 
    return this;
  }
  
  public void a() {
    for (Iterator<Integer> iterator = this.c.keySet().iterator(); iterator.hasNext(); ) {
      int i = ((Integer)iterator.next()).intValue();
      this.a.b((Unit)this.c.get(Integer.valueOf(i)));
    } 
    if (!this.a.f.getTabs().isEmpty())
      this.a.f.getSelectionModel().select(0); 
  }
}
