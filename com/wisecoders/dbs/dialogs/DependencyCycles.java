package com.wisecoders.dbs.dialogs;

import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.dialogs.layout.DependencyNode;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import java.util.ArrayList;
import java.util.List;

public class DependencyCycles {
  private final List a = new ArrayList();
  
  public void a(Unit paramUnit) {
    ArrayList arrayList = new ArrayList();
    this.a.clear();
    a(paramUnit, arrayList);
  }
  
  private void a(Unit paramUnit, List<?> paramList) {
    if (paramUnit instanceof Layout) {
      Layout layout = (Layout)paramUnit;
      for (Entity entity : layout.getEntities())
        a(entity, paramList); 
    } else if (paramUnit instanceof Project) {
      Project project = (Project)paramUnit;
      for (Schema schema : project.schemas)
        a(schema, paramList); 
    } else if (paramUnit instanceof Schema) {
      Schema schema = (Schema)paramUnit;
      for (Entity entity : schema.getEntities())
        a(entity, paramList); 
    } else if (paramUnit instanceof AbstractTable) {
      AbstractTable abstractTable = (AbstractTable)paramUnit;
      for (ForeignKey foreignKey : abstractTable.getRelations()) {
        boolean bool = false;
        for (ForeignKey foreignKey1 : paramList) {
          if (foreignKey1.getEntity() == foreignKey.getTargetEntity()) {
            bool = true;
            break;
          } 
        } 
        if (bool) {
          this.a.add(new ArrayList(paramList));
          continue;
        } 
        paramList.add(foreignKey);
        a(foreignKey.getTargetEntity(), paramList);
        paramList.remove(foreignKey);
      } 
    } 
  }
  
  public boolean a(DependencyNode paramDependencyNode) {
    if (paramDependencyNode.e != null)
      for (List list : this.a) {
        if (list.contains(paramDependencyNode.e))
          return true; 
      }  
    return false;
  }
}
