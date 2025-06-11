package com.wisecoders.dbs.generator.engine.generators;

import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GeneratorTable;
import java.text.ParseException;

public class SequenceGenerator extends Generator {
  public static final String a = "sequence:";
  
  public static String b = "sequence:from=0;step=1;";
  
  private long c = 0L, d = 1L;
  
  private long g = Long.MIN_VALUE;
  
  public SequenceGenerator(String paramString, int paramInt) {
    super(paramInt, -1);
    parseProperties(paramString);
  }
  
  public void addProperty(String paramString1, String paramString2, int paramInt) {
    if ("from".equalsIgnoreCase(paramString1)) {
      this.c = Long.parseLong(paramString2);
    } else if ("step".equalsIgnoreCase(paramString1)) {
      this.d = Long.parseLong(paramString2);
    } else {
      throw new ParseException("Unknown property " + paramString1, paramInt);
    } 
  }
  
  public Number a(GeneratorTable paramGeneratorTable, Column paramColumn) {
    if (this.g == Long.MIN_VALUE) {
      this.g = this.c;
      return Long.valueOf(this.g);
    } 
    this.g += this.d;
    if (this.g > -2147483648L && this.g < 2147483647L)
      return Integer.valueOf((int)this.g); 
    return Long.valueOf(this.g);
  }
  
  public String toString() {
    return "Sequence Generator";
  }
}
