package com.wisecoders.dbs.project.governance.model;

public abstract class InspectorDefinition {
  public final String a;
  
  public final Class b;
  
  public final String[] c;
  
  public final InspectorCallback d;
  
  public InspectorDefinition(String paramString, Class paramClass, String[] paramArrayOfString, InspectorCallback paramInspectorCallback) {
    this.a = paramString;
    this.b = paramClass;
    this.c = paramArrayOfString;
    this.d = paramInspectorCallback;
  }
  
  public String toString() {
    return this.a;
  }
}
