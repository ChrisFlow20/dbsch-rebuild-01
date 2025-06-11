package com.wisecoders.dbs.generator.engine.generators;

import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GeneratorTable;

public class BooleanGenerator extends Generator {
  public static final String a = "boolean";
  
  public static String b = "boolean:percent_true=0.5";
  
  private double c = 0.5D;
  
  public BooleanGenerator(String paramString, int paramInt1, int paramInt2) {
    super(paramInt1, paramInt2);
    parseProperties(paramString);
  }
  
  public void addProperty(String paramString1, String paramString2, int paramInt) {
    if ("percent_true".equalsIgnoreCase(paramString1))
      this.c = Double.parseDouble(paramString2); 
  }
  
  public Boolean a(GeneratorTable paramGeneratorTable, Column paramColumn) {
    return Boolean.valueOf((this.f.nextDouble() < this.c));
  }
  
  public String toString() {
    return "Boolean Generator";
  }
}
