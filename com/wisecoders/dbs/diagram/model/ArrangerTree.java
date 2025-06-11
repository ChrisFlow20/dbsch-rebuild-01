package com.wisecoders.dbs.diagram.model;

public class ArrangerTree extends ArrangerMixed {
  ArrangerTree(Diagram paramDiagram, ArrangerMode paramArrangerMode) {
    super(paramDiagram, paramArrangerMode);
  }
  
  protected void a(Group paramGroup) {
    boolean bool;
    do {
      bool = false;
      for (byte b = 0; b < this.a.groups.size(); b++) {
        Group group = this.a.groups.get(b);
        if (group != paramGroup)
          for (int i = b + 1; i < this.a.groups.size(); i++) {
            Group group1 = this.a.groups.get(i);
            if (group1 != paramGroup) {
              double d = group.getDependencyCost(group1);
              if (d > 0.0D) {
                group.mergeWith(group1);
                bool = true;
              } 
            } 
          }  
      } 
    } while (bool);
  }
  
  protected void a() {
    for (Callout callout : this.a.callouts)
      callout.e(); 
    this.a.refresh();
    Group group = b();
    this.a.refresh();
    for (Group group1 : this.a.groups) {
      if (this.b == group1) {
        new a(group1.getDepicts(), ArrangerSpacing.a);
        continue;
      } 
      new ArrangerDepictTree(group1.getDepicts(), group1.b());
    } 
    this.a.refresh();
    c();
    new a(this.a.groups, ArrangerSpacing.a);
    for (Callout callout : this.a.callouts)
      callout.f(); 
    group.markForDeletion();
    this.a.refresh();
  }
}
