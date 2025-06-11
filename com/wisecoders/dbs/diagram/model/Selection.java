package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.sys.UniqueCopyOnWriteArrayList;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class Selection {
  public final List a = new UniqueCopyOnWriteArrayList();
  
  public final List b = new UniqueCopyOnWriteArrayList();
  
  public final List c = new UniqueCopyOnWriteArrayList();
  
  public final List d = new UniqueCopyOnWriteArrayList();
  
  public final List e = new UniqueCopyOnWriteArrayList();
  
  private final List f;
  
  public void a() {
    if (this.d.isEmpty())
      for (Entity entity : this.b) {
        if (entity.getRelations() != null)
          for (Relation relation : entity.getRelations())
            this.d.add(relation);  
      }  
  }
  
  public Selection b() {
    this.a.clear();
    this.b.clear();
    this.c.clear();
    this.d.clear();
    this.e.clear();
    e();
    return this;
  }
  
  public Selection a(Unit paramUnit) {
    this.a.remove(paramUnit);
    this.b.remove(paramUnit);
    this.c.remove(paramUnit);
    this.d.remove(paramUnit);
    this.e.remove(paramUnit);
    e();
    return this;
  }
  
  public Selection b(Unit paramUnit) {
    this.a.remove(paramUnit);
    this.a.add(paramUnit);
    if (paramUnit instanceof Entity) {
      Entity entity = (Entity)paramUnit;
      this.b.remove(entity);
      this.b.add(entity);
    } else if (paramUnit instanceof Callout) {
      this.c.add((Callout)paramUnit);
    } else if (paramUnit instanceof Relation) {
      this.d.add((Relation)paramUnit);
    } else if (paramUnit instanceof Attribute) {
      this.e.add((Attribute)paramUnit);
    } else if (paramUnit instanceof com.wisecoders.dbs.schema.LayoutDepict) {
      this.b.add(paramUnit.getEntity());
      this.a.add(paramUnit.getEntity());
    } 
    e();
    return this;
  }
  
  public Selection a(List paramList) {
    for (Unit unit : paramList)
      b(unit); 
    e();
    return this;
  }
  
  public Unit c() {
    return (this.a.size() == 1) ? this.a.get(0) : null;
  }
  
  public Entity d() {
    return (this.b.size() == 1) ? this.b.get(0) : null;
  }
  
  public String toString() {
    return SyncUtil.b(this.a);
  }
  
  public Selection() {
    this.f = new ArrayList();
  }
  
  public Selection(List paramList) {
    this.f = new ArrayList();
    this.a.addAll(paramList);
    for (Unit unit : paramList) {
      if (unit instanceof Entity) {
        this.b.add((Entity)unit);
        continue;
      } 
      if (unit instanceof Attribute) {
        Attribute attribute = (Attribute)unit;
        this.e.add(attribute);
        this.b.add(attribute.getEntity());
        continue;
      } 
      if (unit instanceof Relation) {
        Relation relation = (Relation)unit;
        this.d.add(relation);
        this.b.add(relation.getTargetEntity());
        this.b.add(relation.getEntity());
      } 
    } 
  }
  
  public void a(EventHandler paramEventHandler) {
    this.f.add(paramEventHandler);
  }
  
  private void e() {
    for (EventHandler eventHandler : this.f)
      eventHandler.handle((Event)new ActionEvent(this, null)); 
  }
}
