package com.wisecoders.dbs.generator.engine.generators;

import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GeneratorTable;

public class PythonGenerator extends Generator {
  public static final String a = "pyhton:";
  
  private final String b;
  
  public PythonGenerator(String paramString, int paramInt) {
    super(paramInt, -1);
    this.b = paramString.substring("python:".length());
  }
  
  public Object generate(GeneratorTable paramGeneratorTable, Column paramColumn) {
    return null;
  }
  
  public String toString() {
    return "Python Generator";
  }
}
