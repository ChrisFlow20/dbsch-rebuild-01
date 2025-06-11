package com.wisecoders.dbs.project.governance.model;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.UniqueArrayList;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class InspectorFolder implements InspectorNode {
  private final InspectorFolder c;
  
  public final InspectorFolder$Operation a;
  
  public final ArrayList b = new ArrayList();
  
  private boolean d = false;
  
  public InspectorFolder(InspectorFolder paramInspectorFolder, InspectorFolder$Operation paramInspectorFolder$Operation) {
    this.c = paramInspectorFolder;
    this.a = paramInspectorFolder$Operation;
  }
  
  public InspectorRoot a() {
    return (this.c != null) ? this.c.a() : (InspectorRoot)this;
  }
  
  public boolean a(AbstractUnit paramAbstractUnit, StringWriter paramStringWriter) {
    boolean bool;
    switch (InspectorFolder$1.a[this.a.ordinal()]) {
      case 6:
        for (InspectorNode inspectorNode : this.b) {
          if (!inspectorNode.a(paramAbstractUnit, paramStringWriter))
            return false; 
        } 
      case 7:
        bool = false;
        for (InspectorNode inspectorNode : this.b) {
          boolean bool1 = inspectorNode.a(paramAbstractUnit, paramStringWriter);
          if (inspectorNode instanceof Inspector && ((Inspector)inspectorNode).h() && !bool1)
            break; 
          if (bool1)
            bool = true; 
        } 
        return bool;
      case 8:
        bool = true;
        if (paramAbstractUnit instanceof Project) {
          Project project = (Project)paramAbstractUnit;
          for (Schema schema : project.schemas) {
            if (b(schema, paramStringWriter))
              bool = false; 
          } 
        } 
        return bool;
      case 1:
        bool = true;
        if (paramAbstractUnit instanceof Schema) {
          Schema schema = (Schema)paramAbstractUnit;
          for (Table table : schema.tables) {
            if (b(table, paramStringWriter))
              bool = false; 
          } 
        } 
        return bool;
      case 2:
        bool = true;
        if (paramAbstractUnit instanceof Table) {
          Table table = (Table)paramAbstractUnit;
          for (Column column : table.columns) {
            if (b(column, paramStringWriter))
              bool = false; 
          } 
        } 
        return bool;
      case 4:
        bool = true;
        if (paramAbstractUnit instanceof Table) {
          Table table = (Table)paramAbstractUnit;
          for (ForeignKey foreignKey : table.foreignKeys) {
            if (b(foreignKey, paramStringWriter))
              bool = false; 
          } 
        } 
        return bool;
      case 3:
        bool = true;
        if (paramAbstractUnit instanceof Table) {
          Table table = (Table)paramAbstractUnit;
          for (Index index : table.indexes) {
            if (b(index, paramStringWriter))
              bool = false; 
          } 
        } 
        return bool;
    } 
    return true;
  }
  
  private boolean b(AbstractUnit paramAbstractUnit, StringWriter paramStringWriter) {
    boolean bool1 = true;
    boolean bool2 = false;
    for (InspectorNode inspectorNode : this.b) {
      boolean bool = inspectorNode.a(paramAbstractUnit, paramStringWriter);
      if (inspectorNode instanceof Inspector) {
        Inspector inspector = (Inspector)inspectorNode;
        bool2 = true;
        if (inspector.h() && !bool)
          break; 
      } 
      if (!bool)
        bool1 = false; 
    } 
    if (bool2 && paramStringWriter != null)
      paramStringWriter.append("\n"); 
    return !bool1;
  }
  
  public Inspector a(FilterDefinition paramFilterDefinition, boolean paramBoolean) {
    Inspector inspector = new Inspector(this, paramFilterDefinition);
    inspector.a(paramBoolean);
    this.b.add(inspector);
    return inspector;
  }
  
  public Inspector a(FieldDefinition paramFieldDefinition) {
    Inspector inspector = new Inspector(this, paramFieldDefinition);
    this.b.add(inspector);
    return inspector;
  }
  
  public InspectorFolder a(InspectorFolder$Operation paramInspectorFolder$Operation) {
    InspectorFolder inspectorFolder = new InspectorFolder(this, paramInspectorFolder$Operation);
    this.b.add(inspectorFolder);
    return inspectorFolder;
  }
  
  public boolean a(InspectorNode paramInspectorNode) {
    int i = this.b.indexOf(paramInspectorNode);
    if (i < 1 || i > this.b.size())
      return false; 
    InspectorNode inspectorNode = this.b.remove(i);
    this.b.add(i - 1, inspectorNode);
    return true;
  }
  
  public boolean b(InspectorNode paramInspectorNode) {
    int i = this.b.indexOf(paramInspectorNode);
    if (i < 0 || i > this.b.size() - 1)
      return false; 
    InspectorNode inspectorNode = this.b.remove(i);
    this.b.add(i + 1, inspectorNode);
    return true;
  }
  
  public boolean b() {
    return (this.a != null);
  }
  
  public String toString() {
    return a(a().m(), this.a);
  }
  
  public static String a(String paramString, InspectorFolder$Operation paramInspectorFolder$Operation) {
    if (paramString == null)
      paramString = "MySql"; 
    switch (InspectorFolder$1.a[paramInspectorFolder$Operation.ordinal()]) {
      default:
        throw new IncompatibleClassChangeError();
      case 8:
      
      case 1:
      
      case 2:
      
      case 3:
      
      case 4:
      
      case 5:
      
      case 6:
      
      case 7:
        break;
    } 
    return 






      
      "Group - all should fail";
  }
  
  public void c() {
    this.b.removeIf(InspectorNode::e);
    for (InspectorNode inspectorNode : this.b)
      inspectorNode.c(); 
  }
  
  public void d() {
    this.d = true;
  }
  
  public boolean e() {
    return this.d;
  }
  
  public boolean f() {
    return this.b.isEmpty();
  }
  
  public List g() {
    return this.b;
  }
  
  public boolean i() {
    for (InspectorNode inspectorNode : this.b) {
      if (!inspectorNode.i())
        return false; 
    } 
    return true;
  }
  
  public InspectorFolder j() {
    return this.c;
  }
  
  public List h() {
    UniqueArrayList uniqueArrayList = new UniqueArrayList();
    if (this.a == InspectorFolder$Operation.h || this.a == InspectorFolder$Operation.g) {
      if (j() != null)
        uniqueArrayList.addAll(j().h()); 
    } else if (this.a == InspectorFolder$Operation.a) {
      uniqueArrayList.add(InspectorFolder$Operation.b);
    } else if (this.a == InspectorFolder$Operation.b) {
      uniqueArrayList.add(InspectorFolder$Operation.c);
      uniqueArrayList.add(InspectorFolder$Operation.d);
      uniqueArrayList.add(InspectorFolder$Operation.e);
      uniqueArrayList.add(InspectorFolder$Operation.f);
    } 
    return uniqueArrayList;
  }
  
  public List k() {
    UniqueArrayList uniqueArrayList = new UniqueArrayList();
    for (FilterDefinition filterDefinition : InspectorRepository.a) {
      if (filterDefinition.b.isAssignableFrom(this.a.i))
        uniqueArrayList.add(filterDefinition); 
    } 
    return uniqueArrayList;
  }
  
  public List l() {
    UniqueArrayList uniqueArrayList = new UniqueArrayList();
    for (FieldDefinition fieldDefinition : InspectorRepository.b) {
      if (fieldDefinition.b.isAssignableFrom(this.a.i))
        uniqueArrayList.add(fieldDefinition); 
    } 
    return uniqueArrayList;
  }
}
