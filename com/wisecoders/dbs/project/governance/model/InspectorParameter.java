package com.wisecoders.dbs.project.governance.model;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import java.io.StringWriter;
import java.util.List;

public class InspectorParameter implements InspectorNode {
  public final String a;
  
  private String b;
  
  public InspectorParameter(String paramString) {
    this.a = paramString;
  }
  
  public boolean a(AbstractUnit paramAbstractUnit, StringWriter paramStringWriter) {
    return true;
  }
  
  public void a(String paramString) {
    this.b = paramString;
  }
  
  public String a() {
    return this.b;
  }
  
  public boolean b() {
    return false;
  }
  
  public void d() {}
  
  public boolean e() {
    return false;
  }
  
  public void c() {}
  
  public String toString() {
    return this.a;
  }
  
  public boolean h() {
    return this.a.startsWith("#");
  }
  
  public boolean k() {
    return this.a.startsWith("is");
  }
  
  public boolean f() {
    return false;
  }
  
  public List g() {
    return null;
  }
  
  public boolean i() {
    return (this.b != null);
  }
  
  public InspectorFolder j() {
    return null;
  }
}
