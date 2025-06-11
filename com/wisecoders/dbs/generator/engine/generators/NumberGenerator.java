package com.wisecoders.dbs.generator.engine.generators;

import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GeneratorTable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

public class NumberGenerator extends Generator {
  public static final String a = "long:";
  
  public static final String b = "int:";
  
  public static final String c = "short:";
  
  public static final String d = "double:";
  
  public static final String g = "float:";
  
  private double h = 0.0D;
  
  private double i = 1.0E8D;
  
  private final int j;
  
  private DecimalFormat k;
  
  public NumberGenerator(String paramString, int paramInt1, int paramInt2, int paramInt3) {
    super(paramInt2, paramInt3);
    this.j = paramInt1;
    parseProperties(paramString);
  }
  
  public void addProperty(String paramString1, String paramString2, int paramInt) {
    if ("from".equalsIgnoreCase(paramString1)) {
      this.h = Double.parseDouble(paramString2);
    } else if ("to".equalsIgnoreCase(paramString1)) {
      this.i = Double.parseDouble(paramString2);
    } else if ("format".equalsIgnoreCase(paramString1)) {
      this.k = new DecimalFormat(paramString2, DecimalFormatSymbols.getInstance(Locale.ENGLISH));
      try {
        Double.parseDouble(this.k.format(123.1212D));
      } catch (NumberFormatException numberFormatException) {
        throw new ParseException("Wrong format '" + paramString2 + "'. " + String.valueOf(numberFormatException), paramInt + "format=".length());
      } 
    } else {
      throw new ParseException("Unknown property " + paramString1, paramInt);
    } 
  }
  
  public Number a(GeneratorTable paramGeneratorTable, Column paramColumn) {
    double d1 = this.i - this.h;
    double d2 = this.f.nextDouble();
    double d3 = this.h + d2 * d1;
    if (this.k != null)
      d3 = Double.parseDouble(this.k.format(d3)); 
    switch (this.j) {
      case 4:
        return Integer.valueOf((int)d3);
      case -5:
        return Long.valueOf((long)d3);
      case 6:
      case 7:
        return Float.valueOf((float)d3);
      case 5:
        return Short.valueOf((short)(int)d3);
    } 
    return Double.valueOf(d3);
  }
}
