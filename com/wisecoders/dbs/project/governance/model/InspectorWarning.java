package com.wisecoders.dbs.project.governance.model;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class InspectorWarning implements InspectorNode {
  public final InspectorRoot a;
  
  public final AbstractUnit b;
  
  public final List c = new ArrayList();
  
  public InspectorWarning(InspectorRoot paramInspectorRoot, AbstractUnit paramAbstractUnit) {
    this.a = paramInspectorRoot;
    this.b = paramAbstractUnit;
  }
  
  public void a(InspectorDefinition paramInspectorDefinition) {
    this.c.add(paramInspectorDefinition);
  }
  
  public boolean a(AbstractUnit paramAbstractUnit, StringWriter paramStringWriter) {
    return false;
  }
  
  public List g() {
    return null;
  }
  
  public boolean b() {
    return false;
  }
  
  public void d() {}
  
  public boolean e() {
    return false;
  }
  
  public void c() {}
  
  public boolean f() {
    return false;
  }
  
  public boolean i() {
    return false;
  }
  
  public String toString() {
    return this.b.getName() + " " + this.b.getName();
  }
  
  public String a() {
    StringBuilder stringBuilder = new StringBuilder();
    for (InspectorDefinition inspectorDefinition : this.c)
      stringBuilder.append(inspectorDefinition.a).append(" "); 
    return stringBuilder.toString();
  }
  
  public InspectorFolder j() {
    return this.a;
  }
}
