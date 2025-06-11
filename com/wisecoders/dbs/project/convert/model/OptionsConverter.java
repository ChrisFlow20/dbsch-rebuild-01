package com.wisecoders.dbs.project.convert.model;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.StringUtil;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OptionsConverter extends Converter {
  private String d;
  
  public final ObservableList c = FXCollections.observableArrayList();
  
  public OptionsConverter(ConversionDictionary paramConversionDictionary) {
    super(paramConversionDictionary);
  }
  
  public void b(String paramString) {
    if (StringUtil.isFilledTrim(paramString))
      this.d = paramString; 
  }
  
  public ObservableList a() {
    return this.c;
  }
  
  public OptionsConverterReplacement c(String paramString) {
    OptionsConverterReplacement optionsConverterReplacement = new OptionsConverterReplacement(this, paramString);
    this.c.add(optionsConverterReplacement);
    return optionsConverterReplacement;
  }
  
  public boolean d(String paramString) {
    for (OptionsConverterReplacement optionsConverterReplacement : this.c) {
      if (paramString.equals(optionsConverterReplacement.a))
        return true; 
    } 
    return false;
  }
  
  public boolean a(AbstractUnit paramAbstractUnit) {
    String str = b(paramAbstractUnit);
    if (this.d != null) {
      if (StringUtil.isFilledTrim(str) && Pattern.compile(this.d).matcher(str).find())
        return true; 
      if (paramAbstractUnit instanceof Column) {
        Column column = (Column)paramAbstractUnit;
        if (column.getDefinition() != null)
          return Pattern.compile(this.d).matcher(column.getDefinition()).find(); 
        if (column.getIdentity() != null)
          return Pattern.compile(this.d).matcher(column.getIdentity()).find(); 
      } 
    } 
    return false;
  }
  
  public String b() {
    return "Default Option";
  }
  
  public String toString() {
    return this.d;
  }
  
  public String e() {
    return this.d;
  }
  
  public String b(AbstractUnit paramAbstractUnit) {
    return (paramAbstractUnit instanceof Table) ? ((Table)paramAbstractUnit).getOptions() : ((paramAbstractUnit instanceof Column) ? ((Column)paramAbstractUnit).getOptions() : ((paramAbstractUnit instanceof Sequence) ? ((Sequence)paramAbstractUnit).getOptions() : null));
  }
}
