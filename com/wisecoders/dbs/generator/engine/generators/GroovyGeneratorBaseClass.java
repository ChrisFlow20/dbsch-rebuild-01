package com.wisecoders.dbs.generator.engine.generators;

import com.wisecoders.dbs.schema.GroovyMethod;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import groovy.lang.Script;

@DoNotObfuscate
public abstract class GroovyGeneratorBaseClass extends Script {
  @GroovyMethod
  public static Object generate(String paramString) {
    return Generator.getGenerator(paramString, 0, -1).generate(null, null);
  }
  
  @GroovyMethod
  public static Object generate(String paramString, int paramInt) {
    return Generator.getGenerator(paramString, paramInt, -1).generate(null, null);
  }
  
  @GroovyMethod
  public static Object generate(String paramString, int paramInt1, int paramInt2) {
    return Generator.getGenerator(paramString, paramInt1, paramInt2).generate(null, null);
  }
}
