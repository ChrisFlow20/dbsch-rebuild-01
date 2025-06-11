package com.wisecoders.dbs.generator.engine.generators;

import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GeneratorTable;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import java.util.Map;

public class GroovyGenerator extends Generator {
  public static final String a = "groovy:";
  
  private final String b;
  
  private final Binding c;
  
  private Script d;
  
  public GroovyGenerator(String paramString, int paramInt) {
    super(paramInt, -1);
    this.c = new Binding();
    this.b = paramString.substring("groovy:".length());
  }
  
  private void a() {
    if (this.d == null) {
      this.d = (new GroovyShell(new GroovyGenerator$GroovyGeneratorCompilerConf())).parse(this.b);
      this.d.setBinding(this.c);
    } 
  }
  
  public Object generate(GeneratorTable paramGeneratorTable, Column paramColumn) {
    a();
    return this.d.run();
  }
  
  public Object a(Map paramMap) {
    for (Column column : paramMap.keySet())
      this.c.setVariable(column.getName(), paramMap.get(column)); 
    a();
    return this.d.run();
  }
  
  public String toString() {
    return "Groovy Generator";
  }
}
