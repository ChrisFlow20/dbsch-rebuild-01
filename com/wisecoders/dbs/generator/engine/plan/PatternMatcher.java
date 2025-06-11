package com.wisecoders.dbs.generator.engine.plan;

import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.DataTypeUtil;
import java.util.regex.Pattern;

public class PatternMatcher {
  public final DefinedPattern a;
  
  public final String b;
  
  public final String c;
  
  public final String d;
  
  public final String e;
  
  public final int f;
  
  private final Pattern g;
  
  private final Pattern h;
  
  public PatternMatcher(DefinedPattern paramDefinedPattern, String paramString1, String paramString2, int paramInt, String paramString3, String paramString4) {
    this.a = paramDefinedPattern;
    this.f = paramInt;
    this.b = paramString1;
    this.c = paramString2;
    this.d = paramString3;
    this.e = paramString4;
    this.g = (paramString1 != null) ? Pattern.compile(paramString1, 2) : null;
    this.h = Pattern.compile(paramString2, 2);
  }
  
  public int a(Column paramColumn) {
    if (a(this.d, paramColumn) && (this.g == null || this.g
      .matcher(paramColumn.getEntity().getName()).matches()) && this.h
      .matcher(paramColumn.getName()).matches() && (this.e == null || this.e
      .contains((paramColumn.getEntity().getSchema()).project.getDbId())) && (paramColumn
      .getLength() < 0 || this.a.d() <= paramColumn.getLength()))
      return this.f; 
    return -1;
  }
  
  private boolean a(String paramString, Column paramColumn) {
    if (paramString == null)
      return true; 
    for (String str : paramString.split(",")) {
      if (str.equalsIgnoreCase(paramColumn.getDataType().getName()))
        return true; 
      Integer integer = DataTypeUtil.getTypeByName(paramString);
      if (integer != null && integer.intValue() == paramColumn.getDataType().getJavaType())
        return true; 
    } 
    return false;
  }
  
  public String toString() {
    return "Match '" + this.c + "' to " + String.valueOf(this.a);
  }
}
