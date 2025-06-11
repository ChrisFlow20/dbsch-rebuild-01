package com.wisecoders.dbs.diagram.model;

import java.util.Comparator;

public class ArrangerMixed {
  protected final Diagram a;
  
  protected Group b;
  
  ArrangerMixed(Diagram paramDiagram, ArrangerMode paramArrangerMode) {
    this.a = paramDiagram;
    if (paramArrangerMode == ArrangerMode.KEEP_GROUPS || paramArrangerMode == ArrangerMode.REMOVE_GROUPS || paramDiagram.json) {
      d();
      a();
      if (paramArrangerMode == ArrangerMode.REMOVE_GROUPS)
        for (Group group : paramDiagram.groups)
          group.markForDeletion();  
      paramDiagram.refresh();
    } else {
      a();
    } 
  }
  
  private void d() {
    this.a.refresh();
    f();
    c();
    this.b = e();
    a(this.b);
    b(this.b);
    this.a.colorGroups(true);
    for (Group group : this.a.groups)
      group.orderDepictsAndRenameGroup(); 
  }
  
  protected void a() {
    for (Callout callout : this.a.callouts)
      callout.e(); 
    this.a.refresh();
    Group group = b();
    this.a.refresh();
    for (Group group1 : this.a.groups)
      new a(group1.getDepicts(), (this.b == group1) ? ArrangerSpacing.a : group1.b()); 
    this.a.refresh();
    c();
    new a(this.a.groups, ArrangerSpacing.a);
    for (Callout callout : this.a.callouts)
      callout.f(); 
    group.markForDeletion();
    this.a.refresh();
  }
  
  protected Group b() {
    Group group = this.a.createGroup("GroupFree");
    for (Depict depict : this.a.depicts) {
      if (depict.isGroupFree())
        group.attachDepict(depict); 
    } 
    return group;
  }
  
  private Group e() {
    Group group = null;
    for (Group group1 : this.a.groups) {
      if (group1.a() < 0.1D) {
        if (group == null) {
          group = group1;
          continue;
        } 
        group.mergeWith(group1);
      } 
    } 
    return group;
  }
  
  private void f() {
    for (Group group : this.a.groups)
      group.markForDeletion(); 
    this.a.refresh();
    for (Depict depict : this.a.depicts) {
      Group group = this.a.createGroup(depict.getEntity().getName());
      group.attachDepict(depict);
    } 
    this.a.refresh();
  }
  
  private void b(Group paramGroup) {
    for (Group group : this.a.groups) {
      if (group.size() == 1 && group.a() < 1.0D) {
        if (paramGroup == null) {
          paramGroup = group;
          continue;
        } 
        paramGroup.mergeWith(group);
      } 
    } 
  }
  
  void c() {
    g();
    Group group = null;
    for (Group group1 : this.a.groups) {
      group1.a(group1.getGlobalCost((group != null) ? group.getName() : null));
      group = group1;
    } 
    h();
  }
  
  private void g() {
    this.a.groups.sort((paramGroup1, paramGroup2) -> paramGroup1.getName().compareToIgnoreCase(paramGroup2.getName()));
  }
  
  private void h() {
    this.a.groups.sort(Comparator.comparingDouble(Group::a));
  }
  
  protected void a(Group paramGroup) {
    boolean bool;
    do {
      bool = false;
      for (byte b = 0; b < this.a.groups.size(); b++) {
        Group group = this.a.groups.get(b);
        if (group != paramGroup) {
          Group group1 = null;
          double d = 0.0D;
          for (int i = b + 1; i < this.a.groups.size(); i++) {
            Group group2 = this.a.groups.get(i);
            if (group2 != paramGroup) {
              double d1 = group.getDependencyCost(group2) * a(group, group2);
              if (group.size() + group2.size() > 9)
                d1 = 0.0D; 
              if (d1 > d) {
                group1 = group2;
                d = d1;
              } 
            } 
          } 
          if (group1 != null && d > 0.25D && group.size() + group1.size() < 10) {
            group.mergeWith(group1);
            bool = true;
          } 
        } 
      } 
    } while (bool);
  }
  
  private double a(Group paramGroup1, Group paramGroup2) {
    int i = Math.abs(paramGroup1.size() - paramGroup2.size());
    return (i + 1.0D) / (paramGroup1.size() + paramGroup2.size());
  }
}
