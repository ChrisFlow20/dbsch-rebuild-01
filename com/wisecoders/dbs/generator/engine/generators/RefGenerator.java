package com.wisecoders.dbs.generator.engine.generators;

import com.wisecoders.dbs.generator.engine.plan.DefinedPattern;
import com.wisecoders.dbs.generator.engine.plan.Domain;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GeneratorTable;

public class RefGenerator extends Generator {
  public static final String a = "ref";
  
  private Generator b;
  
  public RefGenerator(String paramString, int paramInt1, int paramInt2) {
    super(paramInt1, paramInt2);
    parseProperties(paramString);
  }
  
  public void addProperty(String paramString1, String paramString2, int paramInt) {
    if ("name".equalsIgnoreCase(paramString1)) {
      DefinedPattern definedPattern = Domain.b(paramString2);
      this.b = (definedPattern != null) ? Generator.getGenerator(definedPattern.e(), this.e, 0) : null;
    } 
  }
  
  public Object generate(GeneratorTable paramGeneratorTable, Column paramColumn) {
    if (this.b != null)
      return this.b.generate(paramGeneratorTable, paramColumn); 
    return null;
  }
  
  public String toString() {
    return "Ref";
  }
}
