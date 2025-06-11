package com.wisecoders.dbs.project.convert.model;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.schema.Table;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OptionsConverterReplacement extends ConverterReplacement {
  private final OptionsConverter b;
  
  private String c;
  
  public OptionsConverterReplacement(OptionsConverter paramOptionsConverter, String paramString) {
    super(paramString);
    this.b = paramOptionsConverter;
  }
  
  public void a(AbstractUnit paramAbstractUnit) {
    String str = this.b.b(paramAbstractUnit);
    Matcher matcher = Pattern.compile(this.b.e()).matcher(str);
    if (matcher.find()) {
      String str1 = matcher.replaceAll(this.c);
      if (paramAbstractUnit instanceof Table) {
        ((Table)paramAbstractUnit).setOptions(str1);
      } else if (paramAbstractUnit instanceof Column) {
        ((Column)paramAbstractUnit).setOptions(str1);
      } else if (paramAbstractUnit instanceof Sequence) {
        ((Sequence)paramAbstractUnit).setOptions(str1);
      } 
    } else if (paramAbstractUnit instanceof Column) {
      Column column = (Column)paramAbstractUnit;
      if (column.getDefinition() != null) {
        Matcher matcher1 = Pattern.compile(this.b.e()).matcher(column.getDefinition());
        if (matcher1.find())
          column.setDefinition(matcher1.replaceAll(this.c)); 
      } 
    } 
  }
  
  public void b() {
    this.c = this.b.e();
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
