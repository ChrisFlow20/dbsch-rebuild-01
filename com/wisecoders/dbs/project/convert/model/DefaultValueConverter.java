package com.wisecoders.dbs.project.convert.model;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.sys.StringUtil;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DefaultValueConverter extends Converter {
  public String c;
  
  public DataType d;
  
  public boolean e = false;
  
  public final ObservableList f = FXCollections.observableArrayList();
  
  public DefaultValueConverter(ConversionDictionary paramConversionDictionary) {
    super(paramConversionDictionary);
  }
  
  public void b(String paramString) {
    this.c = paramString;
  }
  
  public void a(DataType paramDataType) {
    this.d = paramDataType;
  }
  
  public ObservableList a() {
    return this.f;
  }
  
  public DefaultValueConverterReplacement c(String paramString) {
    DefaultValueConverterReplacement defaultValueConverterReplacement = new DefaultValueConverterReplacement(this, paramString);
    this.f.add(defaultValueConverterReplacement);
    return defaultValueConverterReplacement;
  }
  
  public boolean d(String paramString) {
    for (DefaultValueConverterReplacement defaultValueConverterReplacement : this.f) {
      if (paramString.equals(defaultValueConverterReplacement.a))
        return true; 
    } 
    return false;
  }
  
  public boolean a(AbstractUnit paramAbstractUnit) {
    if (paramAbstractUnit instanceof Column) {
      Column column = (Column)paramAbstractUnit;
      String str = a(column);
      if (this.c != null && str != null && (column
        
        .getSpec() != AttributeSpec.normal || ((this.d == null || this.d == column
        .getDataType()) && StringUtil.isFilled(this.c)))) {
        if (this.e)
          return Pattern.compile(this.c).matcher(str).matches(); 
        return this.c.equalsIgnoreCase(str);
      } 
    } 
    return false;
  }
  
  public static String a(Column paramColumn) {
    switch (DefaultValueConverter$1.a[paramColumn.getSpec().ordinal()]) {
      case 1:
        return paramColumn.getDefinition();
      case 2:
        return paramColumn.getName();
    } 
    return paramColumn.getDefaultValue();
  }
  
  public String b() {
    return "Default Value";
  }
  
  public String toString() {
    return ((this.d != null) ? (String.valueOf(this.d) + " ") : "") + ((this.d != null) ? (String.valueOf(this.d) + " ") : "") + (this.e ? "regExp:" : "");
  }
  
  public void c(boolean paramBoolean) {
    this.e = paramBoolean;
  }
  
  public boolean e() {
    return this.e;
  }
}
