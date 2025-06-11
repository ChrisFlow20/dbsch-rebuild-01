package com.wisecoders.dbs.generator.engine.generators;

import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GeneratorTable;

public class SkipGenerator extends Generator {
  public static final String a = "skip";
  
  public SkipGenerator(int paramInt1, int paramInt2) {
    super(paramInt1, paramInt2);
  }
  
  public Boolean a(GeneratorTable paramGeneratorTable, Column paramColumn) {
    return null;
  }
  
  public String toString() {
    return "Skip";
  }
}
