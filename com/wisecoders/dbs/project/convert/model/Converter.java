package com.wisecoders.dbs.project.convert.model;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import javafx.collections.ObservableList;

public abstract class Converter {
  public static final String a = "-Any-";
  
  public final ConversionDictionary b;
  
  private boolean c = false;
  
  private boolean d = false;
  
  public Converter(ConversionDictionary paramConversionDictionary) {
    this.b = paramConversionDictionary;
  }
  
  public abstract ObservableList a();
  
  public abstract boolean a(AbstractUnit paramAbstractUnit);
  
  public void a(String paramString, AbstractUnit paramAbstractUnit) {
    for (ConverterReplacement converterReplacement : a()) {
      if (converterReplacement.a.equals(paramString))
        converterReplacement.a(paramAbstractUnit); 
    } 
  }
  
  public ConverterReplacement a(String paramString) {
    for (ConverterReplacement converterReplacement : a()) {
      if (converterReplacement.a.equals(paramString))
        return converterReplacement; 
    } 
    return null;
  }
  
  public abstract String b();
  
  public void a(boolean paramBoolean) {
    this.c = paramBoolean;
  }
  
  public boolean c() {
    return this.c;
  }
  
  public void b(boolean paramBoolean) {
    this.d = paramBoolean;
  }
  
  public boolean d() {
    return this.d;
  }
}
