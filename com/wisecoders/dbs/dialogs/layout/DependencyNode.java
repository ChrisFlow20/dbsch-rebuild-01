package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DependencyNode {
  public boolean a = false;
  
  public final Unit b;
  
  public DependencyNode c;
  
  public final Entity d;
  
  public final Relation e;
  
  public final List f = new ArrayList();
  
  public DependencyNode(Project paramProject) {
    this.b = paramProject;
    this.d = null;
    this.e = null;
    for (Schema schema : paramProject.schemas) {
      if (a(schema))
        a(new DependencyNode(schema)); 
    } 
  }
  
  DependencyNode(Layout paramLayout) {
    this.b = paramLayout;
    this.d = null;
    this.e = null;
    for (Depict depict : paramLayout.getDepicts())
      a(new DependencyNode(null, depict.getEntity(), true)); 
  }
  
  DependencyNode(Schema paramSchema) {
    this.b = paramSchema;
    this.d = null;
    this.e = null;
    if (a(paramSchema.tables))
      a(new DependencyNode(paramSchema.tables)); 
    if (a(paramSchema.views))
      a(new DependencyNode(paramSchema.views)); 
    if (a(paramSchema.materializedViews))
      a(new DependencyNode(paramSchema.materializedViews)); 
  }
  
  private DependencyNode(Folder paramFolder) {
    this.b = paramFolder;
    this.d = null;
    this.e = null;
    for (Entity entity : paramFolder) {
      if (a(entity))
        a(new DependencyNode(null, entity, true)); 
    } 
  }
  
  public DependencyNode(Relation paramRelation, Entity paramEntity, boolean paramBoolean) {
    this.b = paramEntity;
    this.e = paramRelation;
    this.d = paramEntity;
  }
  
  private void a(DependencyNode paramDependencyNode) {
    this.f.add(paramDependencyNode);
    paramDependencyNode.c = this;
  }
  
  protected void a() {
    if (!this.a && this.d != null) {
      for (Relation relation : this.d.getRelations()) {
        if (relation != this.e) {
          Entity entity = relation.getTargetEntity();
          a(new DependencyNode(relation, entity, false));
        } 
      } 
      for (Relation relation : this.d.getImportedRelations()) {
        if (relation != this.e) {
          Entity entity = relation.getEntity();
          a(new DependencyNode(relation, entity, false));
        } 
      } 
      this.a = true;
    } 
  }
  
  String b() {
    StringBuilder stringBuilder = new StringBuilder();
    boolean bool = true;
    if (this.e != null) {
      stringBuilder.append(this.e).append(" (");
      for (Iterator<Attribute> iterator1 = this.e.getSourceAttributes().iterator(), iterator2 = this.e.getTargetAttributes().iterator(); iterator1.hasNext() && iterator2.hasNext(); ) {
        Attribute attribute1 = iterator1.next();
        Attribute attribute2 = iterator2.next();
        if (!bool)
          stringBuilder.append(", "); 
        bool = false;
        if (attribute1.getName().equalsIgnoreCase(attribute2.getName())) {
          stringBuilder.append(attribute1);
          continue;
        } 
        stringBuilder.append(attribute1).append("=").append(attribute2);
      } 
      stringBuilder.append(")");
    } 
    return stringBuilder.toString();
  }
  
  private static boolean a(TreeUnit paramTreeUnit) {
    if (paramTreeUnit instanceof Entity) {
      Entity entity = (Entity)paramTreeUnit;
      return ((entity.getImportedRelations() != null && entity.getImportedRelations().size() > 0) || (entity
        .getRelations() != null && entity.getRelations().size() > 0));
    } 
    for (byte b = 0; b < paramTreeUnit.getChildrenCount(); b++) {
      TreeUnit treeUnit = paramTreeUnit.getChildAt(b);
      if (a(treeUnit))
        return true; 
    } 
    return false;
  }
  
  public boolean c() {
    return (this.a && this.f.isEmpty());
  }
}
