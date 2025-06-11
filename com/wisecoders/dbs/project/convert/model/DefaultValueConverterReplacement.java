package com.wisecoders.dbs.project.convert.model;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.schema.Column;

public class DefaultValueConverterReplacement extends ConverterReplacement {
  private final DefaultValueConverter b;
  
  private String c;
  
  public DefaultValueConverterReplacement(DefaultValueConverter paramDefaultValueConverter, String paramString) {
    super(paramString);
    this.b = paramDefaultValueConverter;
  }
  
  public void a(AbstractUnit paramAbstractUnit) {
    Column column = (Column)paramAbstractUnit;
    switch (DefaultValueConverterReplacement$1.a[column.getSpec().ordinal()]) {
      case 1:
        column.setDefinition(this.c);
        return;
      case 2:
        column.rename(this.c);
        return;
    } 
    column.setDefaultValue(this.c);
  }
  
  public void b() {
    this.c = this.b.c;
  }
  
  public void a(String paramString) {
    this.c = paramString;
  }
  
  public String c() {
    return this.c;
  }
  
  public boolean a() {
    return true;
  }
  
  public String toString() {
    return this.c;
  }
}
