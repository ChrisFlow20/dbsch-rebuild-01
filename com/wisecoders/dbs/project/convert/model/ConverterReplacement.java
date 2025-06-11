package com.wisecoders.dbs.project.convert.model;

import com.wisecoders.dbs.diagram.model.AbstractUnit;

public abstract class ConverterReplacement {
  public final String a;
  
  public ConverterReplacement(String paramString) {
    this.a = paramString;
  }
  
  public abstract void a(AbstractUnit paramAbstractUnit);
  
  public abstract boolean a();
}
